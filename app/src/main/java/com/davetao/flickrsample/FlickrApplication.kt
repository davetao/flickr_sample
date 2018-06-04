package com.davetao.flickrsample

import android.app.Application
import com.davetao.flickrsample.di.AppComponent
import com.davetao.flickrsample.di.DaggerAppComponent
import com.davetao.flickrsample.di.PhotoModule

class FlickrApplication : Application() {

    val component: AppComponent by lazy {
        DaggerAppComponent.builder()
                .photoModule(PhotoModule(this))
                .build()
    }

}