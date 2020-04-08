package otus.movieapp.domain.use_case

import otus.movieapp.domain.repository.MovieRepository
import javax.inject.Inject

class MovieDetailUseCase @Inject constructor(
    private val repository: MovieRepository
) {

    fun getMovieDetail(id: Int) = repository.getMovie(id)
}