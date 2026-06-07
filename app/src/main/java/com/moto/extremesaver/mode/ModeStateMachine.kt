package com.moto.extremesaver.mode

/**
 * Deterministic state machine for the Extreme Saver Mode.
 * States are explicit and transitions are validated.
 * Pure function — zero side effects.
 */
enum class Mode {
    STANDARD,
    TRANSITIONING,
    EXTREME_SAVER
}

sealed class ModeEvent {
    data object ActivateRequested : ModeEvent()
    data object ActivationComplete : ModeEvent()
    data object DeactivateRequested : ModeEvent()
    data object DeactivationComplete : ModeEvent()
    data object ActivationFailed : ModeEvent()
}

object ModeStateMachine {
    fun transition(current: Mode, event: ModeEvent): Mode = when (current) {
        Mode.STANDARD -> when (event) {
            is ModeEvent.ActivateRequested -> Mode.TRANSITIONING
            else -> current
        }
        Mode.TRANSITIONING -> when (event) {
            is ModeEvent.ActivationComplete -> Mode.EXTREME_SAVER
            is ModeEvent.ActivationFailed -> Mode.STANDARD
            is ModeEvent.DeactivationComplete -> Mode.STANDARD
            else -> current
        }
        Mode.EXTREME_SAVER -> when (event) {
            is ModeEvent.DeactivateRequested -> Mode.TRANSITIONING
            else -> current
        }
    }
}
