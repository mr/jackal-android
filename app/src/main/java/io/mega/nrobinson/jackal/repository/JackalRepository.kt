package io.mega.nrobinson.jackal.repository

import io.mega.nrobinson.jackal.api.JackalService
import io.mega.nrobinson.jackal.api.model.JackalProgress
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject

class JackalRepository @Inject constructor(private val service: JackalService) {
    fun start(torrent: ByteArray): Completable {
        val body =
            torrent.toRequestBody("application/octet-stream".toMediaTypeOrNull())
        return service.start(body)
            .subscribeOn(Schedulers.io())
    }

    fun progress(): Single<JackalProgress> =
        service.progress()
            .subscribeOn(Schedulers.io())
}