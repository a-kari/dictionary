package jp.neechan.akari.dictionary.domain_tts.domain

import jp.neechan.akari.dictionary.core_api.domain.usecases.TextToSpeechService
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule

class SpeakUseCaseTest {

    @get:Rule
    val mockitoRule: MockitoRule = MockitoJUnit.rule()

    @Mock private lateinit var mockTtsService: TextToSpeechService

    private lateinit var useCaseUnderTest: SpeakUseCase

    @Before
    fun setup() {
        useCaseUnderTest = SpeakUseCase(mockTtsService)
    }

    @Test
    fun `invoke() should call ttsService_speak()`() = runBlockingTest {
        val expectedArgument = "hello"

        useCaseUnderTest.invoke(expectedArgument)

        verify(mockTtsService).speak(expectedArgument)
    }
}