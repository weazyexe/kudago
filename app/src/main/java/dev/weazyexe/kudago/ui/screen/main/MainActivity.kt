package dev.weazyexe.kudago.ui.screen.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.core.view.*
import androidx.core.view.WindowInsetsCompat.*
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import dev.chrisbanes.insetter.applyInsetter
import dev.weazyexe.core.utils.extensions.makeEdgeToEdge
import dev.weazyexe.kudago.app.App
import dev.weazyexe.kudago.databinding.ActivityMainBinding
import dev.weazyexe.kudago.ui.common.EasyLoadStateAdapter
import dev.weazyexe.kudago.ui.screen.main.adapter.EventsAdapter
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.net.ConnectException

/**
 * Главный экран
 */
class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<MainViewModel>()
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    private val adapter = EventsAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        (application as App).appComponent.inject(viewModel)
        viewModel.onCreate()

        initViews()
        initEdgeToEdge()
        initListeners()
        initObservers()
    }

    private fun initObservers() {
        lifecycleScope.launchWhenCreated {
            viewModel.uiState.collect(::render)
        }
    }

    private fun initListeners() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.loadEvents(isSwipeRefresh = true)
        }
    }

    private fun initViews() = with(binding) {
        eventCardsRv.layoutManager = LinearLayoutManager(this@MainActivity)
        eventCardsRv.adapter = adapter.withLoadStateFooter(
            footer = EasyLoadStateAdapter { adapter.retry() }
        )
    }

    private fun initEdgeToEdge() = with(binding) {
        makeEdgeToEdge()

        eventCardsRv.applyInsetter {
            type(Type.navigationBars()) { padding() }
        }
        mainLayout.applyInsetter {
            type(Type.statusBars()) { padding() }
        }
        toolbarContainer.applyInsetter {
            type(Type.navigationBars()) { padding(horizontal = true) }
        }
    }

    private fun render(state: MainState) {
        renderEvents(state)
        renderCity(state)
    }

    private fun renderEvents(state: MainState) = with(binding) {
        val loadState = state.eventsLoadState
        val data = loadState.data

        when {
            loadState.isSwipeRefresh -> {
                // Do nothing
            }
            loadState.isLoading -> {
                swipeRefreshLayout.isRefreshing = false
                connectionFailedLayout.isVisible = false
                eventCardsRv.isVisible = false
                loadingLayout.isVisible = loadState.isLoading
                errorLayout.isVisible = false
            }
            loadState.error is ConnectException -> {
                swipeRefreshLayout.isRefreshing = false
                connectionFailedLayout.isVisible = true
                eventCardsRv.isVisible = false
                loadingLayout.isVisible = false
                errorLayout.isVisible = false
            }
            loadState.error != null -> {
                swipeRefreshLayout.isRefreshing = false
                connectionFailedLayout.isVisible = false
                eventCardsRv.isVisible = false
                loadingLayout.isVisible = false
                errorLayout.isVisible = true
            }
            data != null -> {
                swipeRefreshLayout.isRefreshing = false
                connectionFailedLayout.isVisible = false
                eventCardsRv.isVisible = true
                loadingLayout.isVisible = false
                errorLayout.isVisible = false

                lifecycleScope.launch {
                    adapter.submitData(data)
                }
            }
            else -> {
                // Do nothing
            }
        }
    }

    private fun renderCity(state: MainState) = with(binding.toolbarLayout) {
        val loadState = state.cityLoadState
        val data = loadState.data

        if (data != null) {
            cityTv.text = data.title
        }
    }
}
