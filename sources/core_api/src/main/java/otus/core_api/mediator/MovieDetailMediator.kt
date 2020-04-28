package otus.core_api.mediator

import android.content.Context
import android.widget.ImageView
import androidx.annotation.IdRes
import androidx.fragment.app.FragmentManager
import otus.core_api.dto.MovieData

interface MovieDetailMediator {

    fun startMovieDetailScreen(context: Context, movieData: MovieData, imageView: ImageView)
}