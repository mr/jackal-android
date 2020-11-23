package io.mega.nrobinson.jackal.ui.progress

import android.app.Activity
import android.content.Intent
import android.net.Uri
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
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.jakewharton.rxbinding4.swiperefreshlayout.refreshes
import com.jakewharton.rxbinding4.view.clicks
import io.mega.nrobinson.jackal.R
import io.mega.nrobinson.jackal.api.model.JackalProgress
import io.mega.nrobinson.jackal.extensions.addTo
import io.mega.nrobinson.jackal.rx.AutoDisposable
import io.mega.nrobinson.jackal.ui.viewmodel.ProgressViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.BehaviorSubject
import okio.buffer
import okio.source
import javax.inject.Inject

class ProgressFragment @Inject constructor(
    private val viewModelFactory: ViewModelProvider.Factory
): Fragment() {

    private val progressViewModel: ProgressViewModel by viewModels { viewModelFactory }
    private val disposable = AutoDisposable(lifecycle)

    private val fileResult = BehaviorSubject.create<Uri>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_progress, container, false)
        val recycler = v.findViewById(R.id.progress_recycler) as RecyclerView
        val adapter = ProgressAdapter()
        val layoutManager = LinearLayoutManager(context)
        recycler.apply {
            this.adapter = adapter
            setHasFixedSize(true)
            this.layoutManager = layoutManager
            addItemDecoration(DividerItemDecoration(context, layoutManager.orientation))
        }
        val swipe = v.findViewById(R.id.recycler_swipe) as SwipeRefreshLayout
        Observable.merge(
            progressViewModel.progress(),
            swipe.refreshes().switchMap { progressViewModel.progress() })
            .subscribeOn(AndroidSchedulers.mainThread())
            .onErrorReturn {
                Log.e("ProgressFragment", it.message, it)
                JackalProgress(listOf(), listOf(), listOf(), listOf())
            }
            .subscribe { progress ->
                swipe.isRefreshing = false
                adapter.submitList(listOf(
                    progress.ftps,
                    progress.calculating,
                    progress.pending,
                    progress.torrents).flatten())
            }
            .addTo(disposable)
        val fab = v.findViewById(R.id.fab) as FloatingActionButton
        fab.clicks()
            .switchMap{ getFileContent() }
            .switchMap(::startFile)
            .subscribeOn(AndroidSchedulers.mainThread())
            .subscribe {
                Log.i(TAG, "Started torrent")
            }
            .addTo(disposable)
        return v
    }

    private fun startFile(uri: Uri): Observable<Unit> =
        context?.contentResolver
            ?.openInputStream(uri)
            ?.source()
            ?.buffer()
            ?.readByteString()
            ?.let(progressViewModel::start)
            ?: Observable.empty()

    private fun getFileContent(): Observable<Uri> {
        val intent = Intent(Intent.ACTION_GET_CONTENT).apply {
            type = "*/*"
            addCategory(Intent.CATEGORY_OPENABLE)
            putExtra(Intent.EXTRA_LOCAL_ONLY, true)
        }
        startActivityForResult(intent, FILE_REQUEST_CODE)
        return fileResult
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode != FILE_REQUEST_CODE || resultCode != Activity.RESULT_OK) {
            return
        }

        val uri = data?.data ?: return
        fileResult.onNext(uri)
    }

    companion object {
        private const val FILE_REQUEST_CODE = 0
        private val TAG = ProgressFragment::class.simpleName
    }
}
