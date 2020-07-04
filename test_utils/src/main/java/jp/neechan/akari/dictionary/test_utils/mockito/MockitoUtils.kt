package jp.neechan.akari.dictionary.test_utils.mockito

import org.mockito.Mockito

object MockitoUtils {

    inline fun <reified T> anyNonNull(): T = Mockito.any(T::class.java)

    inline fun <reified T> anySuspendFunction(): suspend () -> T = anyNonNull()
}
