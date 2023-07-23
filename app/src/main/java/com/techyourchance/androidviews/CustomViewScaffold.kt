package com.techyourchance.androidviews

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import com.techyourchance.androidviews.demonstrations._08_path_animation.PathAnimationView

open class CustomViewScaffold: View {

    var showBorder = false

    private val viewBorderPaint = Paint()
    private val viewBorderRect = RectF()

    // Secondary constructors (don't use @JvmOverloads as it can lead to bugs).
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(
        context,
        attrs,
        defStyleAttr,
        defStyleRes
    )

    // Will be called when this View changes its size.
    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        val viewBorderLineSize = dpToPx(PathAnimationView.VIEW_BORDER_LINE_SIZE_DP)
        val viewBorderPadding = viewBorderLineSize / 2
        viewBorderRect.set(
            viewBorderPadding,
            viewBorderPadding,
            width.toFloat() - viewBorderPadding,
            height.toFloat() - viewBorderPadding
        )

        viewBorderPaint.color = Color.RED
        viewBorderPaint.style = Paint.Style.STROKE
        viewBorderPaint.strokeWidth = viewBorderLineSize
        viewBorderPaint.pathEffect = DashPathEffect(floatArrayOf(10f, 10f), 0f)
    }

    // Will be called with clean Canvas when this View needs to redraw itself on the screen.
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (showBorder) {
            canvas.drawRect(viewBorderRect, viewBorderPaint)
        }
    }

    protected fun dpToPx(dp: Float): Float {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dp,
            context.resources.displayMetrics,
        )
    }


    companion object {
        const val VIEW_BORDER_LINE_SIZE_DP = 1f
    }
}