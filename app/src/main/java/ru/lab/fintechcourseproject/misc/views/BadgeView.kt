package ru.lab.fintechcourseproject.misc.views

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.Gravity
import android.widget.TextView
import ru.lab.fintechcourseproject.R


class BadgeView : TextView {
    // text
    private var defaultText: String? = "123"
    private var defaultColor: Int = Color.WHITE

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        init()
    }

    private fun init() {
        // prepare main objs
        background = context.getDrawable(R.drawable.rect_badge)

        // prepare text
        text = defaultText
        gravity = Gravity.CENTER
        typeface = Typeface.DEFAULT_BOLD
        setTextColor(defaultColor)
    }
}
