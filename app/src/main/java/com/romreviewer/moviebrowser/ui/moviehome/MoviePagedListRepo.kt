package com.romreviewer.moviebrowser.ui.moviehome

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.romreviewer.moviebrowser.data.api.POST_PER_PAGE
import com.romreviewer.moviebrowser.data.api.TheMovieDBInterface
import com.romreviewer.moviebrowser.data.model.Movie
import com.romreviewer.moviebrowser.data.repository.MovieDataSource
import com.romreviewer.moviebrowser.data.repository.MovieDataSourceFactory
import com.romreviewer.moviebrowser.data.repository.NetworkState
import io.reactivex.disposables.CompositeDisposable

class MoviePagedListRepo (private val apiService : TheMovieDBInterface) {

    lateinit var moviePagedList: LiveData<PagedList<Movie>>
    lateinit var moviesDataSourceFactory: MovieDataSourceFactory

    fun fetchLiveMoviePagedList (compositeDisposable: CompositeDisposable) : LiveData<PagedList<Movie>> {
        moviesDataSourceFactory = MovieDataSourceFactory(apiService, compositeDisposable)

        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(POST_PER_PAGE)
            .build()

        moviePagedList = LivePagedListBuilder(moviesDataSourceFactory, config).build()

        return moviePagedList
    }

    fun getNetworkState(): LiveData<NetworkState> {
        return Transformations.switchMap<MovieDataSource, NetworkState>(
            moviesDataSourceFactory.moviesLiveDataSource, MovieDataSource::networkState)
    }
}