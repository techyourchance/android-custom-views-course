package com.techyourchance.androidviews.exercises._05_

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import com.techyourchance.androidviews.CustomViewScaffold

class MyCheckmarkView : CustomViewScaffold {

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
    }

    fun stopAnimation() {
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
    }

    companion object {
        const val LINE_SIZE_DP = 15f
    }
}