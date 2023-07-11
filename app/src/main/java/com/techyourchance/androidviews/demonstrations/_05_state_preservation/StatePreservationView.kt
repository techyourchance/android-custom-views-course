package com.techyourchance.androidviews.demonstrations._05_state_preservation

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
import com.techyourchance.androidviews.general.extensions.MotionEventExtensions.distanceTo
import kotlin.math.min

@SuppressLint("ClickableViewAccessibility")
class StatePreservationView : CustomViewScaffold {

    private val paint = Paint()

    private var circleXCenterFraction = 0.5f
    private var circleYCenterFraction = 0.5f

    private var circleXCenter = 0f
    private var circleYCenter = 0f
    private var circleRadius = 0f

    private var lastMotionEventX = 0f
    private var lastMotionEventY = 0f
    private var isDragged = false

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
        val distanceFromCircleCenter = event.distanceTo(circleXCenter, circleYCenter)
        return if (distanceFromCircleCenter <= circleRadius && event.action == MotionEvent.ACTION_DOWN) {
            isDragged = true
            lastMotionEventX = event.x
            lastMotionEventY = event.y
            true
        } else if (isDragged && event.action == MotionEvent.ACTION_MOVE) {
            val dx = event.x - lastMotionEventX
            val dy = event.y - lastMotionEventY
            circleXCenter += dx
            circleYCenter += dy
            circleXCenterFraction = circleXCenter / width
            circleYCenterFraction = circleYCenter / height
            lastMotionEventX = event.x
            lastMotionEventY = event.y
            invalidate()
            true
        } else {
            isDragged = false
            false
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        circleXCenter = w * circleXCenterFraction
        circleYCenter = h * circleYCenterFraction
        circleRadius = min(w.toFloat(), h.toFloat()) / 6f
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        paint.color = ContextCompat.getColor(context, R.color.primary_variant)
        paint.style = Paint.Style.FILL
        canvas.drawCircle(circleXCenter, circleYCenter, circleRadius, paint)
    }

    override fun onSaveInstanceState(): Parcelable {
        val superSavedState = super.onSaveInstanceState()
        return CircleSavedState(superSavedState, circleXCenterFraction, circleYCenterFraction)
    }

    override fun onRestoreInstanceState(state: Parcelable) {
        if (state is CircleSavedState) {
            super.onRestoreInstanceState(state.superSavedState)
            circleXCenterFraction = state.circleXCenterFraction
            circleYCenterFraction = state.circleYCenterFraction
            invalidate()
        } else {
            super.onRestoreInstanceState(state)
        }
    }

    private data class CircleSavedState(
        var superSavedState: Parcelable?,
        var circleXCenterFraction: Float,
        var circleYCenterFraction: Float,
    ) : View.BaseSavedState(superSavedState) {

        constructor(parcel: Parcel) : this(
            parcel.readParcelable(View.BaseSavedState::class.java.classLoader),
            parcel.readFloat(),
            parcel.readFloat(),
        )

        override fun writeToParcel(out: Parcel, flags: Int) {
            super.writeToParcel(out, flags)
            out.writeParcelable(superSavedState, flags)
            out.writeFloat(circleXCenterFraction)
            out.writeFloat(circleYCenterFraction)
        }

        companion object CREATOR : Parcelable.Creator<CircleSavedState> {
            override fun createFromParcel(parcel: Parcel): CircleSavedState {
                return CircleSavedState(parcel)
            }

            override fun newArray(size: Int): Array<CircleSavedState?> {
                return arrayOfNulls(size)
            }
        }
    }


}