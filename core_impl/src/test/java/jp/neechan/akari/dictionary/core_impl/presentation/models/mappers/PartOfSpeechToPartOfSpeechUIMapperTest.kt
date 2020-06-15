import jp.neechan.akari.dictionary.core_api.domain.entities.PartOfSpeech
import jp.neechan.akari.dictionary.core_api.presentation.models.PartOfSpeechUI
import jp.neechan.akari.dictionary.core_impl.presentation.models.mappers.PartOfSpeechToPartOfSpeechUIMapper
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

@RunWith(Parameterized::class)
class PartOfSpeechToPartOfSpeechUIMapperTest(private val partOfSpeech: PartOfSpeech,
                                             private val partOfSpeechUI: PartOfSpeechUI) {

    private val mapperUnderTest = PartOfSpeechToPartOfSpeechUIMapper()

    @Test
    fun `test mapToInternalLayer()`() {
        val inputPartOfSpeechUI = partOfSpeechUI
        val expectedPartOfSpeech = partOfSpeech

        val actualPartOfSpeech = mapperUnderTest.mapToInternalLayer(inputPartOfSpeechUI)

        assertEquals(expectedPartOfSpeech, actualPartOfSpeech)
    }

    @Test
    fun `test mapToExternalLayer()`() {
        val inputPartOfSpeech = partOfSpeech
        val expectedPartOfSpeechUI = partOfSpeechUI

        val actualPartOfSpeechUI = mapperUnderTest.mapToExternalLayer(inputPartOfSpeech)

        assertEquals(expectedPartOfSpeechUI, actualPartOfSpeechUI)
    }

    companion object {
        @JvmStatic
        @Parameterized.Parameters
        fun getParams(): Collection<Array<Enum<*>>> {
            return listOf(
                arrayOf(PartOfSpeech.NOUN,        PartOfSpeechUI.NOUN),
                arrayOf(PartOfSpeech.PRONOUN,     PartOfSpeechUI.PRONOUN),
                arrayOf(PartOfSpeech.ADJECTIVE,   PartOfSpeechUI.ADJECTIVE),
                arrayOf(PartOfSpeech.VERB,        PartOfSpeechUI.VERB),
                arrayOf(PartOfSpeech.ADVERB,      PartOfSpeechUI.ADVERB),
                arrayOf(PartOfSpeech.PREPOSITION, PartOfSpeechUI.PREPOSITION),
                arrayOf(PartOfSpeech.CONJUNCTION, PartOfSpeechUI.CONJUNCTION),
                arrayOf(PartOfSpeech.UNKNOWN,     PartOfSpeechUI.UNKNOWN),
                arrayOf(PartOfSpeech.ALL,         PartOfSpeechUI.ALL)
            )
        }
    }
}