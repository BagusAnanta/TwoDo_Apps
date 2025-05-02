package com.example.twodoapps.apiService

import android.util.Log
import com.example.twodoapps.dataClassTodo.UserDataClass
import com.example.twodoapps.utils.ApiResult
import com.google.gson.Gson
import fuel.FuelBuilder
import kotlinx.io.readString
import okhttp3.HttpUrl.Companion.toHttpUrl
import org.json.JSONObject

object UserService {
    private const val BASE_URL = "http://172.188.241.74/"
    var gson = Gson()
    var fuel = FuelBuilder().build()

    // for remember : 200 -> Ok for get, 201 -> Ok for create

    // for Login
    suspend fun getUsersLogin(user: UserDataClass): ApiResult<String> {
        return try{
            // make url with BASE_URL Structure like this http://172.188.241.74/auth/login
            val urlLogin = BASE_URL.toHttpUrl().newBuilder().addPathSegment("auth").addPathSegment("login").build()

            val response = fuel.post{
                url = urlLogin.toString()
                body = gson.toJson(user)
                headers = mapOf("Content-Type" to "application/json")
            }

            when(response.statusCode){
                201 -> {
                    // get token
                    val cookieHeader = response.headers["Set-Cookie"]?.firstOrNull()
                    val token = cookieHeader?.toString()?.substringAfter("token=")?.substringBefore(";")
                    ApiResult.Success(token ?: ApiResult.Error("Token not found", -1).toString())
                }
                200 -> {
                    // get token
                    val cookieHeader = response.headers["Set-Cookie"]?.firstOrNull()
                    val token = cookieHeader?.toString()?.substringAfter("token=")?.substringBefore(";")
                    ApiResult.Success(token ?: ApiResult.Error("Token not found", -1).toString())
                }
                else -> ApiResult.Error(
                    "Server Error Login: ${response.source.readString()}",
                    response.statusCode
                )
            }
        } catch (e : Exception){
            ApiResult.Error("Error Network Connection Login $e", -1)
        }
    }

    // for register
    suspend fun addUserRegister(user: UserDataClass) : ApiResult<String>{
        return try{
            // make url with BASE_URL Structure like this http://172.188.241.74/auth/register
            val urlAddUser = BASE_URL.toHttpUrl().newBuilder().addPathSegment("auth").addPathSegment("register").build()

            val response = fuel.post {
                url = urlAddUser.toString()
                body = gson.toJson(user)
                headers = mapOf("Content-Type" to "application/json")
            }

            when(response.statusCode){
                201 -> {
                    // get token
                    val token = JSONObject(response.source.readString()).getString("token")
                    ApiResult.Success(token)
                }
                200 -> {
                    // get token
                    val token = JSONObject(response.source.readString()).getString("token")
                    ApiResult.Success(token)
                }
                else -> ApiResult.Error(
                    "Server Error Register: ${response.source.readString()}",
                    response.statusCode
                )
            }
        } catch (e : Exception){
            ApiResult.Error("Error Network Connection Register $e", -1)
        }
    }

}