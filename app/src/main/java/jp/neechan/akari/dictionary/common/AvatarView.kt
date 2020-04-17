package jp.neechan.akari.dictionary.common

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import jp.neechan.akari.dictionary.R
import java.util.*
import kotlin.math.abs

class AvatarView : View {

    private var text: String = ""
    private var letter: String = ""

    private val circleColors = context.resources.getIntArray(R.array.avatarColors)
    private val circlePaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val textPaint = Paint(Paint.ANTI_ALIAS_FLAG)

    constructor(context: Context) : super(context)

    constructor(context: Context, attributeSet: AttributeSet?)
            : super(context, attributeSet)

    constructor(context: Context, attributeSet: AttributeSet?, defStyleAttr: Int)
            : super(context, attributeSet, defStyleAttr)

    init {
        textPaint.apply {
            color = Color.WHITE
            textSize = context.resources.getDimensionPixelSize(R.dimen.word_avatar_text_size).toFloat()
            textAlign = Paint.Align.CENTER
        }
    }

    fun setText(text: String) {
        this.text = text
        this.letter = text.getFirstCharacter().toUpperCase(Locale.getDefault())
        invalidate()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.apply {
            val centerX = width / 2f
            val centerY = height / 2f

            val circleRadius = width / 2f
            circlePaint.color = getColorForText(text)
            drawCircle(centerX, centerY, circleRadius, circlePaint)

            val textStartX = centerX
            val textStartY = centerY - (textPaint.descent() + textPaint.ascent()) / 2f
            drawText(letter, textStartX, textStartY, textPaint)
        }
    }

    private fun getColorForText(text: String?): Int {
        return circleColors[abs(text.hashCode()) % circleColors.size]
    }
}