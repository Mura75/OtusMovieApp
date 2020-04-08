package otus.core_api.mediator

import otus.core_api.network.NetworkProvider

interface ProvidersFacade : MediatorsProvider, NetworkProvider, AppProvider