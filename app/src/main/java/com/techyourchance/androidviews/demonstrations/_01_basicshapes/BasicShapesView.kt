package com.techyourchance.androidviews.demonstrations._01_basicshapes

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.graphics.Color
import android.graphics.RectF
import androidx.core.content.ContextCompat
import com.techyourchance.androidviews.CustomViewScaffold
import com.techyourchance.androidviews.R

class BasicShapesView : CustomViewScaffold {

    private val paint = Paint()

    private var lineXLeft: Float = 0f
    private var lineXRight: Float = 0f
    private var lineYPos: Float = 0f
    private var lineHeight: Float = 0f

    private val rectangleRect: RectF = RectF()

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(
        context,
        attrs,
        defStyleAttr,
        defStyleRes
    )

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        val lineMarginHorizontal = dpToPx(LINE_MARGIN_HORIZONTAL_DP)
        lineXLeft = lineMarginHorizontal
        lineXRight = w - lineMarginHorizontal
        lineYPos = height * LINE_Y_POS_FRACTION
        lineHeight = dpToPx(LINE_HEIGHT_DP)

        val rectangleMarginHorizontal = dpToPx(RECTANGLE_MARGIN_HORIZONTAL_DP)
        val rectangleWidth = w - 2 * rectangleMarginHorizontal
        val rectangleHeight = rectangleWidth / 2
        val rectangleTop = (h - rectangleHeight) / 2
        val rectangleBottom = rectangleTop + rectangleHeight
        rectangleRect.set(rectangleMarginHorizontal, rectangleTop, rectangleMarginHorizontal + rectangleWidth, rectangleBottom)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        paint.color = ContextCompat.getColor(context, R.color.primary_variant)
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = lineHeight

        // Draw the line
        canvas.drawLine(lineXLeft, lineYPos, lineXRight, lineYPos, paint)

        // Draw the rectangle
        canvas.drawRect(rectangleRect, paint)
    }


    companion object {
        const val LINE_Y_POS_FRACTION = 0.2f
        const val LINE_MARGIN_HORIZONTAL_DP = 20f
        const val LINE_HEIGHT_DP = 5f
        const val RECTANGLE_MARGIN_HORIZONTAL_DP = 20f
    }
}