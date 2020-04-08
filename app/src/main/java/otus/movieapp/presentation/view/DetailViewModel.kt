package otus.movieapp.presentation.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import otus.movieapp.domain.repository.MovieRepository
import otus.movieapp.domain.use_case.MovieDetailUseCase
import otus.movieapp.presentation.MovieState
import otus.movieapp.presentation.base.BaseViewModel
import javax.inject.Inject

class DetailViewModel @Inject constructor(
    private val movieDetailUseCase: MovieDetailUseCase
) : BaseViewModel() {

    private val state = MutableLiveData<MovieState>()
    val liveData: LiveData<MovieState> = state

    fun getMovie(id: Int) {
        addDisposable(
            movieDetailUseCase.getMovieDetail(id = id)
                .subscribeOn(Schedulers.io())
                .map { movie -> MovieState.ResultItem(movie) }
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