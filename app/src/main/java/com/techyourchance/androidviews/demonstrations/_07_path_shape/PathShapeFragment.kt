package com.techyourchance.androidviews.demonstrations._07_path_shape

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.techyourchance.androidviews.general.BaseFragment
import com.techyourchance.androidviews.R

class PathShapeFragment : BaseFragment() {

    override val screenName get() = getString(R.string.screen_name_path_shape)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return layoutInflater.inflate(R.layout.layout_path_shape, container, false)
    }

    companion object {
        fun newInstance(): PathShapeFragment {
            return PathShapeFragment()
        }
    }

}