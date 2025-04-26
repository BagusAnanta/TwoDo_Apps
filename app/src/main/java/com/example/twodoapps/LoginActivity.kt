package com.example.twodoapps

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.LocalActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.twodoapps.dataClassTodo.UserDataClass
import com.example.twodoapps.ui.theme.TwoDoAppsTheme
import com.example.twodoapps.utils.ApiResult
import com.example.twodoapps.viewModel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel

@AndroidEntryPoint
class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TwoDoAppsTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    ComponentApp(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun ComponentApp(modifier: Modifier = Modifier, viewmodel : UserViewModel = hiltViewModel()) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    var loginState = viewmodel.loginState.value
    var context = LocalContext.current
    var activity = LocalActivity.current

    Column(
        modifier = modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("TwoDO App")

        OutlinedTextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("Username") },
            modifier = modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            modifier = modifier.fillMaxWidth()
        )

        Spacer(modifier = modifier.padding(top = 10.dp))

        Button(
            onClick = {
                viewmodel.getUsersLogin(
                    UserDataClass(
                        username = username,
                        password = password
                    )
                )
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

        // Api Handle at here
        ApiHandleResult(
            result = loginState,
            onSuccess = {
                // intent into main activity
                var intent = Intent(context, MainActivity::class.java)
                context.startActivity(intent)
                activity?.finish()
            },
            onError = {
                // show error message
                Toast.makeText(context, "Something Wrong!", Toast.LENGTH_SHORT).show()
                Log.e("Error", it.toString())
            }
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingPreview() {
    TwoDoAppsTheme {
        ComponentApp()
    }
}