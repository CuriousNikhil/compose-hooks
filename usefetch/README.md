# useFetch for Jetpack Compose

Need to fetch some data? And don't want to setup everything unnecessarily like viewmodel, repository and the retrofit ahhhhh... to much pain just to fetch the data?
Try out this minimal `useFetch` Jetpack Compose hook to quickly fetch some data that returns response as a state values of `Loading`, `Error` and `Success` for you!

Inspired from react-hooks [useFetch](https://use-http.com/#/)


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


**Checkout the API docs [here](https://javadoc.io/doc/me.nikhilchaudhari/compose-usehttp/latest/usehttp/me.nikhilchaudhari.usehttp/use-get.html)**

## Getting started

### Parameters

You can use the following parameters to pass to the `useFetch` composables to get the network response as a state value in your composables/compose functions.



### Response as a state

When you use `useFetch("<url>")`, it returns you `State<Result>` object. The [`Result`](https://javadoc.io/doc/me.nikhilchaudhari/compose-usehttp/1.0.0-alpha1.1/usehttp/me.nikhilchaudhari.usehttp/-result/index.html) has three values as follows - 

[`Error(error: Throwable?)`](https://javadoc.io/doc/me.nikhilchaudhari/compose-usehttp/1.0.0-alpha1.1/usehttp/me.nikhilchaudhari.usehttp/-result/-error/index.html) : An error state, when any error is thrown

[`Response(data: ResponseData)`](https://javadoc.io/doc/me.nikhilchaudhari/compose-usehttp/1.0.0-alpha1.1/usehttp/me.nikhilchaudhari.usehttp/-result/-response/index.html) : A response state, a successful network request returning response

[`Loading`](https://javadoc.io/doc/me.nikhilchaudhari/compose-usehttp/1.0.0-alpha1.1/usehttp/me.nikhilchaudhari.usehttp/-result/-loading/index.html) : A loading state when request is ongoing

And these are the three states we usually use while doing a long running task/network call. 

You get the [`ResponseData`](https://javadoc.io/doc/me.nikhilchaudhari/compose-usehttp/1.0.0-alpha1.1/usehttp/me.nikhilchaudhari.usehttp.response/-response-data/index.html) object in the `Response` state which you can use to process. You can use `Moshi`, `GSON` or any other JSON adapters libraries. Checkout the [sample app](https://github.com/CuriousNikhil/compose-usehttp/tree/main/app/src/main/java/me/nikhilchaudhari/usefetch) for how it's used.

Internally, the network request is performed on IO dispatcher and result returned with the help of `produceState(..)` side-effect which is provided by the compose runtime itself. You can create many such side-effects by extending provided effects (in react terminologies, it's called as hooks).
