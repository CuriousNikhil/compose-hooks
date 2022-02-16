# useNetworkState for Jetpack Compose

Need to observe the Network availability? Cool, then just `useNetworkState`. This will return a `State` value if the device gets connected/disconnected from network.


[![](https://img.shields.io/badge/mavencentral-v1.0.0--alpha2.1-yellowgreen?style=flat&logo=gradle)](https://github.com/CuriousNikhil/compose-hooks/useNetworkState) | 
[![javadoc](https://javadoc.io/badge2/me.nikhilchaudhari/compose-usenetworkstate/javadoc.svg)](https://javadoc.io/doc/me.nikhilchaudhari/compose-usenetworkstate)


## Usage

1. Add the following dependency in your `build.gradle`

```kotlin
implementation("me.nikhilchaudhari:compose-usenetworkstate:{latest-version}")
```

2. Add `useNetworkState` to observe the network states

```kotlin
    val networkState by useNetworkState()

    if (networkState == NetworkState.Online) {
        // Call the main UI
    } else {
        // Show the network is disconnected/offline UI
        Text(text = "You are offline! Please connect to the network!")
    }
```

## Getting started

`useNetworkState` returns a `MutableState`. This state changes the value to `NetworkState.Offline` or `NetworkState.Online` depending on 
whether your device is disconnected or connected to network resp.

That's it. Simple hook but useful. 
