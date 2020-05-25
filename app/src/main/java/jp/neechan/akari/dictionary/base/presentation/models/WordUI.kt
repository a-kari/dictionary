package jp.neechan.akari.dictionary.base.presentation.models

import java.util.Date

data class WordUI(val word: String,
                  val pronunciation: String?,
                  val syllables: String?,
                  val frequency: FrequencyUI,
                  val definitions: LinkedHashMap<PartOfSpeechUI, List<DefinitionUI>>?,
                  val saveDate: Date?,
                  val isSaved: Boolean = saveDate != null)