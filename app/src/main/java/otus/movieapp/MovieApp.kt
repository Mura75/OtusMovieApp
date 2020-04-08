package otus.movieapp

import android.app.Application
import com.mobile.moviedatabase.core.di.DaggerMainComponent
import com.mobile.moviedatabase.core.di.MainComponent

class MovieApp : Application() {

    private val mainComponent by lazy {
        MainComponent.create(this)
    }

    override fun onCreate() {
        super.onCreate()
        mainComponent.inject(this)
    }
}