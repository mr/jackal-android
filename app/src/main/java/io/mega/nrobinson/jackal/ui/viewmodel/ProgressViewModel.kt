package io.mega.nrobinson.jackal.ui.viewmodel

import androidx.lifecycle.ViewModel
import arrow.core.Either
import io.mega.nrobinson.jackal.api.model.JackalProgress
import io.mega.nrobinson.jackal.extensions.addTo
import io.mega.nrobinson.jackal.repository.JackalRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.subjects.BehaviorSubject
import javax.inject.Inject

class ProgressViewModel @Inject constructor(
    private val repository: JackalRepository
): ViewModel() {
    private val startBehavior = BehaviorSubject.create<Either<Throwable, Unit>?>()
    private val progressBehavior = BehaviorSubject.create<Either<Throwable, JackalProgress>?>()
    private val disposable = CompositeDisposable()

    fun started(): Observable<Either<Throwable, Unit>?> = startBehavior
    fun progresses(): Observable<Either<Throwable, JackalProgress>?> = progressBehavior

    fun start(torrent: ByteArray) {
        startBehavior.onNext(null)
        repository.start(torrent)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                startBehavior.onNext(Either.right(Unit))
            }, {
                startBehavior.onNext(Either.left(it))
            })
            .addTo(disposable)
    }

    fun progress() {
        progressBehavior.onNext(null)
        repository.progress()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                progressBehavior.onNext(Either.right(it))
            }, {
                progressBehavior.onNext(Either.left(it))
            })
            .addTo(disposable)
    }
}
