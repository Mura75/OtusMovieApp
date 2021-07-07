package otus.movie_list.repository

import io.reactivex.Single
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.reset
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule
import otus.core_api.dto.MovieData
import otus.core_api.dto.MoviesResponse
import otus.movie_list.RxJavaRule
import otus.movieapp.data.network.MovieApi
import retrofit2.Response

class MovieRepositoryTest {

    @get:Rule
    val mockitoRule: MockitoRule = MockitoJUnit.rule()

    @get:Rule
    val rxRule: RxJavaRule = RxJavaRule()

    @Mock
    lateinit var movieApi: MovieApi

    @Test
    fun `get first page of movies list`() {
        val movieList = listOf(MovieData(id = 1))
        val movieDataList = listOf(MovieData(id = 1))
        val response = Response.success(
            MoviesResponse(1, movieDataList, 1, 1)
        )

        val repository = MovieRepositoryImpl(movieApi)

        `when`(movieApi.getPopularMovies(page = 1))
            .thenReturn(Single.just(response))

        repository.getMovies(page = 1)
            .test()
            .assertResult(Pair(1, movieList))
    }

    @Test
    fun `get list with error`() {
        val errorBody: ResponseBody = "{\"error\": \"client error\"}"
            .toResponseBody("application/json".toMediaTypeOrNull())
        val error = Response.error<MoviesResponse>(403, errorBody)

        val repository = MovieRepositoryImpl(movieApi)

        `when`(movieApi.getPopularMovies(page = 1))
            .thenReturn(Single.just(error))

        repository.getMovies(page = 1)
            .test()
            .assertFailure(Throwable::class.java)
    }

    @After
    fun tearDown() {
        reset(movieApi)
    }
}