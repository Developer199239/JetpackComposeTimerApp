package com.example.timerapp

import android.os.Bundle
import android.widget.ImageButton
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.timerapp.ui.theme.TimerAppTheme
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.Date

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TimerApp()
        }
    }
}

@Composable
fun TimerApp(modifier: Modifier = Modifier) {
    TimerAppTheme {
        TimerScreen()
    }
}

@Composable
fun TimerScreen(modifier: Modifier = Modifier) {
    val initialTime = 30L
    var remainingtime: Long by remember {
        mutableLongStateOf(initialTime)
    }

    var isRunning by remember {
        mutableStateOf(false)
    }

    var progress by remember {
        mutableStateOf(0f)
    }

    if(isRunning) {
        LaunchedEffect(key1 = remainingtime) {
            while (remainingtime > 0) {
                delay(1000)
                remainingtime--
                progress = remainingtime.toFloat() / initialTime
            }
            isRunning = false
            progress = 0f
            remainingtime = initialTime
        }
    }

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(contentAlignment = Alignment.Center) {
            CircularProgressIndicator(
                progress = {progress},
                color = Color.Yellow,
                trackColor = Color.Gray,
                strokeWidth = 10.dp,
                modifier = Modifier.size(200.dp)
            )
            Text(text = formatTime(remainingtime), style = MaterialTheme.typography.displayLarge)
        }
        Spacer(modifier = Modifier.height(20.dp))
        Row {
           IconButton(onClick = {
               isRunning = !isRunning
           }) {
               val icon = if (isRunning) R.drawable.icons_pause else R.drawable.icons_play
               Icon(
                   painter = painterResource(id = icon),
                   contentDescription = if (isRunning) "pause" else "play",
                   modifier = modifier.size(50.dp)
               )
           }

           IconButton(onClick = {
               isRunning = false
               progress = 0f
               remainingtime = initialTime
           }) {
               Icon(
                   painter = painterResource(id = R.drawable.icons_reload),
                   contentDescription = "Refresh",
                   modifier = modifier.size(50.dp)
               )
           }
        }
    }
}

fun formatTime(time: Long) : String {
    return SimpleDateFormat("mm:ss").format(Date(time * 1000))
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TimerApp()
}