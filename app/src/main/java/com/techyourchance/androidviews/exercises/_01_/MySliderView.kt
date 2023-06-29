package com.techyourchance.androidviews.exercises._01_

import android.content.Context
import android.util.AttributeSet
import com.techyourchance.androidviews.CustomViewScaffold

class MySliderView : CustomViewScaffold {

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(
        context,
        attrs,
        defStyleAttr,
        defStyleRes
    )

}