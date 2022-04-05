package com.example.coinRankingUpdate.ui.cryptocurrency

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.coinRankingUpdate.R
import com.example.coinRankingUpdate.databinding.FragmentCryptocurrencyDetailBinding
import com.example.coinRankingUpdate.ui.gone
import com.example.coinRankingUpdate.ui.setSpinner
import com.example.coinRankingUpdate.ui.visible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CryptocurrencyDetailFragment : Fragment() {

    private lateinit var binding: FragmentCryptocurrencyDetailBinding
    private val args by navArgs<CryptocurrencyDetailFragmentArgs>()

    private val viewModel: CryptocurrencyDetailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCryptocurrencyDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.cryptocurrency = args.cryptocurrency

        setSpinner(
            requireActivity(),
            binding.spinnerTime,
            R.array.array_limitedTimes
        ) {
            viewModel.setTimePeriod(it)
        }

        setSpinner(
            requireActivity(),
            binding.spinnerComparison,
            R.array.array_comparisons
        ) { item -> binding.isBtc = item == "BTC" }

        collectResult()
    }

    private fun collectResult() {
        viewModel.setId(args.cryptocurrency.uuid)
        viewModel.cryptocurrencyResource.observe(viewLifecycleOwner) { response ->
            val data = response.handle(
                tag = "CRYPTOCURRENCY_DETAIL",
                context = requireContext(),
                errMsg = "failed to load cryptocurrency",
                startLoad = { startLoad() },
                endLoad = { endLoad() }
            )
            data?.let { binding.cryptocurrency = it }
        }
        viewModel.refresh()
    }

    private fun startLoad() {
        with(binding) {
            tvIntro.gone()
            tvIntroTitle.gone()
            progressbar.visible()
        }
    }

    private fun endLoad() {
        with(binding) {
            tvIntro.visible()
            tvIntroTitle.visible()
            progressbar.gone()
        }
    }
}