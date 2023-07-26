package com.techyourchance.androidviews.demonstrations._09_text

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.techyourchance.androidviews.CustomViewScaffold
import com.techyourchance.androidviews.R

class AutoScalingTextView : CustomViewScaffold {

    private var text = ""
    private val textPaint = Paint(Paint.ANTI_ALIAS_FLAG)

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

    fun setText(text: String) {
        this.text = text
        textPaint.textSize = calculateMaxTextSize(text, width.toFloat(), height.toFloat())
        invalidate()
    }

    private fun calculateMaxTextSize(text: String, maxWidth: Float, maxHeight: Float): Float {
        var size = 1f
        val bounds = Rect()

        // Binary search can be used for better performance
        while (true) {
            textPaint.textSize = size
            textPaint.getTextBounds(text, 0, text.length, bounds)
            if (bounds.width() > maxWidth || bounds.height() > maxHeight) {
                return size - 1
            }
            size++
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        setText(text)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val x = width / 2f
        val y = (height / 2f) - ((textPaint.descent() + textPaint.ascent()) / 2f)
        canvas.drawText(text, x, y, textPaint)
    }
}