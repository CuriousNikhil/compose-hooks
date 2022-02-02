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
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat.getSystemService

/**
 * Represents the network state values
 * [Offline] - When the device is not connected to any network
 * [Online] - When the device is connected to network
 */
sealed class NetworkState {
    object Offline : NetworkState()
    object Online : NetworkState()
}

/**
 * Use the network state
 * This composable let you to use the network state of the current device. It'll toggle to two states
 * [NetworkState].
 *
 * @param initialValue - Pass the initial value for the network state of the [NetworkState] type. Default value is [NetworkState.Offline]
 * @return Returns a compose [State] with type of [NetworkState] which you can consume
 */
@Composable
fun useNetworkState(initialValue: NetworkState = NetworkState.Offline): State<NetworkState> {
    val context = LocalContext.current
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
