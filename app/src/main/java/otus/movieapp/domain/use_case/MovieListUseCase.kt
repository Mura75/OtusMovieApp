package otus.movieapp.domain.use_case

import otus.movieapp.domain.repository.MovieRepository

class MovieListUseCase(
    private val repository: MovieRepository
) {

    fun getMovies(page: Int) = repository.getMovies(page)
}