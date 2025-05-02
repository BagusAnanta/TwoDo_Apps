package com.example.twodoapps

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.LocalActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.twodoapps.ui.theme.TwoDoAppsTheme
import com.example.twodoapps.dataClassTodo.Twododata
import com.example.twodoapps.utils.ApiResult
import com.example.twodoapps.viewModel.TwoDoViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
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
    val activity : Activity? = LocalActivity.current
    val context : Context = LocalContext.current

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
    ) { innerPadding ->
        ContentApp(
            innerPaddingValues = innerPadding
        )
    }
}


@Composable
fun ContentApp(viewModel : TwoDoViewModel = hiltViewModel(), innerPaddingValues : PaddingValues, modifier : Modifier = Modifier){

    // for state
    // for state done, get data from API
    var isTaskDone by remember { mutableStateOf(false) }
    var expanded by remember { mutableStateOf(false) }
    var visible by remember { mutableStateOf(false) }
    var id by remember { mutableStateOf("") }

    var context = LocalContext.current

    var twoDoTitleUpdate by remember { mutableStateOf("") }

    var twoDoTitle by remember { mutableStateOf("") }

    val twoDoState = viewModel.todosState.value

    LaunchedEffect(Unit){
        viewModel.getAllTodo()
    }

    Column(
        modifier = Modifier.padding(innerPaddingValues)
    ) {

        Card(
            elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
            modifier = modifier
                .fillMaxWidth()
                .height(200.dp)
                .padding(10.dp)
        ) {
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .padding(top = 10.dp),
            ) {
                Text(
                    text = "Create ToDo",
                    modifier = modifier.padding(start = 10.dp, end = 10.dp),
                    style = TextStyle(
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Monospace
                    )
                )

                Spacer(modifier = modifier.padding(top = 5.dp))

                Text(
                    text = "Create you ToDo at Here !",
                    modifier = modifier.padding(start = 10.dp, end = 10.dp),
                    style = TextStyle(
                        fontSize = 15.sp,
                        fontStyle = FontStyle.Italic)
                )

                Spacer(modifier = modifier.padding(top = 10.dp))

                OutlinedTextField(
                    value = twoDoTitle,
                    onValueChange = {
                        twoDoTitle = it
                    },
                    label = {
                        Text("What your plan ?")
                    },
                    trailingIcon = {
                        IconButton(
                            onClick = {
                                if(twoDoTitle.isNotEmpty()){
                                    viewModel.addTodo(
                                        Twododata(
                                            name = twoDoTitle
                                        )
                                    )
                                }
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Create,
                                contentDescription = "CreateButton"
                            )
                        }
                    },
                    modifier = modifier
                        .padding(start = 10.dp, end = 10.dp, bottom = 10.dp)
                        .fillMaxWidth()
                )
            }
        }

        Text(
            text = "Your Plan",
            modifier = modifier.padding(10.dp),
            style = TextStyle(fontWeight = FontWeight.Bold)
        )

        when(twoDoState){
            is ApiResult.Loading -> {
                // make loading here
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
            is ApiResult.Success -> {
                // LazyColumn -> if you make list column, you use LazyColumn
                LazyColumn(
                    modifier = Modifier
                        .padding(10.dp),
                    verticalArrangement = Arrangement.spacedBy(15.dp)
                ) {
                    items(twoDoState.data){
                        ElevatedCard(
                            elevation = CardDefaults.elevatedCardElevation(
                                defaultElevation = 6.dp
                            ),
                            modifier = modifier
                                .fillMaxWidth()
                                .animateContentSize(),
                            onClick = {
                                // place id at here for change value and save id where user choose
                                id = it.id
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

                                // expanded && id == it.id
                                if(expanded && id == it.id){
                                    // on expanded, I want make update and delete on inside card too
                                    Column(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                    ) {
                                        Column(
                                            modifier = modifier.fillMaxWidth()
                                        ) {
                                            Text(
                                                "Create At : ${it.createAt}",
                                                style = TextStyle(
                                                    fontStyle = FontStyle.Italic
                                                ),
                                                modifier = modifier.padding(bottom = 5.dp)
                                            )

                                            Text(
                                                "Update At : ${it.updateAt}",
                                                style = TextStyle(
                                                    fontStyle = FontStyle.Italic
                                                )
                                            )
                                        }

                                        OutlinedTextField(
                                            value = twoDoTitleUpdate,
                                            onValueChange = {
                                                twoDoTitleUpdate = it
                                            },
                                            label = {
                                                Text("Update ToDo")
                                            },
                                            trailingIcon = {
                                                IconButton(
                                                    onClick = {
                                                        viewModel.updateTodo(
                                                            id = it.id,
                                                            todo = Twododata(
                                                                name = twoDoTitleUpdate,
                                                                status = isTaskDone
                                                            )
                                                        )
                                                    }
                                                ) {
                                                    Icon(
                                                        imageVector = Icons.Filled.Create,
                                                        contentDescription = "UpdateButton"
                                                    )
                                                }
                                            },
                                            modifier = modifier
                                                .fillMaxWidth()
                                                .padding(5.dp, top = 10.dp)
                                        )

                                        Spacer(modifier = modifier.padding(top = 10.dp))

                                        Button(
                                            onClick = {
                                                viewModel.deleteTodo(it.id)
                                            },
                                            shape = RoundedCornerShape(20.dp),
                                            elevation = ButtonDefaults.elevatedButtonElevation(),
                                            modifier = modifier.fillMaxWidth(),
                                            colors = ButtonDefaults.buttonColors(
                                                containerColor = Color.Red
                                            )
                                        ) {
                                            Text("Delete")
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            is ApiResult.Error -> {
                // show error message
                Toast.makeText(context, "Something Wrong!", Toast.LENGTH_SHORT).show()
                Log.e("Error", twoDoState.message)
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AppsPreview() {
    TwoDoAppsTheme {
        TopAndBottomComponent()
    }
}