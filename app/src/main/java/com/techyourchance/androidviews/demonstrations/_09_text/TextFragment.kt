package com.techyourchance.androidviews.demonstrations._09_text

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.techyourchance.androidviews.general.BaseFragment
import com.techyourchance.androidviews.R

class TextFragment : BaseFragment() {

    override val screenName get() = getString(R.string.screen_name_text)

    private lateinit var autoScalingTextView: AutoScalingTextView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return layoutInflater.inflate(R.layout.layout_text, container, false).apply {
            autoScalingTextView = findViewById(R.id.autoScalingTextView)
            autoScalingTextView.showBorder = true
            autoScalingTextView.setText(getString(R.string.hello_world))
        }
    }

    companion object {
        fun newInstance(): TextFragment {
            return TextFragment()
        }
    }

}