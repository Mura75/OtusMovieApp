package otus.core_impl

import android.content.Context
import android.net.ConnectivityManager
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor constructor(
    private val context: Context
) : Interceptor {
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