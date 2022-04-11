package com.example.coinRankingUpdate.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.coinRankingUpdate.data.entity.CryptocurrencyEntity
import com.example.coinRankingUpdate.databinding.ItemCryptocurrencyBinding
import com.example.coinRankingUpdate.ui.gone

class SearchCryptoListAdapter(
    private val onItemClicked: (crypto: CryptocurrencyEntity) -> Unit,

    ) : ListAdapter<CryptocurrencyEntity, SearchCryptoListAdapter.CryptocurrencyViewHolder>(

    object : DiffUtil.ItemCallback<CryptocurrencyEntity>() {
        // Id should be unique
        override fun areItemsTheSame(oldItem: CryptocurrencyEntity, newItem: CryptocurrencyEntity): Boolean {
            return newItem.uuid == oldItem.uuid
        }

        override fun areContentsTheSame(oldItem: CryptocurrencyEntity, newItem: CryptocurrencyEntity): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }
) {

    // view holder class
    inner class CryptocurrencyViewHolder(val binding: ItemCryptocurrencyBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                onItemClicked(getItem(bindingAdapterPosition))
            }
        }

        //bind data and handle UI changes
        fun bind(c: CryptocurrencyEntity) {
            binding.cryptocurrency = c
            binding.ivBookmark.gone()
            binding.tvPrice.gone()
            binding.tvRate.gone()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CryptocurrencyViewHolder {
        val binding = ItemCryptocurrencyBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CryptocurrencyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CryptocurrencyViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    override fun getItemCount(): Int {
        return currentList.size
    }

    public override fun getItem(position: Int): CryptocurrencyEntity {
        return currentList[position]
    }
}