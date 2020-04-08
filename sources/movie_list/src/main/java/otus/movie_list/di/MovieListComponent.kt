package otus.movie_list.di

import android.app.Activity
import android.app.Application
import dagger.Component
import otus.core.CoreProvidersFactory
import otus.core_api.mediator.ProvidersFacade
import otus.core_api.network.NetworkProvider
import otus.core_api.view_model.ViewModelsProvider
import otus.movie_list.view.MovieListFragment
import javax.inject.Singleton

@Singleton
@Component(
    modules = [MovieListModule::class],
    dependencies = [ProvidersFacade::class, ViewModelsProvider::class]
)
interface MovieListComponent : ViewModelsProvider {

    companion object {
        fun create(providersFacade: ProvidersFacade): MovieListComponent {
            return DaggerMovieListComponent
                .builder()
                .viewModelsProvider(CoreProvidersFactory.createViewModelBuilder())
                .providersFacade(providersFacade)
                .build()
        }
    }

    fun inject(movieListFragment: MovieListFragment)
}