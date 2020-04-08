package otus.core_impl

import okhttp3.Interceptor
import okhttp3.Response
import otus.movieapp.data.network.NetworkConstants

class AuthInterceptor : Interceptor {

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