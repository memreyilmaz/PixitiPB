package com.payback.pixiti.ui.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.payback.pixiti.databinding.LayoutListStateFooterBinding
import com.payback.pixiti.utils.showIf

typealias ListFooterRetryClickListener = () -> Unit

class ImageListLoadStateAdapter : LoadStateAdapter<ImageListLoadStateAdapter.ListLoadStateViewHolder>() {

    var onRetryClickListener: ListFooterRetryClickListener? = null

    override fun onCreateViewHolder(
            parent: ViewGroup,
            loadState: LoadState
    ): ListLoadStateViewHolder {
        val binding = LayoutListStateFooterBinding.inflate(LayoutInflater.from(parent.context))
        return ListLoadStateViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListLoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    inner class ListLoadStateViewHolder(private val binding: LayoutListStateFooterBinding) :
            RecyclerView.ViewHolder(binding.root) {

        fun bind(loadState: LoadState) {
            binding.apply {
                progressBarListFooter.showIf(loadState is LoadState.Loading)
                textViewListFooterError.showIf(loadState !is LoadState.Loading)

                buttonListRetry.apply {
                    showIf(loadState !is LoadState.Loading)
                    setOnClickListener {
                        onRetryClickListener?.invoke()
                    }
                }
            }
        }
    }
}