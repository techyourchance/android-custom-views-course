package com.techyourchance.androidviews.demonstrations._11_on_measure

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.techyourchance.androidviews.R
import com.techyourchance.androidviews.general.BaseFragment

class OnMeasureFragment : BaseFragment() {

    override val screenName get() = getString(R.string.screen_name_on_measure)

    private lateinit var view: OnMeasureView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return layoutInflater.inflate(R.layout.layout_on_measure, container, false).apply {
            view = findViewById(R.id.viewOnMeasure)
            view.showBorder = true
        }
    }

    companion object {
        fun newInstance(): OnMeasureFragment {
            return OnMeasureFragment()
        }
    }

}