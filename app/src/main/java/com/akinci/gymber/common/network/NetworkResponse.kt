package com.akinci.gymber.common.network

sealed class NetworkResponse<out T> {
    data class Success<T>(val data: T?) : NetworkResponse<T>()
    data class Error(val message: String) : NetworkResponse<Nothing>()
    object NetworkError: NetworkResponse<Nothing>()
    data class Loading(val message: String = "") : NetworkResponse<Nothing>()
}