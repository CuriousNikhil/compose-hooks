package me.nikhilchaudhari.usefetch.network

import org.json.JSONArray
import org.json.JSONObject
import java.nio.charset.Charset

/**
 * Represents a network response
 *
 * @property [url] The request URL
 * @property [statusCode] status/response code for the given network resposne
 * @property [encoding] Encoding of the response stream
 * @property [headers] Response headers map of string, string
 * @property [text] Your network response in text format
 */
data class ResponseData(
    val url: String,
    val statusCode: Int,
    val encoding: Charset?,
    val headers: Map<String, String>,
    val text: String
)

/**
 * [JSONObject] of your network response
 */
val ResponseData.jsonObject
    get() = JSONObject(this.text)

/**
 * [JSONArray] of your network response
 */
val ResponseData.jsonArray
    get() = JSONArray(this.text)
