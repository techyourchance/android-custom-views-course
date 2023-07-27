package com.techyourchance.androidviews.solutions._07_

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import androidx.core.content.ContextCompat
import com.techyourchance.androidviews.CustomViewScaffold
import com.techyourchance.androidviews.R

class SolutionExercise7View : CustomViewScaffold {

    private val cellPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val textPaint = Paint(Paint.ANTI_ALIAS_FLAG)

    private var borderLineSize = 0f
    private var cellWidth = 0f

    private val borderPath = Path()

    private var totalCoupons = 0
    private var usedCoupons = 0

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(
        context,
        attrs,
        defStyleAttr,
        defStyleRes
    )

    fun setCoupons(totalCoupons: Int, usedCoupons: Int) {
        this.totalCoupons = totalCoupons
        this.usedCoupons = usedCoupons
        if (width > 0 && height > 0) {
            updateBorderPath()
            updateTextSize()
            invalidate()
        }
    }

    private fun updateBorderPath() {
        val borderLinePadding = borderLineSize / 2
        cellWidth = (width.toFloat() - 2 * borderLinePadding)  / totalCoupons
        val cornerRadius = dpToPx(CORNER_RADIUS_DP)

        var cellLeft = borderLinePadding
        var cellRight = cellLeft + cellWidth
        borderPath.reset()
        for (i in 0 until totalCoupons) {
            val cornerRadiiArray = getCornerRadiusArrayForCell(i, cornerRadius)
            borderPath.addRoundRect(
                RectF(cellLeft, borderLinePadding, cellRight, height.toFloat() - borderLinePadding),
                cornerRadiiArray,
                Path.Direction.CW
            )
            cellLeft = cellRight
            cellRight = cellLeft + cellWidth
        }
    }

    private fun getCornerRadiusArrayForCell(cellIndex: Int, cornerRadius: Float) = when (cellIndex) {
        0 -> floatArrayOf(
            0f, 0f,
            0f, 0f,
            0f, 0f,
            cornerRadius, cornerRadius, // bottom-left
        )
        totalCoupons - 1 -> floatArrayOf(
            0f, 0f,
            0f, 0f,
            cornerRadius, cornerRadius, // bottom-right
            0f, 0f,
        )
        else -> floatArrayOf(0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f)
    }

    private fun updateTextSize() {
        val minPadding = dpToPx(CELL_MIN_PADDING_DP)
        val maxTextWidth = cellWidth - 2 * (minPadding + borderLineSize)
        val maxTextHeight = height.toFloat() - 2 * (minPadding + borderLineSize)

        val longestNumOfCouponsText = "99" // assume double-digit number of coupons at most

        var size = 1f
        val bounds = Rect()

        // Binary search can be used for better performance
        while (true) {
            textPaint.textSize = size
            textPaint.getTextBounds(longestNumOfCouponsText, 0, longestNumOfCouponsText.length, bounds)
            if (bounds.width() > maxTextWidth || bounds.height() > maxTextHeight) {
                textPaint.textSize = size - 1
                return
            }
            size++
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        borderLineSize = dpToPx(LINE_SIZE_DP)
        cellPaint.color = ContextCompat.getColor(context, R.color.primary_variant)
        cellPaint.style = Paint.Style.STROKE
        cellPaint.strokeWidth = borderLineSize

        textPaint.textAlign = Paint.Align.CENTER
        textPaint.isFakeBoldText = true
        textPaint.color = ContextCompat.getColor(context, R.color.gray_dark)

        updateBorderPath()
        updateTextSize()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawPath(borderPath, cellPaint)
        val textHeight = textPaint.descent() - textPaint.ascent()
        val textY = height / 2f + textHeight / 2f
        for (i in 0 until totalCoupons) {
            val textX = cellWidth * (i + 0.5f)
            textPaint.alpha = if (i <= usedCoupons - 1) { 80 } else { 255 }
            canvas.drawText((i + 1).toString(), textX, textY, textPaint)
        }
    }

    companion object {
        private const val LINE_SIZE_DP = 2f
        private const val CELL_MIN_PADDING_DP = 5f
        private const val CORNER_RADIUS_DP = 20f
    }

}