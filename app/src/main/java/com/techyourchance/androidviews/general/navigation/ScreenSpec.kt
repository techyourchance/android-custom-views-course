package com.techyourchance.androidviews.general.navigation

import java.io.Serializable

@Suppress("ClassName")
sealed class ScreenSpec(): Serializable {
    object Home: ScreenSpec()
    object BasicShapes: ScreenSpec()
    object Exercise1: ScreenSpec()

    companion object {
        /**
         * Intent extra key for Serialized ScreenSpec's
         */
        const val INTENT_EXTRA_SCREEN_SPEC = "INTENT_EXTRA_SCREEN_SPEC"
    }

}