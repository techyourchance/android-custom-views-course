package com.techyourchance.androidviews.demonstrations._16_rotation_gesture_detector

import android.graphics.PointF
import android.view.MotionEvent
import kotlin.math.atan2

class RotationGestureDetector {

    interface OnRotationGestureListener {
        fun onRotationBegin(): Boolean
        fun onRotationEnd()
        fun onRotation(angleDiff: Float): Boolean
    }

    var onRotationGestureListener: OnRotationGestureListener? = null

    private var isTwoPointersDown = false
    private val lastPointer0Point = PointF()
    private val lastPointer1Point = PointF()

    fun onTouchEvent(event: MotionEvent): Boolean {
        val action = event.actionMasked
        val pointerIndex = event.actionIndex
        return when(action) {
            MotionEvent.ACTION_DOWN -> {
                lastPointer0Point.set(event.getX(0), event.getY(0))
                true
            }
            MotionEvent.ACTION_POINTER_DOWN -> {
                if (pointerIndex != 1) {
                    resetGesture()
                    false
                } else {
                    lastPointer1Point.set(event.getX(1), event.getY(1))
                    if (onRotationGestureListener?.onRotationBegin() == true) {
                        isTwoPointersDown = true
                        true
                    } else {
                        false
                    }
                }
            }
            MotionEvent.ACTION_MOVE -> {
                if (isTwoPointersDown) {
                    val newPointer0Point = PointF(event.getX(0), event.getY(0))
                    val newPointer1Point = PointF(event.getX(1), event.getY(1))
                    processNewPointersPoints(newPointer0Point, newPointer1Point)
                    true
                } else {
                    false
                }
            }
            else -> {
                resetGesture()
                false
            }
        }
    }

    private fun processNewPointersPoints(newPointer0Point: PointF, newPointer1Point: PointF) {
        val lastPointersDiffX = lastPointer0Point.x - lastPointer1Point.x
        val lastPointersDiffY = lastPointer0Point.y - lastPointer1Point.y

        val newPointersDiffX = newPointer0Point.x - newPointer1Point.x
        val newPointersDiffY = newPointer0Point.y - newPointer1Point.y

        val lastAngleRadians = atan2(lastPointersDiffY, lastPointersDiffX)
        val newAngleRadians = atan2(newPointersDiffY, newPointersDiffX)

        var lastAngleDegrees = Math.toDegrees(lastAngleRadians.toDouble()).toFloat()
        var newAngleDegrees = Math.toDegrees(newAngleRadians.toDouble()).toFloat()

        lastPointer0Point.set(newPointer0Point)
        lastPointer1Point.set(newPointer1Point)

        onRotationGestureListener?.onRotation(lastAngleDegrees - newAngleDegrees)
    }

    private fun resetGesture() {
        if (isTwoPointersDown) {
            onRotationGestureListener?.onRotationEnd()
        }
        isTwoPointersDown = false
    }

}