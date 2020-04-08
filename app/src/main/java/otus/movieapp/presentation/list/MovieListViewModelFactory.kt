package otus.movieapp.presentation.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import otus.movieapp.domain.repository.MovieRepository
import otus.movieapp.presentation.source.MovieDataSourceFactory

class MovieListViewModelFactory(
    private val dataSourceFactory: MovieDataSourceFactory
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(MovieDataSourceFactory::class.java)
            .newInstance(dataSourceFactory)
    }
}