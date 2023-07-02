package com.techyourchance.androidviews.general

import kotlin.math.sqrt

object MathUtils {

    /**
     * Compute the Euclidean distance between two points using the Pythagorean theorem
     */
    fun pointsDistance(x1: Float, y1: Float, x2: Float, y2: Float): Float {
        val dx = x1 - x2
        val dy = y1 - y2
        return sqrt(dx * dx + dy * dy)
    }

}