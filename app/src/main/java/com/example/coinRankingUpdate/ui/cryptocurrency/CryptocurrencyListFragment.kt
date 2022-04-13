package com.example.coinRankingUpdate.ui.cryptocurrency

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.coinRankingUpdate.R
import com.example.coinRankingUpdate.core.entity.OrderDirection
import com.example.coinRankingUpdate.core.utils.initRecyclerViewAdapterDataObserver
import com.example.coinRankingUpdate.core.utils.isMoved
import com.example.coinRankingUpdate.data.entity.BookmarkEntity
import com.example.coinRankingUpdate.data.entity.CryptocurrencyEntity
import com.example.coinRankingUpdate.databinding.FragmentCryptocurrencyListBinding
import com.example.coinRankingUpdate.ui.bookmark.BookmarkViewModel
import com.example.coinRankingUpdate.ui.gone
import com.example.coinRankingUpdate.ui.setSpinner
import com.example.coinRankingUpdate.ui.showSystemUI
import com.example.coinRankingUpdate.ui.visible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CryptocurrencyListFragment : Fragment() {

    private lateinit var binding: FragmentCryptocurrencyListBinding
    private lateinit var listAdapter: CryptocurrencyListAdapter

    private val cryptocurrencyListViewModel: CryptocurrencyListViewModel by viewModels()
    private val bookmarkViewModel: BookmarkViewModel by activityViewModels()

    private lateinit var adapterDataObserver: RecyclerView.AdapterDataObserver

    private var isPriceAsc = false
    private var isMarketCapAsc = false

    private var ascIcon: Drawable? = null
    private var descIcon: Drawable? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCryptocurrencyListBinding.inflate(inflater, container, false)
        showSystemUI()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        collectResult()
    }

    private fun initViews() {
        ascIcon = ContextCompat.getDrawable(requireContext(), R.drawable.ic_arrow_up)
        descIcon = ContextCompat.getDrawable(requireContext(), R.drawable.ic_arrow_down)
        setupAdapter()
        setupRecyclerview()
        setupSwipeRefresh()

        setUpChips()
        setSpinner(
            requireContext(),
            binding.spinnerTime,
            R.array.times
        ) {
            cryptocurrencyListViewModel.setTimePeriod(it)
        }
    }

    private fun setupAdapter() {
        listAdapter = CryptocurrencyListAdapter(
            onItemClicked = {
                onItemClicked(it)
            },
            onItemBookmarked = {
                onItemBookmarked(it)
            },
            onItemUnBookmarked = {
                onItemUnBookmarked(it)
            }
        )
    }

    private fun onItemClicked(crypto: CryptocurrencyEntity) {
        findNavController().navigate(
            CryptocurrencyListFragmentDirections.actionCryptocurrencyListFragmentToCryptocurrencyDetailFragment(
                crypto
            )
        )
    }

    private fun onItemBookmarked(crypto: CryptocurrencyEntity) {
        bookmarkViewModel.bookmark(BookmarkEntity(crypto.uuid))
    }

    private fun onItemUnBookmarked(crypto: CryptocurrencyEntity) {
        bookmarkViewModel.unBookmark(crypto.uuid)
    }

    private fun setupRecyclerview() {
        with(binding.rvCryptocurrency) {
            setHasFixedSize(true)
            adapter = listAdapter

            adapterDataObserver = initRecyclerViewAdapterDataObserver { change ->
                val scrollToTop = isMoved(
                    layoutManager,
                    change
                )
                if (scrollToTop) {
                    scrollToPosition(0)
                }
            }
            listAdapter.registerAdapterDataObserver(adapterDataObserver)
        }
    }

    private fun setupSwipeRefresh() {
        binding.srlRefresh.setOnRefreshListener {
            cryptocurrencyListViewModel.refresh()
        }
    }

    private fun setUpChips() {
        with(binding.chipPrice) {
            setOnClickListener {
                isPriceAsc = !isPriceAsc
                closeIcon = if (isPriceAsc) {
                    cryptocurrencyListViewModel.setPriceFilter(OrderDirection.ASC)
                    ascIcon
                } else {
                    cryptocurrencyListViewModel.setPriceFilter(OrderDirection.DESC)
                    descIcon
                }
            }
        }
        with(binding.chipMarketCap) {
            setOnClickListener {
                isMarketCapAsc = !isMarketCapAsc
                closeIcon = if (isMarketCapAsc) {
                    cryptocurrencyListViewModel.setMarketCapFilter(OrderDirection.ASC)
                    ascIcon
                } else {
                    cryptocurrencyListViewModel.setMarketCapFilter(OrderDirection.DESC)
                    descIcon
                }
            }
        }
    }

    private fun collectResult() {
        cryptocurrencyListViewModel.cryptocurrenciesResource.observe(viewLifecycleOwner) { resource ->
            val data = resource.handle(
                tag = "CRYPTOCURRENCY_LIST",
                context = requireContext(),
                errMsg = "failed to load cryptocurrencies",
                startLoad = { startLoad() },
                endLoad = { endLoad() }
            )
            listAdapter.submitList(data.orEmpty())
        }
        cryptocurrencyListViewModel.refresh()
    }

    private fun startLoad() {
        with(binding) {
            srlRefresh.isRefreshing = true
            rvCryptocurrency.gone()
        }
    }

    private fun endLoad() {
        with(binding) {
            rvCryptocurrency.visible()
            srlRefresh.isRefreshing = false
        }
    }
}