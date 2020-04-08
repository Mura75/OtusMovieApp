package otus.movie_detail.di

import dagger.Component
import otus.core.CoreProvidersFactory
import otus.core_api.mediator.ProvidersFacade
import otus.core_api.view_model.ViewModelsProvider
import otus.movie_detail.view.MovieDetailActivity
import javax.inject.Singleton

@Singleton
@Component(
    modules = [MovieDetailModule::class],
    dependencies = [ProvidersFacade::class, ViewModelsProvider::class]
)
interface MovieDetailComponent : ViewModelsProvider {

    companion object {
        fun create(providersFacade: ProvidersFacade): MovieDetailComponent {
            return DaggerMovieDetailComponent
                .builder()
                .viewModelsProvider(CoreProvidersFactory.createViewModelBuilder())
                .providersFacade(providersFacade)
                .build()
        }
    }

    fun inject(movieDetailActivity: MovieDetailActivity)
}