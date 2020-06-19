package jp.neechan.akari.dictionary.core_impl.presentation.models.mappers

import jp.neechan.akari.dictionary.core_api.domain.entities.Result
import jp.neechan.akari.dictionary.core_api.domain.entities.mappers.ModelMapper
import jp.neechan.akari.dictionary.core_api.presentation.models.UIState
import jp.neechan.akari.dictionary.test_utils.mockito.MockitoUtils.anyNonNull
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.experimental.runners.Enclosed
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.verifyZeroInteractions

@RunWith(Enclosed::class)
class ResultToUIStateMapperTest {

    @RunWith(Parameterized::class)
    class TestWithoutContentMapper(private val result: Result<List<String>>,
                                   private val uiState: UIState<List<String>>) {

        private val mapperUnderTest = ResultToUIStateMapper<List<String>, List<String>>()

        @Test
        fun `test mapToInternalLayer()`() {
            val inputUIState = uiState
            val expectedResult = result

            val actualResult = mapperUnderTest.mapToInternalLayer(inputUIState)

            assertEquals(expectedResult::class, actualResult::class)
            if (expectedResult is Result.Success && actualResult is Result.Success) {
                assertEquals(expectedResult.value, actualResult.value)
            }
        }

        @Test
        fun `test mapToExternalLayer()`() {
            val inputResult = result
            val expectedUIState = uiState

            val actualUIState = mapperUnderTest.mapToExternalLayer(inputResult)

            assertEquals(expectedUIState::class, actualUIState::class)
            if (expectedUIState is UIState.ShowContent && actualUIState is UIState.ShowContent) {
                assertEquals(expectedUIState.content, actualUIState.content)
            }
        }

        companion object {
            @JvmStatic
            @Parameterized.Parameters
            fun getParams(): Collection<Array<Any>> {
                val exception = RuntimeException("Something went wrong")

                return listOf(
                    arrayOf(
                        Result.Success(listOf("Giraffe", "Leopard", "Zebra")),
                        UIState.ShowContent(listOf("Giraffe", "Leopard", "Zebra"))
                    ),
                    arrayOf(Result.ConnectionError, UIState.ShowConnectionError),
                    arrayOf(Result.NotFoundError, UIState.ShowNotFoundError),
                    arrayOf(Result.Error(exception), UIState.ShowError(exception)),
                    arrayOf(Result.Loading, UIState.ShowLoading)
                )
            }
        }
    }

    @RunWith(Parameterized::class)
    class TestWithContentMapper(private val result: Result<List<String>>,
                                private val uiState: UIState<List<String>>,
                                private val mockContentMapper: ModelMapper<List<String>, List<String>>) {

        private val mapperUnderTest = ResultToUIStateMapper(mockContentMapper)

        @Test
        fun `test mapToInternalLayer()`() {
            val inputUIState = uiState
            val expectedResult = result
            if (expectedResult is Result.Success) {
                `when`(mockContentMapper.mapToInternalLayer(anyNonNull())).thenReturn(expectedResult.value)
            }

            val actualResult = mapperUnderTest.mapToInternalLayer(inputUIState)

            assertEquals(expectedResult::class, actualResult::class)
            if (inputUIState is UIState.ShowContent) {
                verify(mockContentMapper).mapToInternalLayer(inputUIState.content)

            } else {
                verifyZeroInteractions(mockContentMapper)
            }
        }

        @Test
        fun `test mapToExternalLayer()`() {
            val inputResult = result
            val expectedUIState = uiState
            if (expectedUIState is UIState.ShowContent) {
                `when`(mockContentMapper.mapToExternalLayer(anyNonNull())).thenReturn(expectedUIState.content)
            }

            val actualUIState = mapperUnderTest.mapToExternalLayer(inputResult)

            assertEquals(expectedUIState::class, actualUIState::class)
            if (inputResult is Result.Success) {
                verify(mockContentMapper).mapToExternalLayer(inputResult.value)

            } else {
                verifyZeroInteractions(mockContentMapper)
            }
        }

        companion object {
            @JvmStatic
            @Parameterized.Parameters
            fun getParams(): Collection<Array<Any>> {
                val exception = RuntimeException("Something went wrong")

                return listOf(
                    arrayOf(
                        Result.Success(listOf("Giraffe", "Leopard", "Zebra")),
                        UIState.ShowContent(listOf("GiraffeMapped", "LeopardMapped", "ZebraMapped")),
                        mock(ModelMapper::class.java)
                    ),
                    arrayOf(Result.ConnectionError, UIState.ShowConnectionError, mock(ModelMapper::class.java)),
                    arrayOf(Result.NotFoundError, UIState.ShowNotFoundError, mock(ModelMapper::class.java)),
                    arrayOf(Result.Error(exception), UIState.ShowError(exception), mock(ModelMapper::class.java)),
                    arrayOf(Result.Loading, UIState.ShowLoading, mock(ModelMapper::class.java))
                )
            }
        }
    }
}