package me.nikhilchaudhari.usehttp

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.produceState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import me.nikhilchaudhari.usehttp.helper.Auth
import me.nikhilchaudhari.usehttp.helper.RawFiles
import me.nikhilchaudhari.usehttp.request.RequestImpl
import me.nikhilchaudhari.usehttp.response.ResponseData
import me.nikhilchaudhari.usehttp.response.ResponseDataImpl
import javax.net.ssl.SSLContext

/**
 * Default connection timeout
 */
const val DEFAULT_TIMEOUT = 30.0

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
 * @param data [Any] data you want to pass along with the request as body, can pass it as [Map]. You can either pass [json] or [data]
 * @param json JSON data to pass along with request as body
 * @param timeout timeout value for this request. Default is [DEFAULT_TIMEOUT]
 * @param allowRedirects set this flag if you want to toggle allowRedirects for this request
 * @param stream Set this flag if this request is a continuous stream
 * @param files Send multipart data of [RawFiles] along with request
 * @param sslContext set custom [SSLContext]
 *
 * @return [State][Result] based on the response. The [Result] has three values [Result.Loading], [Result.Response], [Result.Error] which are pretty straightforward and
 * you can consume these state values.
 */
@Composable
fun useGet(
    url: String,
    headers: Map<String, String?> = mapOf(),
    params: Map<String, String> = mapOf(),
    auth: Auth? = null,
    data: Any? = null,
    json: Any? = null,
    timeout: Double = DEFAULT_TIMEOUT,
    allowRedirects: Boolean? = null,
    stream: Boolean = false,
    files: List<RawFiles> = listOf(),
    sslContext: SSLContext? = null
): State<Result> = produceState<Result>(
    initialValue = Result.Loading,
    listOf(url, headers, params, auth, data, json)
) {
    value = makeRequest { request("GET", url, headers, params, auth, data, json, timeout, allowRedirects, stream, files, sslContext) }
}

/**
 * Use DELETE network request
 * This composable lets you to create a DELETE network request and returns
 * the response as a [State] values. You receive your appropriate [Result] as [State].
 * The `resultState` has three states as mentioned in the return type. You can consume [Result.Response.data] to get the [ResponseData] object.
 *
 * Example -
 * <code>
 * var resultState = useDelete(url = "https://yoururl.com/something", headers = mapOf("some" to "thing"))
 * </code>
 *
 * @param url String url
 * @param headers [String] [Map] of headers, optional
 * @param params [String] [Map] Params in the URL, optional
 * @param auth Authorization [Auth] - You can use [BaseAuth] or extend [Auth] interface to return your own authorization header
 * @param data [Any] data you want to pass along with the request as body, can pass it as [Map]. You can either pass [json] or [data]
 * @param json JSON data to pass along with request as body
 * @param timeout timeout value for this request. Default is [DEFAULT_TIMEOUT]
 * @param allowRedirects set this flag if you want to toggle allowRedirects for this request
 * @param stream Set this flag if this request is a continuous stream
 * @param files Send multipart data of [RawFiles] along with request
 * @param sslContext set custom [SSLContext]
 *
 * @return [State] [Result] based on the response. The [Result] has three values [Result.Loading], [Result.Response], [Result.Error] which are pretty straightforward and
 * you can consume these state values.
 */
@Composable
fun useDelete(
    url: String,
    headers: Map<String, String?> = mapOf(),
    params: Map<String, String> = mapOf(),
    auth: Auth? = null,
    data: Any? = null,
    json: Any? = null,
    timeout: Double = DEFAULT_TIMEOUT,
    allowRedirects: Boolean? = null,
    stream: Boolean = false,
    files: List<RawFiles> = listOf(),
    sslContext: SSLContext? = null
) = produceState<Result>(
    initialValue = Result.Loading,
    listOf(url, headers, params, auth, data, json)
) {
    value = makeRequest { request("DELETE", url, headers, params, auth, data, json, timeout, allowRedirects, stream, files, sslContext) }
}

/**
 * Use HEAD network request
 *
 * This composable lets you to create a HEAD network request and returns
 * the response as a [State] values. You receive your appropriate [Result] as [State].
 * The `resultState` has three states as mentioned in the return type. You can consume [Result.Response.data] to get the [ResponseData] object.
 *
 *
 * @param url String url
 * @param headers [String] [Map] of headers, optional
 * @param params [String] [Map] Params in the URL, optional
 * @param auth Authorization [Auth] - You can use [BaseAuth] or extend [Auth] interface to return your own authorization header
 * @param data [Any] data you want to pass along with the request as body, can pass it as [Map]. You can either pass [json] or [data]
 * @param json JSON data to pass along with request as body
 * @param timeout timeout value for this request. Default is [DEFAULT_TIMEOUT]
 * @param allowRedirects set this flag if you want to toggle allowRedirects for this request
 * @param stream Set this flag if this request is a continuous stream
 * @param files Send multipart data of [RawFiles] along with request
 * @param sslContext set custom [SSLContext]
 *
 * @return [State] [Result] based on the response. The [Result] has three values [Result.Loading], [Result.Response], [Result.Error] which are pretty straightforward and
 * you can consume these state values.
 */
