package otus.movie_detail.repository

import io.reactivex.Single
import otus.core_api.dto.MovieData
import otus.movieapp.data.network.MovieApi
import javax.inject.Inject

class MovieDetailRepositoryImpl @Inject constructor(
    private val movieApi: MovieApi
) : MovieDetailRepository {

    override fun getMovie(movieId: Int): Single<MovieData> {
        return movieApi.getMovie(movieId)
            .flatMap { response ->
                if (response.isSuccessful) {
                    Single.just(response.body())
                } else {
                    Single.error(Throwable(""))
                }
            }
    }
}