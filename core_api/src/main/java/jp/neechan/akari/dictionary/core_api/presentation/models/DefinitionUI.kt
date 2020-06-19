package jp.neechan.akari.dictionary.core_api.presentation.models

data class DefinitionUI(val id: Long,
                        val wordId: String,
                        val definition: String,
                        val partOfSpeech: PartOfSpeechUI,
                        val examples: String?,
                        val synonyms: String?,
                        val antonyms: String?)