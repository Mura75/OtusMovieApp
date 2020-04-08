package otus.core_impl

import dagger.Component
import otus.core_api.view_model.ViewModelsProvider
import javax.inject.Singleton

@Singleton
@Component(modules = [ViewModelModule::class])
interface ViewModelComponent : ViewModelsProvider