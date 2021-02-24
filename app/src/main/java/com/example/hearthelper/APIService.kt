package com.example.hearthelper

import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response

import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Url

interface APIService {
    @POST
    suspend fun createRequest(@Url url: String, @Body requestBody: RequestBody, @Header("Authorization") key: String): Response<ResponseBody>
}