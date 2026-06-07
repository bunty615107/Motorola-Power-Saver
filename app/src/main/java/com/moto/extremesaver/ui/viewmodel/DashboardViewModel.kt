package com.moto.extremesaver.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moto.extremesaver.domain.repository.ModeRepository
import com.moto.extremesaver.domain.usecase.*
import com.moto.extremesaver.mode.Mode
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class DashboardUiState(
    val mode: Mode = Mode.STANDARD,
    val batteryLevel: Int = 100,
    val estimatedHours: Int = 50,
    val drainRatePerHour: Float = 2f,
    val isCharging: Boolean = false,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val enableExtremeModeUseCase: EnableExtremeModeUseCase,
    private val disableExtremeModeUseCase: DisableExtremeModeUseCase,
    private val fetchBatteryStatsUseCase: FetchBatteryStatsUseCase,
    private val modeRepository: ModeRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(DashboardUiState())
    val uiState: StateFlow<DashboardUiState> = _uiState.asStateFlow()

    init {
        observeModeState()
        observeBatteryStats()
    }

    private fun observeModeState() {
        viewModelScope.launch {
            modeRepository.observeModeState().collect { entity ->
                val mode = entity?.currentMode?.let {
                    try { Mode.valueOf(it) } catch (_: Exception) { Mode.STANDARD }
                } ?: Mode.STANDARD
                _uiState.update { it.copy(mode = mode) }
            }
        }
    }

    private fun observeBatteryStats() {
        viewModelScope.launch {
            fetchBatteryStatsUseCase.observeLatest().collect { stat ->
                if (stat != null) {
                    val info = fetchBatteryStatsUseCase.calculateBatteryInfo()
                    _uiState.update {
                        it.copy(
                            batteryLevel = info.level,
                            estimatedHours = info.estimatedHoursRemaining,
                            drainRatePerHour = info.drainRatePerHour,
                            isCharging = info.isCharging
                        )
                    }
                }
            }
        }
    }

    fun enableExtremeMode() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }
            val result = enableExtremeModeUseCase()
            result.fold(
                onSuccess = { mode ->
                    _uiState.update { it.copy(mode = mode, isLoading = false) }
                },
                onFailure = { error ->
                    _uiState.update { it.copy(isLoading = false, errorMessage = error.message) }
                }
            )
        }
    }

    fun disableExtremeMode() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }
            val result = disableExtremeModeUseCase()
            result.fold(
                onSuccess = { mode ->
                    _uiState.update { it.copy(mode = mode, isLoading = false) }
                },
                onFailure = { error ->
                    _uiState.update { it.copy(isLoading = false, errorMessage = error.message) }
                }
            )
        }
    }
}
