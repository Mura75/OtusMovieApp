package otus.movieapp.data.network

import io.reactivex.Single
import otus.movieapp.data.model.MovieData
import otus.movieapp.data.model.MoviesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApi {

    @GET("movie/{movie_id}")
    fun getMovie(@Path("movie_id") movieId: Int) : Single<Response<MovieData>>

    @GET("movie/popular")
    fun getPopularMovies(@Query("page") page: Int) : Single<Response<MoviesResponse>>

}