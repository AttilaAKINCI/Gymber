package com.akinci.gymber.core.network

import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpStatusCode

suspend inline fun <reified T> HttpResponse.toResponse() =
    when (status) {
        HttpStatusCode.OK -> body<T>()
        else -> throw Throwable("Serverside error with code: ${status.value}")
    }