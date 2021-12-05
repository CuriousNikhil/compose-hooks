package me.nikhilchaudhari.usehttp

import me.nikhilchaudhari.usehttp.response.ResponseData

/**
 * Result class represents the state value of your network response
 */
sealed class Result {
    /**
     * [Response] state contains [ResponseData] object of a network request
     * [ResponseData] object has properties to access all your response contents
     */
    data class Response(val data: ResponseData) : Result()

    /**
     * [Error] state contains a [Throwable] object in case of any error
     */
    data class Error(val error: Throwable?) : Result()

    /**
     * [Loading] state means the network request is in progress
     */
    object Loading : Result()
}
