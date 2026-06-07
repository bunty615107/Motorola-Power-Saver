package com.moto.extremesaver.domain.usecase

import com.moto.extremesaver.data.entity.ModeStateEntity
import com.moto.extremesaver.data.entity.RestrictionStateEntity
import com.moto.extremesaver.data.entity.UsageEventEntity
import com.moto.extremesaver.domain.repository.ModeRepository
import com.moto.extremesaver.domain.repository.RestrictionRepository
import com.moto.extremesaver.domain.repository.UsageEventRepository
import com.moto.extremesaver.mode.Mode
import com.moto.extremesaver.mode.ModeEvent
import com.moto.extremesaver.mode.ModeStateMachine
import javax.inject.Inject

class DisableExtremeModeUseCase @Inject constructor(
    private val modeRepository: ModeRepository,
    private val restrictionRepository: RestrictionRepository,
    private val usageEventRepository: UsageEventRepository
) {
    companion object {
        private val ALL_FEATURES = listOf("wifi", "bluetooth", "location", "sync", "animations")
    }

    suspend operator fun invoke(): Result<Mode> = runCatching {
        val currentEntity = modeRepository.getModeState()
        val currentMode = currentEntity?.currentMode?.let { Mode.valueOf(it) } ?: Mode.STANDARD

        val transitioning = ModeStateMachine.transition(currentMode, ModeEvent.DeactivateRequested)
        check(transitioning != currentMode) { "Cannot deactivate from state: $currentMode" }

        // Clear all restrictions
        ALL_FEATURES.forEach { feature ->
            restrictionRepository.setRestriction(
                RestrictionStateEntity(featureName = feature, isRestricted = false)
            )
        }

        // Complete transition
        val finalMode = ModeStateMachine.transition(transitioning, ModeEvent.DeactivationComplete)
        modeRepository.saveModeState(
            ModeStateEntity(
                currentMode = finalMode.name,
                deactivatedAt = System.currentTimeMillis(),
                activationCount = currentEntity?.activationCount ?: 0
            )
        )

        usageEventRepository.logEvent(
            UsageEventEntity(eventType = "MODE_DEACTIVATED", details = "Extreme Saver disabled")
        )

        finalMode
    }
}
