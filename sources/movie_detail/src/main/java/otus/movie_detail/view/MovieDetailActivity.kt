package otus.movie_detail.view

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.mobile.moviedatabase.features.movies.detail.MovieDetailViewModel
import otus.core_api.AppConstants
import otus.core_api.dto.MovieData
import otus.core_api.mediator.AppWithFacade
import otus.movie_detail.R
import otus.movie_detail.di.EagerTrigger
import otus.movie_detail.di.MovieDetailComponent
import otus.movieapp.data.network.NetworkConstants
import javax.inject.Inject

class MovieDetailActivity : AppCompatActivity() {

    companion object {
        fun start(context: Context, movie: MovieData, imageView: ImageView) {
            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                (context as Activity),
                imageView,
                imageView.transitionName
            )
            val intent = Intent(context, MovieDetailActivity::class.java)
            intent.putExtra(AppConstants.MOVIE_DATA, movie)
            intent.putExtra(AppConstants.TRANSITION, imageView.transitionName)
            context.startActivity(intent, options.toBundle())
        }
    }

    @Inject
    lateinit var eagerTrigger: EagerTrigger

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory).get(MovieDetailViewModel::class.java)
    }

    private lateinit var progressBar: ProgressBar
    private lateinit var ivPoster: ImageView
    lateinit var tvName: TextView
    private lateinit var tvGenre: TextView
    private lateinit var tvDate: TextView
    private lateinit var tvRating: TextView
    private lateinit var tvDescription: TextView

    private val movie by lazy {
        intent?.getParcelableExtra<MovieData>(AppConstants.MOVIE_DATA)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MovieDetailComponent.create((application as AppWithFacade).getFacade())
            .inject(this)
        setContentView(R.layout.fragment_movie_details)
        bindViews()
        setData()
    }

    private fun bindViews() {
        progressBar = findViewById(R.id.progressBar)
        ivPoster = findViewById(R.id.ivPoster)
        tvName = findViewById(R.id.tvName)
        tvGenre = findViewById(R.id.tvGenre)
        tvDate = findViewById(R.id.tvDate)
        tvRating = findViewById(R.id.tvRating)
        tvDescription = findViewById(R.id.tvDescription)
    }

    private fun setData() {
        movie?.let { movieData ->
            viewModel.getMovieDetail(movieId = movieData.id)
            ivPoster.transitionName = intent.getStringExtra(AppConstants.TRANSITION)

            Glide.with(this)
                .load("${NetworkConstants.BACKDROP_BASE_URL}${movieData.backdropPath}")
                .addListener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        supportStartPostponedEnterTransition()
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        supportStartPostponedEnterTransition()
                        return false
                    }
                })
                .into(ivPoster)
        }
        viewModel.liveData.observe(this, Observer { result ->
            when(result) {
                is MovieState.ShowLoading -> {
                    progressBar.visibility = View.VISIBLE
                }
                is MovieState.HideLoading -> {
                    progressBar.visibility = View.GONE
                }
                is MovieState.Result -> {
                    tvName.text = result.movie.title
                    tvDescription.text = result.movie.overview
                    tvGenre.text = result.movie.genres?.first()?.name
                    tvDate.text = result.movie.releaseDate
                    tvRating.text = "${result.movie.voteAverage}/10"
                }
                is MovieState.Error -> {
                    Toast.makeText(this, result.error, Toast.LENGTH_SHORT).show()
                }
                is MovieState.IntError -> {
                    Toast.makeText(this, result.error, Toast.LENGTH_SHORT).show()
                }
            }
        })
    }
}
