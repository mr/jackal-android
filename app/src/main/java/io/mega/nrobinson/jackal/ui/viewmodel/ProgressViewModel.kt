package io.mega.nrobinson.jackal.ui.viewmodel

import androidx.lifecycle.ViewModel
import io.mega.nrobinson.jackal.api.model.JackalProgress
import io.mega.nrobinson.jackal.extensions.addTo
import io.mega.nrobinson.jackal.repository.JackalRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.subjects.BehaviorSubject
import okio.ByteString
import javax.inject.Inject

class ProgressViewModel @Inject constructor(
    private val repository: JackalRepository
): ViewModel() {
    private val startBehavior = BehaviorSubject.create<Unit>()
    private val progressBehavior = BehaviorSubject.create<JackalProgress>()
    private val disposable = CompositeDisposable()

    fun start(torrent: ByteString): Observable<Unit> {
        repository.start(torrent)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ startBehavior.onNext(Unit) }, startBehavior::onError)
            .addTo(disposable)
        return startBehavior
    }

    fun progress(): Observable<JackalProgress> {
        repository.progress()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(progressBehavior::onNext, progressBehavior::onError)
            .addTo(disposable)
        return progressBehavior
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}
