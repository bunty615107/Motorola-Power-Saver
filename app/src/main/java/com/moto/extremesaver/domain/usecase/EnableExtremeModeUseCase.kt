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

class EnableExtremeModeUseCase @Inject constructor(
    private val modeRepository: ModeRepository,
    private val restrictionRepository: RestrictionRepository,
    private val usageEventRepository: UsageEventRepository
) {
    companion object {
        private val DEFAULT_RESTRICTIONS = listOf("wifi", "bluetooth", "location", "sync", "animations")
    }

    suspend operator fun invoke(): Result<Mode> = runCatching {
        val currentEntity = modeRepository.getModeState()
        val currentMode = currentEntity?.currentMode?.let { Mode.valueOf(it) } ?: Mode.STANDARD
        val activationCount = (currentEntity?.activationCount ?: 0) + 1
        val now = System.currentTimeMillis()

        // Validate transition
        val transitioning = ModeStateMachine.transition(currentMode, ModeEvent.ActivateRequested)
        check(transitioning != currentMode) { "Cannot activate from state: $currentMode" }

        // Persist transitioning state
        modeRepository.saveModeState(
            ModeStateEntity(currentMode = transitioning.name, activatedAt = now, activationCount = activationCount)
        )

        // Apply default restrictions
        DEFAULT_RESTRICTIONS.forEach { feature ->
            restrictionRepository.setRestriction(
                RestrictionStateEntity(featureName = feature, isRestricted = true)
            )
        }

        // Complete transition
        val finalMode = ModeStateMachine.transition(transitioning, ModeEvent.ActivationComplete)
        modeRepository.saveModeState(
            ModeStateEntity(currentMode = finalMode.name, activatedAt = now, activationCount = activationCount)
        )

        // Log event
        usageEventRepository.logEvent(
            UsageEventEntity(eventType = "MODE_ACTIVATED", details = "Extreme Saver enabled")
        )

        finalMode
    }
}
