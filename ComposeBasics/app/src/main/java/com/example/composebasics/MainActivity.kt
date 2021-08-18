package com.example.composebasics

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.example.composebasics.ui.theme.ComposeBasicsTheme
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val names = List(1000) { "Hello for the $it time" }
        setContent {
            MainContent {
                GreetingsRow(names)
            }
        }
    }
}

@Composable
fun MainContent(content: @Composable () -> Unit) {
    ComposeBasicsTheme {
        // A surface container using the 'background' color from the theme
        Surface(color = Color.White) {
            content()
        }
    }
}

@Composable
fun GreetingsRow(names: List<String>) {
    val count = remember { mutableStateOf(0) }

    Column(modifier = Modifier.fillMaxHeight()) {
        NamesList(names = names, modifier = Modifier.weight(1f))
        Counter(count.value, onClickButton = { newValue ->
            if (count.value == 10) count.value = 0
            else count.value = newValue
        })
    }
}

@Composable
fun NamesList(names: List<String>, modifier: Modifier = Modifier) {
    LazyColumn(modifier = modifier) {
        items(items = names) { name ->
            Greeting(name = name)
            Divider(color = Color.Gray)
        }
    }
}

@Composable
fun Greeting(name: String) {
    var isSelected by remember { mutableStateOf(false) }
    val backgroundColor by animateColorAsState(targetValue = if (isSelected) Color.Red else Color.Transparent)
    Text(
        text = "Hello $name!",
        modifier = Modifier
            .padding(24.dp)
            .background(color = backgroundColor)
            .clickable { isSelected = !isSelected },
        style = MaterialTheme.typography.body2
    )
}

@Composable
fun Counter(count: Int, onClickButton: (Int) -> Unit) {
    Button(
        onClick = { onClickButton(count + 1) },
        colors = ButtonDefaults.buttonColors(
            backgroundColor = if (count > 5) Color.Gray else Color.Green
        )
    ) {
        Text(text = "Button clicked $count times")
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MainContent {
        GreetingsRow(List(1000) { "Hello for the $it time" })
    }
}