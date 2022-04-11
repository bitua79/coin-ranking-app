package com.example.coinRankingUpdate.ui.bookmark

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.coinRankingUpdate.R
import com.example.coinRankingUpdate.data.entity.BookmarkEntity
import com.example.coinRankingUpdate.data.entity.CryptocurrencyEntity
import com.example.coinRankingUpdate.data.relation.BookmarkedCrypto
import com.example.coinRankingUpdate.databinding.ItemCryptocurrencyBinding

class BookmarkListAdapter(
    val onItemClicked: (crypto: CryptocurrencyEntity) -> Unit,
    val onItemUnBookmarked: (bookmark: BookmarkEntity) -> Unit,

    ) : ListAdapter<BookmarkedCrypto, BookmarkListAdapter.CryptoWithUuidViewHolder>(

    object : DiffUtil.ItemCallback<BookmarkedCrypto>() {
        // Id should be unique
        override fun areItemsTheSame(
            oldItem: BookmarkedCrypto,
            newItem: BookmarkedCrypto
        ): Boolean {
            return oldItem.bookmarkEntity.uuid == newItem.bookmarkEntity.uuid
        }

        override fun areContentsTheSame(
            oldItem: BookmarkedCrypto,
            newItem: BookmarkedCrypto
        ): Boolean {
            return oldItem.cryptocurrencyEntity.hashCode() == newItem.cryptocurrencyEntity.hashCode()
        }
    }
) {

    // view holder class
    inner class CryptoWithUuidViewHolder(val binding: ItemCryptocurrencyBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            // Navigate to crypto detail
            binding.root.setOnClickListener {
                onItemClicked(getItem(bindingAdapterPosition).cryptocurrencyEntity!!)
            }

            // UnBookmark coin by clicking on bookmark icon
            binding.ivBookmark.setOnClickListener {
                binding.ivBookmark.setImageResource(R.drawable.ic_bookmark_outline)
                onItemUnBookmarked(getItem(bindingAdapterPosition).bookmarkEntity)
            }
        }

        // Change UI of bookmarked cryptocurrency and bind
        fun bind(c: BookmarkedCrypto) {
            binding.cryptocurrency = c.cryptocurrencyEntity
            c.cryptocurrencyEntity?.let {
                it.isBookmarked = true
            }
            binding.ivBookmark.setImageResource(R.drawable.ic_bookmark)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CryptoWithUuidViewHolder {
        val binding = ItemCryptocurrencyBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CryptoWithUuidViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CryptoWithUuidViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    override fun getItemCount(): Int {
        return currentList.size
    }

    public override fun getItem(position: Int): BookmarkedCrypto {
        return currentList[position]
    }
}