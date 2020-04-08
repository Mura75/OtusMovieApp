package otus.movieapp.presentation.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import otus.movieapp.domain.repository.MovieRepository
import otus.movieapp.domain.use_case.MovieListUseCase
import otus.movieapp.presentation.source.MovieDataSourceFactory

class MovieListViewModelFactory(
    private val movieListUseCase: MovieListUseCase
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(MovieListUseCase::class.java)
            .newInstance(movieListUseCase)
    }
}