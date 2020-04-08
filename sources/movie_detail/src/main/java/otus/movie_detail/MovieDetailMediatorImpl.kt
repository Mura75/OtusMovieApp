package otus.movie_detail

import android.content.Context
import otus.core_api.mediator.MovieDetailMediator
import otus.movie_detail.view.MovieDetailActivity
import javax.inject.Inject

class MovieDetailMediatorImpl @Inject constructor() : MovieDetailMediator {

    override fun startMovieDetailScreen(context: Context, movieId: Int) {
        MovieDetailActivity.start(context, movieId)
    }
}