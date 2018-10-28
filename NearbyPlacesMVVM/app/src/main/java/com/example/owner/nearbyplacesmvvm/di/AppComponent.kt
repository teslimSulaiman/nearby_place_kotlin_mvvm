package com.example.owner.nearbyplacesmvvm.di

import com.example.owner.nearbyplacesmvvm.application.MyApp
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(
        AndroidSupportInjectionModule::class,
        AppModule::class,
        BuildActivityModule::class
))
interface AppComponent : AndroidInjector<MyApp> {
    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<MyApp>()
}