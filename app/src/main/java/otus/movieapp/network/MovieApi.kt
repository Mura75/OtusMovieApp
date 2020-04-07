package otus.movieapp.network

import kotlinx.coroutines.flow.Flow
import otus.movieapp.model.Movie
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface MovieApi {

    @GET("movie/{movie_id}")
    suspend fun getMovie(@Path("movie_id") movieId: Int) : Flow<Response<Movie>>

}