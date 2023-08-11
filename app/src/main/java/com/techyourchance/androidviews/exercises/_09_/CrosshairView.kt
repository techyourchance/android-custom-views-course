package com.techyourchance.androidviews.exercises._09_

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import androidx.core.content.ContextCompat
import com.techyourchance.androidviews.CustomViewScaffold
import com.techyourchance.androidviews.R

class CrosshairView : CustomViewScaffold {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var dotColor = 0
    private var crosshairColor = 0

    private var crosshairCircleRadius = 0f
    private var crosshairDotRadius = 0f
    private var crosshairHairLength = 0f

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
        crosshairCircleRadius = dpToPx(CROSSHAIR_CIRCLE_RADIUS_DP)
        crosshairDotRadius = dpToPx(CROSSHAIR_DOT_RADIUS_DP)
        crosshairHairLength = dpToPx(CROSSHAIR_HAIR_LENGTH_DP)
        paint.strokeWidth = dpToPx(CROSSHAIR_LINE_SIZE_DP)
        dotColor = ContextCompat.getColor(context, R.color.red)
        crosshairColor = ContextCompat.getColor(context, R.color.primary_variant)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
    }

    companion object {
        private const val CROSSHAIR_CIRCLE_RADIUS_DP = 40f
        private const val CROSSHAIR_DOT_RADIUS_DP = 4f
        private const val CROSSHAIR_HAIR_LENGTH_DP = 20f
        private const val CROSSHAIR_LINE_SIZE_DP = 2f
    }

}