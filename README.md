# compose-useRequest
Minimal http client for compose providing response as state when you don't want to setup everything (using viewmodel/repository and then adding retrofit  service...ahhhhh! too much pain) when you just want to do a quick PoC or build a hobby project or for anything.

Inspired from react-hooks


## Usage

1. Add the following dependency in your `build.gradle`

```kotlin
implementation("me.nikhilchaudhari:useRequest:1.0.0-alpha01")
```

2. And use in your regular compose functions

```kotlin
@Composable
fun Greeting() {
  
  // use the provided `useRequest` construct and pass whichever request you want. This returns the response as a state.
  // There are all network requests available-  get(), post(), put() etc.
  
  val resultState = useRequest { get("https://yourdomain.com/greetings") }

  // Use the Result state to update your UI
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
    
    Text(text = "$text")
}
```

Checkout the API docs [here](https://javadoc.io/doc/me.nikhilchaudhari/userequest/latest/me/nikhilchaudhari/userequest/UseRequestKt.html) 

~~~ Note
Documentation is in progress 🧰 🪛 👷‍♂️
~~~

[LICENSE Apache 2.0](https://github.com/CuriousNikhil/compose-useRequest/blob/main/LICENSE)
