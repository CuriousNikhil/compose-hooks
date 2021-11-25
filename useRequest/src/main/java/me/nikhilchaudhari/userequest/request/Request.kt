package me.nikhilchaudhari.userequest.request

import me.nikhilchaudhari.userequest.helper.Auth
import me.nikhilchaudhari.userequest.helper.RawFiles
import javax.net.ssl.SSLContext

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
