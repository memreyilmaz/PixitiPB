package com.payback.pixiti.ui.list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.payback.pixiti.databinding.FragmentImageListBinding
import com.payback.pixiti.utils.DEFAULT_QUERY
import com.payback.pixiti.utils.showIf
import com.payback.pixiti.utils.toast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ImageListFragment : Fragment() {

    private val imageViewModel by viewModels<ImageViewModel>()

    private var _binding: FragmentImageListBinding? = null
    private val binding get() = _binding!!

    private val listAdapter by lazy {
        ImageListAdapter().apply {
            onItemClickListener = { image ->
                image?.let {
                    val action = ImageListFragmentDirections.actionImageListFragmentToImageDetailFragment(image)
                    findNavController().navigate(action)
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentImageListBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(
            view: View, savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)
        initListAdapter()
        viewLifecycleOwner.lifecycleScope.launch {
            imageViewModel.searchImages(DEFAULT_QUERY).collectLatest {
                listAdapter.submitData(viewLifecycleOwner.lifecycle, it)
            }
        }
    }

    private fun initListAdapter() {
        binding.recyclerViewList.apply {
            setHasFixedSize(true)
            layoutManager =
                    StaggeredGridLayoutManager(LIST_GRID_COUNT, StaggeredGridLayoutManager.VERTICAL)
            adapter = listAdapter.withLoadStateHeaderAndFooter(
                    header = ImageListLoadStateAdapter().apply {
                        onRetryClickListener = { listAdapter.retry() }
                    },
                    footer = ImageListLoadStateAdapter().apply {
                        onRetryClickListener = { listAdapter.retry() }
                    }
            )
            isNestedScrollingEnabled = false
        }

        listAdapter.addLoadStateListener { loadState ->
            binding.apply {
                recyclerViewList.showIf(loadState.source.refresh is LoadState.NotLoading)
                progressBar.showIf(loadState.source.refresh is LoadState.Loading)
                buttonListMainRetry.showIf(loadState.source.refresh is LoadState.Error)
                textViewEmptySearchList.showIf(loadState.source.refresh is LoadState.NotLoading && loadState.append.endOfPaginationReached)
            }

            val errorState = loadState.source.append as? LoadState.Error
                    ?: loadState.source.prepend as? LoadState.Error
                    ?: loadState.append as? LoadState.Error
                    ?: loadState.prepend as? LoadState.Error
            errorState?.let {
                requireContext().applicationContext.toast("${it.error}")
            }
        }
        binding.buttonListMainRetry.setOnClickListener { listAdapter.retry() }
    }

    companion object {
        const val LIST_GRID_COUNT = 2
    }
}