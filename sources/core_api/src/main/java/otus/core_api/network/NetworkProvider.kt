package otus.core_api.network

import com.google.gson.Gson
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import otus.movieapp.data.network.MovieApi
import retrofit2.Retrofit

interface NetworkProvider {

    fun provideMovieApi(): MovieApi

    fun provideRetrofit(): Retrofit

    fun provideOkHttpClient(): OkHttpClient

    fun provideLoggingInterceptor(): HttpLoggingInterceptor

    fun provideInterceptor(): Interceptor

    fun provideGson(): Gson
}