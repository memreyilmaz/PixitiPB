package com.payback.pixiti.ui.list

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
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.payback.pixiti.R
import com.payback.pixiti.databinding.FragmentImageListBinding
import com.payback.pixiti.model.Image
import com.payback.pixiti.ui.MainActivity
import com.payback.pixiti.utils.hideKeyboard
import com.payback.pixiti.utils.showAlertDialog
import com.payback.pixiti.utils.showIf
import com.payback.pixiti.utils.toast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ImageListFragment : Fragment() {

    private val imageViewModel by viewModels<ImageViewModel>()

    private var _binding: FragmentImageListBinding? = null
    private val binding get() = _binding!!
    private var currentQuery : String? = null

    private var searchJob: Job? = null

    private val listAdapter by lazy {
        ImageListAdapter().apply {
            onItemClickListener = { image ->
                image?.let {
                    showDetailNavigationDialog(image)
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

        imageViewModel.images.observe(viewLifecycleOwner) {
            listAdapter.submitData(viewLifecycleOwner.lifecycle, it)
        }
        setHasOptionsMenu(true)
    }

    override fun onResume() {
        super.onResume()
        (activity as MainActivity).supportActionBar?.show()
    }

    private fun searchImages(){
        searchJob?.cancel()
        searchJob = lifecycleScope.launch {
            imageViewModel.images.observe(viewLifecycleOwner) {
                listAdapter.submitData(viewLifecycleOwner.lifecycle, it)
            }
        }
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

    private fun showDetailNavigationDialog(image: Image) {
        context?.showAlertDialog(
                message = getString(R.string.desc_detail_navigation_info),
                negativeButtonText = getString(R.string.label_cancel),
                positiveButtonText = getString(R.string.label_go_to_detail),
                positiveButtonListener = {
                    val action = ImageListFragmentDirections.actionImageListFragmentToImageDetailFragment(image)
                    findNavController().navigate(action)
                }
        )
    }

    private fun showAboutFragment(){
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
                        currentQuery = searchQuery
                        imageViewModel.searchImages(searchQuery)
                       // searchImages(searchQuery)
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

    companion object {
        private const val LIST_GRID_COUNT = 2
    }
}