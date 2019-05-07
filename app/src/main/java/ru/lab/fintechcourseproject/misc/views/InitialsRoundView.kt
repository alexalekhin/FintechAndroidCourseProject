package ru.lab.fintechcourseproject.misc.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import androidx.core.widget.TextViewCompat
import ru.lab.fintechcourseproject.R


class InitialsRoundView : FrameLayout {

    private var _roundColor: Int = Color.BLACK
    private var _initials: String? = "AB"
    private var _textColor: Int = Color.WHITE

    private lateinit var circle: CircleView
    private lateinit var initialsText: TextView

    var initials: String?
        get() = _initials
        set(value) {
            _initials = value
            initialsText.text = _initials
            initialsText.invalidate()
        }

    var roundColor: Int
        get() = _roundColor
        set(value) {
            _roundColor = value
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
            attrs, R.styleable.InitialsRoundView, defStyle, 0
        )

        _roundColor = a.getColor(R.styleable.InitialsRoundView_roundColor, _roundColor)

        _initials = a.getString(R.styleable.InitialsRoundView_initials) ?: _initials

        circle = CircleView(context).apply {
            (layoutParams as FrameLayout.LayoutParams?)?.apply {
                width = ViewGroup.LayoutParams.MATCH_PARENT
                height = ViewGroup.LayoutParams.MATCH_PARENT
            }
        }
        addView(circle)
        initialsText = TextView(context).apply {
            text = _initials
            setTextSize(TypedValue.COMPLEX_UNIT_SP, STD_TEXT_SIZE)
            setTextColor(_textColor)
            gravity = Gravity.CENTER

            (layoutParams as FrameLayout.LayoutParams?)?.apply {
                width = ViewGroup.LayoutParams.MATCH_PARENT
                height = ViewGroup.LayoutParams.MATCH_PARENT
            }
        }
        TextViewCompat
            .setAutoSizeTextTypeUniformWithConfiguration(
                initialsText,
                MIN_TEXT_SIZE,
                MAX_TEXT_SIZE,
                TEXT_SIZE_STEP,
                TypedValue.COMPLEX_UNIT_SP
            )
        addView(initialsText)
        a.recycle()

    }

    override fun onDraw(canvas: Canvas?) {
        initialsText.text = _initials
        circle.circleColor = _roundColor
        super.onDraw(canvas)
    }

    companion object {
        private const val STD_TEXT_SIZE: Float = 16.0f
        private const val MIN_TEXT_SIZE: Int = 16
        private const val MAX_TEXT_SIZE: Int = 36
        private const val TEXT_SIZE_STEP: Int = 1
    }
}
