package com.techyourchance.androidviews.demonstrations._12_matrix_transformation

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.techyourchance.androidviews.R
import com.techyourchance.androidviews.exercises._03_.SliderChangeListener
import com.techyourchance.androidviews.general.BaseFragment
import com.techyourchance.androidviews.solutions._04_.SolutionExercise4View


class MatrixTransformationFragment : BaseFragment() {

    override val screenName get() = getString(R.string.screen_name_matrix_transformation)

    private val uiHandler = Handler(Looper.getMainLooper())

    private lateinit var viewMatrixTransformation: MatrixTransformationView
    private lateinit var sliderTranslationX: SolutionExercise4View
    private lateinit var sliderTranslationY: SolutionExercise4View
    private lateinit var sliderRotation: SolutionExercise4View
    private lateinit var sliderScale: SolutionExercise4View

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return layoutInflater.inflate(R.layout.layout_matrix_transformation, container, false).apply {
            viewMatrixTransformation = findViewById(R.id.viewMatrixTranformation)
            sliderTranslationX = findViewById(R.id.sliderTranslationX)
            sliderTranslationY = findViewById(R.id.sliderTranslationY)
            sliderRotation = findViewById(R.id.sliderRotation)
            sliderScale = findViewById(R.id.sliderScale)

            viewMatrixTransformation.showBorder = true

            sliderTranslationX.sliderChangeListener = object : SliderChangeListener {
                override fun onValueChanged(value: Float) {
                    viewMatrixTransformation.innerTranslationX = value * 0.5f * viewMatrixTransformation.width
                }
            }

            sliderTranslationY.sliderChangeListener = object : SliderChangeListener {
                override fun onValueChanged(value: Float) {
                    viewMatrixTransformation.innerTranslationY = value * 0.5f * viewMatrixTransformation.height
                }
            }

            sliderRotation.sliderChangeListener = object : SliderChangeListener {
                override fun onValueChanged(value: Float) {
                    viewMatrixTransformation.innerRotation = value * 360
                }
            }

            sliderScale.sliderChangeListener = object : SliderChangeListener {
                override fun onValueChanged(value: Float) {
                    viewMatrixTransformation.innerScale = 1 + value
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        uiHandler.post {
            updateUiValues()
        }
    }

    private fun updateUiValues() {
        sliderTranslationX.value = viewMatrixTransformation.innerTranslationX / viewMatrixTransformation.width
        sliderTranslationY.value = viewMatrixTransformation.innerTranslationY / viewMatrixTransformation.height
        sliderRotation.value = viewMatrixTransformation.innerRotation
        sliderScale.value = viewMatrixTransformation.innerScale - 1
    }

    companion object {
        fun newInstance(): MatrixTransformationFragment {
            return MatrixTransformationFragment()
        }
    }

}