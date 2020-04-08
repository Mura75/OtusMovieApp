package otus.movieapp

import dagger.Binds
import dagger.Module
import dagger.Reusable
import otus.core_api.mediator.MainMediator
import otus.core_api.mediator.MovieListMediator
import otus.main.navigation.MainMediatorImpl
import otus.movie_list.MovieListMediatorImpl

@Module
interface MediatorsBindings {

    @Binds
    @Reusable
    fun bindsMovieListMediator(mediator: MovieListMediatorImpl): MovieListMediator

    @Binds
    @Reusable
    fun bindsMainMediator(mediator: MainMediatorImpl): MainMediator

}