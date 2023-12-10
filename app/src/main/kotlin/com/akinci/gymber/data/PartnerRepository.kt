package com.akinci.gymber.data

import com.akinci.gymber.core.network.toResponse
import com.akinci.gymber.data.remote.PartnerListServiceResponse
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.request.parameter
import io.ktor.client.request.url
import javax.inject.Inject

class PartnerRepository @Inject constructor(
    private val httpClient: HttpClient,
) {

    suspend fun getPartners() = runCatching {
        httpClient.get {
            url("v2/nl-nl/partners/search")
            headers { append("X-Onefit-Client", "website/29.18.1") }
            parameter("city", "AMS")
        }
            .toResponse<PartnerListServiceResponse>()
            .getOrThrow()
    }
}
