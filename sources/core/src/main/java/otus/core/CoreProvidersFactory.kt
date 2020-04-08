package otus.core

import otus.core_api.mediator.AppProvider
import otus.core_api.network.NetworkProvider
import otus.core_api.view_model.ViewModelsProvider
import otus.core_impl.DaggerNetworkComponent
import otus.core_impl.DaggerViewModelComponent

object CoreProvidersFactory {

    fun createNetworkBuilder(appProvider: AppProvider): NetworkProvider {
        return DaggerNetworkComponent.builder().appProvider(appProvider).build()
    }

    fun createViewModelBuilder(): ViewModelsProvider {
        return DaggerViewModelComponent.create()
    }
}