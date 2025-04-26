package com.example.twodoapps.dependencyInjection

import com.example.twodoapps.apiService.TwoDoService
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import fuel.Fuel
import fuel.FuelBuilder
import fuel.HttpLoader

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    fun provideGson() = Gson()

    @Provides
    fun provideFuelClient() : HttpLoader {
        return FuelBuilder().build()
    }

    @Provides
    fun provideTwoDoService(fuel : Fuel, gson: Gson) : TwoDoService {
        return TwoDoService
    }
}