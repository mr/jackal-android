package io.mega.nrobinson.jackal.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import io.mega.nrobinson.jackal.ui.viewmodel.ProgressViewModel

@Module
interface ViewModelModule {
    @Binds
    fun bindViewModelFactory(factory: DaggerViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(ProgressViewModel::class)
    fun progressViewModel(progressViewModel: ProgressViewModel): ViewModel
}