package jp.neechan.akari.dictionary.base.domain.usecases

import jp.neechan.akari.dictionary.base.domain.entities.FilterParams
import jp.neechan.akari.dictionary.base.domain.entities.Page
import jp.neechan.akari.dictionary.base.domain.entities.Result
import kotlinx.coroutines.channels.ConflatedBroadcastChannel

interface LoadWordsUseCase {

    suspend operator fun invoke(params: FilterParams): Result<Page<String>>

    fun observeRecentlyUpdated(): ConflatedBroadcastChannel<Boolean>
}