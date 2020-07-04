package jp.neechan.akari.dictionary.base_ui.presentation.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import jp.neechan.akari.dictionary.base_ui.R
import java.util.Locale
import kotlin.math.abs

class AvatarView : View {

    private var letter: String = ""

    private val circleColors = context.resources.getIntArray(R.array.avatarColors)
    private val circlePaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val textPaint = Paint(Paint.ANTI_ALIAS_FLAG)

    constructor(context: Context) : super(context)

    constructor(context: Context, attributeSet: AttributeSet?) :
            super(context, attributeSet)

    constructor(context: Context, attributeSet: AttributeSet?, defStyleAttr: Int) :
            super(context, attributeSet, defStyleAttr)

    init {
        textPaint.apply {
            color = Color.WHITE
            textSize = context.resources.getDimensionPixelSize(R.dimen.avatar_text_size).toFloat()
            textAlign = Paint.Align.CENTER
        }
    }

    fun setText(text: String) {
        letter = text.getFirstCharacter().toUpperCase(Locale.getDefault())
        circlePaint.color = circleColors[abs(text.hashCode()) % circleColors.size]
        invalidate()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.apply {
            val centerX = width / 2f
            val centerY = height / 2f

            val circleRadius = width / 2f
            drawCircle(centerX, centerY, circleRadius, circlePaint)

            val textStartX = centerX
            val textStartY = centerY - (textPaint.descent() + textPaint.ascent()) / 2f
            drawText(letter, textStartX, textStartY, textPaint)
        }
    }

    private fun String.getFirstCharacter(): String {
        return if (isNotEmpty()) this[0].toString() else ""
    }
}
