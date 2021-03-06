package com.example.pixiti.ui.list

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.pixiti.R
import com.example.pixiti.databinding.FragmentImageListBinding
import com.example.pixiti.model.Image
import com.example.pixiti.ui.MainActivity
import com.example.pixiti.utils.ErrorUtil
import com.example.pixiti.utils.hideKeyboard
import com.example.pixiti.utils.showAlertDialog
import com.example.pixiti.utils.showIf
import dagger.hilt.android.AndroidEntryPoint

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

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        _binding = FragmentImageListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
            view: View, savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)
        initListAdapter()

        imageViewModel.images.observe(viewLifecycleOwner) {
            listAdapter.submitData(viewLifecycleOwner.lifecycle, it)
        }
        setHasOptionsMenu(true)
    }

    override fun onResume() {
        super.onResume()
        (activity as MainActivity).supportActionBar?.show()
    }

    @SuppressLint("ResourceType")
    private fun initListAdapter() {
        binding.recyclerViewList.apply {
            setHasFixedSize(true)
            val columns = resources.getInteger(R.integer.list_grid_count)
            layoutManager =
                    StaggeredGridLayoutManager(columns, StaggeredGridLayoutManager.VERTICAL)
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
                textViewEmptySearchList.showIf(loadState.source.refresh is LoadState.NotLoading
                        && loadState.append.endOfPaginationReached && listAdapter.itemCount < 1)
            }

            val errorState = loadState.source.append as? LoadState.Error
                    ?: loadState.source.prepend as? LoadState.Error
                    ?: loadState.append as? LoadState.Error
                    ?: loadState.prepend as? LoadState.Error
                    ?: loadState.source.refresh as? LoadState.Error
            errorState?.let {
                ErrorUtil.handleError(it,requireContext())
            }
        }
        binding.buttonListMainRetry.setOnClickListener { listAdapter.retry() }
    }

    private fun showAboutFragment() {
        val aboutFragment: DialogFragment = AboutFragment.newInstance()
        aboutFragment.show(childFragmentManager, AboutFragment.TAG)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_image_list, menu)
        val searchItem = menu.findItem(R.id.item_search)
        val searchView = searchItem.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                view?.let {
                    activity?.hideKeyboard(it)
                    it.clearFocus()
                }
                query?.let {
                    if (it.trim().isNotEmpty()) {
                        val searchQuery = query.trim()
                        binding.recyclerViewList.scrollToPosition(0)
                        imageViewModel.searchImages(searchQuery)
                    }
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }

        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_info -> {
                showAboutFragment()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}