package jp.neechan.akari.dictionary.core_impl.data.framework.dto

import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

@RunWith(Parameterized::class)
internal class PartOfSpeechDtoTest(private val inputString: String?,
                                   private val expectedPartOfSpeech: PartOfSpeechDto) {

    @Test
    fun `test valueOf(inputString)`() {
        val actualPartOfSpeech = PartOfSpeechDto.valueOf(inputString)
        assertEquals(expectedPartOfSpeech, actualPartOfSpeech)
    }

    companion object {
        @JvmStatic
        @Parameterized.Parameters
        fun getParams(): Collection<Array<out Any?>> {
            return listOf(
                arrayOf("noun",        PartOfSpeechDto.NOUN),
                arrayOf("pronoun",     PartOfSpeechDto.PRONOUN),
                arrayOf("adjective",   PartOfSpeechDto.ADJECTIVE),
                arrayOf("verb",        PartOfSpeechDto.VERB),
                arrayOf("adverb",      PartOfSpeechDto.ADVERB),
                arrayOf("preposition", PartOfSpeechDto.PREPOSITION),
                arrayOf("conjunction", PartOfSpeechDto.CONJUNCTION),
                arrayOf("all",         PartOfSpeechDto.ALL),
                arrayOf("random",      PartOfSpeechDto.UNKNOWN),
                arrayOf(null,          PartOfSpeechDto.UNKNOWN)
            )
        }
    }
}