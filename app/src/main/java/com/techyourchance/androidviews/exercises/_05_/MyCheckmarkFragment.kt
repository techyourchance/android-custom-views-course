package com.techyourchance.androidviews.exercises._05_

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.techyourchance.androidviews.R
import com.techyourchance.androidviews.general.BaseFragment

class MyCheckmarkFragment : BaseFragment() {

    override val screenName get() = getString(R.string.screen_name_my_checkmark)

    private lateinit var viewCheckmarkWide: MyCheckmarkView
    private lateinit var viewCheckmarkTall: MyCheckmarkView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return layoutInflater.inflate(R.layout.layout_my_checkmark, container, false).apply {
            viewCheckmarkWide = findViewById(R.id.viewCheckmarkWide)
            viewCheckmarkTall = findViewById(R.id.viewCheckmarkTall)
            viewCheckmarkWide.showBorder = true
            viewCheckmarkTall.showBorder = true
        }
    }

    override fun onResume() {
        super.onResume()
        viewCheckmarkWide.startAnimation(ANIMATION_DURATION_MS)
        viewCheckmarkTall.startAnimation(ANIMATION_DURATION_MS)
    }

    override fun onPause() {
        super.onPause()
        viewCheckmarkWide.stopAnimation()
        viewCheckmarkTall.stopAnimation()
    }

    companion object {

        const val ANIMATION_DURATION_MS = 1000L

        fun newInstance(): MyCheckmarkFragment {
            return MyCheckmarkFragment()
        }
    }

}