package jp.neechan.akari.dictionary.domain_words.domain

import jp.neechan.akari.dictionary.core_api.domain.entities.FilterParams
import jp.neechan.akari.dictionary.core_api.domain.entities.Page
import jp.neechan.akari.dictionary.core_api.domain.entities.Result
import kotlinx.coroutines.channels.ConflatedBroadcastChannel

interface LoadWordsUseCase {

    suspend operator fun invoke(params: FilterParams): Result<Page<String>>

    fun observeRecentlyUpdated(): ConflatedBroadcastChannel<Boolean>
}