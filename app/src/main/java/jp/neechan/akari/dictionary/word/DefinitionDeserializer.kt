package jp.neechan.akari.dictionary.word

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type

class DefinitionDeserializer : JsonDeserializer<DefinitionDTO> {

    override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): DefinitionDTO {
        val definitionJson = json.asJsonObject
        val definition = definitionJson.getAsJsonPrimitive("definition").asString
        val partOfSpeech = PartOfSpeech.valueOf(definitionJson.getAsJsonPrimitive("partOfSpeech")?.asString)
        val examples = definitionJson.getAsJsonArray("examples")?.map { it.asString }
        val antonyms = definitionJson.getAsJsonArray("antonyms")?.map { it.asString }

        val synonyms = mutableListOf<String>()
        definitionJson.getAsJsonArray("synonyms")?.forEach { synonyms.add(it.asString) }
        definitionJson.getAsJsonArray("similarTo")?.forEach { synonyms.add(it.asString) }

        return DefinitionDTO(definition, partOfSpeech, examples, synonyms, antonyms)
    }
}