package otus.movieapp.presentation.view

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.coroutines.*
import otus.movieapp.R
import otus.movieapp.data.MovieMapper
import otus.movieapp.data.model.MovieData
import otus.movieapp.data.network.ApiService
import otus.movieapp.data.network.NetworkConstants
import otus.movieapp.data.repository.MovieRepositoryImpl
import otus.movieapp.domain.model.Movie
import otus.movieapp.domain.repository.MovieRepository
import otus.movieapp.presentation.MovieState
import otus.movieapp.presentation.MovieViewModelFactory
import otus.movieapp.presentation.list.MovieListViewModel
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class DetailActivity : AppCompatActivity(), HasAndroidInjector {

    companion object {
        fun start(context: Context, movieId: Int) {
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra("movie_id", movieId)
            context.startActivity(intent)
        }
    }

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory).get(DetailViewModel::class.java)
    }

    private val movieId by lazy {
        intent.getIntExtra("movie_id", 0)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        setData()
    }

    override fun androidInjector(): AndroidInjector<Any> = dispatchingAndroidInjector

    private fun setData() {
        viewModel.getMovie(id = movieId)
        viewModel.liveData.observe(this, Observer { result ->
            when (result) {
                is MovieState.ShowLoading -> { progressBar.visibility = View.VISIBLE }
                is MovieState.HideLoading -> { progressBar.visibility = View.GONE }
                is MovieState.ResultItem -> { showInfo(result.movie) }
                is MovieState.Error -> {}
            }
        })
    }

    private fun showInfo(movieData: Movie) {
        Glide.with(this)
            .load("${NetworkConstants.BACKDROP_BASE_URL}${movieData.backdropPath}")
            .into(ivPoster)

        tvName.text = movieData.title
        tvDescription.text = movieData.overview
        tvGenre.text = movieData.genres?.first()?.name
        tvDate.text = movieData.releaseDate
        tvRating.text = "${movieData.voteAverage}/10"
    }
}
