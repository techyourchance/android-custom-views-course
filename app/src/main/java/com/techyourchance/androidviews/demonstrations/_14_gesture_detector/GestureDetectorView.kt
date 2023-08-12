package com.techyourchance.androidviews.demonstrations._14_gesture_detector

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.GestureDetector.OnDoubleTapListener
import android.view.MotionEvent
import androidx.core.content.ContextCompat
import com.techyourchance.androidviews.CustomViewScaffold
import com.techyourchance.androidviews.R
import com.techyourchance.androidviews.general.extensions.MotionEventExtensions.distanceTo
import timber.log.Timber
import kotlin.math.min
import kotlin.math.pow
import kotlin.math.sqrt

@SuppressLint("ClickableViewAccessibility")
class GestureDetectorView : CustomViewScaffold {

    private val paint = Paint()

    private var circleXCenter = 0f
    private var circleYCenter = 0f
    private var circleRadius = 0f

    private val onGestureListener = object : GestureDetector.OnGestureListener {
        override fun onDown(e: MotionEvent): Boolean {
            Timber.i("onDown()")
            return e.distanceTo(circleXCenter, circleYCenter) <= circleRadius
        }

        override fun onShowPress(e: MotionEvent) {
            Timber.i("onShowPress()")
        }

        override fun onSingleTapUp(e: MotionEvent): Boolean {
            Timber.i("onSingleTapUp()")
            return true
        }

        override fun onScroll(e1: MotionEvent, e2: MotionEvent, distanceX: Float, distanceY: Float): Boolean {
            Timber.i("onScroll()")
            circleXCenter -= distanceX
            circleYCenter -= distanceY
            invalidate()
            return true
        }

        override fun onLongPress(e: MotionEvent) {
            Timber.i("onLongPress()")
        }

        override fun onFling(e1: MotionEvent, e2: MotionEvent, velocityX: Float, velocityY: Float): Boolean {
            val velocity = sqrt(velocityX.pow(2) +  velocityY.pow(2))
            Timber.i("onFling(); velocity: $velocity")
            return true
        }
    }

    private val doubleTapListener = object : OnDoubleTapListener {
        override fun onSingleTapConfirmed(e: MotionEvent): Boolean {
            Timber.i("onSingleTapConfirmed()")
            return true
        }

        override fun onDoubleTap(e: MotionEvent): Boolean {
            Timber.i("onDoubleTap()")
            return true
        }

        override fun onDoubleTapEvent(e: MotionEvent): Boolean {
            Timber.i("onDoubleTapEvent(); event: $e")
            return true
        }
    }

    private val gestureDetector: GestureDetector = GestureDetector(context, onGestureListener ).apply {
        setIsLongpressEnabled(false)
        setOnDoubleTapListener(doubleTapListener)
    }

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
            return super.onTouchEvent(null)
        }
        return gestureDetector.onTouchEvent(event) || super.onTouchEvent(event)
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

}