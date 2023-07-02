package com.techyourchance.androidviews.general.extensions

import android.view.MotionEvent
import com.techyourchance.androidviews.general.MathUtils

object MotionEventExtensions {

    fun MotionEvent.distanceTo(x: Float, y: Float): Float {
        return MathUtils.pointsDistance(this.x, this.y, x, y)
    }
}