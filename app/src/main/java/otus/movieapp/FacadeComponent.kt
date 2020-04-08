package otus.movieapp

import android.app.Application
import dagger.Component
import otus.core.CoreProvidersFactory
import otus.core_api.mediator.AppProvider
import otus.core_api.mediator.ProvidersFacade
import otus.core_api.network.NetworkProvider

@Component(
    dependencies = [AppProvider::class, NetworkProvider::class],
    modules = [MediatorsBindings::class]
)
interface FacadeComponent : ProvidersFacade {

    companion object {

        fun init(application: Application): FacadeComponent =
            DaggerFacadeComponent.builder()
                .appProvider(AppComponent.create(application))
                .networkProvider(CoreProvidersFactory.createNetworkBuilder(AppComponent.create(application)))
                .build()
    }

    fun inject(app: MovieApp)
}