package com.techyourchance.androidviews.solutions._03_

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.techyourchance.androidviews.general.BaseFragment
import com.techyourchance.androidviews.R
import com.techyourchance.androidviews.exercises._03_.SliderChangeListener

class SolutionExercise3Fragment : BaseFragment(), SliderChangeListener {

    override val screenName get() = getString(R.string.screen_name_solution_exercise_3)

    private lateinit var slider: SolutionExercise3View
    private lateinit var txtValue: TextView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return layoutInflater.inflate(R.layout.layout_solution_exercise_3, container, false).apply {
            slider = findViewById(R.id.slider)
            txtValue = findViewById(R.id.txtValue)
            slider.sliderChangeListener = this@SolutionExercise3Fragment
        }
    }

    override fun onStart() {
        super.onStart()
        txtValue.text = slider.value.toString()
    }

    override fun onValueChanged(value: Float) {
        txtValue.text = value.toString()
    }

    companion object {
        fun newInstance(): SolutionExercise3Fragment {
            return SolutionExercise3Fragment()
        }
    }

}