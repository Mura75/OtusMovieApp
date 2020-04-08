package otus.core

import android.content.Context

interface AppProvider {
    fun provideContext(): Context
}