package com.emotionb.ggs.service

import android.util.Log
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
class HttpRequestServiceImpl(
    private val client: HttpClient
) : HttpRequestService {

    override suspend fun getResponse(url: String): HttpResponse {
            val response: HttpResponse = client.get(url)
            client.close()

            return response
    }
}