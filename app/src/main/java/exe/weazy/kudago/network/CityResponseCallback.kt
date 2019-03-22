package exe.weazy.kudago.network

interface CityResponseCallback<R : CityResponse> {
    fun onSuccess(apiResponse: R)
    fun onFailure(error: String)
}