@Composable
fun useHead(
    url: String,
    headers: Map<String, String?> = mapOf(),
    params: Map<String, String> = mapOf(),
    auth: Auth? = null,
    data: Any? = null,
    json: Any? = null,
    timeout: Double = DEFAULT_TIMEOUT,
    allowRedirects: Boolean? = null,
    stream: Boolean = false,
    files: List<RawFiles> = listOf(),
    sslContext: SSLContext? = null
) = produceState<Result>(
    initialValue = Result.Loading,
    listOf(url, headers, params, auth, data, json)
) {
    value = makeRequest { request("HEAD", url, headers, params, auth, data, json, timeout, allowRedirects, stream, files, sslContext) }
}

/**
 * Use OPTIONS network request
 *
 * This composable lets you to create a OPTIONS network request and returns
 * the response as a [State] values. You receive your appropriate [Result] as [State].
 * The `resultState` has three states as mentioned in the return type. You can consume [Result.Response.data] to get the [ResponseData] object.
 *
 *
 * @param url String url
 * @param headers [String] [Map] of headers, optional
 * @param params [String] [Map] Params in the URL, optional
 * @param auth Authorization [Auth] - You can use [BaseAuth] or extend [Auth] interface to return your own authorization header
 * @param data [Any] data you want to pass along with the request as body, can pass it as [Map]. You can either pass [json] or [data]
 * @param json JSON data to pass along with request as body
 * @param timeout timeout value for this request. Default is [DEFAULT_TIMEOUT]
 * @param allowRedirects set this flag if you want to toggle allowRedirects for this request
 * @param stream Set this flag if this request is a continuous stream
 * @param files Send multipart data of [RawFiles] along with request
 * @param sslContext set custom [SSLContext]
 *
 * @return [State] [Result] based on the response. The [Result] has three values [Result.Loading], [Result.Response], [Result.Error] which are pretty straightforward and
 * you can consume these state values.
 */
@Composable
fun useOptions(
    url: String,
    headers: Map<String, String?> = mapOf(),
    params: Map<String, String> = mapOf(),
    auth: Auth? = null,
    data: Any? = null,
    json: Any? = null,
    timeout: Double = DEFAULT_TIMEOUT,
    allowRedirects: Boolean? = null,
    stream: Boolean = false,
    files: List<RawFiles> = listOf(),
    sslContext: SSLContext? = null
) = produceState<Result>(
    initialValue = Result.Loading,
    listOf(url, headers, params, auth, data, json)
) {
    value = makeRequest { request("OPTIONS", url, headers, params, auth, data, json, timeout, allowRedirects, stream, files, sslContext) }
}

/**
 * Use PATCH network request
 *
 * This composable lets you to create a PATCH network request and returns
 * the response as a [State] values. You receive your appropriate [Result] as [State].
 * The `resultState` has three states as mentioned in the return type. You can consume [Result.Response.data] to get the [ResponseData] object.

 * @param url String url
 * @param headers [String] [Map] of headers, optional
 * @param params [String] [Map] Params in the URL, optional
 * @param auth Authorization [Auth] - You can use [BaseAuth] or extend [Auth] interface to return your own authorization header
 * @param data [Any] data you want to pass along with the request as body, can pass it as [Map]. You can either pass [json] or [data]
 * @param json JSON data to pass along with request as body
 * @param timeout timeout value for this request. Default is [DEFAULT_TIMEOUT]
 * @param allowRedirects set this flag if you want to toggle allowRedirects for this request
 * @param stream Set this flag if this request is a continuous stream
 * @param files Send multipart data of [RawFiles] along with request
 * @param sslContext set custom [SSLContext]
 *
 * @return [State] [Result] based on the response. The [Result] has three values [Result.Loading], [Result.Response], [Result.Error] which are pretty straightforward and
 * you can consume these state values.
 */
@Composable
fun usePatch(
    url: String,
    headers: Map<String, String?> = mapOf(),
    params: Map<String, String> = mapOf(),
    auth: Auth? = null,
    data: Any? = null,
    json: Any? = null,
    timeout: Double = DEFAULT_TIMEOUT,
    allowRedirects: Boolean? = null,
    stream: Boolean = false,
    files: List<RawFiles> = listOf(),
    sslContext: SSLContext? = null
) = produceState<Result>(
    initialValue = Result.Loading,
    listOf(url, headers, params, auth, data, json)
) {
    value = makeRequest { request("PATCH", url, headers, params, auth, data, json, timeout, allowRedirects, stream, files, sslContext) }
}

