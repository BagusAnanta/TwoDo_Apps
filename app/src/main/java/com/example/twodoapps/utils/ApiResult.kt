package com.example.twodoapps.utils

sealed class ApiResult<out T> {
    data class Success<out T>(val data: T) : ApiResult<T>()
    data class Error(val message: String, val code: Int) : ApiResult<Nothing>()
    object Loading : ApiResult<Nothing>()
}