package dev.weazyexe.kudago.ui.screen.cities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.view.WindowInsetsCompat.*
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import dev.chrisbanes.insetter.applyInsetter
import dev.weazyexe.core.utils.extensions.makeEdgeToEdge
import dev.weazyexe.kudago.R
import dev.weazyexe.kudago.app.App
import dev.weazyexe.kudago.databinding.ActivityCitiesBinding
import dev.weazyexe.kudago.ui.screen.cities.CitiesEffect.*
import dev.weazyexe.kudago.ui.screen.cities.adapter.CitiesAdapter
import dev.weazyexe.kudago.ui.screen.cities.contract.PickCityContract.Companion.EXTRA_CITY
import kotlinx.coroutines.flow.collect

/**
 * Экран выбора города
 */
class CitiesActivity : ComponentActivity() {

    private val viewModel by viewModels<CitiesViewModel>()
    private val binding by lazy { ActivityCitiesBinding.inflate(layoutInflater) }

    private val adapter = CitiesAdapter {
        viewModel.pickCity(it)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        (application as App).appComponent.inject(viewModel)

        viewModel.onCreate()

        initEdgeToEdge()
        initViews()
        initListeners()
        initObservers()
    }

    private fun initViews() = with(binding) {
        citiesRv.adapter = adapter
        citiesRv.layoutManager = LinearLayoutManager(this@CitiesActivity)

        toolbar.navigationIcon = AppCompatResources.getDrawable(
            this@CitiesActivity, R.drawable.ic_close_red
        )
        toolbar.title = getString(R.string.choose_city)
    }

    private fun initListeners() = with(binding) {
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    private fun initObservers() {
        lifecycleScope.launchWhenCreated {
            viewModel.uiState.collect(::render)
        }
        lifecycleScope.launchWhenCreated {
            viewModel.effects.collect(::handleEffect)
        }
    }

    private fun initEdgeToEdge() = with(binding) {
        makeEdgeToEdge()

        citiesRv.applyInsetter {
            type(Type.navigationBars()) { padding() }
        }
        toolbar.applyInsetter {
            type(Type.navigationBars()) { padding(horizontal = true) }
            type(Type.statusBars()) { margin() }
        }
    }

    private fun handleEffect(effect: CitiesEffect) {
        when (effect) {
            is ChooseCity -> {
                val resultIntent = Intent().apply {
                    putExtra(EXTRA_CITY, effect.city)
                }

                setResult(Activity.RESULT_OK, resultIntent)
                finish()
            }
        }
    }

    private fun render(state: CitiesState) {
        renderCities(state)
    }

    private fun renderCities(state: CitiesState) {
        val loadState = state.cities
        val data = loadState.data

        with(binding) {
            when {
                loadState.isLoading -> {
                    loadingPb.isVisible = true
                    successLayout.isVisible = false
                    connectionFailedLayout.isVisible = false
                }
                loadState.error != null -> {
                    loadingPb.isVisible = false
                    successLayout.isVisible = false
                    connectionFailedLayout.isVisible = true
                }
                data != null -> {
                    loadingPb.isVisible = false
                    successLayout.isVisible = true
                    connectionFailedLayout.isVisible = false

                    adapter.submitList(data)
                }
            }
        }
    }
}
