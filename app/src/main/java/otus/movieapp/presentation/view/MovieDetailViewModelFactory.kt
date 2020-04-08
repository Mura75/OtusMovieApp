package otus.movieapp.presentation.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import otus.movieapp.domain.repository.MovieRepository
import otus.movieapp.domain.use_case.MovieDetailUseCase

class MovieDetailViewModelFactory(
    private val movieDetailUseCase: MovieDetailUseCase
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(MovieDetailUseCase::class.java)
            .newInstance(movieDetailUseCase)
    }
}