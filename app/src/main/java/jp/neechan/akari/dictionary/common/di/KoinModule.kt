package jp.neechan.akari.dictionary.common.di

import org.koin.core.module.Module

interface KoinModule {

    fun get(): Module
}