package jp.neechan.akari.dictionary.common.models.dto

enum class FrequencyDto(val from: Float, val to: Float) {

    VERY_RARE(0f, 2f),
    RARE(2f, 4f),
    NORMAL(4f, 5f),
    FREQUENT(5f, 6f),
    VERY_FREQUENT(6f, 10f),
    UNKNOWN(-1f, -1f);

    companion object {
        fun valueOf(frequencyNumber: Float?): FrequencyDto {
            return if (frequencyNumber != null) {
                values().first { it.from <= frequencyNumber && frequencyNumber <= it.to }

            } else {
                UNKNOWN
            }
        }
    }
}