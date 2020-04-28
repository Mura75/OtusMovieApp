package otus.movie_detail

import android.content.Context
import android.widget.ImageView
import otus.core_api.dto.MovieData
import otus.core_api.mediator.MovieDetailMediator
import otus.movie_detail.view.MovieDetailActivity
import javax.inject.Inject

class MovieDetailMediatorImpl @Inject constructor() : MovieDetailMediator {

    override fun startMovieDetailScreen(context: Context, movieData: MovieData, imageView: ImageView) {
        MovieDetailActivity.start(context, movieData, imageView)
    }
}