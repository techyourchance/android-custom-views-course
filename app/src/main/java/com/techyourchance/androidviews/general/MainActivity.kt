package com.techyourchance.androidviews.general

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.ncapdevi.fragnav.FragNavController
import com.techyourchance.androidviews.R
import com.techyourchance.androidviews.general.navigation.ScreenNameDelegate
import com.techyourchance.androidviews.general.navigation.ScreenSpec
import com.techyourchance.androidviews.general.navigation.ScreensNavigator
import com.techyourchance.androidviews.general.navigation.ToolbarBackButtonDelegate

class MainActivity : AppCompatActivity(),
    ScreenNameDelegate,
    ToolbarBackButtonDelegate {

    lateinit var screensNavigator: ScreensNavigator

    private val defaultOnBackPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            screensNavigator.navigateBack()
        }
    }

    private lateinit var btnNavigateBack: View
    private lateinit var txtTitle: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        btnNavigateBack = findViewById(R.id.viewNavigateBack)
        txtTitle = findViewById(R.id.txtToolbarTitle)

        initScreensNavigator(savedInstanceState)

        onBackPressedDispatcher.addCallback(defaultOnBackPressedCallback)

        btnNavigateBack.setOnClickListener {
            screensNavigator.navigateBack()
        }
    }

    @Suppress("DEPRECATION")
    private fun initScreensNavigator(savedInstanceState: Bundle?) {
        screensNavigator = ScreensNavigator(
            this,
            FragNavController(
                supportFragmentManager,
                R.id.fragmentContainerViewMain
            ),
            this,
            this
        )
        screensNavigator.init(savedInstanceState)

        if (savedInstanceState == null) {
            var startingScreen = intent.getSerializableExtra(ScreenSpec.INTENT_EXTRA_SCREEN_SPEC) as ScreenSpec?
            if (startingScreen == null) {
                startingScreen = ScreenSpec.Home
            }
            screensNavigator.toScreen(startingScreen)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        screensNavigator.onSaveInstanceState(outState)
    }

    override fun showScreenName(screenName: String) {
        if (screenName.isNotBlank()) {
            txtTitle.isVisible = true
            txtTitle.text = screenName
        } else {
            txtTitle.isVisible = false
        }
    }

    override fun clearScreenName() {
        showScreenName("")
    }

    override fun showBackButton() {
        btnNavigateBack.isVisible = true
    }

    override fun hideBackButton() {
        btnNavigateBack.isVisible = false
    }
}