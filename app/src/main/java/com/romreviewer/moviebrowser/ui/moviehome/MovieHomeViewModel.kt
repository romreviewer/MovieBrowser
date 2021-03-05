package com.romreviewer.moviebrowser.ui.moviehome

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.romreviewer.moviebrowser.data.model.Movie
import com.romreviewer.moviebrowser.data.repository.NetworkState
import io.reactivex.disposables.CompositeDisposable

class MovieHomeViewModel(val movieListRepo : MoviePagedListRepo) : ViewModel() {
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


    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}