package otus.movieapp.presentation.list

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import otus.movieapp.R
import otus.movieapp.data.MovieMapper
import otus.movieapp.data.network.ApiService
import otus.movieapp.data.repository.MovieRepositoryImpl
import otus.movieapp.domain.repository.MovieRepository
import otus.movieapp.presentation.MovieViewModelFactory
import otus.movieapp.presentation.MovieState
import otus.movieapp.presentation.view.DetailActivity

class MainActivity : AppCompatActivity() {

    private val adapter by lazy {
        MoviesAdapter(
            itemClickListener = { item ->
                DetailActivity.start(
                    this,
                    item.id
                )
            })
    }

    private lateinit var viewModel: MovieListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initDependencies()
        setAdapter()
        getMovies()

        srlMovies.setOnRefreshListener {
            viewModel.clear()
        }
    }

    private fun setAdapter() {
        rvMovies.layoutManager = LinearLayoutManager(this)
        rvMovies.adapter = adapter
    }

    private fun initDependencies() {
        val repository: MovieRepository = MovieRepositoryImpl(
            movieApi = ApiService.getMovieApi(),
            movieMapper = MovieMapper()
        )
        val factory = MovieViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory).get(MovieListViewModel::class.java)
    }

    private fun getMovies() {
        viewModel.liveData.observe(this, Observer { result ->
            when (result) {
                is MovieState.ShowLoading -> { srlMovies.isRefreshing = true }
                is MovieState.HideLoading -> { srlMovies.isRefreshing = false }
                is MovieState.Error -> {}
            }
        })

        viewModel.pagedListLiveData.observe(this, Observer { result ->
            Log.d("activity_result", result.toString())
            adapter.submitList(result)
        })
    }

}
