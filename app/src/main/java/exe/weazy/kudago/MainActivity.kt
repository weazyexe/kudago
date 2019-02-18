package exe.weazy.kudago

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import exe.weazy.kudago.entities.Category
import exe.weazy.kudago.entities.City
import exe.weazy.kudago.entities.Event

class MainActivity : AppCompatActivity() {

    lateinit var cities : List<City>
    lateinit var eventCategories : List<Category>
    lateinit var placeCategories : List<Category>
    lateinit var events : List<Event>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
