package otus.movieapp.presentation.source

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import io.reactivex.disposables.CompositeDisposable
import otus.movieapp.domain.model.Movie
import otus.movieapp.domain.repository.MovieRepository


class MovieDataSourceFactory(
    private val repository: MovieRepository,
    private val compositeDisposable: CompositeDisposable
) : DataSource.Factory<Int, Movie>() {

    val movieLiveData = MutableLiveData<MovieDataSource>()

    private lateinit var movieDataSource: MovieDataSource

    override fun create(): DataSource<Int, Movie> {
        movieDataSource = MovieDataSource(repository, compositeDisposable)
        movieLiveData.postValue(movieDataSource)
        return movieDataSource
    }

    fun invalidate() {
        movieDataSource.invalidate()
    }
}