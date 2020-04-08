package otus.movie_list.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import io.reactivex.disposables.CompositeDisposable
import otus.core_api.base.BaseViewModel
import otus.core_api.dto.MovieData
import otus.movie_list.repository.MovieRepository
import otus.movie_list.source.MovieDataSource
import otus.movie_list.source.MovieDataSourceFactory
import javax.inject.Inject

class MovieListViewModel @Inject constructor(
    private val movieRepository: MovieRepository
) : ViewModel() {

    val pagedListLiveData : LiveData<PagedList<MovieData>>
    val liveData: LiveData<MovieState>

    private val compositeDisposable = CompositeDisposable()

    private val movieDataSourceFactory: MovieDataSourceFactory

    init {
        val pagedListConfig = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(MovieDataSource.PAGE_SIZE * 2)
            .setPageSize(MovieDataSource.PAGE_SIZE)
            .setPrefetchDistance(10)
            .build()

        movieDataSourceFactory = MovieDataSourceFactory(movieRepository, compositeDisposable)
        pagedListLiveData = LivePagedListBuilder(movieDataSourceFactory, pagedListConfig)
            .build()

        liveData = Transformations.switchMap(
            movieDataSourceFactory.movieLiveData,
            MovieDataSource::getStateMutableLiveData
        )
    }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }

    fun clear() = movieDataSourceFactory.invalidate()
}