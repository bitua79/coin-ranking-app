package com.example.coinRankingUpdate.ui.cryptocurrency

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.coinRankingUpdate.data.entity.CryptocurrencyEntity
import com.example.coinRankingUpdate.databinding.ItemCryptocurrencyBinding

class CryptocurrencyListAdapter(
    private val onItemClicked: (crypto: CryptocurrencyEntity) -> Unit,
    private val onItemBookmarked: (crypto: CryptocurrencyEntity) -> Unit,
    private val onItemUnBookmarked: (crypto: CryptocurrencyEntity) -> Unit

) : ListAdapter<CryptocurrencyEntity, CryptocurrencyListAdapter.CryptocurrencyViewHolder>(

    object : DiffUtil.ItemCallback<CryptocurrencyEntity>() {
        // Id should be unique
        override fun areItemsTheSame(
            oldItem: CryptocurrencyEntity,
            newItem: CryptocurrencyEntity
        ): Boolean {
            // TODO : replace with id
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(
            oldItem: CryptocurrencyEntity,
            newItem: CryptocurrencyEntity
        ): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }
) {

    // view holder class
    inner class CryptocurrencyViewHolder(private val binding: ItemCryptocurrencyBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                onItemClicked(getItem(bindingAdapterPosition))
            }
        }

        //bind data and handle rate color
        fun bind(c: CryptocurrencyEntity) {
            with(binding) {
                cryptocurrency = c
                ivBookmark.setOnClickListener {
                    if (c.isBookmarked)
                        onItemUnBookmarked(getItem(bindingAdapterPosition))
                    else
                        onItemBookmarked(getItem(bindingAdapterPosition))
                }
            }
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