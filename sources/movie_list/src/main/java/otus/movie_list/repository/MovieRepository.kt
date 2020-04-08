package otus.movie_list.repository

import io.reactivex.Single
import otus.core_api.dto.MovieData

interface MovieRepository {
    fun getMovies(page: Int): Single<Pair<Int, List<MovieData>>>
    fun getMovie(movieId: Int): Single<MovieData>
}