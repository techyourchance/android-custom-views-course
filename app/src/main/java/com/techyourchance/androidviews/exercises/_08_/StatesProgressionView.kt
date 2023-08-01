package com.techyourchance.androidviews.exercises._08_

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import androidx.core.content.ContextCompat
import com.techyourchance.androidviews.CustomViewScaffold
import com.techyourchance.androidviews.R

class StatesProgressionView : CustomViewScaffold {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val textBoundsRect = Rect()
    private var colorActive = 0
    private var colorInactive = 0

    private val states = mutableListOf<State>()
    private var currentState: State? = null

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(
        context,
        attrs,
        defStyleAttr,
        defStyleRes
    )

    init {
        setBackgroundColor(ContextCompat.getColor(context, R.color.gray_dark))
        colorActive = ContextCompat.getColor(context, R.color.green)
        colorInactive = ContextCompat.getColor(context, R.color.semi_transparent)
    }

    fun bindStates(states: List<State>) {
        this.states.clear()
        this.states.addAll(states)
    }

    fun bindCurrentState(state: State?) {
        this.currentState = state
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
    }

    private fun getLongestStateNameLength(): Int {
        var longestStateNameLength = 0
        states.forEach {state ->
            paint.getTextBounds(state.name, 0, state.name.length, textBoundsRect)
            if (textBoundsRect.width() > longestStateNameLength) {
                longestStateNameLength = textBoundsRect.width()
            }
        }
        return longestStateNameLength
    }

    companion object {
        private const val MARGIN_HORIZONTAL_DP = 25f
        private const val MARGIN_VERTICAL_DP = 25f
        private const val STATES_SPACING_VERTICAL_DP = 20f
        private const val CIRCLE_RADIUS_DP = 8f
        private const val CIRCLE_TO_TEXT_SPACING_HORIZONTAL_DP = 15f
        private const val TEXT_SIZE_DP = 20f
    }

}