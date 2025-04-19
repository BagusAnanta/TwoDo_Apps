package com.example.twodoapps

import android.graphics.Paint.Align
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.twodoapps.ui.theme.TwoDoAppsTheme

class CreateTwoDo : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TwoDoAppsTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    AddTwoDoTopBar(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTwoDoTopBar(modifier: Modifier = Modifier) {
    val topBarScrollBehaviour = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        "2Do Apps",
                        style = TextStyle(
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            /*place at here*/
                        }
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "back icon"
                        )
                    }
                },
                scrollBehavior = topBarScrollBehaviour
            )
        }
    ) { innerPadding ->
        CreateContentApp(innerPadding)
    }
}

@Composable
fun CreateContentApp(innerPaddingValue : PaddingValues, modifier : Modifier = Modifier){
    var twoDoTitle by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(innerPaddingValue)
    ) {
        Text(
            text = "Create ToDo",
            modifier = modifier
                .padding(start = 10.dp, end = 10.dp),
            style = TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp
            )
        )

        TextField(
            value = twoDoTitle,
            onValueChange = {
                twoDoTitle = it
            },
            label = {
                Text("Create you ToDo at Here !")
            },
            modifier = modifier
                .fillMaxWidth()
                .padding(10.dp)
        )

        Button(
            onClick = {
                /*do anything*/
                if(twoDoTitle.isNotEmpty()){
                    // if a state not empty
                }
            },
            shape = RoundedCornerShape(20.dp),
            elevation = ButtonDefaults.elevatedButtonElevation(
                defaultElevation = 6.dp
            ),
            modifier = modifier
                .fillMaxWidth()
                .padding(start = 10.dp, end = 10.dp)
        ) {
            Text("Create ToDo")
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AppPreviewCreate() {
    TwoDoAppsTheme {
        AddTwoDoTopBar()
    }
}