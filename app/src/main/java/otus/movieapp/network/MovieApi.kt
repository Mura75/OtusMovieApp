package otus.movieapp.network

import kotlinx.coroutines.Deferred
import kotlinx.coroutines.flow.Flow
import otus.movieapp.model.Movie
import otus.movieapp.model.MoviesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApi {

    @GET("movie/{movie_id}")
    suspend fun getMovie(@Path("movie_id") movieId: Int) : Response<Movie>

    @GET("movie/popular")
    suspend fun getPopularMovies(@Query("page") page: Int) : Response<MoviesResponse>

}