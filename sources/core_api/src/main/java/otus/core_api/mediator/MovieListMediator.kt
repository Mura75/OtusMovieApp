package otus.core_api.mediator

import androidx.annotation.IdRes
import androidx.fragment.app.FragmentManager

interface MovieListMediator {

    fun startMovieListScreen(@IdRes containerId: Int, fragmentManager: FragmentManager)
}