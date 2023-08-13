package com.techyourchance.androidviews.demonstrations._15_scale_gesture_detector

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.view.ScaleGestureDetector.OnScaleGestureListener
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.techyourchance.androidviews.CustomViewScaffold
import com.techyourchance.androidviews.R
import timber.log.Timber
import kotlin.math.min

@SuppressLint("ClickableViewAccessibility")
class ScaleGestureDetectorView : CustomViewScaffold {

    private val textPaint = Paint(Paint.ANTI_ALIAS_FLAG)

    private var scaleFactor = 0f

    private val scaleGestureDetector: ScaleGestureDetector = ScaleGestureDetector(context, object : OnScaleGestureListener {
        override fun onScaleBegin(detector: ScaleGestureDetector): Boolean {
            Timber.i("onScaleBegin()")
            scaleFactor = 1f
            invalidate()
            return true
        }

        override fun onScaleEnd(detector: ScaleGestureDetector) {
            Timber.i("onScaleEnd()")
            scaleFactor = 0f
            invalidate()
        }

        override fun onScale(detector: ScaleGestureDetector): Boolean {
            scaleFactor *= detector.scaleFactor
            invalidate()
            return true
        }
    })

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
        textPaint.color = ContextCompat.getColor(context, R.color.primary_variant)
        textPaint.textAlign = Paint.Align.CENTER
        textPaint.typeface = ResourcesCompat.getFont(context, R.font.assistantregular)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (event == null) {
            return super.onTouchEvent(null)
        }
        return scaleGestureDetector.onTouchEvent(event) || super.onTouchEvent(event)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        textPaint.textSize = min(width, height) / 10f
        canvas.drawText("%.3f".format(scaleFactor), width / 2f, height / 2f, textPaint)
    }

}