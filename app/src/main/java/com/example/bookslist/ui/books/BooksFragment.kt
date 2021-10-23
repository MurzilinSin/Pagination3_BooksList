package com.example.bookslist.ui.books

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState
import com.example.bookslist.databinding.FragmentBooksBinding
import com.example.bookslist.ui.books.adapter.Adapter
import com.example.bookslist.ui.books.adapter.BooksLoadStateAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

@ExperimentalPagingApi
class BooksFragment : Fragment() {
    private var _binding: FragmentBooksBinding? = null
    private val binding get() = _binding!!
    @ExperimentalPagingApi
    private val viewModel by inject<BooksVM>()
    private val adapter by lazy {
        Adapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBooksBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter(true)
        CoroutineScope(Dispatchers.IO).launch {
            viewModel.books.collectLatest {
                adapter.submitData(it)
            }
        }
    }

    private fun initAdapter(isMediator: Boolean = false) {
        binding.recycler.adapter = adapter.withLoadStateHeaderAndFooter(
            header = BooksLoadStateAdapter { adapter.retry() },
            footer = BooksLoadStateAdapter { adapter.retry() }
        )
        adapter.addLoadStateListener { loadState ->
            val refreshState =
                if (isMediator) {
                    loadState.mediator?.refresh
                } else {
                    loadState.source.refresh
                }
            binding.recycler.isVisible = refreshState is LoadState.NotLoading
            binding.progressBar.isVisible = refreshState is LoadState.Loading
        }
    }

    companion object {
        fun newInstance() = BooksFragment()
    }
}