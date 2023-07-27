package com.techyourchance.androidviews.demonstrations._10_path_arcs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.techyourchance.androidviews.general.BaseFragment
import com.techyourchance.androidviews.R

class PathArcFragment : BaseFragment() {

    override val screenName get() = getString(R.string.screen_name_path_arc)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return PathArcView(context)
    }

    companion object {
        fun newInstance(): PathArcFragment {
            return PathArcFragment()
        }
    }

}