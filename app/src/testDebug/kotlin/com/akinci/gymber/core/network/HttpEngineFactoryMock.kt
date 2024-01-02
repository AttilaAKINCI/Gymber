package com.akinci.gymber.core.network

import com.akinci.gymber.core.network.json.GymServiceResponse
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.HttpStatusCode.Companion.OK
import io.ktor.http.headersOf
import java.nio.charset.Charset

class HttpEngineFactoryMock : HttpEngineFactory() {

    private val responseHeaders = headersOf(HttpHeaders.ContentType, "application/json")
    var statusCode: HttpStatusCode = OK
    var simulateException = false

    override fun create(): MockEngine {
        return MockEngine { request ->
            if (simulateException) throw Throwable("Simulated Network Exception")

            val path = request.url.encodedPath
            val content = when {
                path == "/v2/nl-nl/partners/search" -> getGymListContent(statusCode)
                else -> throw IllegalStateException("Unsupported path")
            }

            respond(
                content = content,
                status = statusCode,
                headers = responseHeaders,
            )
        }
    }

    private fun getGymListContent(statusCode: HttpStatusCode): ByteArray {
        return when (statusCode) {
            OK -> GymServiceResponse.success.toByteArray(Charset.defaultCharset())
            else -> throw Throwable(statusCode.description)
        }
    }
}
