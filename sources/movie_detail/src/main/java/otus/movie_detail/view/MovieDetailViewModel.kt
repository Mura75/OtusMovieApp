package com.mobile.moviedatabase.features.movies.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import otus.core_api.base.BaseViewModel
import otus.core_api.dto.MovieData
import otus.movie_detail.repository.MovieDetailRepository
import otus.movie_detail.view.MovieState
import javax.inject.Inject

class MovieDetailViewModel @Inject constructor(
    private val movieDetailRepository: MovieDetailRepository
): BaseViewModel() {

    private val state = MutableLiveData<MovieState>()
    val liveData: LiveData<MovieState> = state

    fun getMovieDetail(movieId: Int) {
        addDisposable(
            movieDetailRepository.getMovie(movieId)
                .subscribeOn(Schedulers.io())
                .map { movie -> MovieState.Result(movie) }
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { state.value = MovieState.ShowLoading }
                .doFinally { state.value = MovieState.HideLoading }
                .subscribe(
                    { result -> state.value = result },
                    { error -> state.value = MovieState.Error(error.localizedMessage) }
                )
        )
    }
}