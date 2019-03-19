package exe.weazy.kudago.network

interface ResponseCallback<R : ApiResponse> {
    fun onSuccess(apiResponse: R)
    fun onFailure(errorMessage: String)
}