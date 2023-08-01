package com.techyourchance.androidviews.exercises._08_

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.techyourchance.androidviews.R
import com.techyourchance.androidviews.general.BaseFragment
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class StatesProgressionFragment : BaseFragment() {

    override val screenName get() = getString(R.string.screen_name_states)

    private lateinit var viewStates: StatesProgressionView

    private val states = listOf(
        State("state 1"),
        State("state 2"),
        State("state 3 - longest name"),
        State("state 4"),
    )

    private var stateChangeJob: Job? = null
    private var currentStateIndex = -1

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return layoutInflater.inflate(R.layout.layout_states, container, false).apply {
            viewStates = findViewById(R.id.viewCoupons)
            viewStates.showBorder = true
        }
    }

    override fun onStart() {
        super.onStart()
        stateChangeJob = lifecycleScope.launch {
            delay(10)
            viewStates.bindStates(states)
            while (isActive) {
                viewStates.bindCurrentState(states.getOrNull(currentStateIndex))
                currentStateIndex = (currentStateIndex + 1) % (states.size + 1)
                delay(1000)
            }
        }
    }

    override fun onStop() {
        super.onStop()
        stateChangeJob?.cancel()
    }

    companion object {
        fun newInstance(): StatesProgressionFragment {
            return StatesProgressionFragment()
        }
    }

}