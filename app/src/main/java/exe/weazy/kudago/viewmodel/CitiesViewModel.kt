package exe.weazy.kudago.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import exe.weazy.kudago.arch.CitiesContract
import exe.weazy.kudago.presenter.CitiesPresenter

class CitiesViewModel : ViewModel() {

    private lateinit var presenter : MutableLiveData<CitiesContract.Presenter>

    fun getPresenter() : LiveData<CitiesContract.Presenter> {
        if (!::presenter.isInitialized) {
            presenter = MutableLiveData()
            presenter.postValue(CitiesPresenter())
        }

        return presenter
    }
}