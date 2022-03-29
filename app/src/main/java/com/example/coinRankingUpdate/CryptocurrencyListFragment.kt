package com.example.coinRankingUpdate

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.coinRankingUpdate.databinding.FragmentCryptocurrencyListBinding

class CryptocurrencyListFragment : Fragment() {

    private lateinit var binding: FragmentCryptocurrencyListBinding
    private lateinit var listAdapter: CryptocurrencyListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCryptocurrencyListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupAdapter()
        setupRecyclerview()
        initValuesRecyclerView()

    }

    //Setup adapter
    private fun setupAdapter() {
        listAdapter = CryptocurrencyListAdapter(
            onItemClicked = {
                onItemClicked(it)
            },
            onItemBookmarked = {
                onItemBookmarked(it)
            }
        )
    }

    private fun setupRecyclerview() {
        with(binding.rvCryptocurrency) {
            setHasFixedSize(true)
            adapter = listAdapter
        }
    }

    private fun initValuesRecyclerView() {
        listAdapter.submitList(FakeData.list)
    }

    private fun onItemClicked(crypto: Cryptocurrency) {
        //TODO: implement click action
    }

    private fun onItemBookmarked(crypto: Cryptocurrency) {
        //TODO: implement bookmark action
    }
}