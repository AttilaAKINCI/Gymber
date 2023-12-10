package com.akinci.gymbercompose.data.api

import com.akinci.gymbercompose.common.network.RestConfig
import com.akinci.gymbercompose.data.output.PartnerListResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface PartnerServiceDao {

    @GET(RestConfig.PARTNER_LIST_URL)
    suspend fun getPartnerList(@Path("cityCode") cityCode: String = "AMS") : Response<PartnerListResponse>

}