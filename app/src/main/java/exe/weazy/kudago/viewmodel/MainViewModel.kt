package exe.weazy.kudago.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import exe.weazy.kudago.arch.MainContract
import exe.weazy.kudago.presenter.MainPresenter

class MainViewModel : ViewModel() {

    private lateinit var presenter : MutableLiveData<MainContract.Presenter>

    fun getPresenter() : LiveData<MainContract.Presenter> {
        if (!::presenter.isInitialized) {
            presenter = MutableLiveData()
            presenter.postValue(MainPresenter())
        }

        return presenter
    }
}