package otus.movie_list.di

import android.util.Log
import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import otus.movie_list.repository.MovieRepository
import otus.movie_list.repository.MovieRepositoryImpl
import otus.movie_list.view.MovieListViewModel
import otus.movie_list.view.MovieViewModel
import otus.movieapp.data.network.MovieApi
import javax.inject.Singleton

@Module
abstract class MovieListModule {

    @Binds
    @Singleton
    abstract fun bindsMovieRepository(movieRepository: MovieRepositoryImpl): MovieRepository

    @Module
    companion object {

        @Provides
        @Singleton
        @JvmStatic
        fun provideMovieListViewModel(
            map: @JvmSuppressWildcards MutableMap<Class<out ViewModel>, ViewModel>,
            movieRepository: MovieRepository
        ): ViewModel = MovieListViewModel(movieRepository).also {
            map[MovieListViewModel::class.java] = it
            Log.d("vm_map_init", map.toString())
        }

        @Provides
        @Singleton
        @JvmStatic
        fun provideDummy(viewModel: ViewModel) = EagerTrigger()
    }
}

class EagerTrigger