package jp.neechan.akari.dictionary.core_impl.data.framework.db.typeconverters

import jp.neechan.akari.dictionary.core_impl.data.framework.db.typeconverters.PartOfSpeechDtoTypeConverter
import jp.neechan.akari.dictionary.core_impl.data.framework.dto.PartOfSpeechDto
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

@RunWith(Parameterized::class)
internal class PartOfSpeechDtoTypeConverterTest(private val inputPartOfSpeech: PartOfSpeechDto) {

    // The test calls two methods of the unit (save() & restore()).
    // But it doesn't matter in what form the unit saves the part of speech -
    // actually, we just need to check that a restored part of speech equals to original.
    @Test
    fun `restored part of speech should be equal to input`() {
        val expectedPartOfSpeech = inputPartOfSpeech

        val actualPartOfSpeech = PartOfSpeechDtoTypeConverter.restore(
            PartOfSpeechDtoTypeConverter.save(inputPartOfSpeech)
        )

        assertEquals(expectedPartOfSpeech, actualPartOfSpeech)
    }

    companion object {
        @JvmStatic
        @Parameterized.Parameters
        fun getParams(): List<PartOfSpeechDto> {
            return listOf(
                PartOfSpeechDto.NOUN,
                PartOfSpeechDto.PRONOUN,
                PartOfSpeechDto.ADJECTIVE,
                PartOfSpeechDto.VERB,
                PartOfSpeechDto.ADVERB,
                PartOfSpeechDto.PREPOSITION,
                PartOfSpeechDto.CONJUNCTION,
                PartOfSpeechDto.UNKNOWN,
                PartOfSpeechDto.ALL
            )
        }
    }
}