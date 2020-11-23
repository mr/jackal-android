package io.mega.nrobinson.jackal.repository

import io.mega.nrobinson.jackal.api.JackalService
import io.mega.nrobinson.jackal.api.model.JackalProgress
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import okio.ByteString
import javax.inject.Inject

class JackalRepository @Inject constructor(private val service: JackalService) {
    fun start(torrent: ByteString): Completable =
        service.start(torrent)
            .subscribeOn(Schedulers.io())

    fun progress(): Single<JackalProgress> =
        service.progress()
            .subscribeOn(Schedulers.io())
}