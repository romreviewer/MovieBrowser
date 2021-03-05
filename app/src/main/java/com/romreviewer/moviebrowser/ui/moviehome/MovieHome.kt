package com.romreviewer.moviebrowser.ui.moviehome

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import androidx.recyclerview.widget.GridLayoutManager
import com.romreviewer.moviebrowser.data.api.TheMovieDBClient
import com.romreviewer.moviebrowser.data.api.TheMovieDBInterface
import com.romreviewer.moviebrowser.data.repository.NetworkState
import com.romreviewer.moviebrowser.databinding.MovieHomeFragmentBinding
import com.yarolegovich.discretescrollview.DSVOrientation
import com.yarolegovich.discretescrollview.InfiniteScrollAdapter
import com.yarolegovich.discretescrollview.transform.ScaleTransformer

class MovieHome : Fragment() {

    companion object {
        fun newInstance() = MovieHome()
    }

    private lateinit var viewModel: MovieHomeViewModel
    private lateinit var binding: MovieHomeFragmentBinding
    private lateinit var movieRepository: MoviePagedListRepo
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val apiService: TheMovieDBInterface = TheMovieDBClient.getClient()

        movieRepository = MoviePagedListRepo(apiService)
        binding = MovieHomeFragmentBinding.inflate(inflater, container, false)
        viewModel = getViewModel()
        binding.vm = viewModel
        binding.lifecycleOwner = this
        uiFunction()
        return binding.root
    }

    private fun uiFunction() {

        val movieAdapter = MovieHomePopularList(requireContext())

        val gridLayoutManager = GridLayoutManager(requireContext(), 3)

        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                val viewType = movieAdapter.getItemViewType(position)
                return if (viewType == movieAdapter.MOVIE_VIEW_TYPE) 1
                else 3
            }
        }
        slider()
        binding.lowerRecycler.setHasFixedSize(true)
        binding.lowerRecycler.layoutManager = gridLayoutManager
        binding.lowerRecycler.adapter = movieAdapter
        binding.lowerRecycler.isNestedScrollingEnabled = false
        viewModel.moviePagedList.observe(viewLifecycleOwner, {
            movieAdapter.submitList(it)
        })
        viewModel.networkState.observe(viewLifecycleOwner, {
            binding.progressBarPopular.visibility =
                if (viewModel.listIsEmpty() && it == NetworkState.LOADING) View.VISIBLE else View.GONE
            binding.txtErrorPopular.visibility =
                if (viewModel.listIsEmpty() && it == NetworkState.ERROR) View.VISIBLE else View.GONE
            if (!viewModel.listIsEmpty()) {
                movieAdapter.setNetworkState(it)
            }
        })
    }
    private fun slider()
    {
        binding.itemPicker.setOrientation(DSVOrientation.HORIZONTAL)
        binding.itemPicker.adapter = InfiniteScrollAdapter.wrap(MoviePagerAdapter(viewModel.getData()))
        binding.itemPicker.isNestedScrollingEnabled = false
        binding.itemPicker.setItemTransformer(
            ScaleTransformer.Builder()
                .setMinScale(0.8f)
                .build()
        )
    }

    private fun getViewModel(): MovieHomeViewModel {
        return ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return MovieHomeViewModel(movieRepository) as T
            }
        })[MovieHomeViewModel::class.java]
    }
}