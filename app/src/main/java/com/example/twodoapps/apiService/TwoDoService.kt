package com.example.twodoapps.apiService

import android.util.Log
import com.example.twodoapps.dataClassTodo.Twododata
import com.google.gson.Gson
import fuel.FuelBuilder
import fuel.httpGet
import kotlinx.io.readString
import okhttp3.HttpUrl.Companion.toHttpUrl

object TwoDoService {
    private const val BASE_URL = "https://publicobject.com/helloworld.txt"
    private val gson = Gson()
    private val fuel = FuelBuilder().build()

    suspend fun getTodos() : List<Twododata>{
        try{
            val response = fuel.get{BASE_URL}.source.readString()
            return gson.fromJson(response, Array<Twododata>::class.java).toList()
        } catch (e: Exception){
            // catch Exception at here
        }

        return emptyList()
    }

    suspend fun getTodo(id: String) : List<Twododata>{
        try{
            val url = BASE_URL.toHttpUrl().newBuilder().addPathSegment(id).build() // it similar like : url.com/id
            val response = fuel.get{url.toString()}.source.readString()
            return gson.fromJson(response, Array<Twododata>::class.java).toList()
        } catch (e : Exception){
            // catch exception at here
        }

        return emptyList()
    }

    suspend fun addTodo(todo: Twododata){
        try{
            val response = fuel.post {
                url = BASE_URL
                body = gson.toJson(todo)
            }

            Log.d("Response", response.statusCode.toString())

        } catch (e : Exception){
            // catch exception at here
        }
    }

    suspend fun updateTodo(id: String, todo: Twododata){
        try{
            val urlUpdate = BASE_URL.toHttpUrl().newBuilder().addPathSegment(id).build()
            val response = fuel.put {
                url = urlUpdate.toString()
                body = gson.toJson(todo)
            }

            Log.d("Response", response.statusCode.toString())

        } catch (e : Exception){
            // catch exception at here
        }
    }

    suspend fun deleteTodo(id: String){
        try {
            val urlDelete = BASE_URL.toHttpUrl().newBuilder().addPathSegment(id).build()
            val response = fuel.delete {
                url = urlDelete.toString()
            }
        } catch (e : Exception){
            // catch exception at here
        }
    }


    
}