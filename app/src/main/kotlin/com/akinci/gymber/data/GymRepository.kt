package com.akinci.gymber.data

import com.akinci.gymber.core.network.toResponse
import com.akinci.gymber.data.remote.GymServiceResponse
import com.akinci.gymber.domain.mapper.toDomain
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.request.parameter
import javax.inject.Inject

class GymRepository @Inject constructor(
    private val httpClient: HttpClient,
) {

    suspend fun getGyms() = runCatching {
        httpClient.get(PATH_GYMS) {
            headers { append("X-Onefit-Client", "website/29.18.1") }
            parameter("city", "AMS")
        }.toResponse<GymServiceResponse>().map {
            it.toDomain()
        }.getOrThrow()
    }

    companion object {
        const val PATH_GYMS = "v2/nl-nl/partners/search"
    }
}
