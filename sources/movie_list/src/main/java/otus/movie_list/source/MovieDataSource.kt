package otus.movie_list.source

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import otus.core_api.dto.MovieData
import otus.movie_list.repository.MovieRepository
import otus.movie_list.view.MovieState


class MovieDataSource(
    private val repository: MovieRepository,
    private val compositeDisposable: CompositeDisposable
) : PageKeyedDataSource<Int, MovieData>() {

    companion object {
        const val PAGE_SIZE = 20
        private const val FIRST_PAGE = 1
    }

    private val stateMutableLiveData = MutableLiveData<MovieState>()

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, MovieData>
    ) {
        compositeDisposable.add(
            repository.getMovies(page = FIRST_PAGE)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { stateMutableLiveData.postValue(MovieState.ShowLoading) }
                .doFinally { stateMutableLiveData.postValue(MovieState.HideLoading) }
                .subscribe(
                    { result ->
                        callback.onResult(result.second, null, FIRST_PAGE + 1)
                    },
                    { error ->
                        Log.d("paged_data_value_init", error.toString())
                    }
                )
        )
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, MovieData>) {
        compositeDisposable.add(
            repository.getMovies(params.key)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { stateMutableLiveData.postValue(MovieState.ShowLoading) }
                .doFinally { stateMutableLiveData.postValue(MovieState.HideLoading) }
                .subscribe(
                    { pair ->
                        Log.d("paged_data_value_after", pair.toString())
                        val key = if (pair.second.size > 0) params.key + 1 else null
                        callback.onResult(pair.second, key)
                    },
                    { error ->
                        Log.d("paged_data_value", error.toString())
                    }
                )
        )
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, MovieData>) {

    }

    fun getStateMutableLiveData() = stateMutableLiveData
}