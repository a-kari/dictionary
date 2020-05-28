package jp.neechan.akari.dictionary.base.presentation.models

data class DefinitionUI(val id: Long,
                        val wordId: String,
                        val definition: String,
                        val partOfSpeech: PartOfSpeechUI,
                        val examples: String?,
                        val synonyms: String?,
                        val antonyms: String?)