package com.techyourchance.androidviews.demonstrations._08_path_animation

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.animation.LinearInterpolator
import androidx.core.content.ContextCompat
import com.techyourchance.androidviews.CustomViewScaffold
import com.techyourchance.androidviews.R
import kotlin.math.sqrt

class PathAnimationView : CustomViewScaffold {

    private val trianglePaint = Paint()
    private var triangleLineSize = 0f
    private var triangleSideLength = 0f
    private val referenceTrianglePath = Path()
    private val trianglePath = Path()

    private val viewBorderPaint = Paint()
    private val viewBorderRect = RectF()

    private var valueAnimator: ValueAnimator? = null

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(
        context,
        attrs,
        defStyleAttr,
        defStyleRes
    )

    fun startAnimation(durationMs: Long) {
        valueAnimator = ValueAnimator.ofFloat(0f, 2f).apply {
            interpolator = LinearInterpolator()
            duration = durationMs
            repeatCount = ValueAnimator.INFINITE
            addUpdateListener {
                updateTrianglePath(it.animatedValue as Float)
            }
            start()
        }
    }

    fun stopAnimation() {
        valueAnimator?.cancel()
    }

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

        updateReferenceTrianglePath(w, h, triangleLineSize)
    }

    private fun updateReferenceTrianglePath(width: Int, height: Int, minPadding: Float) {
        triangleSideLength = computeMaxAvailableTriangleSideLength(width, height, minPadding)
        val triangleHorizontalMargin = (width - triangleSideLength) / 2

        val triangleLeft = PointF(triangleHorizontalMargin, height.toFloat() - minPadding)
        val triangleRight = PointF(width - triangleHorizontalMargin, triangleLeft.y)
        val triangleTop = PointF(width / 2f, height - triangleSideLength * sqrt(3f) / 2)

        referenceTrianglePath.reset()
        referenceTrianglePath.moveTo(triangleTop.x, triangleTop.y)
        referenceTrianglePath.lineTo(triangleRight.x, triangleRight.y)
        referenceTrianglePath.lineTo(triangleLeft.x, triangleLeft.y)
        referenceTrianglePath.lineTo(triangleTop.x, triangleTop.y)
        referenceTrianglePath.close()
    }

    private fun updateTrianglePath(fraction: Float) {
        val pathMeasure = PathMeasure(referenceTrianglePath, false)
        val totalLength = 3 * triangleSideLength
        trianglePath.reset()
        if (fraction <= 1f) {
            pathMeasure.getSegment(0f, fraction * totalLength, trianglePath, true)
            if (fraction == 1f) {
                trianglePath.close()
            }
        } else {
            val startFraction = fraction - 1
            pathMeasure.getSegment(startFraction * totalLength, totalLength, trianglePath, true)
        }
        invalidate()
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