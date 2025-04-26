package com.example.twodoapps.repository

import com.example.twodoapps.apiService.TwoDoService
import com.example.twodoapps.apiService.UserService
import com.example.twodoapps.dataClassTodo.Twododata
import com.example.twodoapps.dataClassTodo.UserDataClass
import javax.inject.Inject

class ApiRepositories @Inject constructor(
    private val apiService : TwoDoService,
    private val userApiService : UserService
) {

    // for Api Service
    suspend fun getAllTodo() = apiService.getTodos()
    suspend fun getTodoById(id : String) = apiService.getTodo(id)
    suspend fun addTodo(todo : Twododata) = apiService.addTodo(todo)
    suspend fun updateTodoById(id : String,todo : Twododata) = apiService.updateTodo(id, todo)
    suspend fun deleteTodo(id : String) = apiService.deleteTodo(id)

    // for Api Service User
    suspend fun getUsersLogin(user : UserDataClass) = userApiService.getUsersLogin(user)
    suspend fun addUserRegister(user : UserDataClass) = userApiService.addUserRegister(user)

}