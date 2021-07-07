package otus.movie_list.view

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import androidx.paging.RxPagedListBuilder
import io.reactivex.BackpressureStrategy
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import otus.core_api.base.BaseViewModel
import otus.core_api.dto.MovieData
import otus.movie_list.repository.MovieRepository
import otus.movie_list.source.MovieDataSource
import otus.movie_list.source.MovieDataSourceFactory
import javax.inject.Inject

class MovieListViewModel @Inject constructor(
    private val movieRepository: MovieRepository
) : BaseViewModel() {

    private lateinit var movieDataSourceFactory: MovieDataSourceFactory

    private val pagedList = MutableLiveData<PagedList<MovieData>>()
    val pagedListLiveData: LiveData<PagedList<MovieData>> = pagedList

    lateinit var liveData: LiveData<MovieState>

    init {
        // Comment for checking docker.
        getMovies()
    }

    fun getMovies() {
        val pagedListConfig = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(MovieDataSource.PAGE_SIZE * 2)
            .setPageSize(MovieDataSource.PAGE_SIZE)
            .setPrefetchDistance(10)
            .build()

        movieDataSourceFactory = MovieDataSourceFactory(movieRepository, compositeDisposable)

        addDisposable(
            RxPagedListBuilder(movieDataSourceFactory, pagedListConfig)
                .setFetchScheduler(Schedulers.io())
                .buildFlowable(BackpressureStrategy.BUFFER)
                .cache()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { result ->
                        pagedList.postValue(result)
                    },
                    { error -> Log.d("paged_data_value_rx", error.toString()) }
                )
        )
        liveData = Transformations.switchMap(
            movieDataSourceFactory.movieLiveData,
            MovieDataSource::getStateMutableLiveData
        )
    }

    fun clear() = movieDataSourceFactory.invalidate()
}