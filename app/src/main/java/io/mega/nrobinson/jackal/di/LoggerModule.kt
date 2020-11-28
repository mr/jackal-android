package io.mega.nrobinson.jackal.di

import dagger.Module
import dagger.Provides
import timber.log.Timber

@Module
class LoggerModule {
    @Provides
    fun provideDebugTree(): Timber.Tree = Timber.DebugTree()
}