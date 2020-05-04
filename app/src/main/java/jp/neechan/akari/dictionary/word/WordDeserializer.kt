package jp.neechan.akari.dictionary.word

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type

class WordDeserializer : JsonDeserializer<WordDTO> {

    override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): WordDTO {
        val wordJson = json.asJsonObject
        val word = wordJson.getAsJsonPrimitive("word").asString
        val pronunciation = wordJson.getAsJsonObject("pronunciation")?.getAsJsonPrimitive("all")?.asString
        val syllables = wordJson.getAsJsonObject("syllables")?.getAsJsonArray("list")?.map { it.asString }
        val frequency = Frequency.valueOf(wordJson.getAsJsonPrimitive("frequency").asFloat)

        val definitionsJson = wordJson.getAsJsonArray("results")
        val definitions = definitionsJson?.map { context.deserialize(it, DefinitionDTO::class.java) as DefinitionDTO }

        return WordDTO(word, pronunciation, syllables, frequency, definitions)
    }
}