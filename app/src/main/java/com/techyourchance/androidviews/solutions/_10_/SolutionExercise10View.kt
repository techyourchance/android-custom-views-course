package com.techyourchance.androidviews.solutions._10_

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Matrix
import android.graphics.Rect
import android.os.Parcel
import android.os.Parcelable
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.view.ScaleGestureDetector.SimpleOnScaleGestureListener
import android.view.View
import androidx.core.content.ContextCompat
import com.techyourchance.androidviews.CustomViewScaffold
import com.techyourchance.androidviews.R
import com.techyourchance.androidviews.demonstrations._16_rotation_gesture_detector.RotationGestureDetector
import kotlin.math.min

class SolutionExercise10View : CustomViewScaffold {

    private val drawable = ContextCompat.getDrawable(context, R.drawable.ic_smiley)!!
    private val drawableBoundsRect = Rect()
    private val transformationMatrix = Matrix()

    private var drawableTranslationXFraction = 0f
    private var drawableTranslationYFraction = 0f

    private var drawableTranslationX = 0f
        set(value) {
            field = value
            updateTransformationMatrix()
        }

    private var drawableTranslationY = 0f
        set(value) {
            field = value
            updateTransformationMatrix()
        }

    private var drawableScale = 1f
        set(value) {
            field = value
            updateTransformationMatrix()
        }

    private var drawableRotation = 0f
        set(value) {
            field = value
            updateTransformationMatrix()
        }

    private var isConsumeNextGesture = false

    private val onGestureListener = object : GestureDetector.SimpleOnGestureListener() {

        override fun onScroll(
            e1: MotionEvent?,
            e2: MotionEvent,
            distanceX: Float,
            distanceY: Float
        ): Boolean {
            return if (isConsumeNextGesture) {
                drawableTranslationX -= distanceX
                drawableTranslationY -= distanceY
                drawableTranslationXFraction -= distanceX / width
                drawableTranslationYFraction -= distanceY / height
                invalidate()
                true
            } else {
                false
            }
        }
    }

    private val gestureDetector = GestureDetector(context, onGestureListener)

    private val scaleGestureDetector: ScaleGestureDetector = ScaleGestureDetector(context, object : SimpleOnScaleGestureListener() {
        override fun onScaleBegin(detector: ScaleGestureDetector): Boolean {
            return isConsumeNextGesture
        }

        override fun onScale(detector: ScaleGestureDetector): Boolean {
            drawableScale *= detector.scaleFactor
            invalidate()
            return true
        }
    })

