package io.mega.nrobinson.jackal.ui.progress

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import arrow.core.Either
import io.mega.nrobinson.jackal.R
import io.mega.nrobinson.jackal.ui.viewmodel.ProgressViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import javax.inject.Inject

class ProgressFragment @Inject constructor(
    private val viewModelFactory: ViewModelProvider.Factory
): Fragment() {

    private val progressViewModel: ProgressViewModel by viewModels { viewModelFactory }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_progress, container)
        val recycler = v.findViewById(R.id.progress_recycler) as RecyclerView
        val adapter = ProgressAdapter()
        val layoutManager = LinearLayoutManager(context)
        recycler.apply {
            this.adapter = adapter
            setHasFixedSize(true)
            this.layoutManager = layoutManager
            addItemDecoration(DividerItemDecoration(context, layoutManager.orientation))
        }
        progressViewModel.progress()
        progressViewModel.progresses()
            .subscribeOn(AndroidSchedulers.mainThread())
            .subscribe { progress ->
                when (progress) {
                    is Either.Left -> Log.e("ProgressFragment", progress.a.message, progress.a)
                    is Either.Right -> adapter.submitList(listOf(
                        progress.b.ftps,
                        progress.b.calculating,
                        progress.b.pending,
                        progress.b.torrents).flatten())
                }
            }
        return v
    }
}
