package otus.movie_list.repository

import android.util.Log
import io.reactivex.Single
import otus.core_api.dto.MovieData
import otus.movieapp.data.network.MovieApi
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val movieApi: MovieApi
) : MovieRepository {

    override fun getMovies(page: Int): Single<Pair<Int, List<MovieData>>> {
        return movieApi.getPopularMovies(page)
            .flatMap { response ->
                if (response.isSuccessful) {
                    val pages = response.body()?.page ?: 0
                    val list = response.body()?.results ?: emptyList()
                    val pair = Pair(pages, list)
                    Single.just(pair)
                } else {
                    Single.error(Throwable("movie list error"))
                }
            }
    }
}