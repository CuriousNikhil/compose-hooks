package me.nikhilchaudhari.usefetch

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import me.nikhilchaudhari.usefetch.ui.theme.UseFetchTheme
import me.nikhilchaudhari.userequest.get
import me.nikhilchaudhari.userequest.useRequest
import me.nikhilchaudhari.userequest.Result

const val TAG = "nikhil"

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            UseFetchTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    Greeting("Android")
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {

    val resultState = useRequest { get("https://reqres.in/api/users") }

    val text: String? = when (val data = resultState.value) {

        is Result.Error -> {
            data.error?.toString()
        }
        is Result.Success -> {
            data.data.text
        }
        is Result.Loading -> {
            "Loading..."
        }
    }
    Text(text = "Hello $text!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    UseFetchTheme {
        Greeting("Android")
    }
}
