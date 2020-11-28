package io.mega.nrobinson.jackal.di

import dagger.Component
import io.mega.nrobinson.jackal.JackalApplication
import io.mega.nrobinson.jackal.ui.MainActivity

@Component(
    modules = [
        NetworkModule::class,
        FragmentModule::class,
        ViewModelModule::class,
        LoggerModule::class
    ]
)
interface ApplicationComponent {
    fun inject(application: JackalApplication)
    fun inject(activity: MainActivity)
    fun inject(navHostFragment: DaggerNavHostFragment)
}