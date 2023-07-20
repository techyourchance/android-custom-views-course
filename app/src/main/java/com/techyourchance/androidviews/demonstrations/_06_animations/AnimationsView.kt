package com.techyourchance.androidviews.demonstrations._06_animations

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import androidx.core.content.ContextCompat
import com.techyourchance.androidviews.CustomViewScaffold
import com.techyourchance.androidviews.R
import kotlin.math.min

@SuppressLint("ClickableViewAccessibility")
class AnimationsView : CustomViewScaffold {

    private val paint = Paint()

    private var circleXCenter = 0f
    private var circleYCenter = 0f
    private var circleRadius = 0f

    private var borderSize = 0f
    private val borderRect = RectF()


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

        circleXCenter = w / 2f
        circleYCenter = h / 2f
        circleRadius = min(w.toFloat(), h.toFloat()) / 6f

        borderSize = dpToPx(BORDER_STROKE_SIZE_DP)
        borderRect.set(borderSize, borderSize, w - borderSize, height - borderSize)

    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        paint.color = ContextCompat.getColor(context, R.color.primary_light)
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = borderSize
        canvas.drawRect(borderRect, paint)
        paint.color = ContextCompat.getColor(context, R.color.primary_variant)
        paint.style = Paint.Style.FILL
        canvas.drawCircle(circleXCenter, circleYCenter, circleRadius, paint)
    }

    companion object {
        private const val BORDER_STROKE_SIZE_DP = 2f
    }

}