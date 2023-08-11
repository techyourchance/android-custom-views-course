package com.techyourchance.androidviews.exercises._09_

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.techyourchance.androidviews.R
import com.techyourchance.androidviews.general.BaseFragment

class CrosshairFragment : BaseFragment() {

    override val screenName get() = getString(R.string.screen_name_crosshair)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return CrosshairView(context).apply{
            id = R.id.crosshairId
            showBorder = true
        }
    }

    companion object {
        fun newInstance(): CrosshairFragment {
            return CrosshairFragment()
        }
    }

}