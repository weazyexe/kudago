package exe.weazy.kudago.network

interface CityResponseCallback<R : String> {
    fun onSuccess(apiResponse: R)
    fun onFailure(error: String)
}