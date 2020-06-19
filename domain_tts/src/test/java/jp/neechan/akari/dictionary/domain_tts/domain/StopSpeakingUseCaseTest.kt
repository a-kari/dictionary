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

class StopSpeakingUseCaseTest {

    @get:Rule
    val mockitoRule: MockitoRule = MockitoJUnit.rule()

    @Mock private lateinit var mockTtsService: TextToSpeechService

    private lateinit var useCaseUnderTest: StopSpeakingUseCase

    @Before
    fun setup() {
        useCaseUnderTest = StopSpeakingUseCase(mockTtsService)
    }

    @Test
    fun `invoke() should call ttsService_stopSpeaking()`() = runBlockingTest {
        useCaseUnderTest.invoke()

        verify(mockTtsService).stopSpeaking()
    }
}