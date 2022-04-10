package com.example.coinRankingUpdate.ui.cryptocurrency

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.coinRankingUpdate.R
import com.example.coinRankingUpdate.core.entity.OrderDirection
import com.example.coinRankingUpdate.data.entity.Cryptocurrency
import com.example.coinRankingUpdate.databinding.FragmentCryptocurrencyListBinding
import com.example.coinRankingUpdate.ui.gone
import com.example.coinRankingUpdate.ui.setSpinner
import com.example.coinRankingUpdate.ui.visible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CryptocurrencyListFragment : Fragment() {

    private lateinit var binding: FragmentCryptocurrencyListBinding
    private lateinit var listAdapter: CryptocurrencyListAdapter
    private val viewModel: CryptocurrencyListViewModel by viewModels()

    private var isPriceAsc = false
    private var isMarketCapAsc = true

    private var ascIcon: Drawable? = null
    private var descIcon: Drawable? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCryptocurrencyListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        collectResult()
    }

    private fun initViews(){
        ascIcon = ContextCompat.getDrawable(requireContext(), R.drawable.ic_arrow_up)
        descIcon = ContextCompat.getDrawable(requireContext(), R.drawable.ic_arrow_down)
        setupAdapter()
        setUpChips()
        setSpinner(
            requireContext(),
            binding.spinnerTime,
            R.array.times
        ) {
            viewModel.setTimePeriod(it)
        }
        setupRecyclerview()
    }

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

    private fun onItemClicked(crypto: Cryptocurrency) {
        findNavController().navigate(
            CryptocurrencyListFragmentDirections.actionCryptocurrencyListFragmentToCryptocurrencyDetailFragment(
                crypto
            )
        )
    }

    private fun onItemBookmarked(crypto: Cryptocurrency) {
        //TODO: implement bookmark action
    }

    private fun setupRecyclerview() {
        with(binding.rvCryptocurrency) {
            setHasFixedSize(true)
            adapter = listAdapter
        }
    }

    private fun setUpChips() {
        with(binding.chipPrice) {
            setOnClickListener {
                isPriceAsc = !isPriceAsc
                closeIcon = if (isPriceAsc) {
                    viewModel.setPriceFilter(OrderDirection.ASC)
                    ascIcon
                } else {
                    viewModel.setPriceFilter(OrderDirection.DESC)
                    descIcon
                }
            }
        }
        with(binding.chipMarketCap) {
            setOnClickListener {
                isMarketCapAsc = !isMarketCapAsc
                closeIcon = if (isMarketCapAsc) {
                    viewModel.setMarketCapFilter(OrderDirection.ASC)
                    ascIcon
                } else {
                    viewModel.setMarketCapFilter(OrderDirection.DESC)
                    descIcon
                }
            }
        }
    }

    private fun collectResult() {
        viewModel.cryptocurrenciesResource.observe(viewLifecycleOwner) { resource ->
            val data = resource.handle(
                tag = "CRYPTOCURRENCY_LIST",
                context = requireContext(),
                errMsg = "failed to load cryptocurrencies",
                startLoad = { startLoad() },
                endLoad = { endLoad() }
            )
            listAdapter.submitList(data.orEmpty())
            binding.rvCryptocurrency.smoothScrollToPosition(0)
        }
        viewModel.refresh()
    }

    private fun startLoad() {
        with(binding) {
            rvCryptocurrency.gone()
            progressbar.visible()
        }
    }

    private fun endLoad() {
        with(binding) {
            rvCryptocurrency.visible()
            progressbar.gone()
        }
    }
}