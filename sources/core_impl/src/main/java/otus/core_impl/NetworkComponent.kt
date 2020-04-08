package otus.core_impl

import dagger.Component
import otus.core_api.mediator.AppProvider
import otus.core_api.network.NetworkProvider
import javax.inject.Singleton

@Singleton
@Component(
    dependencies = [AppProvider::class],
    modules = [NetworkModule::class]
)
interface NetworkComponent : NetworkProvider