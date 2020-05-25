package jp.neechan.akari.dictionary.base.presentation.extensions

fun String?.isValid() = this != null && isNotBlank() && trim() != "null"

fun String?.getFirstCharacter(): String {
    return if (isValid()) {
        this!!.substring(0, 1)

    } else {
        ""
    }
}