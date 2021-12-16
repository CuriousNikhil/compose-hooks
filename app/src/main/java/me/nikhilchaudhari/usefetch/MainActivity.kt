package me.nikhilchaudhari.usefetch

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import me.nikhilchaudhari.usefetch.model.User
import me.nikhilchaudhari.usefetch.model.UsersList
import me.nikhilchaudhari.usefetch.ui.theme.UseFetchTheme
import kotlin.random.Random

const val TAG = "nikhil"

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            UseFetchTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    UsersList()
                }
            }
        }
    }
}

@Composable
fun NewUrlClick(onClick: () -> Unit) {
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
