package me.nikhilchaudhari.usefetch

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.produceState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import me.nikhilchaudhari.usefetch.helper.Auth
import me.nikhilchaudhari.usefetch.network.ResponseData
import me.nikhilchaudhari.usefetch.network.makeGetNetworkCall

/**
 * Default connection timeout
 */
internal const val DEFAULT_TIMEOUT = 30.0

/**
 * Default headers
 */
val DEFAULT_HEADERS = mapOf(
    "Accept" to "*/*",
    "Accept-Encoding" to "gzip, deflate"
)

/**
 * Default data headers
 */
val DEFAULT_DATA_HEADERS = mapOf("Content-Type" to "text/plain")

/**
 * Default form headers
 */
val DEFAULT_FORM_HEADERS = mapOf("Content-Type" to "application/x-www-form-urlencoded")

/**
 * Default JSON headers
 */
val DEFAULT_JSON_HEADERS = mapOf("Content-Type" to "application/json")

/**
 * Use GET network request
 * This composable lets you to create a GET network request and returns
 * the response as a [State] values. You receive your appropriate [Result] as [State].
 * The `resultState` has three states as mentioned in the return type. You can consume [Result.Response.data] to get the [ResponseData] object.
 *
 * <p>
 * Example -
 * var resultState = useGet(url = "https://yoururl.com/something")
 * </p>
 *
 * @param url String url
 * @param headers [String] [Map] of headers, optional
 * @param params [String] [Map] Params in the URL, optional
 * @param auth Authorization [Auth] - You can use [BaseAuth] or extend [Auth] interface to return your own authorization header
 * @param timeout timeout value for this request. Default is [DEFAULT_TIMEOUT]
 *
 * @return [State][Result] based on the response. The [Result] has three values [Result.Loading], [Result.Response], [Result.Error] which are pretty straightforward and
 * you can consume these state values.
 */
@Composable
fun useFetch(
    url: String,
    headers: Map<String, String?> = mapOf(),
    params: Map<String, String> = mapOf(),
    auth: Auth? = null,
    timeout: Double = DEFAULT_TIMEOUT
): State<Result> = produceState<Result>(
    initialValue = Result.Loading,
    key1 = url, key2 = headers, key3 = params
) {
    value = makeRequest {
        makeGetNetworkCall(url, headers, params, auth, timeout)
    }
}

// invokes the requests on IO dispatcher and returns the result success / error
private suspend fun makeRequest(request: () -> ResponseData): Result {
    return try {
        val resp = withContext(Dispatchers.IO) { request() }
        Result.Response(resp)
    } catch (e: Exception) {
        Result.Error(e)
    }
}
