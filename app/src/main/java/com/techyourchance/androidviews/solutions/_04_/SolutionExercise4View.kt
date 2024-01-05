package com.techyourchance.androidviews.solutions._04_

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.os.Parcel
import android.os.Parcelable
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.core.content.ContextCompat
import com.techyourchance.androidviews.CustomViewScaffold
import com.techyourchance.androidviews.R
import com.techyourchance.androidviews.exercises._03_.SliderChangeListener
import com.techyourchance.androidviews.general.extensions.MotionEventExtensions.distanceTo
import kotlin.math.max
import kotlin.math.min

@SuppressLint("ClickableViewAccessibility")
class SolutionExercise4View : CustomViewScaffold {

    var sliderChangeListener: SliderChangeListener? = null
    var value = 0.5f
        set(value) {
            field = value
            updateThumbPosition()
            invalidate()
        }

    private val paint = Paint()

    private var lineXLeft = 0f
    private var lineXRight = 0f
    private var lineYPos = 0f
    private var lineHeight = 0f
    private var lineColor = 0

    private var thumbXCenter = 0f
    private var thumbYCenter = 0f
    private var thumbRadius = 0f
    private var thumbColor = 0

    private var isDragged = false
    private var lastMotionEventX = 0f

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(
        context,
        attrs,
        defStyleAttr,
        defStyleRes
    )

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (event == null) {
            return super.onTouchEvent(event)
        }
        return if (isDownEventInsideThumb(event)) {
            isDragged = true
            lastMotionEventX = event.x
            true
        } else if (isDragged && event.action == MotionEvent.ACTION_MOVE) {
            val dx = event.x - lastMotionEventX
            thumbXCenter = if (event.x < lineXLeft) {
                lineXLeft
            } else if (event.x > lineXRight) {
                lineXRight
            } else if (dx < 0) {
                max(thumbXCenter + dx, lineXLeft)
            } else {
                min(thumbXCenter + dx, lineXRight)
            }
            invalidate()
            lastMotionEventX = event.x
            updateValue()
            true
        } else {
            isDragged = false
            false
        }
    }

    private fun updateValue() {
        val lineWidth = lineXRight - lineXLeft
        val relativeThumbPosition = thumbXCenter - lineXLeft
        val oldValue = value
        value = 1.0f * relativeThumbPosition / lineWidth
        if (value != oldValue) {
            sliderChangeListener?.onValueChanged(value)
        }
    }

    private fun isDownEventInsideThumb(event: MotionEvent): Boolean {
        val isDownEvent = event.action == MotionEvent.ACTION_DOWN
        val isInsideThumb = event.distanceTo(thumbXCenter, thumbYCenter) <= thumbRadius
        return isDownEvent && isInsideThumb
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        thumbRadius = dpToPx(THUMB_RADIUS_DP)

        lineXLeft = thumbRadius
        lineXRight = w - thumbRadius
        lineYPos = height * 0.5f
        lineHeight = dpToPx(LINE_HEIGHT_DP)
        lineColor = ContextCompat.getColor(context, R.color.gray_light)

        updateThumbPosition()
    }

    private fun updateThumbPosition() {
        thumbXCenter = lineXLeft + value * (lineXRight - lineXLeft)
        thumbYCenter = lineYPos
        thumbColor = ContextCompat.getColor(context, R.color.primary_variant)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        paint.style = Paint.Style.FILL
        paint.strokeWidth = lineHeight

        // Draw the line
        paint.color = lineColor
        canvas.drawLine(lineXLeft, lineYPos, lineXRight, lineYPos, paint)

        // Draw the circle
        paint.color = thumbColor
        canvas.drawCircle(thumbXCenter, thumbYCenter, thumbRadius, paint)
    }

    override fun onSaveInstanceState(): Parcelable {
        val superSavedState = super.onSaveInstanceState()
        return MySavedState(superSavedState, value)
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        if (state is MySavedState) {
            super.onRestoreInstanceState(state.superSavedState)
            value = state.sliderValue
        } else {
            super.onRestoreInstanceState(state)
        }

    }

    companion object {
        const val LINE_HEIGHT_DP = 5f
        const val THUMB_RADIUS_DP = 15f
    }


    private class MySavedState: BaseSavedState {

        val superSavedState: Parcelable?
        val sliderValue: Float

        constructor(superSavedState: Parcelable?, sliderValue: Float): super(superSavedState) {
            this.superSavedState = superSavedState
            this.sliderValue = sliderValue
        }

        constructor(parcel: Parcel) : super(parcel) {
            this.superSavedState = parcel.readParcelable(null)
            this.sliderValue = parcel.readFloat()
        }

        override fun writeToParcel(out: Parcel, flags: Int) {
            super.writeToParcel(out, flags)
            out.writeParcelable(superSavedState, flags)
            out.writeFloat(sliderValue)
        }

        companion object CREATOR : Parcelable.Creator<MySavedState> {
            override fun createFromParcel(parcel: Parcel): MySavedState {
                return MySavedState(parcel)
            }

            override fun newArray(size: Int): Array<MySavedState?> {
                return arrayOfNulls(size)
            }
        }
    }
}