package com.techyourchance.androidviews.demonstrations._14_gesture_detector

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.techyourchance.androidviews.R
import com.techyourchance.androidviews.general.BaseFragment


class GestureDetectorFragment : BaseFragment() {

    override val screenName get() = getString(R.string.screen_name_gesture_detector)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return GestureDetectorView(context).apply {
            id = R.id.gestureDetection
            showBorder = true
        }
    }

    companion object {
        fun newInstance(): GestureDetectorFragment {
            return GestureDetectorFragment()
        }
    }

}