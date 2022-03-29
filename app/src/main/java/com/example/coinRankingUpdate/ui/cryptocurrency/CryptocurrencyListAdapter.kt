package com.example.coinRankingUpdate.ui.cryptocurrency

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.coinRankingUpdate.data.model.Cryptocurrency
import com.example.coinRankingUpdate.databinding.ItemCryptocurrencyBinding

class CryptocurrencyListAdapter(
    private val onItemClicked: (crypto: Cryptocurrency) -> Unit,
    private val onItemBookmarked: (crypto: Cryptocurrency) -> Unit

) : ListAdapter<Cryptocurrency, CryptocurrencyListAdapter.CryptocurrencyViewHolder>(

    object : DiffUtil.ItemCallback<Cryptocurrency>() {
        // Id should be unique
        override fun areItemsTheSame(oldItem: Cryptocurrency, newItem: Cryptocurrency): Boolean {
            // TODO : replace with id
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: Cryptocurrency, newItem: Cryptocurrency): Boolean {
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
            binding.ivBookmark.setOnClickListener {
                //TODO: handle bookmark action
                onItemBookmarked(getItem(bindingAdapterPosition))
            }
        }

        //bind data and handle rate color
        fun bind(c: Cryptocurrency) {
            binding.cryptocurrency = c
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

    fun clear() {
        submitList(null)
    }

    public override fun getItem(position: Int): Cryptocurrency {
        return currentList[position]
    }
}