    private val rotationGestureDetector: RotationGestureDetector = RotationGestureDetector().apply {
        onRotationGestureListener = object : RotationGestureDetector.OnRotationGestureListener {
            override fun onRotationBegin(): Boolean {
                return isConsumeNextGesture
            }

            override fun onRotationEnd() {}

            override fun onRotation(angleDiff: Float): Boolean {
                drawableRotation -= angleDiff
                invalidate()
                return true
            }
        }
    }

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(
        context,
        attrs,
        defStyleAttr,
        defStyleRes
    )

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (event == null) {
            return super.onTouchEvent(null)
        }
        if (event.actionMasked == MotionEvent.ACTION_DOWN) {
            isConsumeNextGesture = isWithinDrawable(event)
        }
        val gestureDetectorResult = gestureDetector.onTouchEvent(event)
        val scaleGestureDetectorResult = scaleGestureDetector.onTouchEvent(event)
        val rotationGestureDetectorResult = rotationGestureDetector.onTouchEvent(event)
        return gestureDetectorResult
                || scaleGestureDetectorResult
                || rotationGestureDetectorResult
                || super.onTouchEvent(event)
    }

    private fun isWithinDrawable(event: MotionEvent): Boolean {
        // The idea here is to perform inverse transformation on the coordinates of the MotionEvent,
        // which will bring them into the coordinate system of the original Drawable's bounding
        // rectangle, and then check whether the transformed coordinates fall within that bounding
        // rectangle.

        // 1. Compute the inverse of the matrix that transforms the Drawable
        val invertedTransformationMatrix = Matrix()
        if (!transformationMatrix.invert(invertedTransformationMatrix)) {
            throw RuntimeException("failed to invert the transformation matrix")
        }

        // 2. Transform the MotionEvent's coordinates using the inverted matrix
        val eventCoordinates = floatArrayOf(event.x, event.y)
        invertedTransformationMatrix.mapPoints(eventCoordinates)

        // 3. Check whether the resulting point is within the original (before transformation) bounds of the Drawable.
        return drawableBoundsRect.contains(
            eventCoordinates[0].toInt(),
            eventCoordinates[1].toInt(),
        )
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        val drawableMaxSize = min(w, h) * DRAWABLE_DEFAULT_SIZE_FRACTION
        val drawableIntrinsicRatio = 1f * drawable.intrinsicWidth / drawable.intrinsicHeight

        val drawableWidth = if (drawableIntrinsicRatio > 1) { 
            drawableMaxSize
        } else {
            drawableMaxSize * drawableIntrinsicRatio 
        }
        val drawableHeight = if (drawableIntrinsicRatio > 1) {
            drawableMaxSize / drawableIntrinsicRatio
        } else {
            drawableMaxSize
        }

        val marginHorizontal = ((w - drawableWidth) / 2).toInt()
        val marginVertical = ((h - drawableHeight) / 2).toInt()

        drawableBoundsRect.set(marginHorizontal, marginVertical, w - marginHorizontal, h - marginVertical)
        drawable.bounds = drawableBoundsRect

        drawableTranslationX = w * drawableTranslationXFraction
        drawableTranslationY = h * drawableTranslationYFraction
    }

    private fun updateTransformationMatrix() {
        transformationMatrix.apply {
            reset()
            postRotate(drawableRotation, width / 2f, height / 2f)
            postScale(drawableScale, drawableScale, width / 2f, height / 2f)
            postTranslate(drawableTranslationX, drawableTranslationY)
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.save()
        canvas.concat(transformationMatrix)
        drawable.draw(canvas)
        canvas.restore()
    }

    override fun onSaveInstanceState(): Parcelable {
        val superSavedState = super.onSaveInstanceState()
        return MySavedState(
            superSavedState,
            drawableTranslationXFraction,
            drawableTranslationYFraction,
            drawableScale,
            drawableRotation,
        )
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        if (state is MySavedState) {
            super.onRestoreInstanceState(state.superSavedState)
            drawableTranslationXFraction = state.drawableTranslationXFraction
            drawableTranslationYFraction = state.drawableTranslationYFraction
            drawableScale = state.drawableScale
            drawableRotation = state.drawableRotation
        } else {
            super.onRestoreInstanceState(state)
        }
    }

    companion object {
        const val DRAWABLE_DEFAULT_SIZE_FRACTION = 0.5f
    }

    private data class MySavedState(
        var superSavedState: Parcelable?,
        var drawableTranslationXFraction: Float,
        var drawableTranslationYFraction: Float,
        var drawableScale: Float,
        var drawableRotation: Float,
    ) : View.BaseSavedState(superSavedState) {

        constructor(parcel: Parcel) : this(
            parcel.readParcelable(View.BaseSavedState::class.java.classLoader),
            parcel.readFloat(),
            parcel.readFloat(),
            parcel.readFloat(),
            parcel.readFloat(),
        )

        override fun writeToParcel(out: Parcel, flags: Int) {
            super.writeToParcel(out, flags)
            out.writeParcelable(superSavedState, flags)
            out.writeFloat(drawableTranslationXFraction)
            out.writeFloat(drawableTranslationYFraction)
            out.writeFloat(drawableScale)
            out.writeFloat(drawableRotation)
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