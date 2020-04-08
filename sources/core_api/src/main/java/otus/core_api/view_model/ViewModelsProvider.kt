package otus.core_api.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

interface ViewModelsProvider {

    fun provideMap(): @JvmSuppressWildcards MutableMap<Class<out ViewModel>, ViewModel>

    fun provideViewModel(): ViewModelProvider.Factory
}