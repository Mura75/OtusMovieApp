package com.mobile.data.module

import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import otus.movieapp.BuildConfig
import otus.movieapp.data.network.MovieApi
import otus.movieapp.data.network.NetworkConstants
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
open class NetworkModule(private val context: Context) {

    companion object {
        private const val CONNECTION_INTERCEPTOR = "connection_interceptor"
        private const val AUTH_INTERCEPTOR = "auth_interceptor"
        private const val LOGGING_INTERCEPTOR = "logging_interceptor"

        private const val TIMEOUT = 60L
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): MovieApi {
        return retrofit.create(MovieApi::class.java)
    }

    @Provides
    @Singleton
    open fun provideRetrofit(okHttpClient: OkHttpClient, gson: Gson): Retrofit {
        return Retrofit.Builder()
            .baseUrl(NetworkConstants.MOVIE_API_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideGson() = GsonBuilder().create()

    @Provides
    @Singleton
    fun provideOkHttp(
        @Named(CONNECTION_INTERCEPTOR) connectionCheckerInterceptor: Interceptor,
        @Named(AUTH_INTERCEPTOR) authInterceptor: Interceptor,
        @Named(LOGGING_INTERCEPTOR) httpLoggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient {
        val okHttpClient = OkHttpClient.Builder()
            .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(TIMEOUT, TimeUnit.SECONDS)
            .addInterceptor(connectionCheckerInterceptor)
            .addInterceptor(authInterceptor)
        if (BuildConfig.DEBUG) {
            okHttpClient.addInterceptor(httpLoggingInterceptor)
        }
        return okHttpClient.build()
    }

    @Provides
    @Singleton
    @Named(CONNECTION_INTERCEPTOR)
    fun provideConnectionCheckerInterceptor(): Interceptor {
        return object : Interceptor {
            override fun intercept(chain: Interceptor.Chain): Response {
                val connectivityManager =
                    context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
                val netInfo = connectivityManager.activeNetworkInfo
                val isConnected = netInfo != null && netInfo.isConnected
                if (!isConnected) {
                    throw Exception("Network error")
                } else {
                    return chain.proceed(chain.request())
                }
            }
        }
    }

    @Provides
    @Singleton
    @Named(AUTH_INTERCEPTOR)
    fun provideAuthInterceptor(): Interceptor {
        return  object : Interceptor {
            override fun intercept(chain: Interceptor.Chain): Response {
                val newUrl = chain.request().url
                    .newBuilder()
                    .addQueryParameter("api_key", NetworkConstants.API_KEY)
                    .build()
                val newRequest = chain.request()
                    .newBuilder()
                    .url(newUrl)
                    .build()
                return chain.proceed(newRequest)
            }
        }
    }

    @Provides
    @Singleton
    @Named(LOGGING_INTERCEPTOR)
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor(logger = object : HttpLoggingInterceptor.Logger {
            override fun log(message: String) {
                Log.d("OkHttp", message)
            }
        }).apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }
}
