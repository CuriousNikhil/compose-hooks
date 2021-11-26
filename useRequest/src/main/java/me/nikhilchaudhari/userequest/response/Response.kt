package me.nikhilchaudhari.userequest.response

import me.nikhilchaudhari.userequest.request.Request
import org.json.JSONArray
import org.json.JSONObject
import java.io.InputStream
import java.net.HttpURLConnection
import java.nio.charset.Charset

/**
 * Represents a network response
 *
 * @property [request] - [Request] the Request you've received this response for
 * @property [statusCode] - [Int] status code of the response from the network
 * @property [headers] - Response headers which are received
 * @property [raw] - Raw response [InputStream]
 * @property [content] - Raw content in the form of [ByteArray] just in case if the connection is stream and not http
 * @property [text] - The response received in [String] format
 * @property [jsonObject] - [JSONObject] of the response
 * @property [jsonArray] - [JSONArray] of the response
 * @property url - Request url [String]
 * @property encoding - Response encoding [Charset]
 * @property history - List of responses of number of requests
 * @property connection - [HttpURLConnection]
 */
interface Response {

    val request: Request

    val statusCode: Int

    val headers: Map<String, String>

    val raw: InputStream

    val content: ByteArray

    val text: String
    val jsonObject: JSONObject

    val jsonArray: JSONArray

    val url: String

    var encoding: Charset

    val history: List<Response>

    val connection: HttpURLConnection

    /**
     * Content interator when you receive content with byte array such as files, raw files
     * @return [Iterator]
     */
    fun contentIterator(chunkSize: Int = 1): Iterator<ByteArray>

    /**
     * Line iterator to iterate over lines at once with [chunkSize] of 512 by default
     * @return [Iterator]
     */
    fun lineIterator(chunkSize: Int = 512, delimiter: ByteArray? = null): Iterator<ByteArray>
}
