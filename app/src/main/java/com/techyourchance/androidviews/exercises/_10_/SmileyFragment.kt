package com.techyourchance.androidviews.exercises._10_

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.techyourchance.androidviews.R
import com.techyourchance.androidviews.general.BaseFragment

class SmileyFragment : BaseFragment() {

    override val screenName get() = getString(R.string.screen_name_smiley)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return SmileyView(context).apply{
            id = R.id.smiley
            showBorder = true
        }
    }

    companion object {
        fun newInstance(): SmileyFragment {
            return SmileyFragment()
        }
    }

}