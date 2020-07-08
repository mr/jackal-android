package io.mega.nrobinson.jackal

import android.app.Application
import io.mega.nrobinson.jackal.di.ApplicationComponent
import io.mega.nrobinson.jackal.di.DaggerApplicationComponent

class JackalApplication: Application() {
    val appComponent: ApplicationComponent = DaggerApplicationComponent.create()
}