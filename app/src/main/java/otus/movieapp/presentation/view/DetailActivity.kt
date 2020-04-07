package otus.movieapp.presentation.view

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.coroutines.*
import otus.movieapp.R
import otus.movieapp.data.model.MovieData
import otus.movieapp.data.network.ApiService
import otus.movieapp.data.network.NetworkConstants
import kotlin.coroutines.CoroutineContext

class DetailActivity : AppCompatActivity(), CoroutineScope {

    companion object {
        fun start(context: Context, movieId: Int) {
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra("movie_id", movieId)
            context.startActivity(intent)
        }
    }

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    private val job = SupervisorJob()

    private val movieId by lazy {
        intent.getIntExtra("movie_id", 0)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        getInfo()
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }

    private fun getInfo() {
        val exceptionHandler = CoroutineExceptionHandler { _, error ->

        }
        launch(exceptionHandler) {
            progressBar.visibility = View.VISIBLE
            val movie = withContext(Dispatchers.IO) {
                val response = ApiService.getMovieApi().getMovie(movieId)
                if (response.isSuccessful) {
                    response.body()
                } else {
                    throw Exception("Movie data exception")
                }
            }
            progressBar.visibility = View.GONE
            movie?.let { showInfo(it) }
        }
    }

    private fun showInfo(movieData: MovieData) {
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
