package com.davetao.flickrsample.di

import android.app.Application
import android.content.Context
import com.davetao.flickrsample.R
import com.davetao.flickrsample.repository.api.FlickrService
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module(includes = arrayOf(ViewModelModule::class))
class PhotoModule(private val application: Application) {

    @Provides
    @Singleton
    fun provideRetrofitCache() = Cache(application.cacheDir, 10 * 1024 * 1024)

    @Provides
    @Singleton
    fun provideAuthenticationInterceptor(context: Context): Interceptor = Interceptor { chain ->
        val original = chain.request()
        val originalUrl = original.url()
        val url = originalUrl.newBuilder()
                .addQueryParameter("api_key", context.getString(R.string.flickr_api_key))
                .build()
        val requestBuilder = original.newBuilder().url(url)
        val request = requestBuilder.build()
        chain.proceed(request)
    }

    @Provides
    @Singleton
    fun provideClient(interceptor: Interceptor, cache: Cache): OkHttpClient = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .cache(cache)
            .build()

    @Provides
    @Singleton
    fun provideFlickrService(okHttpClient: OkHttpClient): FlickrService = Retrofit.Builder()
            .baseUrl("https://api.flickr.com/services/rest/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(FlickrService::class.java)

    @Provides
    @Singleton
    fun provideApplicationContext(): Context = application

}
