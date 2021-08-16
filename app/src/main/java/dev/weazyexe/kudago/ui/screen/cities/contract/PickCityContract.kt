package dev.weazyexe.kudago.ui.screen.cities.contract

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract
import dev.weazyexe.kudago.domain.city.City
import dev.weazyexe.kudago.ui.screen.cities.CitiesActivity

/**
 * Контракт для получения результата с экрана [CitiesActivity]
 */
class PickCityContract : ActivityResultContract<Unit, City?>() {

    override fun createIntent(context: Context, input: Unit?): Intent {
        return Intent(context, CitiesActivity::class.java)
    }

    override fun parseResult(resultCode: Int, intent: Intent?): City? {
        if (resultCode != Activity.RESULT_OK) {
            return null
        }

        return intent?.getSerializableExtra(EXTRA_CITY) as City?
    }

    companion object {

        const val EXTRA_CITY = "EXTRA_CITY"
    }
}