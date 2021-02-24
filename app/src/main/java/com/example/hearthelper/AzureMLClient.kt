package com.example.hearthelper

import android.util.Log
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import retrofit2.Retrofit
import java.io.IOException
import java.net.MalformedURLException
import okhttp3.RequestBody.Companion.toRequestBody



private const val TAG = "AzureMLClient"

enum class DownloadStatus {
    OK, IDLE, NOT_INITIALISED, FAILED_OR_EMPTY, PERMISSIONS_ERROR, ERROR
}

class AzureMLClient(private val endPointURL: String, private val key: String) {  //API KEY
    /*
     Takes an Azure ML Request Body then Returns the Response String Which Contains Scored Lables etc
    */
     private var downloadStatus = DownloadStatus.IDLE

     suspend fun requestResponse(requestBody: String?): String {

        Log.d(TAG, "requestResponse: starts with $requestBody")
        // Create Retrofit
        val retrofit = Retrofit.Builder()
            .baseUrl("https://ussouthcentral.services.azureml.net") // we will overwrite this url in createRequest method below
            .build()

        // Create Service
        val service = retrofit.create(APIService::class.java)

        // Create RequestBody ( We're not using any converter, like GsonConverter, MoshiConverter e.t.c, that's why we use RequestBody )
        val mRequestBody = requestBody?.toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())

            try {
                downloadStatus = DownloadStatus.OK
                val response =
                    service.createRequest(endPointURL, mRequestBody!!, "Bearer $key")

                val responseString =  withContext(IO) {
                   response.body()?.string()
                }
                Log.d(TAG, "requestResponse: ends with $responseString")
                return responseString!!
            } catch (e: Exception) {
                val errorMessage = when (e) {
                    is MalformedURLException -> {
                        downloadStatus = DownloadStatus.NOT_INITIALISED
                        "doInBackground: Invalid URL ${e.message}"
                    }
                    is IOException -> {
                        downloadStatus = DownloadStatus.FAILED_OR_EMPTY
                        "doInBackground: IO Exception reading data: ${e.message}"
                    }
                    is SecurityException -> {
                        downloadStatus = DownloadStatus.PERMISSIONS_ERROR
                        "doInBackground: Security exception: Needs permission? ${e.message}"
                    }
                    else -> {
                        downloadStatus = DownloadStatus.ERROR
                        "Unknown error: ${e.message}"
                    }
                }
                Log.e(TAG, errorMessage)

                Log.d(TAG, "requestResponse: ends with $errorMessage")
                return errorMessage
            }
    }

    fun getDownloadStatus() : DownloadStatus {
        return downloadStatus
    }
 }
