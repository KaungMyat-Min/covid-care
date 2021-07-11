package me.justdeveloper.carecovid.network.responses

import com.google.gson.annotations.SerializedName

/**
 * Use this class if the response supports pagination
 */
class BaseListResponse<T> : BaseDataResponse<T>() {
    @SerializedName("totalPagecount")
    val totalPageCount: Int? = null
}
