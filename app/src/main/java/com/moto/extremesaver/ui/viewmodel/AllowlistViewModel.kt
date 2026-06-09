package com.moto.extremesaver.ui.viewmodel

import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import android.annotation.SuppressLint
import com.moto.extremesaver.data.entity.AllowedAppEntity
import com.moto.extremesaver.domain.usecase.UpdateAllowlistUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
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

@SuppressLint("StaticFieldLeak")
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

            val pm = context.packageManager
            val installedApps = pm.getInstalledApplications(PackageManager.GET_META_DATA)
                .filter { it.flags and ApplicationInfo.FLAG_SYSTEM == 0 } // Non-system only
                .sortedBy { pm.getApplicationLabel(it).toString() }

            updateAllowlistUseCase.observeAllowedApps().collect { allowedEntities ->
                val allowedSet = allowedEntities.map { it.packageName }.toSet()

                val appInfoList = installedApps.map { appInfo ->
                    InstalledAppInfo(
                        packageName = appInfo.packageName,
                        appName = pm.getApplicationLabel(appInfo).toString(),
                        isAllowed = allowedSet.contains(appInfo.packageName),
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
