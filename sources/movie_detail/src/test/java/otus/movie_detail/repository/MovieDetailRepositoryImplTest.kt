package otus.movie_detail.repository

import io.reactivex.Single
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule
import otus.core_api.dto.MovieData
import otus.movieapp.data.network.MovieApi
import retrofit2.Response

class MovieDetailRepositoryImplTest {

    @get:Rule
    val mockitoRule: MockitoRule = MockitoJUnit.rule()

    @Mock
    lateinit var movieApi: MovieApi

    lateinit var movieRepository: MovieDetailRepository

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        movieRepository = MovieDetailRepositoryImpl(movieApi)
    }

    @Test
    fun `get movie by id`() {
        val movie = MovieData(id = 1)
        val response = Response.success(movie)

        Mockito.`when`(movieApi.getMovie(1))
            .thenReturn(Single.just(response))

        movieApi.getMovie(1)
            .test()
            .assertResult(response)
            .assertNoErrors()
    }

    @After
    fun tearDown() {
        Mockito.reset(movieApi)
    }
}