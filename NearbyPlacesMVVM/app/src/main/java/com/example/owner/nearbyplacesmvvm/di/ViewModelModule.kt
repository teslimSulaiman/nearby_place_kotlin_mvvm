package com.example.owner.nearbyplacesmvvm.di

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.example.owner.nearbyplacesmvvm.viewModel.ListActivityViewModel
import com.example.owner.nearbyplacesmvvm.viewModel.ViewModelFactory
import com.example.owner.nearbyplacesmvvm.viewModel.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {
    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory) : ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(ListActivityViewModel::class)
    abstract fun bindListActivityViewModel(listActivityViewModel: ListActivityViewModel) : ViewModel


}