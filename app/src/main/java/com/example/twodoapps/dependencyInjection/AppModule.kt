package com.example.twodoapps.dependencyInjection

import android.content.Context
import com.example.twodoapps.apiService.TwoDoService
import com.example.twodoapps.apiService.UserService
import com.example.twodoapps.cookieJar.PersistentCookieJar
import com.example.twodoapps.repository.ApiRepositories
import com.example.twodoapps.sharePref.AuthHelper
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import fuel.Fuel
import fuel.FuelBuilder
import fuel.HttpLoader
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    val okHttpClient = OkHttpClient.Builder()
        .cookieJar(PersistentCookieJar())
        .build()


    @Provides
    fun provideGson() = Gson()

    @Provides
    fun provideFuelClient() : HttpLoader {
        return FuelBuilder().build()
    }

    @Provides
    fun provideTwoDoService(fuel : HttpLoader, gson: Gson, authHelper: AuthHelper) : TwoDoService {
        TwoDoService.fuel = fuel
        TwoDoService.gson = gson
        TwoDoService.initialize(authHelper)
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

    @Provides
    fun provideAuthHelper(@ApplicationContext context: Context) : AuthHelper {
        return AuthHelper(context)
    }
}