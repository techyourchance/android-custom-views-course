package com.techyourchance.androidviews.general

import androidx.fragment.app.Fragment
import com.techyourchance.androidviews.general.navigation.ScreenNameDelegate

abstract class BaseFragment: Fragment() {

    open val screenName = ""

    private val screenNameDelegate get() = requireActivity() as ScreenNameDelegate

    protected val screensNavigator get() = (requireActivity() as MainActivity).screensNavigator

    override fun onStart() {
        super.onStart()
        screenNameDelegate.showScreenName(screenName)
    }
}