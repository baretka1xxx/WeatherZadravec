package com.example.weatherzadravec.di.module

import com.example.weatherzadravec.BuildConfig
import com.example.weatherzadravec.data.remote.WeatherNetwork
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit = Retrofit.Builder().baseUrl(BuildConfig.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build()

    @Provides
    @Singleton
    fun provideWeatherNetwork(retrofit: Retrofit): WeatherNetwork = retrofit.create(WeatherNetwork::class.java)
}