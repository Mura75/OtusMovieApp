package otus.movie_detail.view_model

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import androidx.lifecycle.Observer
import com.mobile.moviedatabase.features.movies.detail.MovieDetailViewModel
import io.reactivex.Single
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import otus.RxJavaRule
import otus.core_api.dto.MovieData
import otus.movie_detail.repository.MovieDetailRepository
import otus.movie_detail.view.MovieState

class MovieDetailViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val rxRule = RxJavaRule()

    @Mock
    lateinit var lifecycleOwner: LifecycleOwner

    @Mock
    lateinit var observer: Observer<MovieState>

    @Mock
    lateinit var movieRepository: MovieDetailRepository

    lateinit var lifecycle: Lifecycle

    lateinit var movieDetailViewModel: MovieDetailViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        lifecycle = LifecycleRegistry(lifecycleOwner)
        movieDetailViewModel = MovieDetailViewModel(movieRepository)
        movieDetailViewModel.liveData.observeForever(observer)
    }

    @Test
    fun `get single movie detail success`() {
        val movie = MovieData(id = 1, adult = false, popularity = 9.0, title = "Lord of the ring")
        `when`(movieRepository.getMovie(1))
            .thenReturn(Single.just(movie))

        movieDetailViewModel.getMovieDetail(1)
        verify(observer).onChanged(MovieState.ShowLoading)
        verify(observer).onChanged(MovieState.Result(movie))
        verify(observer).onChanged(MovieState.HideLoading)
    }

    @Test
    fun `get single movie detail error`() {
        `when`(movieRepository.getMovie(1))
            .thenReturn(Single.error(Throwable("connection error")))

        movieDetailViewModel.getMovieDetail(1)
        verify(observer).onChanged(MovieState.ShowLoading)
        verify(observer).onChanged(MovieState.HideLoading)
        verify(observer).onChanged(MovieState.Error("connection error"))
    }

    @After
    fun tearDown() {
        reset(observer)
        reset(lifecycleOwner)
        reset(movieRepository)
    }
}