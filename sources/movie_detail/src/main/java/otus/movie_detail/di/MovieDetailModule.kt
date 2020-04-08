package otus.movie_detail.di

import android.util.Log
import androidx.lifecycle.ViewModel
import com.mobile.moviedatabase.features.movies.detail.MovieDetailViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import otus.movie_detail.repository.MovieDetailRepository
import otus.movie_detail.repository.MovieDetailRepositoryImpl
import javax.inject.Singleton

@Module
abstract class MovieDetailModule {

    @Binds
    @Singleton
    abstract fun bindsMovieRepository(movieRepository: MovieDetailRepositoryImpl): MovieDetailRepository

    @Module
    companion object {

        @Provides
        @Singleton
        @JvmStatic
        fun provideMovieListViewModel(
            map: @JvmSuppressWildcards MutableMap<Class<out ViewModel>, ViewModel>,
            movieRepository: MovieDetailRepository
        ): ViewModel = MovieDetailViewModel(movieRepository).also {
            map[MovieDetailViewModel::class.java] = it
            Log.d("vm_map_init", map.toString())
        }

        @Provides
        @Singleton
        @JvmStatic
        fun provideDummy(viewModel: ViewModel) = EagerTrigger()
    }
}

class EagerTrigger