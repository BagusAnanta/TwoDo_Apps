package com.example.twodoapps.apiService

import android.util.Log
import com.example.twodoapps.dataClassTodo.UserDataClass
import com.example.twodoapps.utils.ApiResult
import com.google.gson.Gson
import fuel.FuelBuilder
import kotlinx.io.readString
import okhttp3.HttpUrl.Companion.toHttpUrl

object UserService {
    private const val BASE_URL = "http://172.188.241.74/"
    var gson = Gson()
    var fuel = FuelBuilder().build()

    // for Login
    suspend fun getUsersLogin(user: UserDataClass): ApiResult<Boolean> {
        return try{
            // make url with BASE_URL Structure like this http://172.188.241.74/auth/login
            val urlLogin = BASE_URL.toHttpUrl().newBuilder().addPathSegment("auth").addPathSegment("login").build()

            val response = fuel.post{
                url = urlLogin.toString()
                body = gson.toJson(user)
                headers = mapOf("Content-Type" to "application/json")
            }

            when(response.statusCode){
                201 -> ApiResult.Success(true)
                200 -> ApiResult.Success(true)
                else -> ApiResult.Error(
                    "Server Error : ${response.source.readString()}",
                    response.statusCode
                )
            }
        } catch (e : Exception){
            ApiResult.Error("Error Network Connection $e", -1)
        }
    }

    // for register
    suspend fun addUserRegister(user: UserDataClass) : ApiResult<Boolean>{
        return try{
            // make url with BASE_URL Structure like this http://172.188.241.74/auth/register
            val urlAddUser = BASE_URL.toHttpUrl().newBuilder().addPathSegment("auth").addPathSegment("register").build()

            Log.e("URL", urlAddUser.toString())
            Log.e("UserJson", gson.toJson(user))

            val response = fuel.post {
                url = urlAddUser.toString()
                body = gson.toJson(user)
                headers = mapOf("Content-Type" to "application/json")
            }

            when(response.statusCode){
                201 -> ApiResult.Success(true)
                200 -> ApiResult.Success(true)
                else -> ApiResult.Error(
                    "Server Error : ${response.source.readString()}",
                    response.statusCode
                )
            }
        } catch (e : Exception){
            ApiResult.Error("Error Network Connection $e", -1)
        }
    }

}