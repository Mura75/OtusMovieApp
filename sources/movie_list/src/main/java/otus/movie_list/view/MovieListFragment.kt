package otus.movie_list.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_movie_list.*
import otus.core_api.mediator.AppWithFacade

import otus.movie_list.R
import otus.movie_list.di.EagerTrigger
import otus.movie_list.di.MovieListComponent
import java.util.EnumSet.of
import javax.inject.Inject

class MovieListFragment : Fragment() {

    companion object {
        fun newInstance() = MovieListFragment()
    }

    private val adapter by lazy {
        MoviesAdapter(
            itemClickListener = { item ->

            })
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var eagerTrigger: EagerTrigger

    private lateinit var viewModel: MovieListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MovieListComponent.create((requireActivity().application as AppWithFacade).getFacade())
            .inject(this)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MovieListViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movie_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindViews()
        setAdapter()
        getMovies()
    }

    private fun bindViews() {
        srlMovies.setOnRefreshListener {
            viewModel.clear()
        }
    }

    private fun setAdapter() {
        rvMovies.layoutManager = LinearLayoutManager(requireContext())
        rvMovies.adapter = adapter
    }

    private fun getMovies() {
        viewModel.liveData.observe(viewLifecycleOwner, Observer { result ->
            when (result) {
                is MovieState.ShowLoading -> { srlMovies.isRefreshing = true }
                is MovieState.HideLoading -> { srlMovies.isRefreshing = false }
                is MovieState.Error -> {}
            }
        })

        viewModel.pagedListLiveData.observe(viewLifecycleOwner, Observer { result ->
            Log.d("activity_result", result.toString())
            adapter.submitList(result)
        })
    }

}
