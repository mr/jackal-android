package io.mega.nrobinson.jackal.api

import io.mega.nrobinson.jackal.api.model.JackalProgress
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import okhttp3.RequestBody
import okhttp3.ResponseBody
import okio.ByteString
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST

interface JackalService {
    @Headers("Content-Type: application/octet-stream")
    @POST("/start")
    fun start(@Body body: RequestBody): Completable

    @GET("/progress")
    fun progress(): Single<JackalProgress>
}