package jp.neechan.akari.dictionary.core_impl.data.framework.network

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import dagger.Reusable
import jp.neechan.akari.dictionary.core_api.domain.entities.Page
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type
import javax.inject.Inject

/**
 * Deserializes a page of T items from the API. It's needed to convert the API page model
 * into the app Page model without adding any excess fields to the Page class.
 *
 * A page can look like (the example is for strings, but data may contain any other T objects):
 *
 * {
 *     "query": {
 *         "limit": 100,
 *         "page": "1"
 *     },
 *     "results": {
 *         "total": 476,
 *         "data": [
 *             "giraffe",
 *             "leopard",
 *             "zebra",
 *             ...
 *         ]
 *     }
 * }
 *
 * @param T is a content type. E.g. String for Page<String> or WordDto for Page<WordDto>.
 */

@Reusable
internal class PageDeserializer<T> @Inject constructor() : JsonDeserializer<Page<T>> {

    override fun deserialize(json: JsonElement, genericPageType: Type, context: JsonDeserializationContext): Page<T> {
        val pageJson = json.asJsonObject
        val queryJson = pageJson.getAsJsonObject("query")
        val resultsJson = pageJson.getAsJsonObject("results")

        val pageNumber = queryJson.getAsJsonPrimitive("page").asInt
        val limit = queryJson.getAsJsonPrimitive("limit").asInt
        val totalResults = resultsJson.getAsJsonPrimitive("total").asInt

        // Get the actual content type. E.g. String for Page<String> or WordDto for Page<WordDto>.
        val contentType: Type = (genericPageType as ParameterizedType).actualTypeArguments[0]

        // Delegate content items deserialization to some other deserializer defined in the context
        // (it will be resolved using the content type) and store it into the list.
        val results: List<T> = resultsJson.getAsJsonArray("data").map { context.deserialize(it, contentType) as T }

        return Page(results, pageNumber, limit, totalResults)
    }
}