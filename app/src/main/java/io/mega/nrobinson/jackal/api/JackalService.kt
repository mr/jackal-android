package io.mega.nrobinson.jackal.api

import io.mega.nrobinson.jackal.api.model.JackalProgress
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface JackalService {
    @POST("/start")
    fun start(@Body torrent: ByteArray): Completable

    @GET("/progress")
    fun progress(): Single<JackalProgress>
}