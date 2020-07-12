package io.mega.nrobinson.jackal.di

import android.os.Bundle
import androidx.fragment.app.FragmentFactory
import androidx.navigation.fragment.NavHostFragment
import io.mega.nrobinson.jackal.JackalApplication
import javax.inject.Inject

class DaggerNavHostFragment : NavHostFragment() {
    @Inject protected lateinit var fragmentFactory: FragmentFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        (requireActivity().application as JackalApplication).appComponent.inject(this)
        childFragmentManager.fragmentFactory = fragmentFactory
        super.onCreate(savedInstanceState)
    }
}