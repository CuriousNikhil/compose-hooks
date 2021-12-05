# useHttp for Jetpack Compose
Minimal http client for compose providing response as state when you don't want to setup everything (using viewmodel/repository and then adding retrofit  services...ahhhhh! too much pain) when you just want to do a quick PoC or build a hobby project or for anything.

Inspired from react-hooks [useFetch](https://use-http.com/#/)


## Usage

1. Add the following dependency in your `build.gradle`

```kotlin
implementation("me.nikhilchaudhari:compose-usehttp:{latest-release-version}")
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


**Checkout the API docs [here](https://javadoc.io/doc/me.nikhilchaudhari/compose-usehttp/latest/usehttp/me.nikhilchaudhari.usehttp/use-get.html)**

## Getting started

### Methods available

You can use the following composables to get the network response as a state value in your composables/compose functions. Each of the provided methods contains all the necessary parameters that are required or can be passed over network request. You can check the following Apis - 

[`useGet`](https://javadoc.io/doc/me.nikhilchaudhari/compose-usehttp/1.0.0-alpha1.1/usehttp/me.nikhilchaudhari.usehttp/use-get.html) for GET request

[`usePost`](https://javadoc.io/doc/me.nikhilchaudhari/compose-usehttp/1.0.0-alpha1.1/usehttp/me.nikhilchaudhari.usehttp/use-post.html) for POST request
 
[`usePut`](https://javadoc.io/doc/me.nikhilchaudhari/compose-usehttp/1.0.0-alpha1.1/usehttp/me.nikhilchaudhari.usehttp/use-put.html) for PUT request

[`useOptions`](https://javadoc.io/doc/me.nikhilchaudhari/compose-usehttp/1.0.0-alpha1.1/usehttp/me.nikhilchaudhari.usehttp/use-options.html) for OPTIONS request

[`usePatch`](https://javadoc.io/doc/me.nikhilchaudhari/compose-usehttp/1.0.0-alpha1.1/usehttp/me.nikhilchaudhari.usehttp/use-patch.html) for PATCH request

[`useDelete`](https://javadoc.io/doc/me.nikhilchaudhari/compose-usehttp/1.0.0-alpha1.1/usehttp/me.nikhilchaudhari.usehttp/use-delete.html) for DELETE request

[`useHead`](https://javadoc.io/doc/me.nikhilchaudhari/compose-usehttp/1.0.0-alpha1.1/usehttp/me.nikhilchaudhari.usehttp/use-head.html) for HEAD request


### Response as a state

When you use `useGet("<url>")`, it returns you `State<Result>` object. The [`Result`](https://javadoc.io/doc/me.nikhilchaudhari/compose-usehttp/1.0.0-alpha1.1/usehttp/me.nikhilchaudhari.usehttp/-result/index.html) has three values as follows - 

[`Error(error: Throwable?)`](https://javadoc.io/doc/me.nikhilchaudhari/compose-usehttp/1.0.0-alpha1.1/usehttp/me.nikhilchaudhari.usehttp/-result/-error/index.html) : An error state, when any error is thrown

[`Response(data: ResponseData)`](https://javadoc.io/doc/me.nikhilchaudhari/compose-usehttp/1.0.0-alpha1.1/usehttp/me.nikhilchaudhari.usehttp/-result/-response/index.html) : A response state, a successful network request returning response

[`Loading`](https://javadoc.io/doc/me.nikhilchaudhari/compose-usehttp/1.0.0-alpha1.1/usehttp/me.nikhilchaudhari.usehttp/-result/-loading/index.html) : A loading state when request is ongoing

And these are the three states we usually use while doing a long running task/network call. 

You get the [`ResponseData`](https://javadoc.io/doc/me.nikhilchaudhari/compose-usehttp/1.0.0-alpha1.1/usehttp/me.nikhilchaudhari.usehttp.response/-response-data/index.html) object in the `Response` state which you can use to process. You can use `Moshi`, `GSON` or any other JSON adapters libraries. Checkout the [sample app](https://github.com/CuriousNikhil/compose-usehttp/tree/main/app/src/main/java/me/nikhilchaudhari/usefetch) for how it's used.

Internally, the network request is performed on IO dispatcher and result returned with the help of `produceState(..)` side-effect which is provided by the compose runtime itself. You can create many such side-effects by extending provided effects (in react terminologies, it's called as hooks).

Hop over to Wiki page for the complete documenation.


~~~ Note
Documentation is in progress 🧰 🪛 👷‍♂️
~~~

## Contribution

Please feel free to contribute if you think there are few modifications/changes/edits/improvements required. Thank you!


### License

[LICENSE Apache 2.0](https://github.com/CuriousNikhil/compose-useRequest/blob/main/LICENSE)
