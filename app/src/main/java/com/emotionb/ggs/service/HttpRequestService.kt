package com.emotionb.ggs.service

import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.statement.HttpResponse

interface HttpRequestService {
    suspend fun getResponse(url: String): HttpResponse

    companion object {
        fun create(): HttpRequestService {
            return HttpRequestServiceImpl(
                client = HttpClient(CIO)
            )
        }
    }
}