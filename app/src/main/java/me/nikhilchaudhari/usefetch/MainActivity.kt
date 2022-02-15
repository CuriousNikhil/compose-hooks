package me.nikhilchaudhari.usefetch

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import me.nikhilchaudhari.usefetch.model.User
import me.nikhilchaudhari.usefetch.model.UsersList
import me.nikhilchaudhari.usefetch.ui.theme.UseFetchTheme
import me.nikhilchaudhari.usenetworkstate.NetworkState
import me.nikhilchaudhari.usenetworkstate.useNetworkState
import useReducer
import kotlin.random.Random

const val TAG = "nikhil"

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            UseFetchTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    HomeScreen()
                }
            }
        }
    }
}

@Composable
fun HomeScreen() {
    val networkState by useNetworkState()

    if (networkState == NetworkState.Online) {
        UsersList()
    } else {
        Text(text = "You are offline! Please turn on the network!")
    }
}


@Composable
fun UsersList() {

    var url = remember { mutableStateOf("https://reqres.in/api/users") }
    var resultState = useFetch(url.value)

    when (val data = resultState.value) {

        is Result.Error -> {
            Text(
                modifier = Modifier
                    .size(24.dp)
                    .fillMaxWidth(),
                text = "Error...${data.error}"
            )
        }
        is Result.Response -> {

            val moshi = Moshi.Builder().build()
            val adapter: JsonAdapter<UsersList> = moshi.adapter(UsersList::class.java)
            val list = adapter.fromJson(data.data.text)
            LazyColumn(content = {
                list?.let {
                    items(it.data) { user ->
                        UserCard(user)
                    }
                }
                item {
                    Button(onClick = {
                        url.value = "https://reqres.in/api/users?page=${Random.nextInt(1, 6)}"
                    }) {
                        Text(text = "Fire - ${url.value}")
                    }
                }
                item {
                    TestUseReducerHook()
                }
            })
        }
        is Result.Loading -> {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .size(24.dp),
                text = "Loading..."
            )
        }
    }
}

@Composable
fun UserCard(user: User) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .height(100.dp)
            .fillMaxSize()
    ) {
        Column(modifier = Modifier.padding(10.dp)) {
            Text(text = "First name: ${user.first_name}")
            Text(text = "Last name: ${user.last_name}")
            Text(text = "Email - ${user.email}")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    UseFetchTheme {
        UsersList()
    }
}

@Composable
fun TestUseNetworkState() {
    val networkState by useNetworkState()
    when (networkState) {
        NetworkState.Online -> {
            Toast.makeText(LocalContext.current, "Online", Toast.LENGTH_LONG).show()
        }
        NetworkState.Offline -> {
            Toast.makeText(LocalContext.current, "Offline", Toast.LENGTH_LONG).show()
        }
    }
}


@Composable
fun TestUseReducerHook() {
    var count by remember { mutableStateOf(0) }
    val dispatcher = useReducer {
        "increment" does { count++ }
        "decrement" does { count-- }
    }

    Row(modifier = Modifier.padding(16.dp), horizontalArrangement = Arrangement.Center) {

        Button(onClick = { dispatcher.dispatch("increment") }) {
            Text(text = "+")
        }

        Text(text = count.toString(), fontSize = 16.sp, modifier = Modifier.padding(16.dp, 4.dp))

        Button(onClick = { dispatcher.dispatch("decrement") }) {
            Text(text = "-")
        }
    }
}
