package jp.neechan.akari.dictionary.base.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        DatabaseModule::class,
        NetworkModule::class,
        SharedPreferencesModule::class,
        TextToSpeechModule::class,
        RepositoriesModule::class,
        DtoMappersModule::class,
        UIMappersModule::class
    ]
)
interface AppComponent : RepositoryProvider, TextToSpeechServiceProvider, UIModelMappersProvider {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun context(context: Context): Builder

        fun build(): AppComponent
    }
}