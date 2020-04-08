package otus.core_api.mediator

import android.content.Context
import androidx.annotation.IdRes
import androidx.fragment.app.FragmentManager

interface MovieDetailMediator {

    fun startMovieDetailScreen(context: Context, movieId: Int)
}