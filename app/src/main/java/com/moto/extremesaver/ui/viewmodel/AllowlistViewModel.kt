package com.moto.extremesaver.ui.viewmodel

import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moto.extremesaver.data.entity.AllowedAppEntity
import com.moto.extremesaver.domain.usecase.UpdateAllowlistUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

data class InstalledAppInfo(
    val packageName: String,
    val appName: String,
    val isAllowed: Boolean,
    val isSystemApp: Boolean
)

data class AllowlistUiState(
    val apps: List<InstalledAppInfo> = emptyList(),
    val isLoading: Boolean = true
)

@HiltViewModel
class AllowlistViewModel @Inject constructor(
    private val updateAllowlistUseCase: UpdateAllowlistUseCase,
    @ApplicationContext private val context: Context
) : ViewModel() {

    private val _uiState = MutableStateFlow(AllowlistUiState())
    val uiState: StateFlow<AllowlistUiState> = _uiState.asStateFlow()

    init {
        loadApps()
    }

    private fun loadApps() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            // ⚡ Bolt Optimization: Move expensive PackageManager IPC calls off the main thread.
            // Also cache the app labels to avoid redundant getApplicationLabel calls inside the flow collector.
            val cachedAppInfo = withContext(Dispatchers.IO) {
                val pm = context.packageManager
                pm.getInstalledApplications(PackageManager.GET_META_DATA)
                    .filter { it.flags and ApplicationInfo.FLAG_SYSTEM == 0 } // Non-system only
                    .map { appInfo ->
                        Pair(appInfo.packageName, pm.getApplicationLabel(appInfo).toString())
                    }
                    .sortedBy { it.second }
            }

            updateAllowlistUseCase.observeAllowedApps().collect { allowedEntities ->
                val allowedSet = allowedEntities.map { it.packageName }.toSet()

                val appInfoList = cachedAppInfo.map { (packageName, appName) ->
                    InstalledAppInfo(
                        packageName = packageName,
                        appName = appName,
                        isAllowed = allowedSet.contains(packageName),
                        isSystemApp = false
                    )
                }

                _uiState.update { AllowlistUiState(apps = appInfoList, isLoading = false) }
            }
        }
    }

    fun toggleApp(app: InstalledAppInfo) {
        viewModelScope.launch {
            if (app.isAllowed) {
                updateAllowlistUseCase.removeApp(app.packageName)
            } else {
                updateAllowlistUseCase.addApp(
                    packageName = app.packageName,
                    appName = app.appName,
                    isSystem = app.isSystemApp
                )
            }
        }
    }
}
