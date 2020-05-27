package jp.neechan.akari.dictionary.base.di

object BaseProperties : KoinProperties {

    const val BASE_URL = "baseUrl"
    private const val BASE_URL_VALUE = "https://wordsapiv1.p.rapidapi.com/"

    const val DATABASE_NAME = "databaseName"
    private const val DATABASE_NAME_VALUE = "dictionary.db"

    override val properties = mapOf(
        BASE_URL to BASE_URL_VALUE,
        DATABASE_NAME to DATABASE_NAME_VALUE
    )
}