package com.example.composelayouts

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.example.composelayouts.ui.theme.ComposeLayoutsTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeLayoutsTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    LayoutsCodelab()
                }
            }
        }
    }
}

@Composable
fun LayoutsCodelab() {
    Scaffold(
        topBar = {
                 CodelabTopAppBar()
        },
        bottomBar = {
                CodelabBottomNavigationBar()
        }
    ) { innerPadding ->
        LazyList(Modifier.padding(innerPadding))
    }
}

@Composable
fun CodelabTopAppBar(modifier: Modifier = Modifier) {
    TopAppBar(
        modifier = modifier,
        title = {
            Text(text = "Welcome!")
        },
        actions = {
            IconButton(onClick = { /* empty */ }) {
                Icon(Icons.Filled.Favorite, contentDescription = null)
            }
        }
    )
}

@Composable
fun CodelabBottomNavigationBar(modifier: Modifier = Modifier) {
    BottomNavigation(modifier = modifier) {
        Button(
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .fillMaxWidth()
                .padding(10.dp),
            onClick = { /* empty */ }
        ) {
            Text(text = "A button on the bottom")
        }
    }
}

@Composable
fun LazyList(modifier: Modifier = Modifier) {
    val listSize = 100
    val scrollState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    
    Column(modifier = modifier) {
        ScrollingButtons(coroutineScope = coroutineScope, scrollState = scrollState, listSize = listSize)
        LazyColumn(state = scrollState) {
            items(100) {
                ImageListItem(index = it)
            }
        }
    }
}

@Composable
fun ScrollingButtons(
    coroutineScope: CoroutineScope,
    scrollState: LazyListState,
    listSize: Int,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier.fillMaxWidth()) {
        Button(
            modifier = Modifier.fillMaxWidth(fraction = 0.5f),
            onClick = {
                coroutineScope.launch {
                    scrollState.animateScrollToItem(0)
                }
            }
        ) {
            Text("Scroll to the top")
        }

        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                coroutineScope.launch {
                    scrollState.animateScrollToItem(listSize - 1)
                }
            }
        ) {
            Text("Scroll to the end")
        }
    }
}

@Composable
fun ImageListItem(index: Int, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = rememberImagePainter(
                data = "https://developer.android.com/images/brand/Android_Robot.png"
            ),
            contentDescription = "Android Logo",
            modifier = Modifier.size(50.dp)
        )
        Spacer(Modifier.width(10.dp))
        Text("Item #$index", style = MaterialTheme.typography.subtitle1)
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ComposeLayoutsTheme {
        LayoutsCodelab()
    }
}
