package com.example.coinRankingUpdate.ui.cryptocurrency

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.annotation.ArrayRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.coinRankingUpdate.R
import com.example.coinRankingUpdate.databinding.FragmentCryptocurrencyDetailBinding
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

        setValue()
    }

    private fun setSpinner(
        context: Context,
        spinner: android.widget.Spinner,
        @ArrayRes res: Int,
        onItemSelected: (String) -> Unit
    ) {
        val adapter: ArrayAdapter<CharSequence> = ArrayAdapter.createFromResource(
            context,
            res,
            R.layout.spinner_header
        )
        adapter.setDropDownViewResource(R.layout.spinner_list_item)
        spinner.adapter = adapter
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                pos: Int,
                id: Long
            ) {
                onItemSelected(parent.getItemAtPosition(pos).toString())
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
    }

    fun setValue() {
        viewModel.setId(args.cryptocurrency.uuid)
        viewModel.cryptocurrencyResource.observe(viewLifecycleOwner) { response ->
            //we can use handle method to handle resource or handle it manually
//            when (response) {
//                is Resource.Success -> {
//                    binding.cryptocurrency = response.data
//                    endLoad()
//                }
//                is Resource.Loading -> {
//                    startLoad()
//                }
//                is Resource.Error -> {
//                    Toast.makeText(requireContext(), "get information failed!", Toast.LENGTH_SHORT)
//                        .show()
//                    endLoad()
//                }
//            }
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
            tvIntro.visibility = View.GONE
            tvIntroTitle.visibility = View.GONE
            progressbar.visibility = View.VISIBLE
        }
    }

    private fun endLoad() {
        with(binding) {
            tvIntro.visibility = View.VISIBLE
            tvIntroTitle.visibility = View.VISIBLE
            progressbar.visibility = View.GONE
        }
    }
}