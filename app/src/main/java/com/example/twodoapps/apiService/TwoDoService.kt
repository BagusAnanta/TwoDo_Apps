package com.example.twodoapps.apiService

import android.util.Log
import com.example.twodoapps.dataClassTodo.Twododata
import com.example.twodoapps.sharePref.AuthHelper
import com.example.twodoapps.utils.ApiResult
import com.google.gson.Gson
import fuel.FuelBuilder
import kotlinx.io.readString
import okhttp3.HttpUrl.Companion.toHttpUrl

object TwoDoService {
    // place Base URL at here
    private const val BASE_URL = "http://172.188.241.74/"
    var gson = Gson()
    var fuel = FuelBuilder().build()

    lateinit var authHelper : AuthHelper

    fun initialize(authHelper: AuthHelper){
        this.authHelper = authHelper
    }

    private suspend fun getCookieHeader() : Map<String, String>{
        val token = authHelper.getToken()
        return if(token != null){
            mapOf("Content-Type" to "application/json", "Cookie" to "token=$token")
        } else {
            emptyMap()
        }
    }

    suspend fun getTodos() : ApiResult<List<Twododata>>{
        return try{
            val urlGetTodos = BASE_URL.toHttpUrl().newBuilder().addPathSegment("todos").build() // it similar like : url.com/todos
            val header = getCookieHeader()

            Log.e("Cookie Header", header.toString())

            val response = fuel.get{
                url = urlGetTodos.toString()
                headers = header
            }

            when(response.statusCode){
                200 -> ApiResult.Success(gson.fromJson(response.source.readString(), Array<Twododata>::class.java).toList())
                201 -> ApiResult.Success(gson.fromJson(response.source.readString(), Array<Twododata>::class.java).toList())
                else -> ApiResult.Error(
                    "Server Error GetData : ${response.source.readString()}",
                    response.statusCode
                )
            }
        } catch (e: Exception){
            // catch Exception at here
            ApiResult.Error("Error Network Connection GetData ${e.printStackTrace()}", -1)
        }

    }

    suspend fun addTodo(todo: Twododata) : ApiResult<String>{
        return try{
            val urlAddTodo = BASE_URL.toHttpUrl().newBuilder().addPathSegment("todos").build() // it similar like : url.com/todos
            val header = getCookieHeader()
            val response = fuel.post {
                url = urlAddTodo.toString()
                body = gson.toJson(todo)
                headers = header
            }

            when(response.statusCode){
                201 -> ApiResult.Success(response.source.readString())
                200 -> ApiResult.Success(response.source.readString())
                else -> ApiResult.Error(
                    "Server Error Add Data : ${response.source.readString()}",
                    response.statusCode
                )
            }

        } catch (e : Exception){
            // catch exception at here
            ApiResult.Error(
                "Error Network Connection Add Data $e",
                -1
            )
        }
    }

    suspend fun updateTodo(id: String, todo: Twododata) : ApiResult<String>{
        return try{
            val urlUpdate = BASE_URL.toHttpUrl().newBuilder().addPathSegment("todos").addPathSegment(id).build() // it similar like : url.com/todos/id
            val header = getCookieHeader()
            val response = fuel.put {
                url = urlUpdate.toString()
                body = gson.toJson(todo)
                headers = header
            }

            when(response.statusCode){
                201 -> ApiResult.Success(response.source.readString())
                200 -> ApiResult.Success(response.source.readString())
                else -> ApiResult.Error(
                    "Server Error Update Data : ${response.source.readString()}",
                    response.statusCode
                )
            }

        } catch (e : Exception){
            // catch exception at here
            ApiResult.Error(
                "Error Network Connection Update Data $e",
                -1
            )
        }
    }

    suspend fun deleteTodo(id: String) : ApiResult<String>{
        return try {
            val urlDelete = BASE_URL.toHttpUrl().newBuilder().addPathSegment("todos").addPathSegment(id).build() // it similar like : url.com/todos/id
            val header = getCookieHeader()
            val response = fuel.delete {
                url = urlDelete.toString()
                headers = header
            }

            when(response.statusCode){
                201 -> ApiResult.Success(response.source.readString())
                else -> ApiResult.Error(
                    "Server Error Delete Data : ${response.source.readString()}",
                    response.statusCode
                )
            }


        } catch (e : Exception){
            // catch exception at here
            ApiResult.Error(
                "Error Network Connection Delete Data $e",
                -1
            )
        }
    }


    
}