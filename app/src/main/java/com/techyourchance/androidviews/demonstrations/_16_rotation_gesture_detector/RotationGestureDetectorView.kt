package com.techyourchance.androidviews.demonstrations._16_rotation_gesture_detector

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.techyourchance.androidviews.CustomViewScaffold
import com.techyourchance.androidviews.R
import timber.log.Timber
import kotlin.math.min

@SuppressLint("ClickableViewAccessibility")
class RotationGestureDetectorView : CustomViewScaffold {

    private val textPaint = Paint(Paint.ANTI_ALIAS_FLAG)

    private var rotationAngle = 0f

    private val rotationGestureDetector: RotationGestureDetector = RotationGestureDetector().apply {
        onRotationGestureListener = object : RotationGestureDetector.OnRotationGestureListener {
            override fun onRotationBegin(): Boolean {
                Timber.i("onRotationBegin()")
                rotationAngle = 0f
                invalidate()
                return true
            }

            override fun onRotationEnd() {
                Timber.i("onRotationEnd()")
                rotationAngle = 0f
                invalidate()
            }

            override fun onRotation(angleDiff: Float): Boolean {
                rotationAngle += angleDiff
                invalidate()
                return true
            }

        }
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

    init {
        textPaint.color = ContextCompat.getColor(context, R.color.primary_variant)
        textPaint.textAlign = Paint.Align.CENTER
        textPaint.typeface = ResourcesCompat.getFont(context, R.font.assistantregular)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (event == null) {
            return super.onTouchEvent(null)
        }
        return rotationGestureDetector.onTouchEvent(event) || super.onTouchEvent(event)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        textPaint.textSize = min(width, height) / 10f
        canvas.drawText("%.3f".format(rotationAngle), width / 2f, height / 2f, textPaint)
    }

}