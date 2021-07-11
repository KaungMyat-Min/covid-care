package me.justdeveloper.carecovid.network.callbacks

import androidx.lifecycle.MutableLiveData
import com.medico.clinic.network.responses.BaseDataResponse
import com.medico.clinic.network.responses.DataWrapper

/**
 * Use when response has data tag
 *
 * Example usage:
 *
 * RestClient.getApiService().login(req)
 * .enqueue(
 *          DefaultDataCallback( yourMutableLiveData )
 *     )
 *  )

 * @param T
 * @property liveData
 */
open class DefaultDataCallback<T>(

    private val liveData: MutableLiveData<DataWrapper<T>>,
    resetAfterFinish: Boolean = false,
    private val onSuccess: ((T?) -> Unit)? = null

) : DefaultCallback<BaseDataResponse<T>, T>(
    liveData, resetAfterFinish,
    {
        val data = it.data
        onSuccess?.invoke(data)
        data
    }
)
