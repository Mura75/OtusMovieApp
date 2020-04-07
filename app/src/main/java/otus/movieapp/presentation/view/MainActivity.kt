package otus.movieapp.presentation.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import otus.movieapp.R
import otus.movieapp.data.network.ApiService
import java.io.IOException
import kotlin.coroutines.CoroutineContext

class MainActivity : AppCompatActivity(), CoroutineScope {

    private val job = SupervisorJob()

    private val adapter by lazy {
        MoviesAdapter(
            itemClickListener = { item ->
                DetailActivity.start(this, item.id)
            })
    }

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setAdapter()
        getMovies()

        srlMovies.setOnRefreshListener {
            adapter.clearAll()
            getMovies()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }

    private fun setAdapter() {
        rvMovies.layoutManager = LinearLayoutManager(this)
        rvMovies.adapter = adapter
    }

    private fun getMovies() {
        val coroutineExceptionHandler = CoroutineExceptionHandler { _, exception ->
            Log.e("movie_data_exception", exception.toString())
        }
        launch(coroutineExceptionHandler) {
            srlMovies.isRefreshing = true
            val list = withContext(Dispatchers.IO) {
                val response = ApiService.getMovieApi()
                        .getPopularMovies(1)
                if (response.isSuccessful) {
                    response.body()?.results ?: emptyList()
                } else {
                    throw IOException("")
                }
            }
            Log.d("movie_data_list", list.toString())
            srlMovies.isRefreshing = false
            adapter.addItems(list)
        }
    }

}
