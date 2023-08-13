package com.techyourchance.androidviews.exercises._10_

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Matrix
import android.graphics.Rect
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.core.content.ContextCompat
import com.techyourchance.androidviews.CustomViewScaffold
import com.techyourchance.androidviews.R
import kotlin.math.min

class SmileyView : CustomViewScaffold {

    private val drawable = ContextCompat.getDrawable(context, R.drawable.ic_smiley)!!
    private val drawableBoundsRect = Rect()
    private val transformationMatrix = Matrix()

    private var drawableTranslationXFraction = 0f
    private var drawableTranslationYFraction = 0f

    private var drawableTranslationX = 0f
        set(value) {
            field = value
            updateTransformationMatrix()
        }

    private var drawableTranslationY = 0f
        set(value) {
            field = value
            updateTransformationMatrix()
        }

    private var drawableScale = 1f
        set(value) {
            field = value
            updateTransformationMatrix()
        }

    private var drawableRotation = 0f
        set(value) {
            field = value
            updateTransformationMatrix()
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

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (event == null) {
            return super.onTouchEvent(null)
        }
        return super.onTouchEvent(event)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        val drawableMaxSize = min(w, h) * DRAWABLE_DEFAULT_SIZE_FRACTION
        val drawableIntrinsicRatio = 1f * drawable.intrinsicWidth / drawable.intrinsicHeight

        val drawableWidth = if (drawableIntrinsicRatio > 1) {
            drawableMaxSize
        } else {
            drawableMaxSize * drawableIntrinsicRatio
        }
        val drawableHeight = if (drawableIntrinsicRatio > 1) {
            drawableMaxSize / drawableIntrinsicRatio
        } else {
            drawableMaxSize
        }

        val marginHorizontal = ((w - drawableWidth) / 2).toInt()
        val marginVertical = ((h - drawableHeight) / 2).toInt()

        drawableBoundsRect.set(marginHorizontal, marginVertical, w - marginHorizontal, h - marginVertical)
        drawable.bounds = drawableBoundsRect

        drawableTranslationX = w * drawableTranslationXFraction
        drawableTranslationY = h * drawableTranslationYFraction
    }

    private fun updateTransformationMatrix() {

    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.save()
        canvas.concat(transformationMatrix)
        drawable.draw(canvas)
        canvas.restore()
    }

    companion object {
        const val DRAWABLE_DEFAULT_SIZE_FRACTION = 0.5f
    }

}