package me.justdeveloper.carecovid.network.callbacks

import androidx.lifecycle.MutableLiveData
import me.justdeveloper.carecovid.network.exceptions.NoConnectionException
import com.medico.clinic.network.responses.BaseResponse
import com.medico.clinic.network.responses.DataWrapper
import me.justdeveloper.carecovid.network.responses.Status
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

/**
 * Helper call to handle network callback and transform response into States
 *
 * @param R
 * @param T
 * @property liveData
 * @param resetAfterFinish (if true, the callback will act as a state callback)
 * @property mutator
 */
open class DefaultCallback<R : BaseResponse, T>(
    private val liveData: MutableLiveData<DataWrapper<T>>,
    private val resetAfterFinish: Boolean = false,
    private val mutator: (res: R) -> T?

) : Callback<R> {

    init {
        liveData.value = getInitialState(true)
    }

    private fun getInitialState(isLoading: Boolean): DataWrapper<T> =
        DataWrapper(isLoading = isLoading)

    override fun onFailure(call: Call<R>, t: Throwable) {
        liveData.value = onFailure(t)
        reset()
    }

    override fun onResponse(call: Call<R>, response: Response<R>) {
        liveData.value = onSuccess(response.body())
        reset()
    }

    protected open fun <T> onFailure(it: Throwable): DataWrapper<T>? =
        DataWrapper(
            isLoading = false,
            data = null,
            status = getFailureStatus(it),
            errorMessage = it.message,
            error = it
        )

    protected open fun onSuccess(response: R?) =
        DataWrapper(
            isLoading = false,
            data = response?.let { mutator(it) },
            status = getStatus(response),
            responseCode = response?.responseCode,
            responseMessage = response?.responseMessage,
            apiErrors = response?.apiErrors
        )

    private fun getStatus(response: BaseResponse?): Status {
        return if (response == null) {
            Status.ERROR
        } else {
            Status.SUCCESS
        }
    }

    private fun getFailureStatus(it: Throwable): Status {
        return when (it) {
            is NoConnectionException -> Status.NO_CONNECTION
            is IOException -> Status.SERVER_DOWN
            else -> Status.ERROR_UNKNOWN
        }
    }

    private fun reset() {
        if (resetAfterFinish) {
            liveData.value = getInitialState(false)
        }
    }
}
