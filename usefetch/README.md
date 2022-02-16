# useFetch for Jetpack Compose

Need to fetch some data? And don't want to setup everything unnecessarily like viewmodel, repository and the retrofit ahhhhh... to much pain just to fetch the data?
Try out this minimal `useFetch` Jetpack Compose hook to quickly fetch some data that returns response as a `State<T>` values of `Loading`, `Error` and `Success` for you!

Inspired from react-hooks [useFetch](https://use-http.com/#/)

[![usefetch](https://img.shields.io/maven-central/v/me.nikhilchaudhari/compose-usefetch.svg?logo=kotlin)](https://search.maven.org/artifact/me.nikhilchaudhari/compose-usefetch)
[![javadoc](https://javadoc.io/badge2/me.nikhilchaudhari/compose-usefetch/javadoc.svg)](https://javadoc.io/doc/me.nikhilchaudhari/compose-usefetch)


## Usage

1. Add the following dependency in your `build.gradle`

```kotlin
implementation("me.nikhilchaudhari:compose-usefetch:{latest-release-version}")
```

2. And use in your regular compose functions

```kotlin
@Composable
fun Greeting() {
  
  // You can pass headers, parameters,   body and other params in each of the methods.
  // This `useFetch` returns you the result as a Compose State<T>
  
  val resultState = useFetch(url = "https://yourdomain.com/greetings")

  // Use the Result state to update your UI
  val text: String? = when (val data = resultState.value) {

      is Result.Error -> {
          data.error?.toString()
      }
      is Result.Success -> {
      
          // Result.Success returns a Response object
          
          data.data.text
      }
      is Result.Loading -> {
          "Loading..."
      }
  }
    
    Text(text = "$text")
}
```


**Checkout the API docs [here](https://javadoc.io/doc/me.nikhilchaudhari/compose-usefetch/latest/usefetch/me.nikhilchaudhari.usefetch/index.html)**

## Getting started

### Request

You can use the following parameters to pass to the `useFetch` composables to get the network response as a state value in your composables/compose functions.

```kotlin
 val resultState = useFetch(
        url = "https://yourdomain.com/endpoint",
        headers = mapOf("Content-Type" to "application/json"),
        params = mapOf("query" to "key", "search" to "term")
    )
```

#### URL

Pass the `url` of your GET request to fetch the data.

```kotlin
 val resultState = useFetch(
        url = "https://yourdomain.com/endpoint",
        ...
```        

#### headers

If you want to pass your custom headers, pass it as a `Map<String, String>` in a `header` function param. 

```kotlin
    useFetch(
        ...
        headers = mapOf("Content-Type" to "application/json"),
        ...
```
If you don't pass any header, headers will be empty for that request.

or you can use the Default headers available

```kotlin
DEFAULT_HEADERS = mapOf(
    "Accept" to "*/*",
    "Accept-Encoding" to "gzip, deflate"
)
DEFAULT_DATA_HEADERS = mapOf("Content-Type" to "text/plain")

DEFAULT_FORM_HEADERS = mapOf("Content-Type" to "application/x-www-form-urlencoded")

DEFAULT_JSON_HEADERS = mapOf("Content-Type" to "application/json")
```

#### URL Parameters

For url parameters, you can pass a `Map<String, String>` as a `param` value in the useFetch.

```kotlin
 val resultState = useFetch(
      ....
        params = mapOf("query" to "key", "search" to "term")
      ...
    )
```

#### Authorization

If you need to pass a Basic Authorization ( with user and pass base64 encoded), you can use `BaseAuth("user", "pass")` and pass it in `auth`.

```kotlin
 val resultState = useFetch(
        ...
        auth = BaseAuth(user = "user", password = "pass"),
        ...
    )
```

If you want your custom authorization, you can extend `Auth` interface, add your auth implementation and pass it in `auth`.

```kotlin

    // implement your own authorization with Auth interface
    class CustomAuth(
        val userName: String,
        val userToken: String
    ) : Auth{
        override val header: Pair<String, String>
            get() {
                val hashedValue = "${this.userName}:${this.userToken}".yourEncodingFunction()
                return "Authorization" to "Bearer $hashedValue"
            }
    }
    
   // use your custom auth for authorization
   val resultState = useFetch(
        ...
        auth = CustomAuth(userName = "user", userToken = "token"),
        ...
    )

```

#### Timeout

You can set timeout for the request being made with `useFetch()`. Pass the `Double` seconds value in `timeout`. 

```kotlin
val resultState = useFetch(
        ...
          timeout = 30.0
        ...
    )
```

The default value of timeout, for a request, is 30 seconds.


### Response as a state

When you use `useFetch("<url>")`, it returns you `State<Result>` object. The [`Result`](https://javadoc.io/doc/me.nikhilchaudhari/compose-usefetch/latest/usefetch/me.nikhilchaudhari.usefetch/-result/index.html) has three values as follows - 

[`Error(error: Throwable?)`](https://javadoc.io/doc/me.nikhilchaudhari/compose-usefetch/latest/usefetch/me.nikhilchaudhari.usefetch/-result/-error/index.html) : An error state, when any error is thrown

[`Loading`](https://javadoc.io/doc/me.nikhilchaudhari/compose-usefetch/latest/usefetch/me.nikhilchaudhari.usefetch/-result/-loading/index.html) : A loading state when request is ongoing

[`Response(data: ResponseData)`](https://javadoc.io/doc/me.nikhilchaudhari/compose-usefetch/latest/usefetch/me.nikhilchaudhari.usefetch/-result/-response/index.html) : A response state, a successful network request returning response which will contain response data `ResponseData`

`ResponseData` has following properties which you can access and perform your further operations


`encoding` - Encoding of the response stream  
`headers` - Response headers map of string, string  
`statusCode` - status/response code for the given network resposne  
`text` - Your network response in `String` format  
`url` - The requested URL  


And these are the three states we usually use while doing a long running task/network call. 

You get the [`ResponseData`](https://javadoc.io/doc/me.nikhilchaudhari/compose-usefetch/latest/usefetch/me.nikhilchaudhari.usefetch.network/-response-data/index.html) object in the `Response` state which you can use to process. You can use `Moshi`, `GSON` or any other JSON adapters libraries. Checkout the [sample app](https://github.com/CuriousNikhil/compose-hooks/blob/main/app/src/main/java/me/nikhilchaudhari/usefetch/MainActivity.kt) for how it's used.

Internally, the network request is performed on IO dispatcher and result returned with the help of [`produceState(..)`](https://developer.android.com/jetpack/compose/side-effects#producestate) side-effect that is provided by the Jetpack Compose itself. You can create many such side-effects by extending provided effects.

Even if your composable function changes/re-renders because of some other `State` mutation, this `useFetch` won't get called as it's using the `produceState` side effect. The parameters `url`, `params`, `headers` that you pass to this function are used as `keys` and thus will get called when those changes. 



## TODOs
 - [ ] The screen rotation fix
 - [ ] PRs are welcome so anything you would like to improve here.
