package com.mobile.data.module
import dagger.Module
import dagger.Provides
import otus.movieapp.data.MovieMapper
import otus.movieapp.data.network.MovieApi
import otus.movieapp.data.repository.MovieRepositoryImpl
import otus.movieapp.domain.repository.MovieRepository
import javax.inject.Singleton

@Module(includes = [NetworkModule::class])
class RepositoryModule {

    @Singleton
    @Provides
    fun provideMovieRepository(
        movieApi: MovieApi,
        movieMapper: MovieMapper
    ): MovieRepository = MovieRepositoryImpl(movieApi = movieApi, movieMapper = movieMapper)

}
