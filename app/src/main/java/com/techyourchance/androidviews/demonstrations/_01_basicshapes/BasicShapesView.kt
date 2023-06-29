package com.techyourchance.androidviews.demonstrations._01_basicshapes
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.graphics.Color
import com.techyourchance.androidviews.CustomViewScaffold

class BasicShapesView : CustomViewScaffold {

    private val paint = Paint()

    private var lineXLeft: Float = 0f
    private var lineXRight: Float = 0f
    private var lineYPos: Float = 0f
    private var lineHeight: Float = 0f

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

        val lineMarginHorizontal = dpToPx(LINE_MARGIN_HORIZONTAL_DP)
        lineXLeft = lineMarginHorizontal
        lineXRight = w - lineMarginHorizontal
        lineYPos = height * LINE_Y_POS_FRACTION
        lineHeight = dpToPx(LINE_HEIGHT_DP)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        paint.color = Color.BLACK
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = lineHeight

        // Draw the line
        canvas.drawLine(lineXLeft, lineYPos, lineXRight, lineYPos, paint)
    }


    companion object {
        const val LINE_Y_POS_FRACTION = 0.2f
        const val LINE_MARGIN_HORIZONTAL_DP = 20f
        const val LINE_HEIGHT_DP = 5f
    }
}