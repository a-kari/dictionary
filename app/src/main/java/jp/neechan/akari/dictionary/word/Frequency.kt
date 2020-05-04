package jp.neechan.akari.dictionary.word

import androidx.annotation.StringRes
import jp.neechan.akari.dictionary.R
import jp.neechan.akari.dictionary.word.Frequency.*

enum class Frequency(val fromValue: Float, val toValue: Float) {

    VERY_RARE(0f, 2f),
    RARE(2f, 4f),
    NORMAL(4f, 5f),
    FREQUENT(5f, 6f),
    VERY_FREQUENT(6f, 10f)
}

// todo: The extension should be in UI layer when I apply Clean Architecture.
@StringRes
fun Frequency.getStringResource(): Int {
    return when (this) {
        VERY_RARE -> R.string.frequency_very_rare
        RARE -> R.string.frequency_rare
        NORMAL -> R.string.frequency_normal
        FREQUENT -> R.string.frequency_frequent
        VERY_FREQUENT -> R.string.frequency_very_frequent
    }
}