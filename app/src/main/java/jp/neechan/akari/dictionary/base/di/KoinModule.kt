package jp.neechan.akari.dictionary.base.di

import org.koin.core.module.Module

interface KoinModule {

    fun get(): Module
}