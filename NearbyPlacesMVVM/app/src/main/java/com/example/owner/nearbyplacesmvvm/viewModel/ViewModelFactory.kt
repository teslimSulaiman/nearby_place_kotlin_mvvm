package com.example.owner.nearbyplacesmvvm.viewModel

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.example.owner.nearbyplacesmvvm.api.ApiCallInterface
import com.example.owner.nearbyplacesmvvm.repository.Repository
import com.example.owner.nearbyplacesmvvm.viewModel.ListActivityViewModel
import javax.inject.Inject

class ViewModelFactory @Inject
constructor(private val repository: Repository) : ViewModelProvider.Factory {


    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ListActivityViewModel::class.java!!)) {
            return ListActivityViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }
}