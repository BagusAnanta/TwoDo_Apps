package com.example.twodoapps.viewModel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.twodoapps.dataClassTodo.UserDataClass
import com.example.twodoapps.repository.ApiRepositories
import com.example.twodoapps.utils.ApiResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val repository : ApiRepositories
) : ViewModel() {

    /*Ok, now let focus on Login and Register first, so in here I want
    *  make viewmodel for login and register with post request, and I want
    * make
    * 1. variable for login and get state
    * 2. variable for register and get state
    * 3. function for login and register*/

    private val _loginToken = mutableStateOf<ApiResult<String>>(ApiResult.Loading)
    val loginToken : MutableState<ApiResult<String>> = _loginToken

    private val _registerToken = mutableStateOf<ApiResult<String>>(ApiResult.Loading)
    val registerToken : MutableState<ApiResult<String>> = _registerToken

    fun getUsersLogin(data : UserDataClass){
        viewModelScope.launch {
            _loginToken.value = ApiResult.Loading
            _loginToken.value = repository.getUsersLogin(data)
        }
    }

    fun addUserRegister(data : UserDataClass){
        viewModelScope.launch {
            _registerToken.value = ApiResult.Loading
            _registerToken.value = repository.addUserRegister(data)
        }
    }


}