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
import androidx.navigation.fragment.navArgs
import com.example.coinRankingUpdate.R
import com.example.coinRankingUpdate.databinding.FragmentCryptocurrencyDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CryptocurrencyDetailFragment : Fragment() {

    private lateinit var binding: FragmentCryptocurrencyDetailBinding
    private val args by navArgs<CryptocurrencyDetailFragmentArgs>()

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
        setupSpinners()
    }

    private fun setupSpinners() {
        setSpinner(
            requireActivity(),
            binding.spinnerCryptoTime,
            R.array.array_limitedTimes
        ) {
            //TODO:filter
        }

        setSpinner(
            requireActivity(),
            binding.spinnerCryptoComparison,
            R.array.array_comparisons
        ) { item -> binding.isBtc = item == "BTC" }
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

}