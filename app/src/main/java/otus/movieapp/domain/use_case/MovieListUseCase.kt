package otus.movieapp.domain.use_case

import otus.movieapp.domain.repository.MovieRepository
import javax.inject.Inject

class MovieListUseCase @Inject constructor(
    private val repository: MovieRepository
) {

    fun getMovies(page: Int) = repository.getMovies(page)
}