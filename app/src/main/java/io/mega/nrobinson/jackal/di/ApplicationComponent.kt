package io.mega.nrobinson.jackal.di

import dagger.Component
import io.mega.nrobinson.jackal.ui.MainActivity

@Component(
    modules = [
        NetworkModule::class,
        FragmentModule::class,
        ViewModelModule::class
    ]
)
interface ApplicationComponent {
    fun inject(activity: MainActivity)
}