package com.techyourchance.androidviews.solutions._09_

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PointF
import android.os.Parcel
import android.os.Parcelable
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.graphics.withSave
import com.techyourchance.androidviews.CustomViewScaffold
import com.techyourchance.androidviews.R
import com.techyourchance.androidviews.general.extensions.MotionEventExtensions.distanceTo

@SuppressLint("ClickableViewAccessibility")
class SolutionExercise9View : CustomViewScaffold {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var dotColor = 0
    private var crosshairColor = 0

    private var crosshairXFraction = 0.5f
    private var crosshairYFraction = 0.5f
    private val crosshairPos = PointF()
    private var crosshairCircleRadius = 0f
    private var crosshairDotRadius = 0f
    private var crosshairHairLength = 0f

    private var isDragged = false
    private var lastMotionEventPos = PointF()

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

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (event == null) {
            return super.onTouchEvent(event)
        }
        return if (isDownEventInsideCrosshair(event)) {
            isDragged = true
            lastMotionEventPos.set(event.x, event.y)
            true
        } else if (isDragged && event.action == MotionEvent.ACTION_MOVE) {
            val dx = event.x - lastMotionEventPos.x
            val dy = event.y - lastMotionEventPos.y
            crosshairPos.set(crosshairPos.x + dx, crosshairPos.y + dy)
            crosshairXFraction = crosshairPos.x / width
            crosshairYFraction = crosshairPos.y / height
            lastMotionEventPos.set(event.x, event.y)
            invalidate()
            true
        } else {
            isDragged = false
            false
        }
    }

    private fun isDownEventInsideCrosshair(event: MotionEvent): Boolean {
        val isDownEvent = event.action == MotionEvent.ACTION_DOWN
        val isInsideCrosshair = event.distanceTo(crosshairPos.x, crosshairPos.y) <= crosshairCircleRadius
        return isDownEvent && isInsideCrosshair
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        crosshairPos.set(crosshairXFraction * w, crosshairYFraction * h)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        canvas.save() // not mandatory in this case, but a good practice

        canvas.translate(crosshairPos.x, crosshairPos.y)

        paint.style = Paint.Style.STROKE
        paint.color = crosshairColor

        canvas.drawCircle(0f, 0f, crosshairCircleRadius, paint)

        val hairXStart = crosshairCircleRadius - crosshairHairLength / 2
        val hairXEnd = crosshairCircleRadius + crosshairHairLength / 2

        canvas.withSave {// equivalent to wrapping in save()/restore()
            canvas.rotate(45f)
            repeat(4) {
                canvas.drawLine(hairXStart, 0f, hairXEnd, 0f, paint)
                canvas.rotate(90f)
            }
        }

        paint.style = Paint.Style.FILL
        paint.color = dotColor
        canvas.drawCircle(0f, 0f, crosshairDotRadius, paint)

        canvas.restore() // not mandatory in this case, but a good practice
    }

    override fun onSaveInstanceState(): Parcelable {
        val superSavedState = super.onSaveInstanceState()
        return MySavedState(superSavedState, crosshairXFraction, crosshairYFraction)
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        if (state is MySavedState) {
            super.onRestoreInstanceState(state.superSavedState)
            crosshairXFraction = state.crosshairXFraction
            crosshairYFraction = state.crosshairYFraction
        } else {
            super.onRestoreInstanceState(state)
        }
    }

    companion object {
        private const val CROSSHAIR_CIRCLE_RADIUS_DP = 40f
        private const val CROSSHAIR_DOT_RADIUS_DP = 4f
        private const val CROSSHAIR_HAIR_LENGTH_DP = 20f
        private const val CROSSHAIR_LINE_SIZE_DP = 2f
    }

    private class MySavedState: BaseSavedState {

        val superSavedState: Parcelable?
        val crosshairXFraction: Float
        val crosshairYFraction: Float

        constructor(
            superSavedState: Parcelable?,
            crosshairXFraction: Float,
            crosshairYFraction: Float,
        ): super(superSavedState) {
            this.superSavedState = superSavedState
            this.crosshairXFraction = crosshairXFraction
            this.crosshairYFraction = crosshairYFraction
        }

        constructor(parcel: Parcel) : super(parcel) {
            this.superSavedState = parcel.readParcelable(null)
            this.crosshairXFraction = parcel.readFloat()
            this.crosshairYFraction = parcel.readFloat()
        }

        override fun writeToParcel(out: Parcel, flags: Int) {
            super.writeToParcel(out, flags)
            out.writeParcelable(superSavedState, flags)
            out.writeFloat(crosshairXFraction)
            out.writeFloat(crosshairYFraction)
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