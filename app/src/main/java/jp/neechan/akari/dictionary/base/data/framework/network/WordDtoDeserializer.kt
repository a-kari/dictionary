package jp.neechan.akari.dictionary.base.data.framework.network

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type

/**
 * Deserializes a word from the API into a WordDto instance.
 * It's needed, because the API model's fields structure can differ sometimes.
 *
 * E.g. pronunciation can be a JsonObject with a string or just a string,
 * part of speech and frequency are nullable (but we still need to set their default values), etc.
 *
 * A word from the API can look like:
 *
 * {
 *     "word": "hello",
 *     "results": [{
 *         "definition": "an expression of greeting",
 *         "partOfSpeech": "noun",
 *         "synonyms": [
 *             "hi",
 *             "how-do-you-do",
 *             "howdy",
 *             "hullo"
 *         ],
 *         "typeOf": [
 *             "greeting",
 *             "salutation"
 *         ],
 *         "examples": [
 *             "every morning they exchanged polite hellos"
 *         ]
 *     }],
 *     "syllables": {
 *         "count": 2,
 *         "list": [
 *             "hel",
 *             "lo"
 *         ]
 *     },
 *     "pronunciation": {
 *         "all": "hɛ'loʊ"
 *     },
 *     "frequency": 5.83
 * }
 */
class WordDtoDeserializer : JsonDeserializer<WordDto> {

    override fun deserialize(json: JsonElement,
                             typeOfT: Type,
                             context: JsonDeserializationContext): WordDto {

        val wordJson = json.asJsonObject
        val word = wordJson.getAsJsonPrimitive("word").asString

        // Pronunciation can be JsonObject("pronunciation").string("all") or just string("pronunciation").
        val pronunciation = when {
            wordJson.has("pronunciation") && wordJson.get("pronunciation").isJsonObject -> {
                wordJson.getAsJsonObject("pronunciation").getAsJsonPrimitive("all")?.asString
            }
            wordJson.has("pronunciation") && wordJson.get("pronunciation").isJsonPrimitive -> {
                wordJson.getAsJsonPrimitive("pronunciation")?.asString
            }
            else -> {
                null
            }
        }

        val syllables = wordJson.getAsJsonObject("syllables")?.getAsJsonArray("list")?.map { it.asString }

        // Frequency null-check is needed to avoid a "null can't be cast to JsonPrimitive" exception.
        val frequency = FrequencyDto.valueOf(
            if (wordJson.has("frequency")) {
                wordJson.getAsJsonPrimitive("frequency").asFloat

            } else {
                null
            }
        )

        val definitionsJson = wordJson.getAsJsonArray("results")
        val definitions = definitionsJson?.map { deserializeDefinition(word, it) }

        return WordDto(
            word,
            pronunciation,
            syllables,
            frequency,
            definitions
        )
    }

    private fun deserializeDefinition(wordId: String, json: JsonElement): DefinitionDto {
        val definitionJson = json.asJsonObject
        val definition = definitionJson.getAsJsonPrimitive("definition").asString

        // Part of speech null-check is needed to avoid a "null can't be cast to JsonPrimitive" exception.
        val partOfSpeech = PartOfSpeechDto.valueOf(
            if (definitionJson.get("partOfSpeech").isJsonPrimitive) {
                definitionJson.getAsJsonPrimitive("partOfSpeech").asString

            } else {
                null
            }
        )

        val examples = definitionJson.getAsJsonArray("examples")?.map { it.asString }
        val antonyms = definitionJson.getAsJsonArray("antonyms")?.map { it.asString }

        // Synonyms lie in JsonArray("synonyms") and JsonArray("similarTo"), here we merge them.
        val synonyms = mutableListOf<String>()
        definitionJson.getAsJsonArray("synonyms")?.forEach { synonyms.add(it.asString) }
        definitionJson.getAsJsonArray("similarTo")?.forEach { synonyms.add(it.asString) }

        return DefinitionDto(
            wordId,
            definition,
            partOfSpeech,
            examples,
            if (synonyms.isNotEmpty()) synonyms else null,
            antonyms
        )
    }
}