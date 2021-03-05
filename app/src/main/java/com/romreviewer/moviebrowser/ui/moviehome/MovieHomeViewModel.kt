package com.romreviewer.moviebrowser.ui.moviehome

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.romreviewer.moviebrowser.data.model.Movie
import com.romreviewer.moviebrowser.data.model.MovieViewItem
import com.romreviewer.moviebrowser.data.repository.NetworkState
import io.reactivex.disposables.CompositeDisposable
import java.util.*

class MovieHomeViewModel(val movieListRepo: MoviePagedListRepo) : ViewModel() {
    private val compositeDisposable = CompositeDisposable()

    val  moviePagedList : LiveData<PagedList<Movie>> by lazy {
        movieListRepo.fetchLiveMoviePagedList(compositeDisposable)
    }

    val  networkState : LiveData<NetworkState> by lazy {
        movieListRepo.getNetworkState()
    }

    fun listIsEmpty(): Boolean {
        return moviePagedList.value?.isEmpty() ?: true
    }

    fun getData(): List<MovieViewItem> {
        return listOf<MovieViewItem>(
            MovieViewItem("/8tNX8s3j1O0eqilOQkuroRLyOZA.jpg"),
            MovieViewItem("/vQJ3yBdF91tzd73G8seL5bOxfvG.jpg"),
            MovieViewItem("/3ombg55JQiIpoPnXYb2oYdr6DtP.jpg"),
            MovieViewItem("/7prYzufdIOy1KCTZKVWpjBFqqNr.jpg"),
            MovieViewItem("/cjaOSjsjV6cl3uXdJqimktT880L.jpg"),
            MovieViewItem("/mGJuQwMq1bEboaVTqQAK4p4zQvC.jpg")

        )
    }
    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}