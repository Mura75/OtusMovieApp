package otus.movieapp

import android.app.Application
import otus.core_api.mediator.AppWithFacade
import otus.core_api.mediator.ProvidersFacade

class MovieDBApp : Application(), AppWithFacade {

    companion object {
        private var facadeComponent: FacadeComponent? = null
    }

    override fun onCreate() {
        super.onCreate()
    }

    override fun getFacade(): ProvidersFacade {
        TODO("Not yet implemented")
    }
}