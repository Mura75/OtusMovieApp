package otus.movie_detail.repository

import io.reactivex.Single
import otus.core_api.dto.MovieData

interface MovieDetailRepository {
    fun getMovie(movieId: Int): Single<MovieData>
}