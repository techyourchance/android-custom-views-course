package com.techyourchance.androidviews.exercises._01_

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.techyourchance.androidviews.general.BaseFragment
import com.techyourchance.androidviews.R

class MySliderFragment : BaseFragment() {

    override val screenName get() = getString(R.string.screen_name_my_slider)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return layoutInflater.inflate(R.layout.layout_my_slider, container, false)
    }

    companion object {
        fun newInstance(): MySliderFragment {
            return MySliderFragment()
        }
    }

}