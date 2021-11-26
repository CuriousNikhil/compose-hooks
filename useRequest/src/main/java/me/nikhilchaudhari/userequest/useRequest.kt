package me.nikhilchaudhari.userequest

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import me.nikhilchaudhari.userequest.helper.Auth
import me.nikhilchaudhari.userequest.helper.RawFiles
import me.nikhilchaudhari.userequest.request.RequestImpl
import me.nikhilchaudhari.userequest.response.Response
import me.nikhilchaudhari.userequest.response.ResponseImpl
import javax.net.ssl.SSLContext

const val DEFAULT_TIMEOUT = 30.0

/**
 * Result class represents the state value of your network
 */
sealed class Result {
    /**
     * Success state contains [Response] object of a network request
     * [Response] object has properties to access all your response contents
     */
    data class Success(val data: Response) : Result()

    /**
     * Error state contains a [Throwable] object in case of any error
     */
    data class Error(val error: Throwable?) : Result()

    /**
     * Loading state means the network request is in progress
     */
    object Loading : Result()
}

/**
 * This composable lets you to create a single network request or a series of network requests and returns
 * the response as a [State] values. The state value changes with the network logic and you receive your appropriate result as [State].
 *
 * Example - <code> val resultState = useRequest { get("https://yourapi.com/things") } </code>
 * The `resultState` has three states as mentioned in the return type. You can consume [Result.Success.data] to get the [Response] object.
 *
 *
 * @param [request] Lambda construct for passing the provided network request methods.
 * Following network requests are available - [get], [post], [put], [delete], [patch], [head]
 * @return [State] of [Result] - The [Result] has three values [Result.Loading], [Result.Success], [Result.Error] which are pretty straightforward and
 * you can consume these state values.
 */
@Composable
fun useRequest(request: () -> Response): State<Result> {
    val result = remember {
        flow<Result> {
            emit(Result.Success(request()))
        }.catch {
            emit(Result.Error(it))
        }.flowOn(Dispatchers.IO)
    }
    return result.collectAsState(initial = Result.Loading)
}

/**
 * DELETE network request
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
 */
fun delete(
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
) = request("DELETE", url, headers, params, auth, data, json, timeout, allowRedirects, stream, files, sslContext)

/**
 * GET network request
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
 */
fun get(
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
) = request("GET", url, headers, params, auth, data, json, timeout, allowRedirects, stream, files, sslContext)

/**
 * HEAD network request
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
 */
fun head(
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
) = request("HEAD", url, headers, params, auth, data, json, timeout, allowRedirects, stream, files, sslContext)

/**
 * OPTIONS network request
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
 */
fun options(
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
) = request("OPTIONS", url, headers, params, auth, data, json, timeout, allowRedirects, stream, files, sslContext)

/**
 * PATCH network request
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
 */
fun patch(
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
) = request("PATCH", url, headers, params, auth, data, json, timeout, allowRedirects, stream, files, sslContext)

/**
 * POST network request
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
 */
fun post(
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
) = request("POST", url, headers, params, auth, data, json, timeout, allowRedirects, stream, files, sslContext)

/**
 * PUT network request
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
 */
fun put(
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
) = request("PUT", url, headers, params, auth, data, json, timeout, allowRedirects, stream, files, sslContext)

/**
 * A generic network request where you can specify the name of the request method
 *
 * @param method Network request method name [String] you want to use
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
 */
fun request(
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
): Response {
    return ResponseImpl(
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
