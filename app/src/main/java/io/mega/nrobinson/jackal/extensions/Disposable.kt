package io.mega.nrobinson.jackal.extensions

import io.mega.nrobinson.jackal.rx.AutoDisposable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable

fun Disposable.addTo(compositeDisposable: CompositeDisposable) =
    compositeDisposable.add(this)

fun Disposable.addTo(autoDisposable: AutoDisposable) =
    addTo(autoDisposable.compositeDisposable)
