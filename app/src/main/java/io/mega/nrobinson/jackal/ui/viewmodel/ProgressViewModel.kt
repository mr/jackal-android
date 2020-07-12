package io.mega.nrobinson.jackal.ui.viewmodel

import androidx.lifecycle.ViewModel
import arrow.core.Either
import arrow.core.Option
import io.mega.nrobinson.jackal.api.model.JackalProgress
import io.mega.nrobinson.jackal.extensions.addTo
import io.mega.nrobinson.jackal.repository.JackalRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.subjects.BehaviorSubject
import javax.inject.Inject

typealias ViewModelResult<T> = Option<Either<Throwable, T>>

class ProgressViewModel @Inject constructor(
    private val repository: JackalRepository
): ViewModel() {
    private val startBehavior = BehaviorSubject.create<ViewModelResult<Unit>>()
    private val progressBehavior = BehaviorSubject.create<ViewModelResult<JackalProgress>>()
    private val disposable = CompositeDisposable()

    fun started(): Observable<ViewModelResult<Unit>> = startBehavior
    fun progresses(): Observable<ViewModelResult<JackalProgress>> = progressBehavior

    fun start(torrent: ByteArray) {
        startBehavior.onNext(Option.empty())
        repository.start(torrent)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                startBehavior.onNext(Option.just(Either.right(Unit)))
            }, {
                startBehavior.onNext(Option.just(Either.left(it)))
            })
            .addTo(disposable)
    }

    fun progress() {
        progressBehavior.onNext(Option.empty())
        repository.progress()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                progressBehavior.onNext(Option.just(Either.right(it)))
            }, {
                progressBehavior.onNext(Option.just(Either.left(it)))
            })
            .addTo(disposable)
    }
}
