package com.example.twodoapps.dependencyInjection

import com.example.twodoapps.apiService.TwoDoService
import com.example.twodoapps.apiService.UserService
import com.example.twodoapps.repository.ApiRepositories
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import fuel.Fuel
import fuel.FuelBuilder
import fuel.HttpLoader
import javax.inject.Singleton

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
    fun provideTwoDoService(fuel : HttpLoader, gson: Gson) : TwoDoService {
        TwoDoService.fuel = fuel
        TwoDoService.gson = gson
        return TwoDoService
    }

    @Provides
    fun provideUserService(fuel : HttpLoader, gson: Gson) : UserService {
        UserService.fuel = fuel
        UserService.gson = gson
        return UserService
    }

    @Provides
    fun provideApiRepositories(apiService: TwoDoService, userApiService: UserService): ApiRepositories {
        return ApiRepositories(apiService, userApiService)
    }
}