package jp.neechan.akari.dictionary.base.data.framework.network

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import jp.neechan.akari.dictionary.base.data.framework.dto.DefinitionDto
import jp.neechan.akari.dictionary.base.data.framework.dto.FrequencyDto
import jp.neechan.akari.dictionary.base.data.framework.dto.PartOfSpeechDto
import jp.neechan.akari.dictionary.base.data.framework.dto.WordDto
import java.lang.reflect.Type

class WordDeserializer : JsonDeserializer<WordDto> {

    override fun deserialize(json: JsonElement,
                             typeOfT: Type,
                             context: JsonDeserializationContext): WordDto {

        val wordJson = json.asJsonObject
        val word = wordJson.getAsJsonPrimitive("word").asString

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

        val partOfSpeech = PartOfSpeechDto.valueOf(
            if (definitionJson.get("partOfSpeech").isJsonPrimitive) {
                definitionJson.getAsJsonPrimitive("partOfSpeech").asString

            } else {
                null
            }
        )

        val examples = definitionJson.getAsJsonArray("examples")?.map { it.asString }
        val antonyms = definitionJson.getAsJsonArray("antonyms")?.map { it.asString }

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