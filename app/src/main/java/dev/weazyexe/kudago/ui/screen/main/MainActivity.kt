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
        event_cards_rv.layoutManager = LinearLayoutManager(this)
        event_cards_rv.adapter = adapter
    }

    private fun render(state: MainState) {
        when {
            state.isLoading -> {
                event_cards_rv.isVisible = false
                loading_view.isVisible = true
            }
            state.hasError -> {
                // TODO
            }
            state.events.isNotEmpty() -> {
                event_cards_rv.isVisible = true
                loading_view.isVisible = false

                adapter.setData(state.events)
            }
        }
    }
}
