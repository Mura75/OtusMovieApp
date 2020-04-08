package otus.movie_list

import androidx.fragment.app.FragmentManager
import otus.core_api.mediator.MovieListMediator
import otus.movie_list.view.MovieListFragment
import javax.inject.Inject

class MovieListMediatorImpl @Inject constructor() : MovieListMediator {

    override fun startMovieListScreen(containerId: Int, fragmentManager: FragmentManager) {
        fragmentManager.beginTransaction()
            .add(containerId, MovieListFragment.newInstance())
            .commit()
    }

}