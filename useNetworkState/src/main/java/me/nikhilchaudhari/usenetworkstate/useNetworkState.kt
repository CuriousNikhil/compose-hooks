package me.nikhilchaudhari.usenetworkstate

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.produceState
import androidx.core.content.ContextCompat.getSystemService

sealed class NetworkState {
    object Offline : NetworkState()
    object Online : NetworkState()
}

@Composable
fun useNetworkState(context: Context, initialValue: NetworkState = NetworkState.Offline): State<NetworkState> {

    return produceState(initialValue = initialValue, producer = {

        val networkRequestBuilder = NetworkRequest.Builder()
            .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
            .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)

        val networkCallback = object : ConnectivityManager.NetworkCallback() {
            // network is available for use
            override fun onAvailable(network: Network) {
                super.onAvailable(network)
                value = NetworkState.Online
                Log.e("nikhil", "online")
            }

            // lost network connection
            override fun onLost(network: Network) {
                super.onLost(network)
                value = NetworkState.Offline
                Log.e("nikhil", "offline")
            }
        }

        val connectivityManager = getSystemService(context, ConnectivityManager::class.java)
        connectivityManager?.requestNetwork(networkRequestBuilder.build(), networkCallback)
    })
}
