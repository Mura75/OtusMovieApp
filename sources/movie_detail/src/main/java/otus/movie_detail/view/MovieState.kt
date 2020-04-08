package otus.movie_detail.view

import otus.core_api.dto.MovieData

sealed class MovieState() {
    object ShowLoading: MovieState()
    object HideLoading: MovieState()
    data class Result(val movie: MovieData): MovieState()
    data class Error(val error: String?): MovieState()
    data class IntError(val error: Int): MovieState()
}