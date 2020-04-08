package otus.movieapp.presentation.list

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import io.reactivex.schedulers.Schedulers
import otus.movieapp.presentation.source.MovieDataSource
import otus.movieapp.presentation.source.MovieDataSourceFactory
import otus.movieapp.domain.model.Movie
import otus.movieapp.domain.repository.MovieRepository
import otus.movieapp.domain.use_case.MovieListUseCase
import otus.movieapp.presentation.MovieState
import otus.movieapp.presentation.base.BaseViewModel

class MovieListViewModel(
    private val movieListUseCase: MovieListUseCase
) : BaseViewModel() {

    val pagedListLiveData : LiveData<PagedList<Movie>>
    val liveData: LiveData<MovieState>

    private val movieDataSourceFactory: MovieDataSourceFactory

    init {
        val pagedListConfig = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(MovieDataSource.PAGE_SIZE * 2)
            .setPageSize(MovieDataSource.PAGE_SIZE)
            .setPrefetchDistance(10)
            .build()
        movieDataSourceFactory = MovieDataSourceFactory(movieListUseCase, compositeDisposable)

        pagedListLiveData = LivePagedListBuilder(movieDataSourceFactory, pagedListConfig)
            .build()

        liveData = Transformations.switchMap(
            movieDataSourceFactory.movieLiveData,
            MovieDataSource::getStateMutableLiveData
        )
    }

    fun clear() = movieDataSourceFactory.invalidate()
}