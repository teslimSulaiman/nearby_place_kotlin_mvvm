package com.example.owner.nearbyplacesmvvm.application

import com.example.owner.nearbyplacesmvvm.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication


class MyApp : DaggerApplication() {

    companion object {
        var instance: MyApp? = null
            private set
    }

    override fun applicationInjector(): AndroidInjector<MyApp> =
            DaggerAppComponent.builder().create(this@MyApp)

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

}