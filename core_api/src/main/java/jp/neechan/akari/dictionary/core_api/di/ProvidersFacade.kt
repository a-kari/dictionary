package jp.neechan.akari.dictionary.core_api.di

interface ProvidersFacade : ContextProvider,
                           MediatorsProvider,
                           WordsRepositoryProvider,
                           TextToSpeechServiceProvider,
                           UIMappersProvider
