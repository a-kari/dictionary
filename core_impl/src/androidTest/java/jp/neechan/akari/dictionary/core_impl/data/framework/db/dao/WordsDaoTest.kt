package jp.neechan.akari.dictionary.core_impl.data.framework.db.dao

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import jp.neechan.akari.dictionary.core_impl.data.framework.db.DatabaseService
import jp.neechan.akari.dictionary.core_impl.data.framework.db.dao.SampleData.getSampleWord
import jp.neechan.akari.dictionary.core_impl.data.framework.db.dao.SampleData.getSampleWordWithDefinitions
import jp.neechan.akari.dictionary.core_impl.data.framework.db.dao.SampleData.getSampleWords
import jp.neechan.akari.dictionary.core_impl.data.framework.dto.DefinitionDto
import jp.neechan.akari.dictionary.core_impl.data.framework.dto.FrequencyDto
import jp.neechan.akari.dictionary.core_impl.data.framework.dto.PartOfSpeechDto
import jp.neechan.akari.dictionary.core_impl.data.framework.dto.WordDto
import jp.neechan.akari.dictionary.core_impl.data.framework.dto.WordWithDefinitionsDto
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import java.util.Date

@RunWith(AndroidJUnit4::class)
internal class WordsDaoNonParameterizedTest {

    @get:Rule
    val synchronousAacRule = InstantTaskExecutorRule()

    private lateinit var database: DatabaseService
    private lateinit var daoUnderTest: WordsDao

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(context, DatabaseService::class.java).build()
        daoUnderTest = database.getWordsDao()
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun testSaveWord() = runBlockingTest {
        val expectedSavedWord = getSampleWord()

        daoUnderTest.saveWord(expectedSavedWord)
        val actualSavedWord = daoUnderTest.getWordWithDefinitions(expectedSavedWord.word)!!.word

        assertEquals(expectedSavedWord, actualSavedWord)
    }

    @Test
    fun testSaveDefinitions() = runBlockingTest {
        val inputWord = getSampleWordWithDefinitions()
        val expectedSavedDefinitions = inputWord.definitions!!
        daoUnderTest.saveWord(inputWord) // The word should be pre-saved before its definitions.

        daoUnderTest.saveDefinitions(expectedSavedDefinitions)
        val actualSavedDefinitions = daoUnderTest.getWordWithDefinitions(inputWord.word)!!.definitions!!

        expectedSavedDefinitions.forEachIndexed { index, expectedDefinition ->
            val actualDefinition = actualSavedDefinitions[index]
            assertTrue(actualDefinition.id > 0L)
            assertEquals(expectedDefinition.wordId, actualDefinition.wordId)
            assertEquals(expectedDefinition.definition, actualDefinition.definition)
            assertEquals(expectedDefinition.partOfSpeech, actualDefinition.partOfSpeech)
            assertEquals(expectedDefinition.examples, actualDefinition.examples)
            assertEquals(expectedDefinition.synonyms, actualDefinition.synonyms)
            assertEquals(expectedDefinition.antonyms, actualDefinition.antonyms)
        }
    }

    @Test
    fun testDeleteWord() = runBlockingTest {
        val inputWord = getSampleWord()
        daoUnderTest.saveWord(inputWord)

        daoUnderTest.deleteWord(inputWord.word)

        assertNull(daoUnderTest.getWordWithDefinitions(inputWord.word))
    }

    @Test
    fun testGetWordWithDefinitions() = runBlockingTest {
        val inputWord = getSampleWordWithDefinitions()
        val expectedWordWithDefinitions = WordWithDefinitionsDto(inputWord, inputWord.definitions)
        daoUnderTest.saveWord(inputWord)
        daoUnderTest.saveDefinitions(inputWord.definitions!!)

        val actualWordWithDefinitions = daoUnderTest.getWordWithDefinitions(inputWord.word)!!

        assertEquals(expectedWordWithDefinitions.word.word, actualWordWithDefinitions.word.word)
        assertEquals(expectedWordWithDefinitions.word.pronunciation, actualWordWithDefinitions.word.pronunciation)
        assertEquals(expectedWordWithDefinitions.word.syllables, actualWordWithDefinitions.word.syllables)
        assertEquals(expectedWordWithDefinitions.word.frequency, actualWordWithDefinitions.word.frequency)
        assertEquals(expectedWordWithDefinitions.word.saveDate, actualWordWithDefinitions.word.saveDate)

        expectedWordWithDefinitions.definitions!!.forEachIndexed { index, expectedDefinition ->
            val actualDefinition = actualWordWithDefinitions.definitions!![index]
            assertTrue(actualDefinition.id > 0L)
            assertEquals(expectedDefinition.wordId, actualDefinition.wordId)
            assertEquals(expectedDefinition.definition, actualDefinition.definition)
            assertEquals(expectedDefinition.partOfSpeech, actualDefinition.partOfSpeech)
            assertEquals(expectedDefinition.examples, actualDefinition.examples)
            assertEquals(expectedDefinition.synonyms, actualDefinition.synonyms)
            assertEquals(expectedDefinition.antonyms, actualDefinition.antonyms)
        }
    }

