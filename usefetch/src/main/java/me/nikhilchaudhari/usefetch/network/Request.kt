package me.nikhilchaudhari.usefetch.network

import me.nikhilchaudhari.usefetch.DEFAULT_HEADERS
import me.nikhilchaudhari.usefetch.helper.*
import java.io.IOException
import java.io.InputStream
import java.net.*
import java.nio.charset.Charset
import java.util.*
import java.util.zip.GZIPInputStream
import java.util.zip.InflaterInputStream

private fun URL.toIDN(): URL {
    val newHost = IDN.toASCII(this.host)
    // TODO: Check warning error on the following lines
    // this.javaClass.getDeclaredField("host").apply { this.isAccessible = true }.set(this, newHost)
    // this.javaClass.getDeclaredField("authority").apply { this.isAccessible = true }.set(this, if (this.port == -1) this.host else "${this.host}:${this.port}")
    val query = if (this.query == null) {
        null
    } else {
        URLDecoder.decode(this.query, "UTF-8")
    }
    return URL(
        URI(this.protocol, this.userInfo, this.host, this.port, this.path, query, this.ref).toASCIIString()
    )
}

private fun makeRoute(route: String, params: Map<String, String>): String {
    if (URI(route).scheme !in setOf("http", "https")) {
        throw IllegalArgumentException("Invalid schema. Only http:// and https:// are supported.")
    }
    return URL(route + if (params.isNotEmpty()) "?${Parameters(params)}" else "").toIDN().toString()
}

private fun makeHeaders(headers: Map<String, String?>, auth: Auth?): CaseInsensitiveMutableMap<String> {
    val mutableHeaders = CaseInsensitiveMutableMap(headers.toSortedMap())
    mutableHeaders.putAllIfAbsentWithNull(DEFAULT_HEADERS)

    if (auth != null) {
        val header = auth.header
        mutableHeaders[header.first] = header.second
    }
    val nonNullHeaders: MutableMap<String, String> =
        mutableHeaders.filterValues { it != null }.mapValues { it.value!! }.toSortedMap()

    return CaseInsensitiveMutableMap(nonNullHeaders)
}

private fun makeTimeout(timeout: Double): Int {
    return (timeout * 1000.0).toInt()
}

private fun HttpURLConnection.realInputStream(headers: Map<String, String?>): InputStream {
    val stream = try {
        this.inputStream
    } catch (ex: IOException) {
        this.errorStream
    }
    return when (headers["Content-Encoding"]?.lowercase(Locale.getDefault())) {
        "gzip" -> GZIPInputStream(stream)
        "deflate" -> InflaterInputStream(stream)
        else -> stream
    }
}

private fun getResponseHeaders(headerFields: Map<String, List<String>>?): Map<String, String> {
    if (headerFields != null) {
        return CaseInsensitiveMap(headerFields.mapValues { it.value.joinToString(", ") })
    } else throw IllegalStateException("Set to null by another thread")
}

private fun getResponseEncoding(headers: Map<String, String>): Charset {
    headers["Content-Type"]?.let {
        val charset = it.split(";")
            .asSequence()
            .map { it.split("=") }
            .filter { it[0].trim().lowercase() == "charset" }
            .filter { it.size == 2 }
            .map { it[1] }
            .firstOrNull()
        return Charset.forName(charset?.uppercase() ?: Charsets.UTF_8.name())
    }
    return Charsets.UTF_8
}

@Throws(Exception::class)
internal fun makeGetNetworkCall(
    url: String,
    headers: Map<String, String?>,
    params: Map<String, String>,
    auth: Auth?,
    timeout: Double
): ResponseData {

    val httpURLConnection = URL(makeRoute(url, params)).openConnection() as HttpURLConnection
    val requestHeaders = makeHeaders(headers, auth)

    httpURLConnection.requestMethod = "GET"
    for ((key, value) in requestHeaders) {
        httpURLConnection.setRequestProperty(key, value)
    }
    val requestTimeout = makeTimeout(timeout)
    httpURLConnection.connectTimeout = requestTimeout
    httpURLConnection.readTimeout = requestTimeout

    httpURLConnection.instanceFollowRedirects = true

    val statusCode = httpURLConnection.responseCode
    val responseHeaders = getResponseHeaders(httpURLConnection.headerFields)
    val encoding = getResponseEncoding(responseHeaders)
    val text = httpURLConnection.realInputStream(responseHeaders).use { it.readBytes() }.toString(encoding)
    return ResponseData(url, statusCode, encoding, responseHeaders, text)
}
