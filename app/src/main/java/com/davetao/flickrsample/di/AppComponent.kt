package com.davetao.flickrsample.di

import com.davetao.flickrsample.view.ui.MainActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [(PhotoModule::class)])
interface AppComponent {
    fun inject(mainActivity: MainActivity)
}