package com.payback.pixiti.ui.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.payback.pixiti.databinding.ItemImageBinding
import com.payback.pixiti.model.Image
import com.payback.pixiti.utils.loadImage

typealias ImageItemClickListener = (Image?) -> Unit

class ImageListAdapter : PagingDataAdapter<Image, ImageListAdapter.ListViewHolder>(IMAGE_COMPARATOR) {

    var onItemClickListener: ImageItemClickListener? = null

    override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
    ): ListViewHolder {
        val binding = LayoutInflater.from(parent.context).let {
            ItemImageBinding.inflate(it, parent, false)
        }
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val imageItem = getItem(position)
        if (imageItem != null) {
            holder.bind(imageItem)
        }
    }

    inner class ListViewHolder(private val binding: ItemImageBinding) :
            RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Image) {
            binding.apply {
                imageViewListName.loadImage(imageUrl = item.previewURL, context = itemView.context)
                textViewImageOwner.text = item.user
                textViewImageTags.text = item.tags?.replace(",", "")
                root.setOnClickListener {
                    onItemClickListener?.invoke(item)
                }
            }
        }
    }

    companion object {
        private val IMAGE_COMPARATOR = object : DiffUtil.ItemCallback<Image>() {
            override fun areItemsTheSame(oldItem: Image, newItem: Image): Boolean =
                    oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Image, newItem: Image): Boolean =
                    oldItem == newItem
        }
    }
}