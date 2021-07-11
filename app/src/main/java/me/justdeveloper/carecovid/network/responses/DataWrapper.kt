package me.justdeveloper.carecovid.network.responses

data class DataWrapper<T>(
    val isLoading: Boolean = false,
    val data: T? = null,
    val status: Status? = null,
    val responseMessage: String? = null,
    val responseCode: String? = null,
    val error: Throwable? = null,
    val errorMessage: String? = null,
    val apiErrors: List<ApiError>? = null
) {
    fun <M> map(newData: M) = DataWrapper(
        isLoading = isLoading,
        data = newData,
        status = status,
        responseMessage = responseMessage,
        responseCode = responseCode,
        error = error,
        errorMessage = errorMessage,
        apiErrors = apiErrors
    )
}
