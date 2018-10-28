package com.example.owner.nearbyplacesmvvm.viewModel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.example.owner.nearbyplacesmvvm.api.ApiCallInterface
import com.example.owner.nearbyplacesmvvm.api.ApiResponse
import com.example.owner.nearbyplacesmvvm.repository.Repository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ListActivityViewModel
@Inject constructor(private val repository: Repository) : ViewModel() {
    private lateinit var subscription: Disposable


    private var responseLiveData: MutableLiveData<ApiResponse> = MutableLiveData()
    fun getNearbyLocations(): MutableLiveData<ApiResponse> {

        return responseLiveData
    }
    fun getNearbyPlaces(location: String, radius: String, key: String) {
        if (responseLiveData.value != null)return
        subscription = (repository.executeGetNearbyPlaces(location, radius, key)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe({ d -> responseLiveData.setValue(ApiResponse.loading()) })
                .subscribe(
                        { result -> responseLiveData.setValue(ApiResponse.success(result)) },
                        { throwable -> responseLiveData.setValue(ApiResponse.error(throwable)) }
                ))

    }

    override
    fun onCleared() {
        subscription.dispose()
    }
}