package jp.neechan.akari.dictionary.discover

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type

// todo: It's strange that I have created a non-generic deserializer for the generic type.
class StringPageDeserializer : JsonDeserializer<Page<String>> {

    override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): Page<String> {
        val pageJson = json.asJsonObject
        val queryJson = pageJson.getAsJsonObject("query")
        val resultsJson = pageJson.getAsJsonObject("results")

        val pageNumber = queryJson.getAsJsonPrimitive("page").asInt
        val limit = queryJson.getAsJsonPrimitive("limit").asInt
        val totalResults = resultsJson.getAsJsonPrimitive("total").asInt

        val hasNextPage = totalResults > pageNumber * limit
        val results = resultsJson.getAsJsonArray("data").map { it.asString }

        return Page(
            results,
            pageNumber,
            hasNextPage
        )
    }
}