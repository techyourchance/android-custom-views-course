package com.techyourchance.androidviews.demonstrations._07_path_shape

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import androidx.core.content.ContextCompat
import com.techyourchance.androidviews.CustomViewScaffold
import com.techyourchance.androidviews.R
import kotlin.math.sqrt

class PathShapeView : CustomViewScaffold {

    private val trianglePaint = Paint()
    private var triangleLineSize = 0f
    private val trianglePath = Path()

    private val viewBorderPaint = Paint()
    private val viewBorderRect = RectF()

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

        val viewBorderLineSize = dpToPx(VIEW_BORDER_LINE_SIZE_DP)
        val viewBorderPadding = viewBorderLineSize / 2
        viewBorderRect.set(
            viewBorderPadding,
            viewBorderPadding,
            width.toFloat() - viewBorderPadding,
            height.toFloat() - viewBorderPadding
        )

        viewBorderPaint.color = Color.RED
        viewBorderPaint.style = Paint.Style.STROKE
        viewBorderPaint.strokeWidth = viewBorderLineSize
        viewBorderPaint.pathEffect = DashPathEffect(floatArrayOf(10f, 10f), 0f)

        triangleLineSize = dpToPx(LINE_SIZE_DP)

        trianglePaint.color = ContextCompat.getColor(context, R.color.primary_variant)
        trianglePaint.style = Paint.Style.STROKE
        trianglePaint.strokeWidth = triangleLineSize
        trianglePaint.isAntiAlias = true

        updateTrianglePath(w, h, triangleLineSize)
    }

    private fun updateTrianglePath(width: Int, height: Int, minPadding: Float) {
        val triangleSideLength = computeMaxAvailableTriangleSideLength(width, height, minPadding)
        val triangleHorizontalMargin = (width - triangleSideLength) / 2

        val triangleLeft = PointF(triangleHorizontalMargin, height.toFloat() - minPadding)
        val triangleRight = PointF(width - triangleHorizontalMargin, triangleLeft.y)
        val triangleTop = PointF(width / 2f, height - triangleSideLength * sqrt(3f) / 2)

        trianglePath.reset()
        trianglePath.moveTo(triangleTop.x, triangleTop.y)
        trianglePath.lineTo(triangleRight.x, triangleRight.y)
        trianglePath.lineTo(triangleLeft.x, triangleLeft.y)
        trianglePath.lineTo(triangleTop.x, triangleTop.y)
        trianglePath.close()
    }

    private fun computeMaxAvailableTriangleSideLength(viewWidth: Int, viewHeight: Int, minPadding: Float): Float {
        val viewWidthMinusPadding = viewWidth - 2 * minPadding
        val heightForViewWidthMinusPadding = viewWidthMinusPadding * sqrt(3f) / 2
        return if (viewHeight >= heightForViewWidthMinusPadding) {
            viewWidthMinusPadding
        } else {
            return (viewHeight - 2 * minPadding) * 2 / sqrt(3f)
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawPath(trianglePath, trianglePaint)
        canvas.drawRect(viewBorderRect, viewBorderPaint)
    }

    companion object {
        const val LINE_SIZE_DP = 10f
        const val VIEW_BORDER_LINE_SIZE_DP = 1f
    }
}