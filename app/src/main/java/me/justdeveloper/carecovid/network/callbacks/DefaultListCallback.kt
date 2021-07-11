package me.justdeveloper.carecovid.network.callbacks

import androidx.lifecycle.MutableLiveData
import com.medico.clinic.network.responses.BaseListResponse
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
open class DefaultListCallback<T>(

    private val liveData: MutableLiveData<DataWrapper<T>>,
    resetAfterFinish: Boolean = false

) : DefaultCallback<BaseListResponse<T>, T>(liveData, resetAfterFinish, { it.data })
