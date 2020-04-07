package otus.movieapp.presentation

import otus.movieapp.domain.model.Movie

sealed class MovieState {
    object ShowLoading : MovieState()
    object HideLoading : MovieState()
    data class ResultList(val totalPage: Int, val movies: List<Movie>) : MovieState()
    data class ResultItem(val movie: Movie) : MovieState()
    data class Error(val error: String?) : MovieState()
}