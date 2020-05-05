package jp.neechan.akari.dictionary.common.models.models

import jp.neechan.akari.dictionary.R

enum class Frequency(val stringResource: Int) {

    VERY_RARE(R.string.frequency_very_rare),
    RARE(R.string.frequency_rare),
    NORMAL(R.string.frequency_normal),
    FREQUENT(R.string.frequency_frequent),
    VERY_FREQUENT(R.string.frequency_very_frequent)
}