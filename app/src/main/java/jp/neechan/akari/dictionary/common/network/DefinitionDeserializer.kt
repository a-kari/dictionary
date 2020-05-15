package jp.neechan.akari.dictionary.common.network

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import jp.neechan.akari.dictionary.common.models.dto.DefinitionDto
import jp.neechan.akari.dictionary.common.models.dto.PartOfSpeechDto
import java.lang.reflect.Type

class DefinitionDeserializer : JsonDeserializer<DefinitionDto> {

    override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): DefinitionDto {
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
            definition,
            partOfSpeech,
            examples,
            synonyms,
            antonyms
        )
    }
}