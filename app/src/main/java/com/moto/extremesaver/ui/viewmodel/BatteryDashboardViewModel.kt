package com.moto.extremesaver.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moto.extremesaver.domain.usecase.BatteryInfo
import com.moto.extremesaver.domain.usecase.FetchBatteryStatsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class BatteryDashboardUiState(
    val currentInfo: BatteryInfo = BatteryInfo(100, false, 50, 0f),
    val isLoading: Boolean = true
)

@HiltViewModel
class BatteryDashboardViewModel @Inject constructor(
    private val fetchBatteryStatsUseCase: FetchBatteryStatsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(BatteryDashboardUiState())
    val uiState: StateFlow<BatteryDashboardUiState> = _uiState.asStateFlow()

    init {
        observeBatteryData()
    }

    private fun observeBatteryData() {
        viewModelScope.launch {
            fetchBatteryStatsUseCase.observeLatest()
                .filterNotNull()
                .collect {
                    val updatedInfo = fetchBatteryStatsUseCase.calculateBatteryInfo()
                    _uiState.update { state ->
                        state.copy(currentInfo = updatedInfo, isLoading = false)
                    }
                }
        }
    }
}
