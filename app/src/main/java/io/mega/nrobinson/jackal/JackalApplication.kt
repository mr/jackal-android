package io.mega.nrobinson.jackal

import android.app.Application
import io.mega.nrobinson.jackal.di.ApplicationComponent
import io.mega.nrobinson.jackal.di.DaggerApplicationComponent
import timber.log.Timber
import javax.inject.Inject

class JackalApplication: Application() {
    val appComponent: ApplicationComponent = DaggerApplicationComponent.create()
    @Inject lateinit var tree: Timber.Tree

    override fun onCreate() {
        super.onCreate()
        appComponent.inject(this)
        Timber.plant(tree)
    }
}