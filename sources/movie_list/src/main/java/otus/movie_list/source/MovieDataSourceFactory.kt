package otus.movie_list.source

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import io.reactivex.disposables.CompositeDisposable
import otus.core_api.dto.MovieData
import otus.movie_list.repository.MovieRepository


class MovieDataSourceFactory(
    private val movieRepository: MovieRepository,
    private val compositeDisposable: CompositeDisposable
) : DataSource.Factory<Int, MovieData>() {

    val movieLiveData = MutableLiveData<MovieDataSource>()

    private lateinit var movieDataSource: MovieDataSource

    override fun create(): DataSource<Int, MovieData> {
        movieDataSource = MovieDataSource(movieRepository, compositeDisposable)
        movieLiveData.postValue(movieDataSource)
        return movieDataSource
    }

    fun invalidate() {
        movieDataSource.invalidate()
    }
}