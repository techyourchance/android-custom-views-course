package com.techyourchance.androidviews.solutions._08_

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import androidx.core.content.ContextCompat
import com.techyourchance.androidviews.CustomViewScaffold
import com.techyourchance.androidviews.R
import com.techyourchance.androidviews.exercises._08_.State
import kotlin.math.max
import kotlin.math.min

class SolutionExercise8View : CustomViewScaffold {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var colorActive = 0
    private var colorInactive = 0
    private val textBoundsRect = Rect()

    private var marginVertical = 0f
    private var marginHorizontal = 0f
    private var circleCenterX = 0f
    private var circleRadius = 0f
    private var circleToTextSpacing = 0f
    private var textLeftX = 0f
    private var textHeight = 0f
    private var stateEntryHeight = 0f
    private var statesSpacingVertical = 0f

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

        paint.textSize = dpToPx(TEXT_SIZE_DP)
        paint.textAlign = Paint.Align.LEFT

        marginVertical = dpToPx(MARGIN_VERTICAL_DP)
        marginHorizontal = dpToPx(MARGIN_HORIZONTAL_DP)
        circleCenterX = marginHorizontal + circleRadius
        circleRadius = dpToPx(CIRCLE_RADIUS_DP)
        circleToTextSpacing = dpToPx(CIRCLE_TO_TEXT_SPACING_HORIZONTAL_DP)
        textLeftX = circleCenterX + circleRadius + dpToPx(CIRCLE_TO_TEXT_SPACING_HORIZONTAL_DP)
        textHeight = paint.fontMetrics.descent - paint.fontMetrics.ascent
        stateEntryHeight = 2 * circleRadius
        statesSpacingVertical = dpToPx(STATES_SPACING_VERTICAL_DP)
    }

    fun bindStates(states: List<State>) {
        this.states.clear()
        this.states.addAll(states)
        requestLayout()
    }

    fun bindCurrentState(state: State?) {
        this.currentState = state
        invalidate()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        val desiredWidth = MeasureSpec.getSize(widthMeasureSpec)
        val desiredHeight = MeasureSpec.getSize(heightMeasureSpec)

        val selfWidth =
            2 * marginHorizontal + 2 * circleRadius + circleToTextSpacing + getLongestStateNameLength()

        val selfHeight =
            2 * marginVertical + states.size * stateEntryHeight + max(0, states.size - 1) * statesSpacingVertical

        val width = when(widthMode) {
            MeasureSpec.EXACTLY -> desiredWidth
            MeasureSpec.AT_MOST -> min(desiredWidth, selfWidth.toInt())
            else -> selfWidth.toInt()
        }

        val height = when(heightMode) {
            MeasureSpec.EXACTLY -> desiredHeight
            MeasureSpec.AT_MOST -> min(desiredHeight, selfHeight.toInt())
            else -> selfHeight.toInt()
        }

        setMeasuredDimension(width, height)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val currentStateIndex = getCurrentStateIndex()
        states.forEachIndexed {i, state ->
            if (i <= currentStateIndex) {
                paint.color = colorActive
            } else {
                paint.color = colorInactive
            }
            val circleCenterY = marginVertical + stateEntryHeight / 2 + i * (stateEntryHeight + statesSpacingVertical)
            canvas.drawCircle(circleCenterX, circleCenterY, circleRadius, paint)
            paint.getTextBounds(state.name, 0, state.name.length, textBoundsRect)
            canvas.drawText(state.name, textLeftX, circleCenterY - textBoundsRect.exactCenterY(), paint)
        }
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

    private fun getCurrentStateIndex(): Int {
        return if (currentState != null) {
            states.indexOf(currentState)
        } else {
            -1
        }
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