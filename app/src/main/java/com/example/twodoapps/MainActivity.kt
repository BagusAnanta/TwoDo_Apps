package com.example.twodoapps

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.twodoapps.ui.theme.TwoDoAppsTheme
import dataClassTodo.Twododata

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TwoDoAppsTheme {
                TopAndBottomComponent()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAndBottomComponent(modifier: Modifier = Modifier) {
    // make scrollable on
    val topBarScrollBehaviour = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())

    // make scaffold in here
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
                scrollBehavior = topBarScrollBehaviour
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = {
                    /*Do Something*/
                },
            ){
                Icon(
                    imageVector = Icons.Filled.Create,
                    contentDescription = "CreateButton",
                    modifier = modifier.padding(end = 10.dp)
                )

                Text(
                    text = "Write ToDo"
                )
            }
        }
    ) { innerPadding ->
        ContentApp(
            data = exampleDataList(),
            innerPaddingValues = innerPadding
        )
    }
}

fun exampleDataList() : List<Twododata>{
    return listOf(
        Twododata("123","Makan"),
        Twododata("456","Minum"),
        Twododata("789","Mandi"),
        Twododata("142","Sekolah")
    )
}

@Composable
fun ContentApp(data : List<Twododata>, innerPaddingValues : PaddingValues, modifier : Modifier = Modifier){

    // for state

    // for state done, get data from API
    var isTaskDone by remember { mutableStateOf(false) }
    var expanded by remember { mutableStateOf(false) }
    var visible by remember { mutableStateOf(false) }

    // Lazycolumn -> if you make list column, you use LazyColumn
    LazyColumn(
        contentPadding = innerPaddingValues,
        modifier = Modifier.padding(10.dp),
        verticalArrangement = Arrangement.spacedBy(15.dp)
    ) {
        items(data){
            Card(
                elevation = CardDefaults.elevatedCardElevation(
                    defaultElevation = 6.dp
                ),
                modifier = modifier
                    .fillMaxWidth()
                    .animateContentSize()
                    .height(if(expanded) 140.dp else 50.dp),
                onClick = {
                    expanded = !expanded
                }
            ){
                Column(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(all = 5.dp)
                ){
                    Row(
                        modifier = modifier
                            .fillMaxWidth()
                            .wrapContentWidth(Alignment.CenterHorizontally)
                    ) {
                        // title on right, done task on right
                        Text(
                            text = it.name,
                            style = TextStyle(
                                fontStyle = FontStyle.Normal,
                                fontWeight = FontWeight.SemiBold
                            ),
                            modifier = modifier.align(Alignment.CenterVertically)
                        )

                        IconButton(
                            onClick = {
                                isTaskDone = !isTaskDone
                            },
                            modifier = modifier
                                .fillMaxWidth()
                                .wrapContentWidth(Alignment.End)
                        ) {
                            // icon
                            Icon(
                                painter = if(isTaskDone) painterResource(R.drawable.done_uncheck) else painterResource(R.drawable.done_check),
                                contentDescription = "Done"
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingPreview() {
    TwoDoAppsTheme {
        TopAndBottomComponent()
    }
}