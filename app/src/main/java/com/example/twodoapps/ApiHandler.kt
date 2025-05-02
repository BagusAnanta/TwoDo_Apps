package com.example.twodoapps

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.twodoapps.ui.theme.TwoDoAppsTheme
import com.example.twodoapps.utils.ApiResult

class ApiHandler : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TwoDoAppsTheme {
                // Empty Statement
            }
        }
    }
}

@Composable
fun ApiHandleResult(
    result : ApiResult<*>?,
    onSuccess : () -> Unit = {},
    onError : (String) -> Unit = {},
    onLoading : () -> Unit = {}
){
    LaunchedEffect(result) {
        when(result){
            is ApiResult.Loading -> onLoading()
            is ApiResult.Success -> onSuccess()
            is ApiResult.Error -> onError(result.message)
            else -> {}
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview3() {
    TwoDoAppsTheme {
        // empty statement
    }
}