package com.techyourchance.androidviews.exercises._07_

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import androidx.core.content.ContextCompat
import com.techyourchance.androidviews.CustomViewScaffold
import com.techyourchance.androidviews.R

class CouponsView : CustomViewScaffold {

    private var totalCoupons = 0
    private var usedCoupons = 0

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(
        context,
        attrs,
        defStyleAttr,
        defStyleRes
    )

    fun setCoupons(totalCoupons: Int, usedCoupons: Int) {
        this.totalCoupons = totalCoupons
        this.usedCoupons = usedCoupons
        if (width > 0 && height > 0) {
            // ...
            invalidate()
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
    }

    companion object {
        private const val LINE_SIZE_DP = 2f
        private const val CELL_MIN_PADDING_DP = 5f
        private const val CORNER_RADIUS_DP = 20f
    }

}