package otus.movieapp.presentation.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import otus.movieapp.domain.repository.MovieRepository
import otus.movieapp.presentation.MovieState
import otus.movieapp.presentation.base.BaseViewModel

class MovieListViewModel(
    private val movieRepository: MovieRepository
) : BaseViewModel() {

    private val state = MutableLiveData<MovieState>()
    val liveData: LiveData<MovieState> = state

    fun getMovies(page: Int = 1) {
        addDisposable(
            movieRepository.getMovies(page)
                .subscribeOn(Schedulers.io())
                .map { pair -> MovieState.ResultList(totalPage = pair.first, movies = pair.second) }
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