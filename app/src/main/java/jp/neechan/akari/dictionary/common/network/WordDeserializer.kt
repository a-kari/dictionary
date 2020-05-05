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

        val pronunciation = if (wordJson.get("pronunciation").isJsonObject) {
            wordJson.getAsJsonObject("pronunciation")?.getAsJsonPrimitive("all")?.asString

        } else {
            wordJson.getAsJsonPrimitive("pronunciation")?.asString
        }

        val syllables = wordJson.getAsJsonObject("syllables")?.getAsJsonArray("list")?.map { it.asString }
        val frequency = FrequencyDto.valueOf(wordJson.getAsJsonPrimitive("frequency").asFloat)

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