# Hooks for Jetpack Compose



A collection of small handy, ready-made hooks for Jetpack Compose so that you don't have to think about the states, logic or anything. Just plug it in and use!

![jc-hooks-small](https://user-images.githubusercontent.com/16976114/154118050-c636a2c5-e5cc-409c-9f3d-40d2b0295524.jpg)

Inspired from [`react hooks`](https://reactjs.org/docs/hooks-intro.html).

## [**`useFetch`**](https://github.com/CuriousNikhil/compose-hooks/tree/main/usefetch) 

[![](https://img.shields.io/badge/mavencentral-v1.0.0--alpha1.3-yellowgreen?style=flat&logo=gradle)](https://github.com/CuriousNikhil/compose-hooks/usefetch)

Add the following dependency in your `build.gradle`

```kotlin
  implementation("me.nikhilchaudhari:compose-usefetch:{latest-version}")
```

When you want just to fetch some data and don't want to setup everything unnecessarily!

For example - 

```kotlin

  var resultState = useFetch(url = "https://yoururl.com/something")
  when(val data = resultState.value) {
       Result.Error -> { /* When there is some error, show Toast/snackbar/error message */  }
       Result.Response -> { /* When response is received successfully, show the data on UI */ }
       Result.Loading -> { /* When request is loading, show the loading progress on UI */ }
 }
```

## [**`useNetworkState`**](https://github.com/CuriousNikhil/compose-hooks/tree/main/useNetworkState) 

[![](https://img.shields.io/badge/mavencentral-v1.0.0--alpha2-yellowgreen?style=flat&logo=gradle)](https://github.com/CuriousNikhil/compose-hooks/useNetworkState)

Add the following dependency in your `build.gradle`

```kotlin
  implementation("me.nikhilchaudhari:compose-usenetworkstate:{latest-version}")
```

When you want to use the network connectivity as a state in Compose!

For example -

```kotlin
    val networkState by useNetworkState()

    if (networkState == NetworkState.Online) {
        // Call the main UI
    } else {
        // Show the network is disconnected/offline UI
        Text(text = "You are offline! Please connect to the network!")
    }
```


## [**`useReducer`**](https://github.com/CuriousNikhil/compose-hooks/tree/main/useReducer) 

[![](https://img.shields.io/badge/mavencentral-v1.0.0--alpha1-yellowgreen?style=flat&logo=gradle)](https://github.com/CuriousNikhil/compose-hooks/useReducer)

Add the following dependency in your `build.gradle`

```kotlin
  implementation("me.nikhilchaudhari:compose-usereducer:{latest-version}")
```

When you want to make your state updates in Redux style.

For example -

```kotlin
    var count by remember { mutableStateOf(0) }
    
    val dispatcher = useReducer {
        "increment" does { count++ }
        "decrement" does { count-- }
    }
    
    Button(onClick = { dispatcher.dispatch("increment") }) { / ** / }

    Text(text = count.toString())

    Button(onClick = { dispatcher.dispatch("decrement") }) {/**/}
  
```

Checkout more examples [here](https://github.com/CuriousNikhil/compose-hooks/blob/main/app/src/main/java/me/nikhilchaudhari/usefetch/MainActivity.kt) in the sample app. You can go to individual hook repository to know more about those.

## Want to publish your own compose-hook?

Please feel free to contribute. Create your own hooks.
Checkout the contribution guide for more.

### Reach out to me

[Website](nikhilchaudhari.me/) | [Twitter](https://twitter.com/CuriousNikhyl) | [Mail](nikhyl777@gmail.com) | [LinkedIn](https://www.linkedin.com/in/nikhylchaudhari/)

### License

[LICENSE Apache 2.0](https://github.com/CuriousNikhil/compose-hooks/blob/main/LICENSE)
