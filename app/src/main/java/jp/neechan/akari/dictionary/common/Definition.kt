package jp.neechan.akari.dictionary.common

import java.io.Serializable

// todo: Don't implement Serializable, because Word should be retrieved from a repository.
data class Definition(val definition: String,
                      val examples: String?,
                      val synonyms: String?,
                      val antonyms: String?) : Serializable