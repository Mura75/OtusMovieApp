package otus.movieapp.domain.repository

import io.reactivex.Single
import otus.movieapp.domain.model.Movie

interface MovieRepository {
    fun getMovies(page: Int): Single<Pair<Int, List<Movie>>>
    fun getMovie(movieId: Int): Single<Movie>
}