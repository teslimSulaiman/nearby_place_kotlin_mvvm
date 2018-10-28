package com.example.owner.nearbyplacesmvvm.di

import android.content.Context
import com.example.owner.nearbyplacesmvvm.application.MyApp
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = arrayOf(ViewModelModule::class, NetworkModule::class))
class AppModule {
    @Provides
    @Singleton
    fun provideContext(application: MyApp): Context {
        return application.applicationContext
    }
}