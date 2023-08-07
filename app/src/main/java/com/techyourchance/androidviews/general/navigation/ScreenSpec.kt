package com.techyourchance.androidviews.general.navigation

import java.io.Serializable

@Suppress("ClassName")
sealed class ScreenSpec(): Serializable {
    object Home: ScreenSpec()
    object BasicShapes: ScreenSpec()
    object Exercise1: ScreenSpec()
    object Positioning: ScreenSpec()
    object BasicTouch: ScreenSpec()
    object Drag: ScreenSpec()
    object SolutionExercise3: ScreenSpec()
    object StatePreservation: ScreenSpec()
    object Animations: ScreenSpec()
    object PathShape: ScreenSpec()
    object PathAnimation: ScreenSpec()
    object Exercise5: ScreenSpec()
    object Text: ScreenSpec()
    object PathArc: ScreenSpec()
    object Exercise7: ScreenSpec()
    object OnMeasure: ScreenSpec()
    object Exercise8: ScreenSpec()
    object MatrixTransformation: ScreenSpec()

    companion object {

        /**
         * Intent extra key for Serialized ScreenSpec's
         */
        const val INTENT_EXTRA_SCREEN_SPEC = "INTENT_EXTRA_SCREEN_SPEC"
    }

}