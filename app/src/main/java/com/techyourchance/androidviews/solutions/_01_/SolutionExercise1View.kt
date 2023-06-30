package com.techyourchance.androidviews.solutions._01_

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import androidx.core.content.ContextCompat
import com.techyourchance.androidviews.CustomViewScaffold
import com.techyourchance.androidviews.R

class SolutionExercise1View : CustomViewScaffold {

    private val paint = Paint()

    private var lineXLeft = 0f
    private var lineXRight = 0f
    private var lineYPos = 0f
    private var lineHeight = 0f
    private var lineColor = 0

    private var thumbXCenter = 0f
    private var thumbYCenter = 0f
    private var thumbRadius = 0f
    private var thumbColor = 0

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

        thumbRadius = dpToPx(THUMB_RADIUS_DP)
        thumbXCenter = w / 2f
        thumbYCenter = height * 0.5f
        thumbColor = ContextCompat.getColor(context, R.color.primary_variant)

        lineXLeft = thumbRadius
        lineXRight = w - thumbRadius
        lineYPos = thumbYCenter
        lineHeight = dpToPx(LINE_HEIGHT_DP)
        lineColor = ContextCompat.getColor(context, R.color.gray_light)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        paint.style = Paint.Style.FILL
        paint.strokeWidth = lineHeight

        // Draw the line
        paint.color = lineColor
        canvas.drawLine(lineXLeft, lineYPos, lineXRight, lineYPos, paint)

        // Draw the circle
        paint.color = thumbColor
        canvas.drawCircle(thumbXCenter, thumbYCenter, thumbRadius, paint)
    }


    companion object {
        const val LINE_HEIGHT_DP = 5f
        const val THUMB_RADIUS_DP = 15f
    }
}