    @Test
    fun testGetWordsCount() = runBlockingTest {
        val inputWords = getSampleWords()
        val expectedWordsCount = inputWords.size
        inputWords.forEach { daoUnderTest.saveWord(it) }

        val actualWordsCount = daoUnderTest.getWordsCount()

        assertEquals(expectedWordsCount, actualWordsCount)
    }
}

@RunWith(Parameterized::class)
internal class WordsDaoParameterizedTest(private val inputWords: List<WordDto>,
                                         private val inputLimit: Int,
                                         private val inputOffset: Int,
                                         private val expectedWords: List<String>) {

    @get:Rule
    val synchronousAacRule = InstantTaskExecutorRule()

    private lateinit var database: DatabaseService
    private lateinit var daoUnderTest: WordsDao

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(context, DatabaseService::class.java).build()
        daoUnderTest = database.getWordsDao()
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun testGetWords() = runBlockingTest {
        inputWords.forEach { daoUnderTest.saveWord(it) }

        val actualWords = daoUnderTest.getWords(inputLimit, inputOffset)

        assertEquals(expectedWords, actualWords)
    }

    companion object {
        @JvmStatic
        @Parameterized.Parameters
        fun getParams(): Collection<Array<Any>> = listOf(
            // inputWords           limit offset expectedWords
            arrayOf(getSampleWords(), 10,   0,   listOf("movement", "change", "cold", "answer", "morning", "hello")),
            arrayOf(getSampleWords(), 3,    0,   listOf("movement", "change", "cold")),
            arrayOf(getSampleWords(), 3,    3,   listOf("answer", "morning", "hello")),
            arrayOf(getSampleWords(), 2,    4,   listOf("morning", "hello")),
            arrayOf(getSampleWords(), 10,   4,   listOf("morning", "hello")),
            arrayOf(getSampleWords(), 10,   10,  emptyList<String>())
        )
    }
}

private object SampleData {

    fun getSampleWord() = WordDto(
        "hello",
        "həˈləʊ",
        listOf("hel", "lo"),
        FrequencyDto.FREQUENT,
        null,
        Date(1591850938247L)
    )

    fun getSampleWordWithDefinitions() = WordDto(
        "answer",
        "'ænsər",
        listOf("an", "swer"),
        FrequencyDto.FREQUENT,
        listOf(
            DefinitionDto(
                0,
                "answer",
                "the speech act of replying to a question",
                PartOfSpeechDto.NOUN,
                listOf("their answer was to sue me"),
                listOf("response"),
                listOf("question", "issue")
            ),
            DefinitionDto(
                0,
                "answer",
                "understand the meaning of",
                PartOfSpeechDto.VERB,
                listOf("The question concerning the meaning of life cannot be answered"),
                null,
                null
            )
        ),
        Date(1591850938247L)
    )

    fun getSampleWords() = listOf(
        WordDto("hello",    "həˈləʊ",    listOf("hel", "lo"),    FrequencyDto.FREQUENT,      null, Date(10)),
        WordDto("morning",  "ˈmôrniNG",  listOf("mor", "ning"),  FrequencyDto.VERY_FREQUENT, null, Date(20)),
        WordDto("answer",   "'ænsər",    listOf("an", "swer"),   FrequencyDto.NORMAL,        null, Date(30)),
        WordDto("cold",     "kōld",      listOf("cold"),         FrequencyDto.VERY_FREQUENT, null, Date(40)),
        WordDto("change",   "CHānj",     listOf("change"),       FrequencyDto.FREQUENT,      null, Date(50)),
        WordDto("movement", "ˈmo͞ovmənt", listOf("move", "ment"), FrequencyDto.RARE,          null, Date(60))
    )
}