package com.hackyeah.app.di

import androidx.lifecycle.MutableLiveData
import com.hackyeah.app.data.NetworkState
import dagger.Module
import dagger.Provides

@Module
class AppModule {


    @Provides
    fun provideHomeNetworkState(): MutableLiveData<NetworkState> {
        return MutableLiveData<NetworkState>()
    }

}