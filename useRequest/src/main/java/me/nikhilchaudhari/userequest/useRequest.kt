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

sealed class Result {
    data class Success(val data: Response) : Result()
    data class Error(val error: Throwable?) : Result()
    object Loading : Result()
}

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
