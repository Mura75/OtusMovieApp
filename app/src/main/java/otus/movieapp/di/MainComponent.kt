package com.mobile.moviedatabase.core.di

import android.app.Application
import com.mobile.data.module.NetworkModule
import com.mobile.data.module.RepositoryModule
import com.mobile.telecomapp.di.ActivityModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import otus.movieapp.MovieApp
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        NetworkModule::class,
        RepositoryModule::class,
        ViewModelModule::class,
        ActivityModule::class
    ]
)
interface MainComponent : AndroidInjector<MovieApp> {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun repositoryModule(repositoryModule: RepositoryModule): Builder

        fun networkModule(networkModule: NetworkModule): Builder

        fun build(): MainComponent
    }

    companion object {
        fun create(application: Application): MainComponent {
            return DaggerMainComponent.builder()
                .application(application)
                .networkModule(NetworkModule(application))
                .repositoryModule(RepositoryModule())
                .build()
        }
    }
}