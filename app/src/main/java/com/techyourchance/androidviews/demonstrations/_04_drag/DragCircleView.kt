package com.techyourchance.androidviews.demonstrations._04_drag

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.core.content.ContextCompat
import com.techyourchance.androidviews.CustomViewScaffold
import com.techyourchance.androidviews.R
import timber.log.Timber
import kotlin.math.min
import kotlin.math.sqrt

@SuppressLint("ClickableViewAccessibility")
class DragCircleView : CustomViewScaffold {

    private val paint = Paint()

    private var circleXCenter = 0f
    private var circleYCenter = 0f
    private var circleRadius = 0f

    private var lastMotionEventX = 0f
    private var lastMotionEventY = 0f
    private var isDragged = false

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(
        context,
        attrs,
        defStyleAttr,
        defStyleRes
    )

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (event == null) {
            return super.onTouchEvent(event)
        }
        val distanceFromCircleCenter = pointsDistance(event.x, event.y, circleXCenter, circleYCenter)
        return if (distanceFromCircleCenter <= circleRadius && event.action == MotionEvent.ACTION_DOWN) {
            isDragged = true
            lastMotionEventX = event.x
            lastMotionEventY = event.y
            true
        } else if (isDragged && event.action == MotionEvent.ACTION_MOVE) {
            val dx = event.x - lastMotionEventX
            val dy = event.y - lastMotionEventY
            circleXCenter += dx
            circleYCenter += dy
            lastMotionEventX = event.x
            lastMotionEventY = event.y
            invalidate()
            true
        } else {
            isDragged = false
            false
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        circleXCenter = w / 2f
        circleYCenter = h / 2f
        circleRadius = min(w.toFloat(), h.toFloat()) / 6f
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        paint.color = ContextCompat.getColor(context, R.color.primary_variant)
        paint.style = Paint.Style.FILL
        canvas.drawCircle(circleXCenter, circleYCenter, circleRadius, paint)
    }

    /**
     * Compute the Euclidean distance between two points using the Pythagorean theorem
     */
    private fun pointsDistance(x1: Float, y1: Float, x2: Float, y2: Float): Float {
        val dx = x1 - x2
        val dy = y1 - y2
        return sqrt(dx*dx + dy*dy)
    }

}