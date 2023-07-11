package com.techyourchance.androidviews.general.navigation

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.annotation.UiThread
import androidx.fragment.app.Fragment
import com.ncapdevi.fragnav.FragNavController
import com.techyourchance.androidviews.general.home.HomeFragment
import com.techyourchance.androidviews.demonstrations._01_basicshapes.BasicShapesFragment
import com.techyourchance.androidviews.exercises._01_.MySliderFragment
import com.techyourchance.androidviews.demonstrations._02_positioning.PositioningFragment
import com.techyourchance.androidviews.demonstrations._03_basic_touch.BasicTouchFragment
import com.techyourchance.androidviews.demonstrations._04_drag.DragFragment
import com.techyourchance.androidviews.demonstrations._05_state_preservation.StatePreservationFragment
import com.techyourchance.androidviews.solutions._03_.SolutionExercise3Fragment


@UiThread
class ScreensNavigator constructor(
    private val activity: Activity,
    private val fragNavController: FragNavController,
    private val screenNameDelegate: ScreenNameDelegate,
    private val toolbarBackButtonDelegate: ToolbarBackButtonDelegate,
) {

    interface Listener {
        fun onScreenChanged()
    }

    fun init(savedInstanceState: Bundle?) {
        fragNavController.rootFragments = listOf(getRootFragment())
        fragNavController.initialize(FragNavController.TAB1, savedInstanceState)
    }

    private fun getRootFragment(): Fragment {
        return DummyRootFragment()
    }

    fun onSaveInstanceState(saveInstanceState: Bundle) {
        fragNavController.onSaveInstanceState(saveInstanceState)
    }

    fun toScreen(screenSpec: ScreenSpec) {
        val nextFragment = when(screenSpec) {
            is ScreenSpec.Home -> HomeFragment.newInstance()
            is ScreenSpec.BasicShapes -> BasicShapesFragment.newInstance()
            is ScreenSpec.Exercise1 -> MySliderFragment.newInstance()
            is ScreenSpec.Positioning -> PositioningFragment.newInstance()
            is ScreenSpec.BasicTouch -> BasicTouchFragment.newInstance()
            is ScreenSpec.Drag -> DragFragment.newInstance()
            is ScreenSpec.SolutionExercise3 -> SolutionExercise3Fragment.newInstance()
            is ScreenSpec.StatePreservation -> StatePreservationFragment.newInstance()
        }
        toFragment(nextFragment)
        screenNameDelegate.clearScreenName()
    }

    fun navigateBack() {
        if (fragNavController.isRootFragment) {
            val homeIntent = Intent(Intent.ACTION_MAIN)
            homeIntent.addCategory(Intent.CATEGORY_HOME)
            homeIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            activity.startActivity(homeIntent)
        } else {
            fragNavController.popFragment()
            if (fragNavController.isRootFragment) {
                toolbarBackButtonDelegate.hideBackButton()
            }
        }
    }

    private fun toFragment(fragment: Fragment) {
        if (shouldClearFragmentsStack(fragment)) {
            fragNavController.clearStack()
            fragNavController.replaceFragment(fragment)
            toolbarBackButtonDelegate.hideBackButton()
        } else {
            fragNavController.pushFragment(fragment)
            toolbarBackButtonDelegate.showBackButton()
        }
    }

    private fun shouldClearFragmentsStack(nextFragment: Fragment): Boolean {
        val currentFragment = fragNavController.currentFrag ?: return false
        return currentFragment is DummyRootFragment
                || nextFragment is HomeFragment
    }

}