package com.techyourchance.androidviews.demonstrations._05_state_preservation

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.techyourchance.androidviews.R
import com.techyourchance.androidviews.general.BaseFragment


class StatePreservationFragment : BaseFragment() {

    override val screenName get() = getString(R.string.screen_name_state_preservation)
    
    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return StatePreservationView(context).apply { id = R.id.draggableCircleId }
    }

    companion object {
        fun newInstance(): StatePreservationFragment {
            return StatePreservationFragment()
        }
    }

}