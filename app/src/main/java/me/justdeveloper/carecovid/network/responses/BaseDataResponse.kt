package me.justdeveloper.carecovid.network.responses

import com.google.gson.annotations.SerializedName

/**
 * Use this class when response has data
 *
 * @param T
 */
open class BaseDataResponse<T> : BaseResponse() {
    @SerializedName("data")
    val data: T? = null
}
