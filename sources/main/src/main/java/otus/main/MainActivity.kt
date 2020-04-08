package otus.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import otus.core_api.mediator.AppWithFacade
import otus.core_api.mediator.MovieListMediator
import otus.main.di.MainComponent
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var movieListMediator: MovieListMediator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        MainComponent.create((application as AppWithFacade).getFacade()).inject(this)

        movieListMediator.startMovieListScreen(
            containerId = R.id.main_container,
            fragmentManager = supportFragmentManager
        )
    }
}
