package com.example.coinRankingUpdate.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.coinRankingUpdate.data.entity.Cryptocurrency
import com.example.coinRankingUpdate.databinding.FragmentSearchBinding
import com.example.coinRankingUpdate.ui.doAfterTextChanged
import com.example.coinRankingUpdate.ui.gone
import com.example.coinRankingUpdate.ui.visible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding
    private val viewModel: SearchViewModel by viewModels()

    private lateinit var cryptoCryptoListAdapter: SearchCryptoListAdapter

    private var job: Job = Job()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        collectResult()
    }

    private fun initViews() {
        hideAll()
        sendSearchRequest()
        setupRecyclerViewCrypto()
    }

    private fun sendSearchRequest() {
        binding.etSearchField.doAfterTextChanged(viewLifecycleOwner, 1000) {
            val string = binding.etSearchField.text.toString().trim()
            if (string.isNotEmpty()) {
                startLoad()
                viewModel.search(string)
            } else {
                Toast.makeText(requireContext(), "no coin to show", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setupRecyclerViewCrypto() {
        cryptoCryptoListAdapter = SearchCryptoListAdapter(
            onItemClicked = {
                onItemClicked(it)
            }
        )
        with(binding.rvCryptocurrency) {
            setHasFixedSize(true)
            adapter = cryptoCryptoListAdapter
        }
    }

    private fun collectResult() {
        viewModel.cryptocurrenciesResource.observe(requireActivity()) { resource ->
            val data = resource.handle(
                tag = "CRYPTOCURRENCY_SEARCH",
                context = requireContext(),
                errMsg = "failed to search coins",
                startLoad = { startLoad() },
                endLoad = { endLoad() }
            )
            if (data.isNullOrEmpty()) {
                hideAll()
                cryptoCryptoListAdapter.submitList(emptyList())
            } else {
                showAll()
                val list = resource.data.orEmpty()
                cryptoCryptoListAdapter.submitList(list)
            }
        }
    }

    private fun onItemClicked(crypto: Cryptocurrency) {
        findNavController().navigate(
            SearchFragmentDirections.actionSearchFragmentToCryptocurrencyDetailFragment(
                crypto
            )
        )
    }

    private fun hideAll() {
        binding.tvCryptoTitle.gone()
        binding.rvCryptocurrency.gone()
    }

    private fun showAll() {
        binding.tvCryptoTitle.visible()
        binding.rvCryptocurrency.visible()
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