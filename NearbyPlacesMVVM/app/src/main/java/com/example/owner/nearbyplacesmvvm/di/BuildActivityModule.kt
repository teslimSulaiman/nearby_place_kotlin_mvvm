package com.example.owner.nearbyplacesmvvm.di

import com.example.owner.nearbyplacesmvvm.view.ListNearbyPlaceActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class BuildActivityModule {
    @ContributesAndroidInjector(modules = arrayOf(BuildFragmentModule::class))
    abstract fun contributeListNearbyPlaceActivity(): ListNearbyPlaceActivity
}