package otus.core_api.mediator

interface MediatorsProvider {

    fun provideMainMediator(): MainMediator

    fun provideMovieListMediator(): MovieListMediator

    fun provideMovieMovieDetailMediator(): MovieDetailMediator
}