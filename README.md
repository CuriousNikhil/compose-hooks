# useHttp for Jetpack Compose
Minimal http client for compose providing response as state when you don't want to setup everything (using viewmodel/repository and then adding retrofit  services...ahhhhh! too much pain) when you just want to do a quick PoC or build a hobby project or for anything.

Inspired from react-hooks [useFetch](https://use-http.com/#/)


## Usage

1. Add the following dependency in your `build.gradle`

```kotlin
implementation("me.nikhilchaudhari:compose-usehttp:1.0.0-alpha02")
```

2. And use in your regular compose functions

```kotlin
@Composable
fun Greeting() {
  
  // You can use following network request composable functions - useGet, usePost, useDelete, usePatch, usePut, useOption etc. 
  // You can pass headers, parameters,   body and other params in each of the methods.
  
  val resultState = useGet(url = "https://yourdomain.com/greetings")

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

Checkout the API docs [here](https://javadoc.io/doc/me.nikhilchaudhari/compose-usehttp/latest/usehttp/me.nikhilchaudhari.usehttp/use-get.html) 

~~~ Note
Documentation is in progress 🧰 🪛 👷‍♂️
~~~

## Response as a state

When you use `useGet("<url>")`, it returns you `State<Result>` object. The `Result` has three values `Error(error: Throwable?)`, `Success(data: Response)` and `Loading` and these are the three states we usually use while doing a long running task/network call. 

You get the `Response` object in the `Success` state which you can use to process. You can use `Moshi`, `GSON` or any other JSON adapters libraries.

Internally, the network request is performed in flow on IO dispatcher and result is collected as state. This uses `produceState(..)` side-effect which is provided by the compose runtime itself. You can create many such side-effects (in react terminologies, it's called as hooks).

Hop over to Wiki page for the complete documenation


### License

[LICENSE Apache 2.0](https://github.com/CuriousNikhil/compose-useRequest/blob/main/LICENSE)
