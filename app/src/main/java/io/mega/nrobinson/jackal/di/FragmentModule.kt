package io.mega.nrobinson.jackal.di

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import io.mega.nrobinson.jackal.ui.progress.ProgressFragment

@Module
interface FragmentModule  {
    @Binds
    fun bindFragmentFactory(factory: DaggerFragmentFactory): FragmentFactory

    @Binds
    @IntoMap
    @FragmentKey(ProgressFragment::class)
    fun progressFragment(fragment: ProgressFragment): Fragment
}