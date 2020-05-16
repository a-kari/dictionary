package jp.neechan.akari.dictionary.common.network

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import jp.neechan.akari.dictionary.common.models.dto.DefinitionDto
import jp.neechan.akari.dictionary.common.models.dto.FrequencyDto
import jp.neechan.akari.dictionary.common.models.dto.WordDto
import java.lang.reflect.Type

class WordDeserializer : JsonDeserializer<WordDto> {

    override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): WordDto {
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
        val definitions = definitionsJson?.map { context.deserialize(it, DefinitionDto::class.java) as DefinitionDto }

        return WordDto(
            word,
            pronunciation,
            syllables,
            frequency,
            definitions
        )
    }
}