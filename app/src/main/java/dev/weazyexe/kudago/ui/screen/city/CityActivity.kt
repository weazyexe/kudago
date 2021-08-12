package dev.weazyexe.kudago.ui.screen.city

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import dev.weazyexe.kudago.R

class CityActivity : AppCompatActivity() {

    private val viewModel by viewModels<CitiesViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_city)


    }
}
