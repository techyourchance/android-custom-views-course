package com.techyourchance.androidviews.demonstrations._12_matrix_transformation

import android.content.Context
import android.graphics.*
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
    private val transformationMatrix = Matrix()
    private val arrowHandleStart = PointF()
    private val arrowHandleCenter = PointF()

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

    var innerRotation = 0f
        set(value) {
            field = value
            invalidate()
        }

    var innerScale = 1f
        set(value) {
            field = value
            paint.strokeWidth = dpToPx(LINE_SIZE_DP) * innerScale
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
        paint.strokeWidth = dpToPx(LINE_SIZE_DP) * innerScale
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        val minDimension = min(w, h)
        val arrowHandleLength = minDimension * ARROW_HANDLE_SIZE_FRACTION
        val arrowHeadLength = minDimension * ARROW_HEAD_SIZE_FRACTION
        val marginHorizontal = (w - arrowHandleLength) / 2f
        val arrowHeadAngleRad = Math.toRadians(ARROW_HEAD_ANGLE_DEGREES.toDouble())

        arrowHandleStart.set(marginHorizontal, h / 2f)
        val arrowHandleEnd = PointF(w - marginHorizontal, h / 2f)
        arrowHandleCenter.set(
            arrowHandleStart.x + (arrowHandleEnd.x - arrowHandleStart.x) / 2f,
            arrowHandleStart.y
        )
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

        drawnPath.reset()
        drawnPath.set(referencePath)

        transformationMatrix.reset()
        transformationMatrix.postScale(innerScale, innerScale, arrowHandleCenter.x, arrowHandleCenter.y)
        transformationMatrix.postRotate(innerRotation, arrowHandleStart.x, arrowHandleStart.y)
        transformationMatrix.postTranslate(innerTranslationX, innerTranslationY)
        drawnPath.transform(transformationMatrix)

        canvas.drawPath(drawnPath, paint)
    }

    companion object {
        private const val ARROW_HANDLE_SIZE_FRACTION = 0.5f
        private const val ARROW_HEAD_SIZE_FRACTION = 0.05f
        private const val ARROW_HEAD_ANGLE_DEGREES = 30f
        private const val LINE_SIZE_DP = 5f

    }
}