# useReducer for Jetpack Compose

Do you want to handle a bigger state object? Don't want to clutter the complex state logic in the UI and you have deep state update calls? 
Okay, `useReducer` got your back. You can define the the `actions` and state update logic together and `useReducer` will help you to reduce and run your actions 
when you want to perform them. 

[![](https://img.shields.io/badge/mavencentral-v1.0.0--alpha1-yellowgreen?style=flat&logo=gradle)](https://github.com/CuriousNikhil/compose-hooks/useReducer) | 
[![javadoc](https://javadoc.io/badge2/me.nikhilchaudhari/compose-usereducer/javadoc.svg)](https://javadoc.io/doc/me.nikhilchaudhari/compose-usereducer) 

If you are familiar with the Redux, it's pretty much the same.

Inspired from react's [useReducer](https://reactjs.org/docs/hooks-reference.html#usereducer) hook.


## Usage

1. Add the following dependency in your `build.gradle`

```kotlin
  implementation("me.nikhilchaudhari:compose-usereducer:{latest-version}")
```

2. Use the `useReducer`

```kotlin

    // Declare a state with initial value
    var count by remember { mutableStateOf(0) }
    
    // specify your actions using `useReducer` 
    val dispatcher = useReducer {
        "increment" does { count++ }
        "decrement" does { count-- }
    }
    
      // call the action using `dispatcher.dispatch("<action_name>") to invoke the action
      Button(onClick = { dispatcher.dispatch("increment") }) {
          /* */
      }

      Text(text = "$count")

      Button(onClick = { dispatcher.dispatch("decrement") }) {
          /* */
      }

```    

## Getting started

`useReducer` hook lets you define the `action` with the `name of action` as string and the actual action lambda which will be invoked when we want to run that action.

```kotlin

val dispatcher = useReducer {
  "action_name" does { /* update your state */ }
  ...
  ...
  
}
```

You can define as many actions you want. 


#### Dispatcher

`useReducer` returns a `Dispatcher` reference that can be used to dispatch the actions that you want to perform, say on a button click.

You can call `dispatcher.dispatch("action_name")` to invoke the action you've specified.

