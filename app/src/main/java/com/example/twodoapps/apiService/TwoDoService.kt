package com.example.twodoapps.apiService

import android.util.Log
import com.example.twodoapps.dataClassTodo.Twododata
import com.example.twodoapps.utils.ApiResult
import com.google.gson.Gson
import fuel.FuelBuilder
import fuel.httpGet
import kotlinx.io.readString
import okhttp3.HttpUrl.Companion.toHttpUrl

object TwoDoService {
    // place Base URL at here
    private const val BASE_URL = "http://172.188.241.74/"
    var gson = Gson()
    var fuel = FuelBuilder().build()

    suspend fun getTodos() : ApiResult<List<Twododata>>{
        return try{
            val response = fuel.get{BASE_URL}

            when(response.statusCode){
                200 -> ApiResult.Success(gson.fromJson(response.source.readString(), Array<Twododata>::class.java).toList())
                else -> ApiResult.Error(
                    "Server Error : ${response.headers}",
                    response.statusCode
                )
            }
        } catch (e: Exception){
            // catch Exception at here
            ApiResult.Error("Error Network Connection $e", -1)
        }

    }

    suspend fun getTodo(id: String) : ApiResult<List<Twododata>>{
        return try{
            var url = BASE_URL.toHttpUrl().newBuilder().addPathSegment(id).build() // it similar like : url.com/id
            val response = fuel.get{url}

            when(response.statusCode){
                200 -> ApiResult.Success(gson.fromJson(response.source.readString(), Array<Twododata>::class.java).toList())
                else -> ApiResult.Error(
                    "Server Error : ${response.headers}",
                    response.statusCode
                )
            }

        } catch (e : Exception){
            // catch exception at here
            ApiResult.Error("Error Network Connection $e", -1)
        }
    }

    suspend fun addTodo(todo: Twododata) : ApiResult<String>{
        return try{
            val response = fuel.post {
                url = BASE_URL
                body = gson.toJson(todo)
            }

            when(response.statusCode){
                201 -> ApiResult.Success(response.source.readString())
                else -> ApiResult.Error(
                    "Server Error : ${response.headers}",
                    response.statusCode
                )
            }

        } catch (e : Exception){
            // catch exception at here
            ApiResult.Error(
                "Error Network Connection $e",
                -1
            )
        }
    }

    suspend fun updateTodo(id: String, todo: Twododata) : ApiResult<String>{
        return try{
            val urlUpdate = BASE_URL.toHttpUrl().newBuilder().addPathSegment(id).build()
            val response = fuel.put {
                url = urlUpdate.toString()
                body = gson.toJson(todo)
            }

            when(response.statusCode){
                201 -> ApiResult.Success(response.source.readString())
                else -> ApiResult.Error(
                    "Server Error : ${response.headers}",
                    response.statusCode
                )
            }

        } catch (e : Exception){
            // catch exception at here
            ApiResult.Error(
                "Error Network Connection $e",
                -1
            )
        }
    }

    suspend fun deleteTodo(id: String) : ApiResult<String>{
        return try {
            val urlDelete = BASE_URL.toHttpUrl().newBuilder().addPathSegment(id).build()
            val response = fuel.delete {
                url = urlDelete.toString()
            }

            when(response.statusCode){
                201 -> ApiResult.Success(response.source.readString())
                else -> ApiResult.Error(
                    "Server Error : ${response.headers}",
                    response.statusCode
                )
            }


        } catch (e : Exception){
            // catch exception at here
            ApiResult.Error(
                "Error Network Connection $e",
                -1
            )
        }
    }


    
}