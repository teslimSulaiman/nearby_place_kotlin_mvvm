package com.example.owner.nearbyplacesmvvm.di

import android.arch.lifecycle.ViewModelProvider
import android.content.Context
import com.example.owner.nearbyplacesmvvm.Utility
import com.example.owner.nearbyplacesmvvm.api.ApiCallInterface
import com.example.owner.nearbyplacesmvvm.mapper.NearbyPlaceInfoMapper
import com.example.owner.nearbyplacesmvvm.repository.Repository
import com.example.owner.nearbyplacesmvvm.viewModel.ViewModelFactory
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class NetworkModule {


    @Singleton
    @Provides
    internal fun provideGsonConverterFactory(): GsonConverterFactory {
        return GsonConverterFactory.create()
    }

    @Provides
    @Singleton
    internal fun provideRetrofit(converterFactory: GsonConverterFactory, okHttpClient: OkHttpClient): Retrofit {

        return Retrofit.Builder()
                .baseUrl(Utility.BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .addConverterFactory(converterFactory)
                .build()
    }

    @Provides
    @Singleton
    fun provideCache(context: Context): Cache {
        val cacheSize = 10 * 1024 * 1024 // 10 MB
        val httpCacheDirectory = File(context.getCacheDir(), "http-cache")
        return Cache(httpCacheDirectory, cacheSize.toLong())
    }

    @Provides
    @Singleton
    fun provideCacheInterceptor(): Interceptor {
        return Interceptor { chain ->
            val response = chain.proceed(chain.request())

            var cacheControl = CacheControl.Builder()
                    .maxAge(1, TimeUnit.MINUTES)
                    .build()

            response.newBuilder()
                    .header("Cache-Control", cacheControl.toString())
                    .build()
        }
    }

    @Provides
    @Singleton
    fun provideHttpClient(cache: Cache, networkCacheInterceptor: Interceptor): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        return OkHttpClient.Builder()
                .cache(cache)
                .addNetworkInterceptor(networkCacheInterceptor)
                .addInterceptor(loggingInterceptor)
                .build()

    }

    @Provides
    @Singleton
    internal fun provideNearbyPlaceInfoMapper(): NearbyPlaceInfoMapper {
        return NearbyPlaceInfoMapper()
    }


    @Provides
    @Singleton
    internal fun provideCallInterface(retrofit: Retrofit): ApiCallInterface {
        return retrofit.create<ApiCallInterface>(ApiCallInterface::class.java)
    }

    @Provides
    @Singleton
    internal fun provideViewModelFactory(repository: Repository): ViewModelProvider.Factory {
        return ViewModelFactory(repository)
    }

    @Provides
    @Singleton
    internal fun getRepository(apiCallInterface: ApiCallInterface): Repository {
        return Repository(apiCallInterface)
    }
}
