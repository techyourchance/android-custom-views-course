package com.techyourchance.androidviews.demonstrations._15_scale_gesture_detector

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.techyourchance.androidviews.R
import com.techyourchance.androidviews.general.BaseFragment


class ScaleGestureDetectorFragment : BaseFragment() {

    override val screenName get() = getString(R.string.screen_name_scale_gesture_detector)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return ScaleGestureDetectorView(context).apply {
            id = R.id.scaleGestureDetection
            showBorder = true
        }
    }

    companion object {
        fun newInstance(): ScaleGestureDetectorFragment {
            return ScaleGestureDetectorFragment()
        }
    }

}