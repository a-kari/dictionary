package jp.neechan.akari.dictionary.core_impl.data.framework.network

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonElement
import com.google.gson.JsonParser
import jp.neechan.akari.dictionary.core_impl.data.framework.dto.DefinitionDto
import jp.neechan.akari.dictionary.core_impl.data.framework.dto.FrequencyDto
import jp.neechan.akari.dictionary.core_impl.data.framework.dto.PartOfSpeechDto
import jp.neechan.akari.dictionary.core_impl.data.framework.dto.WordDto
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import org.mockito.Mock
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule

@RunWith(Parameterized::class)
internal class WordDtoDeserializerTest(private val inputJson: JsonElement,
                                       private val expectedWordDto: WordDto) {

    @get:Rule
    val mockitoRule: MockitoRule = MockitoJUnit.rule()

    @Mock private lateinit var mockContext: JsonDeserializationContext

    private val deserializerUnderTest = WordDtoDeserializer()

    @Test
    fun `should correctly deserialize a json to dto`() {
        val actualWordDto = deserializerUnderTest.deserialize(inputJson, WordDto::class.java, mockContext)

        assertEquals(expectedWordDto, actualWordDto)
    }

    companion object {
        @JvmStatic
        @Parameterized.Parameters
        fun getParams(): Collection<Array<Any>> {
            val jsonParser = JsonParser()

            return listOf(
                arrayOf(
                    jsonParser.parse(
                        "{\n" +
                        "\t\"word\": \"hello\",\n" +
                        "\t\"results\": [{\n" +
                        "\t\t\"definition\": \"an expression of greeting\",\n" +
                        "\t\t\"partOfSpeech\": \"noun\",\n" +
                        "\t\t\"synonyms\": [\n" +
                        "\t\t\t\"hi\",\n" +
                        "\t\t\t\"how-do-you-do\",\n" +
                        "\t\t\t\"howdy\",\n" +
                        "\t\t\t\"hullo\"\n" +
                        "\t\t],\n" +
                        "\t\t\"typeOf\": [\n" +
                        "\t\t\t\"greeting\",\n" +
                        "\t\t\t\"salutation\"\n" +
                        "\t\t],\n" +
                        "\t\t\"examples\": [\n" +
                        "\t\t\t\"every morning they exchanged polite hellos\"\n" +
                        "\t\t]\n" +
                        "\t}],\n" +
                        "\t\"syllables\": {\n" +
                        "\t\t\"count\": 2,\n" +
                        "\t\t\"list\": [\n" +
                        "\t\t\t\"hel\",\n" +
                        "\t\t\t\"lo\"\n" +
                        "\t\t]\n" +
                        "\t},\n" +
                        "\t\"pronunciation\": {\n" +
                        "\t\t\"all\": \"hɛ'loʊ\"\n" +
                        "\t},\n" +
                        "\t\"frequency\": 5.83\n" +
                        "}"
                    ),

                    WordDto(
                        "hello",
                        "hɛ'loʊ",
                        listOf("hel", "lo"),
                        FrequencyDto.FREQUENT,
                        listOf(
                            DefinitionDto(
                                "hello",
                                "an expression of greeting",
                                PartOfSpeechDto.NOUN,
                                listOf("every morning they exchanged polite hellos"),
                                listOf("hi", "how-do-you-do", "howdy", "hullo"),
                                null
                            )
                        )
                    )
                ),

                arrayOf(
                    jsonParser.parse(
                        "{\n" +
                        "\t\"word\": \"hello\",\n" +
                        "\t\"results\": [{\n" +
                        "\t\t\"definition\": \"an expression of greeting\",\n" +
                        "\t\t\"partOfSpeech\": \"noun\",\n" +
                        "\t\t\"synonyms\": [\n" +
                        "\t\t\t\"hi\",\n" +
                        "\t\t\t\"how-do-you-do\"\n" +
                        "\t\t],\n" +
                        "\t\t\"similarTo\": [\n" +
                        "\t\t\t\"howdy\",\n" +
                        "\t\t\t\"hullo\"\n" +
                        "\t\t],\n" +
                        "\t\t\"antonyms\": [\n" +
                        "\t\t\t\"goodbye\",\n" +
                        "\t\t\t\"bye\"\n" +
                        "\t\t],\n" +
                        "\t\t\"typeOf\": [\n" +
                        "\t\t\t\"greeting\",\n" +
                        "\t\t\t\"salutation\"\n" +
                        "\t\t],\n" +
                        "\t\t\"examples\": [\n" +
                        "\t\t\t\"every morning they exchanged polite hellos\"\n" +
                        "\t\t]\n" +
                        "\t}],\n" +
                        "\t\"syllables\": {\n" +
                        "\t\t\"count\": 2,\n" +
                        "\t\t\"list\": [\n" +
                        "\t\t\t\"hel\",\n" +
                        "\t\t\t\"lo\"\n" +
                        "\t\t]\n" +
                        "\t},\n" +
                        "\t\"pronunciation\": {\n" +
                        "\t\t\"all\": \"hɛ'loʊ\"\n" +
                        "\t},\n" +
                        "\t\"frequency\": 5.83\n" +
                        "}"
                    ),
                    WordDto(
                        "hello",
                        "hɛ'loʊ",
                        listOf("hel", "lo"),
                        FrequencyDto.FREQUENT,
                        listOf(
                            DefinitionDto(
                                "hello",
                                "an expression of greeting",
                                PartOfSpeechDto.NOUN,
                                listOf("every morning they exchanged polite hellos"),
                                listOf("hi", "how-do-you-do", "howdy", "hullo"),
                                listOf("goodbye", "bye")
                            )
                        )
                    )
                ),

                arrayOf(
                    jsonParser.parse(
                        "{\n" +
                        "\t\"rhymes\": {\n" +
                        "\t\t\"all\": \"-aʊtʃ\"\n" +
                        "\t},\n" +
                        "\t\"word\": \"ouch\",\n" +
                        "\t\"pronunciation\": \"aʊʧ\"\n" +
                        "}"
                    ),
                    WordDto("ouch", "aʊʧ", null, FrequencyDto.UNKNOWN, null, null)
                )
            )
        }
    }
}