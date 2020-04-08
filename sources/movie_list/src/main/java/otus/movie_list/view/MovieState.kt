package otus.movie_list.view

import otus.core_api.dto.MovieData

sealed class MovieState {
    object ShowLoading : MovieState()
    object HideLoading : MovieState()
    data class ResultList(val totalPage: Int, val movies: List<MovieData>) : MovieState()
    data class ResultItem(val movie: MovieData) : MovieState()
    data class Error(val error: String?) : MovieState()
}