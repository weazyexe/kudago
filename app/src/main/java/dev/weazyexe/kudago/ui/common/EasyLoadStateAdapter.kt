package dev.weazyexe.kudago.ui.common

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import dev.weazyexe.kudago.R
import dev.weazyexe.kudago.databinding.ViewHolderLoadingBinding

/**
 * Лоадер загрузки внизу экрана для списков с пагинацией
 */
class EasyLoadStateAdapter(private val onRetryClick: () -> Unit) :
    LoadStateAdapter<EasyLoadStateAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.view_holder_loading, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    inner class ViewHolder(parent: View) : RecyclerView.ViewHolder(parent) {

        private val binding by lazy { ViewHolderLoadingBinding.bind(parent) }

        init {
            binding.retryBtn.setOnClickListener { onRetryClick.invoke() }
        }

        fun bind(loadState: LoadState) = with(binding) {
            when (loadState) {
                is LoadState.Loading -> {
                    loadingPb.isVisible = true
                    retryLayout.isVisible = false
                }
                is LoadState.Error -> {
                    loadingPb.isVisible = false
                    retryLayout.isVisible = true
                }
                else -> {
                    loadingPb.isVisible = false
                    retryLayout.isVisible = false
                }
            }
        }
    }
}