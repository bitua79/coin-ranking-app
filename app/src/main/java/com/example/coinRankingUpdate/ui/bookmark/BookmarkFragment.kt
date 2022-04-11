package com.example.coinRankingUpdate.ui.bookmark

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.coinRankingUpdate.data.entity.BookmarkEntity
import com.example.coinRankingUpdate.data.entity.CryptocurrencyEntity
import com.example.coinRankingUpdate.databinding.FragmentBookmarkBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BookmarkFragment : Fragment() {

    private lateinit var binding: FragmentBookmarkBinding
    private val bookmarkViewModel: BookmarkViewModel by activityViewModels()
    private lateinit var listAdapter: BookmarkListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBookmarkBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        setupAdapter()
        setupRecyclerview()
        bookmarkViewModel.bookmarkedCryptocurrencies.observe(viewLifecycleOwner){
            listAdapter.submitList(it.orEmpty())
        }
        bookmarkViewModel.refresh()
    }

    private fun setupAdapter() {
        listAdapter = BookmarkListAdapter(
            onItemClicked = {
                onItemClicked(it)
            },
            onItemUnBookmarked = {
                onItemUnBookmarked(it)
            }
        )
    }

    private fun onItemClicked(entity: CryptocurrencyEntity) {
        findNavController().navigate(
            BookmarkFragmentDirections.actionBookmarkFragmentToCryptocurrencyDetailFragment(entity)
        )
    }

    private fun onItemUnBookmarked(bookmark: BookmarkEntity) {
        bookmarkViewModel.unBookmark(bookmark.uuid)
    }

    private fun setupRecyclerview() {
        with(binding.rvCryptocurrencies) {
            setHasFixedSize(true)
            adapter = listAdapter
        }
    }

}