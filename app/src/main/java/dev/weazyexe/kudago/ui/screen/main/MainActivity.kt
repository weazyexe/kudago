package dev.weazyexe.kudago.ui.screen.main


import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import dev.weazyexe.kudago.app.App
import dev.weazyexe.kudago.databinding.ActivityMainBinding
import dev.weazyexe.kudago.ui.screen.main.adapter.EventsAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.net.ConnectException

/**
 * Главный экран
 */
class MainActivity : AppCompatActivity() {

    private val viewModel by viewModels<MainViewModel>()
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    private val adapter = EventsAdapter(mutableListOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        (application as App).appComponent.inject(viewModel)
        viewModel.onCreate()

        initViews()
        initListeners()
        initObservers()
    }

    private fun initObservers() {
        lifecycleScope.launchWhenCreated {
            viewModel.state.collect(::render)
        }
    }

    @FlowPreview
    private fun initListeners() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.loadEvents(isSwipeRefresh = true)
        }
    }

    private fun initViews() {
        with (binding.eventCardsRv) {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = this@MainActivity.adapter
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
            data != null && data.isNotEmpty() -> {
                swipeRefreshLayout.isRefreshing = false
                connectionFailedLayout.isVisible = false
                eventCardsRv.isVisible = true
                loadingLayout.isVisible = false
                errorLayout.isVisible = false

                adapter.setData(data)
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
