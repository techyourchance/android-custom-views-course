package com.techyourchance.androidviews.demonstrations._01_basicshapes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.techyourchance.androidviews.general.BaseFragment
import com.techyourchance.androidviews.R

class BasicShapesFragment : BaseFragment() {

    override val screenName get() = getString(R.string.screen_name_basic_shapes)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return layoutInflater.inflate(R.layout.layout_basic_shapes, container, false)
    }

    companion object {
        fun newInstance(): BasicShapesFragment {
            return BasicShapesFragment()
        }
    }

}