/**
 * Use POST network request
 *
 * This composable lets you to create a POST network request and returns
 * the response as a [State] values. You receive your appropriate [Result] as [State].
 * The `resultState` has three states as mentioned in the return type. You can consume [Result.Response.data] to get the [ResponseData] object.
 *
 *
 * @param url String url
 * @param headers [String] [Map] of headers, optional
 * @param params [String] [Map] Params in the URL, optional
 * @param auth Authorization [Auth] - You can use [BaseAuth] or extend [Auth] interface to return your own authorization header
 * @param data [Any] data you want to pass along with the request as body, can pass it as [Map]. You can either pass [json] or [data]
 * @param json JSON data to pass along with request as body
 * @param timeout timeout value for this request. Default is [DEFAULT_TIMEOUT]
 * @param allowRedirects set this flag if you want to toggle allowRedirects for this request
 * @param stream Set this flag if this request is a continuous stream
 * @param files Send multipart data of [RawFiles] along with request
 * @param sslContext set custom [SSLContext]
 *
 * @return [State] [Result] based on the response. The [Result] has three values [Result.Loading], [Result.Response], [Result.Error] which are pretty straightforward and
 * you can consume these state values.
 */
@Composable
fun usePost(
    url: String,
    headers: Map<String, String?> = mapOf(),
    params: Map<String, String> = mapOf(),
    auth: Auth? = null,
    data: Any? = null,
    json: Any? = null,
    timeout: Double = DEFAULT_TIMEOUT,
    allowRedirects: Boolean? = null,
    stream: Boolean = false,
    files: List<RawFiles> = listOf(),
    sslContext: SSLContext? = null
) = produceState<Result>(
    initialValue = Result.Loading,
    listOf(url, headers, params, auth, data, json)
) {
    value = makeRequest { request("POST", url, headers, params, auth, data, json, timeout, allowRedirects, stream, files, sslContext) }
}

/**
 * Use PUT network request
 *
 * This composable lets you to create a PUT network request and returns
 * the response as a [State] values. You receive your appropriate [Result] as [State].
 * The `resultState` has three states as mentioned in the return type. You can consume [Result.Response.data] to get the [ResponseData] object.
 *
 * @param url String url
 * @param headers [String] [Map] of headers, optional
 * @param params [String] [Map] Params in the URL, optional
 * @param auth Authorization [Auth] - You can use [BaseAuth] or extend [Auth] interface to return your own authorization header
 * @param data [Any] data you want to pass along with the request as body, can pass it as [Map]. You can either pass [json] or [data]
 * @param json JSON data to pass along with request as body
 * @param timeout timeout value for this request. Default is [DEFAULT_TIMEOUT]
 * @param allowRedirects set this flag if you want to toggle allowRedirects for this request
 * @param stream Set this flag if this request is a continuous stream
 * @param files Send multipart data of [RawFiles] along with request
 * @param sslContext set custom [SSLContext]
 *
 * @return [State] [Result] based on the response. The [Result] has three values [Result.Loading], [Result.Response], [Result.Error] which are pretty straightforward and
 * you can consume these state values.
 */
@Composable
fun usePut(
    url: String,
    headers: Map<String, String?> = mapOf(),
    params: Map<String, String> = mapOf(),
    auth: Auth? = null,
    data: Any? = null,
    json: Any? = null,
    timeout: Double = DEFAULT_TIMEOUT,
    allowRedirects: Boolean? = null,
    stream: Boolean = false,
    files: List<RawFiles> = listOf(),
    sslContext: SSLContext? = null
) = produceState<Result>(
    initialValue = Result.Loading,
    listOf(url, headers, params, auth, data, json)
) {
    value = makeRequest { request("PUT", url, headers, params, auth, data, json, timeout, allowRedirects, stream, files, sslContext) }
}

/**
 * A generic network request where you can specify the name of the request method
 */
private fun request(
    method: String,
    url: String,
    headers: Map<String, String?> = mapOf(),
    params: Map<String, String> = mapOf(),
    auth: Auth? = null,
    data: Any? = null,
    json: Any? = null,
    timeout: Double = DEFAULT_TIMEOUT,
    allowRedirects: Boolean? = null,
    stream: Boolean = false,
    files: List<RawFiles> = listOf(),
    sslContext: SSLContext? = null
): ResponseData {
    return ResponseDataImpl(
        RequestImpl(
            method, url, params, headers, auth, data, json, timeout, allowRedirects, stream, files, sslContext
        )
    ).run {
        this.init()
        this._history.last().apply {
            this@run._history.remove(this)
        }
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
