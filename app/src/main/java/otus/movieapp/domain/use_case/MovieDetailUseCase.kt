package otus.movieapp.domain.use_case

import otus.movieapp.domain.repository.MovieRepository

class MovieDetailUseCase(
    private val repository: MovieRepository
){
    fun getMovie(id: Int) = repository.getMovie(id)
}