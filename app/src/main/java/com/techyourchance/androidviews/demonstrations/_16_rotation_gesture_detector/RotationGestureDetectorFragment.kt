package com.techyourchance.androidviews.demonstrations._16_rotation_gesture_detector

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.techyourchance.androidviews.R
import com.techyourchance.androidviews.general.BaseFragment


class RotationGestureDetectorFragment : BaseFragment() {

    override val screenName get() = getString(R.string.screen_name_rotation_gesture_detector)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return RotationGestureDetectorView(context).apply {
            id = R.id.rotationGestureDetection
            showBorder = true
        }
    }

    companion object {
        fun newInstance(): RotationGestureDetectorFragment {
            return RotationGestureDetectorFragment()
        }
    }

}