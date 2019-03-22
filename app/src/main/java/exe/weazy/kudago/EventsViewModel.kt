package exe.weazy.kudago

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import exe.weazy.kudago.activity.MainActivity
import exe.weazy.kudago.entity.Event

class EventsViewModel : ViewModel() {

    var events : MutableLiveData<List<Event>> = MutableLiveData()

    init {
        events.value = MainActivity.events
    }

    fun getEventsList() = events
}