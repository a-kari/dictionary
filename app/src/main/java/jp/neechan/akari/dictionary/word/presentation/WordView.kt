package jp.neechan.akari.dictionary.word.presentation

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import jp.neechan.akari.dictionary.R
import kotlinx.android.synthetic.main.view_word.view.*
import kotlinx.android.synthetic.main.view_word_definition.view.*
import kotlinx.android.synthetic.main.view_word_part_of_speech.view.*

class WordView : LinearLayout {

    private lateinit var speakCallback: () -> Unit
    private var definitionExtraTopMargin = 0

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    private fun init() {
        LayoutInflater.from(context).inflate(R.layout.view_word, this, true)
        definitionExtraTopMargin = resources.getDimensionPixelSize(R.dimen.default_padding_small)
    }

    fun setWord(word: WordUI) {
        wordTv.text = word.word
        setPronunciation(word.pronunciation)
        setSyllables(word.syllables)
        setFrequency(word.frequency)
        setDefinitions(word.definitions)
        setupListeners()
    }

    fun setSpeakCallback(speakCallback: () -> Unit) {
        this.speakCallback = speakCallback
    }

    private fun setPronunciation(pronunciation: String?) {
        if (pronunciation != null) {
            speakButton.text = pronunciation
            speakButton.visibility = VISIBLE

        } else {
            speakButton.visibility = GONE
        }
    }

    private fun setSyllables(syllables: String?) {
        if (syllables != null) {
            syllablesTv.text = syllables
            syllablesContainer.visibility = VISIBLE

        } else {
            syllablesContainer.visibility = GONE
        }
    }

    private fun setFrequency(frequency: FrequencyUI) {
        frequencyTv.setText(frequency.stringResource)
    }

    private fun setDefinitions(partOfSpeechToDefinitionsMap: LinkedHashMap<PartOfSpeechUI, List<DefinitionUI>>?) {
        definitionsView.removeAllViews()

        if (partOfSpeechToDefinitionsMap != null && partOfSpeechToDefinitionsMap.isNotEmpty()) {
            for (partOfSpeechToDefinitions in partOfSpeechToDefinitionsMap) {
                definitionsView.addView(getPartOfSpeechView(partOfSpeechToDefinitions.key))

                partOfSpeechToDefinitions.value.forEachIndexed { index, definition ->
                    definitionsView.addView(getDefinitionView(definition, index != 0))
                }
            }
            definitionsContainer.visibility = VISIBLE

        } else {
            definitionsContainer.visibility = GONE
        }
    }

    private fun getPartOfSpeechView(partOfSpeech: PartOfSpeechUI): View {
        val partOfSpeechView = LayoutInflater.from(context).inflate(R.layout.view_word_part_of_speech, definitionsView, false)
        partOfSpeechView.partOfSpeechTv.setText(partOfSpeech.stringResource)
        return partOfSpeechView
    }

    private fun getDefinitionView(definition: DefinitionUI, addExtraTopMargin: Boolean): View {
        val definitionView = LayoutInflater.from(context).inflate(R.layout.view_word_definition, definitionsView, false)
        if (addExtraTopMargin) {
            (definitionView.layoutParams as LayoutParams).topMargin += definitionExtraTopMargin
        }

        definitionView.definitionTv.text = definition.definition

        if (definition.examples.isValid()) {
            definitionView.examplesTv.text = definition.examples
            definitionView.examplesContainer.visibility = VISIBLE

        } else {
            definitionView.examplesContainer.visibility = GONE
        }

        if (definition.synonyms.isValid()) {
            definitionView.synonymsTv.text = definition.synonyms
            definitionView.synonymsContainer.visibility = VISIBLE

        } else {
            definitionView.synonymsContainer.visibility = GONE
        }

        if (definition.antonyms.isValid()) {
            definitionView.antonymsTv.text = definition.antonyms
            definitionView.antonymsContainer.visibility = VISIBLE

        } else {
            definitionView.antonymsContainer.visibility = GONE
        }

        return definitionView
    }

    private fun setupListeners() {
        speakButton.setOnClickListener { speakCallback() }
    }

    private fun String?.isValid() = this != null && isNotBlank()
}