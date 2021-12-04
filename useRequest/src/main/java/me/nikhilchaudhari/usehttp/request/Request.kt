package me.nikhilchaudhari.usehttp.request

import me.nikhilchaudhari.usehttp.helper.Auth
import me.nikhilchaudhari.usehttp.helper.RawFiles
import javax.net.ssl.SSLContext

/**
 * Request interface representing simple network request
 */
interface Request {

    val method: String

    val url: String

    val params: Map<String, String>

    val headers: Map<String, String>

    val auth: Auth?

    val body: ByteArray

    val data: Any?

    val json: Any?

    val timeout: Double

    val allowRedirects: Boolean

    val stream: Boolean

    val files: List<RawFiles>

    val sslContext: SSLContext?
}
