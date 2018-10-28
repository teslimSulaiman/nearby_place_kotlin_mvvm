package com.example.owner.nearbyplacesmvvm.api

import android.support.annotation.NonNull
import com.example.owner.nearbyplacekotlin.model.NearbyPlace
import com.example.owner.nearbyplacesmvvm.Utility

open class ApiResponse private constructor(var status: String, var data: NearbyPlace?, val error: Throwable?) {
    companion object {

        fun loading(): ApiResponse {
            return ApiResponse(Utility.LOADING, null, null)
        }

        fun success(@NonNull data: NearbyPlace): ApiResponse {
            return ApiResponse(Utility.SUCCESS, data, null)
        }

        fun error(@NonNull error: Throwable): ApiResponse {
            return ApiResponse(Utility.ERROR, null, error)
        }
    }

}