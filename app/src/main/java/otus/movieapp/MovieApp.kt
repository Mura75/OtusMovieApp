package otus.movieapp

import android.app.Activity
import android.app.Application
import android.os.Bundle
import com.mobile.moviedatabase.core.di.DaggerMainComponent
import com.mobile.moviedatabase.core.di.MainComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

class MovieApp : Application(), HasAndroidInjector, Application.ActivityLifecycleCallbacks {

    @Inject
    internal lateinit var dispatchingAndroidInjectorAny: DispatchingAndroidInjector<Any>

    private val mainComponent by lazy {
        MainComponent.create(this)
    }

    override fun onCreate() {
        super.onCreate()
        mainComponent.inject(this)
        registerActivityLifecycleCallbacks(this)
    }

    override fun androidInjector(): AndroidInjector<Any> = dispatchingAndroidInjectorAny

    override fun onActivityPaused(activity: Activity) {

    }

    override fun onActivityStarted(activity: Activity) {

    }

    override fun onActivityDestroyed(activity: Activity) {

    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {

    }

    override fun onActivityStopped(activity: Activity) {

    }

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        dispatchingAndroidInjectorAny.maybeInject(activity)
    }

    override fun onActivityResumed(activity: Activity) {

    }
}