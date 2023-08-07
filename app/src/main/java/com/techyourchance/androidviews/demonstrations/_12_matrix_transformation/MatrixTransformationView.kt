package com.techyourchance.androidviews.demonstrations._12_matrix_transformation

import android.content.Context
import android.graphics.Canvas
import android.graphics.Matrix
import android.graphics.Paint
import android.graphics.Path
import android.graphics.PointF
import android.util.AttributeSet
import androidx.core.content.ContextCompat
import com.techyourchance.androidviews.CustomViewScaffold
import com.techyourchance.androidviews.R
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.sin

class MatrixTransformationView : CustomViewScaffold {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val referencePath = Path()
    private val drawnPath = Path()
    private val translationMatrix = Matrix()

    var innerTranslationX = 0f
        set(value) {
            field = value
            invalidate()
        }

    var innerTranslationY = 0f
        set(value) {
            field = value
            invalidate()
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

    init {
        paint.color = ContextCompat.getColor(context, R.color.primary_variant)
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = dpToPx(LINE_SIZE_DP)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        val minDimension = min(w, h)
        val arrowHandleLength = minDimension * ARROW_HANDLE_SIZE_FRACTION
        val arrowHeadLength = minDimension * ARROW_HEAD_SIZE_FRACTION
        val marginHorizontal = (w - arrowHandleLength) / 2f
        val arrowHeadAngleRad = Math.toRadians(ARROW_HEAD_ANGLE_DEGREES.toDouble())

        val arrowHandleStart = PointF(marginHorizontal, h / 2f)
        val arrowHandleEnd = PointF(w - marginHorizontal, h / 2f)
        val arrowHeadPoint1 = PointF(
            (arrowHandleEnd.x - arrowHeadLength * cos(arrowHeadAngleRad)).toFloat(),
            (arrowHandleEnd.y - arrowHeadLength * sin(arrowHeadAngleRad)).toFloat()
        )
        val arrowHeadPoint2 = PointF(
            (arrowHandleEnd.x - arrowHeadLength * cos(arrowHeadAngleRad)).toFloat(),
            (arrowHandleEnd.y + arrowHeadLength * sin(arrowHeadAngleRad)).toFloat()
        )

        referencePath.reset()
        referencePath.moveTo(arrowHandleStart.x, arrowHandleStart.y)
        referencePath.lineTo(arrowHandleEnd.x, arrowHandleEnd.y)
        referencePath.moveTo(arrowHeadPoint1.x, arrowHeadPoint1.y)
        referencePath.lineTo(arrowHandleEnd.x, arrowHandleEnd.y)
        referencePath.lineTo(arrowHeadPoint2.x, arrowHeadPoint2.y)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        translationMatrix.reset()
        translationMatrix.postTranslate(innerTranslationX, innerTranslationY)

        drawnPath.reset()
        drawnPath.set(referencePath)
        drawnPath.transform(translationMatrix)

        canvas.drawPath(drawnPath, paint)
    }

    companion object {
        private const val ARROW_HANDLE_SIZE_FRACTION = 0.5f
        private const val ARROW_HEAD_SIZE_FRACTION = 0.05f
        private const val ARROW_HEAD_ANGLE_DEGREES = 30f
        private const val LINE_SIZE_DP = 5f

    }
}