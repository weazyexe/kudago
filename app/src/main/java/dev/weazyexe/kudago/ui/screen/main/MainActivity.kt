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

    private fun initListeners() {

    }

    private fun initViews() {
        with (binding.eventCardsRv) {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = this@MainActivity.adapter
        }
    }

    private fun render(state: MainState) {
        with (binding) {
            when {
                state.isLoading -> {
                    connectionFailedLayout.isVisible = false
                    eventCardsRv.isVisible = false
                    loadingLayout.isVisible = true
                    errorLayout.isVisible = false
                }
                state.error is ConnectException -> {
                    connectionFailedLayout.isVisible = true
                    eventCardsRv.isVisible = false
                    loadingLayout.isVisible = false
                    errorLayout.isVisible = false
                }
                state.error != null -> {
                    connectionFailedLayout.isVisible = false
                    eventCardsRv.isVisible = false
                    loadingLayout.isVisible = false
                    errorLayout.isVisible = true
                }
                state.events.isNotEmpty() -> {
                    connectionFailedLayout.isVisible = false
                    eventCardsRv.isVisible = true
                    loadingLayout.isVisible = false
                    errorLayout.isVisible = false

                    adapter.setData(state.events)
                }
            }
        }
    }
}
