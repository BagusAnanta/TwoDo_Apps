package com.example.twodoapps

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.LocalActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.twodoapps.dataClassTodo.UserDataClass
import com.example.twodoapps.sharePref.AuthHelper
import com.example.twodoapps.ui.theme.TwoDoAppsTheme
import com.example.twodoapps.utils.ApiResult
import com.example.twodoapps.viewModel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TwoDoAppsTheme {
              /*  Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    ComponentApp(
                        modifier = Modifier.padding(innerPadding)
                    )
                }*/

                ComponentApp()
            }
        }
    }
}

@Composable
fun ComponentApp(modifier: Modifier = Modifier, viewmodel : UserViewModel = hiltViewModel()) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var loadingState by remember { mutableStateOf(false) }

    var loginState = viewmodel.loginToken.value
    var context = LocalContext.current
    var activity = LocalActivity.current
    var authHelper = AuthHelper(context)

    Column(
        modifier = modifier
            .fillMaxHeight()
            .wrapContentHeight(Alignment.CenterVertically)
            .padding(10.dp)
            .blur(if(loadingState) 5.dp else 0.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text("TwoDO App")

        Spacer(modifier = modifier.padding(bottom = 10.dp))

        OutlinedTextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("Username") },
            modifier = modifier.fillMaxWidth()
        )

        Spacer(modifier = modifier.padding(bottom = 10.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            modifier = modifier.fillMaxWidth()
        )

        Spacer(modifier = modifier.padding(bottom = 10.dp))

        Button(
            onClick = {
                viewmodel.getUsersLogin(
                    UserDataClass(
                        username = username,
                        password = password
                    )
                )

                loadingState = true
            },
            modifier = modifier.fillMaxWidth()
        ) {
            Text("Login")
        }

        Text("or")

        Button(
            onClick = {
                // intent into register activity
                var intent = Intent(context, RegisterActivity::class.java)
                context.startActivity(intent)
            },
            modifier = modifier.fillMaxWidth()
        ) {
            Text("Register")
        }

        when(loginState){
            is ApiResult.Success -> {
                authHelper.saveToken(loginState.data)
                Log.e("Token", loginState.data)
            }
            is ApiResult.Error -> {
                Log.e("Error", loginState.message)
            }
            is ApiResult.Loading -> {
               // none
            }
        }

        // Api Handle at here
        ApiHandleResult(
            result = loginState,
            onSuccess = {
                // intent into main activity
                var intent = Intent(context, MainActivity::class.java)
                context.startActivity(intent)
                activity?.finish()
                loadingState = false
            },
            onError = {
                // show error message
                Toast.makeText(context, "Something Wrong!", Toast.LENGTH_SHORT).show()
                Log.e("Error", it.toString())
                loadingState = false
            }
        )
    }

    if(loadingState){
        Box(
            contentAlignment = Alignment.Center,
            modifier = modifier
                .fillMaxSize()
                .wrapContentWidth(Alignment.CenterHorizontally)
        ){
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                CircularProgressIndicator(
                    modifier = modifier
                        .size(50.dp, 50.dp)
                )

                Spacer(modifier = modifier.padding(bottom = 10.dp))

                Text("Please Wait")
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingPreview() {
    TwoDoAppsTheme {
        ComponentApp()
    }
}