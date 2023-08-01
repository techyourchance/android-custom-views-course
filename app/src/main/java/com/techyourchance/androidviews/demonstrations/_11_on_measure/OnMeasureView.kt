package com.techyourchance.androidviews.demonstrations._11_on_measure

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.View
import android.view.View.MeasureSpec
import com.techyourchance.androidviews.CustomViewScaffold
import kotlin.math.min

class OnMeasureView : CustomViewScaffold {

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(
        context,
        attrs,
        defStyleAttr,
        defStyleRes
    )

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        val desiredWidth = MeasureSpec.getSize(widthMeasureSpec)
        val desiredHeight = MeasureSpec.getSize(heightMeasureSpec)

        val selfWidth = dpToPx(SELF_WIDTH_DP).toInt()
        val selfHeight = dpToPx(SELF_HEIGHT_DP).toInt()

        val width = when(widthMode) {
            MeasureSpec.EXACTLY -> desiredWidth
            MeasureSpec.AT_MOST -> min(desiredWidth, selfWidth)
            else -> selfWidth
        }

        val height = when(heightMode) {
            MeasureSpec.EXACTLY -> desiredHeight
            MeasureSpec.AT_MOST -> min(desiredHeight, selfHeight)
            else -> selfHeight
        }

        setMeasuredDimension(width, height)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
    }

    companion object {
        const val SELF_WIDTH_DP = 100f
        const val SELF_HEIGHT_DP = 100f
    }

}