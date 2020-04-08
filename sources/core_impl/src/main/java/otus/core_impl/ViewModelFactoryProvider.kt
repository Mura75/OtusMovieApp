package otus.core_impl

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject

class ViewModelFactoryProvider
@Inject constructor(
    private val creators: @JvmSuppressWildcards MutableMap<Class<out ViewModel>, ViewModel>
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        Log.d("vm_map_creators", creators.toString())
        val found = creators.entries.find {
            modelClass.isAssignableFrom(it.key)
        }
        Log.d("vm_map_found", found.toString())
        val creator = found?.value
            ?: throw ClassNotFoundException("no model provided with for ${modelClass.simpleName}")
        return creator as T
    }
}