package exe.weazy.kudago.network

import exe.weazy.kudago.entity.City

interface CitiesResponseCallback<R : List<City>> {
    fun onSuccess(apiResponse: R)
    fun onFailure(errorMessage: String)
}