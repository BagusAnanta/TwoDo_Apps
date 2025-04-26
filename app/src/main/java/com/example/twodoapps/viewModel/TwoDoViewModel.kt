package com.example.twodoapps.viewModel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.twodoapps.dataClassTodo.Twododata
import com.example.twodoapps.repository.ApiRepositories
import com.example.twodoapps.utils.ApiResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TwoDoViewModel @Inject constructor(
    private val repository : ApiRepositories
) : ViewModel() {

    // make viewmodel here from repository

    // for get data from api
    private val _todosState = mutableStateOf<ApiResult<List<Twododata>>>(ApiResult.Loading)
    val todosState : MutableState<ApiResult<List<Twododata>>> = _todosState

    // for get data by id from api
    private val _getTodosById = mutableStateOf<ApiResult<List<Twododata>>>(ApiResult.Loading)
    val getTodosById : MutableState<ApiResult<List<Twododata>>> = _getTodosById

    // for add data from api
    private val _createTodo = mutableStateOf<ApiResult<String>?>(null)
    val createTodo : MutableState<ApiResult<String>?> = _createTodo

    // for update data from api
    private val _updateTodo = mutableStateOf<ApiResult<String>?>(null)
    val updateTodo : MutableState<ApiResult<String>?> = _updateTodo

    // for delete data from api
    private val _deleteTodo = mutableStateOf<ApiResult<String>?>(null)
    val deleteTodo : MutableState<ApiResult<String>?> = _deleteTodo


    // for read data from api
    fun getAllTodo(){
        viewModelScope.launch {
            _todosState.value = ApiResult.Loading
            _todosState.value = repository.getAllTodo()
        }
    }

    // for read data by id from api
    fun getTodoById(id : String){
       viewModelScope.launch {
           _getTodosById.value = ApiResult.Loading
           _getTodosById.value = repository.getTodoById(id)
       }
    }

    // for add data from api
    fun addTodo(todo : Twododata){
        viewModelScope.launch {
            _createTodo.value = ApiResult.Loading
            _createTodo.value = repository.addTodo(todo)
        }
    }

    // for update data from api
    fun updateTodo(id : String, todo : Twododata){
        viewModelScope.launch {
            _updateTodo.value = ApiResult.Loading
            _updateTodo.value = repository.updateTodoById(id, todo)
        }
    }

    // for delete data from api
    fun deleteTodo(id : String){
        viewModelScope.launch {
            _deleteTodo.value = ApiResult.Loading
            _deleteTodo.value = repository.deleteTodo(id)
        }
    }








}