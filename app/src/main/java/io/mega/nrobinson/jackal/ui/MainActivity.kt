package io.mega.nrobinson.jackal.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentFactory
import io.mega.nrobinson.jackal.JackalApplication
import io.mega.nrobinson.jackal.R
import javax.inject.Inject

class MainActivity: AppCompatActivity() {
    @Inject lateinit var fragmentFactory: FragmentFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as JackalApplication).appComponent.inject(this)
//        supportFragmentManager.fragmentFactory = fragmentFactory
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}