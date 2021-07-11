package me.justdeveloper.carecovid.network.callbacks

import androidx.lifecycle.MutableLiveData
import com.medico.clinic.network.responses.BaseResponse
import com.medico.clinic.network.responses.DataWrapper

/**
 * Use when response doesn't have data tag.
 *
 * It will emit
 * - true for network call for successful network call,
 * - false for error response,
 * - null for application error.
 *
 * Example usage:
 *  RestClient.getApiService().checkOtp(req)
 *  .enqueue(
 *          DefaultStatusCallback( yourLiveData )
 *     )
 *  )
 *
 * @property liveData
 */
class DefaultStatusCallback(
    private val liveData: MutableLiveData<DataWrapper<Boolean>>,
    private val onSuccess: ((isSuccess: Boolean?) -> Unit)? = null
) : DefaultCallback<BaseResponse, Boolean>(
    liveData, true,
    {
        val isSuccess = it.isSuccess()
        onSuccess?.invoke(isSuccess)
        isSuccess
    }
)
