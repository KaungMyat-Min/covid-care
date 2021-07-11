package me.justdeveloper.carecovid.network.responses

import com.google.gson.annotations.SerializedName

/**
 * Use this class when response has no data
 *
 */
open class BaseResponse {

    @SerializedName("responseCode")
    val responseCode: String? = null

    @SerializedName("responseMessage")
    val responseMessage: String? = null

    @SerializedName("errorLists")
    val apiErrors: List<ApiError>? = null

    fun isSuccess() = responseCode == ResponseCode.API_SUCCESS
}
