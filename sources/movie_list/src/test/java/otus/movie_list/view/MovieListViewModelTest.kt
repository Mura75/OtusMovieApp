package otus.movie_list.view

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import io.reactivex.Single
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule
import otus.core_api.dto.MovieData
import otus.movie_list.RxJavaRule
import otus.movie_list.asPagedList
import otus.movie_list.repository.MovieRepository


class MovieListViewModelTest {

    @get:Rule
    val mockitoRule: MockitoRule = MockitoJUnit.rule()

    @get:Rule
    val rxRule: RxJavaRule = RxJavaRule()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    lateinit var movieRepository: MovieRepository

    @Mock
    lateinit var observer: Observer<MovieState>

    @Mock
    lateinit var pageObserver: Observer<PagedList<MovieData>>

    lateinit var movieListViewModel: MovieListViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        movieListViewModel = MovieListViewModel(movieRepository)
        movieListViewModel.liveData.observeForever(observer)
        movieListViewModel.pagedListLiveData.observeForever(pageObserver)
    }

    @Test
    fun `movies list first page success`() {
        val moviesList = listOf(
            MovieData(id = 1, adult = false, popularity = 9.0, title = "Lord of the ring")
        )
        val pair: Pair<Int, List<MovieData>> = Pair(1, moviesList)

        Mockito.`when`(movieRepository.getMovies(page = 1))
            .thenReturn(Single.just(pair))

        movieListViewModel.getMovies()

        val list = moviesList.asPagedList()

        Mockito.verify(pageObserver).onChanged(list)
    }

    @After
    fun tearDown() {
        Mockito.reset(movieRepository)
        Mockito.reset(observer)
        Mockito.reset(pageObserver)
    }
}