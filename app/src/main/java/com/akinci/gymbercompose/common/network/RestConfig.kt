package com.akinci.gymbercompose.common.network

import com.akinci.gymbercompose.BuildConfig

class RestConfig {
    companion object {
        const val API_BASE_URL = BuildConfig.BASE_URL
        const val PARTNER_LIST_URL = "v2/en-nl/partners/city/{cityCode}"
    }
}