package com.mobile.telecomapp.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import otus.movieapp.presentation.list.MainActivity
import otus.movieapp.presentation.view.DetailActivity

@Module
abstract class ActivityModule {

    @ContributesAndroidInjector
    abstract fun contributeAuthActivity(): MainActivity

    @ContributesAndroidInjector()
    abstract fun contributeDetailActivity(): DetailActivity
}