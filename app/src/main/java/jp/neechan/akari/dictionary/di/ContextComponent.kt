package jp.neechan.akari.dictionary.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import jp.neechan.akari.dictionary.core_api.di.ContextProvider
import javax.inject.Singleton

@Singleton
@Component
internal interface ContextComponent : ContextProvider {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun context(context: Context): Builder

        fun build(): ContextComponent
    }

    companion object {
        private var contextComponent: ContextComponent? = null

        fun create(context: Context): ContextComponent {
            return contextComponent
                ?: DaggerContextComponent.builder()
                                         .context(context)
                                         .build()
                                         .also { contextComponent = it  }
        }
    }
}