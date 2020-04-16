package jp.neechan.akari.dictionary.common

fun String?.isValid() = this != null && isNotBlank() && trim() != "null"

fun String?.getFirstCharacter(): String {
    return if (isValid()) {
        return this!!.substring(0, 1)

    } else {
        ""
    }
}