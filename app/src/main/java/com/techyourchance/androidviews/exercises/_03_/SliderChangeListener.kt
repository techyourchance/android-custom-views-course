package com.techyourchance.androidviews.exercises._03_

interface SliderChangeListener {
    /**
     * Called whenever the value of the slider changes.
     * @param value a value in the range [0,1]
     */
    fun onValueChanged(value: Float)
}