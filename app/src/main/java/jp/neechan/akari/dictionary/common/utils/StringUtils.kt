package jp.neechan.akari.dictionary.common.utils

fun String?.isValid() = this != null && isNotBlank() && trim() != "null"

fun String?.getFirstCharacter(): String {
    return if (isValid()) {
        this!!.substring(0, 1)

    } else {
        ""
    }
}