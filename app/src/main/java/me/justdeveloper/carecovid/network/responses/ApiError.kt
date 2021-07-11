package me.justdeveloper.carecovid.network.responses

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
class ApiError(
    @SerializedName("fieldCode")
    val fieldCode: String? = null,

    @SerializedName("errorMessage")
    val errorMessage: String? = null
) : Parcelable
