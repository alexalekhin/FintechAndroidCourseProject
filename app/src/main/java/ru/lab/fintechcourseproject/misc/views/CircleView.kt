package ru.lab.fintechcourseproject.misc.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import ru.lab.fintechcourseproject.R

class CircleView : View {

    private var _circleColor: Int = Color.BLACK
    private lateinit var circlePaint: Paint

    /**
     * The color
     */
    var circleColor: Int
        get() = _circleColor
        set(value) {
            _circleColor = value
        }

    constructor(context: Context) : super(context) {
        init(null, 0)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(attrs, 0)
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        init(attrs, defStyle)
    }

    private fun init(attrs: AttributeSet?, defStyle: Int) {
        val a = context.obtainStyledAttributes(
            attrs, R.styleable.CircleView, defStyle, 0
        )

        _circleColor = a.getColor(
            R.styleable.CircleView_circleColor,
            _circleColor
        )

        a.recycle()

        circlePaint = Paint()
    }


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val contentWidth = width - paddingLeft - paddingRight
        val contentHeight = height - paddingTop - paddingBottom
        circlePaint.apply {
            flags = Paint.ANTI_ALIAS_FLAG
            color = _circleColor
        }
        canvas.drawCircle(
            (contentWidth / 2).toFloat(),
            (contentHeight / 2).toFloat(),
            (contentWidth / 2).toFloat(),
            circlePaint
        )
    }
}
