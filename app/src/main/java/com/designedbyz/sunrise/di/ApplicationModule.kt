package com.designedbyz.sunrise.di

import com.designedbyz.sunrise.imageloader.CatService
import com.designedbyz.sunrise.imageloader.DogService
import com.designedbyz.sunrise.time.SunriseService
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit


import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import kotlinx.serialization.json.JsonConfiguration
import retrofit2.CallAdapter
import retrofit2.converter.jackson.JacksonConverterFactory


@Module
class ApplicationModule {
    val contentType = "application/json".toMediaType()
    @Provides
    fun provideOkHttp(): OkHttpClient {
        return OkHttpClient()
    }

    @ExperimentalSerializationApi
    @Provides
    fun provideCatService(okHttpClient: OkHttpClient): CatService {
        val mapper = ObjectMapper()
        mapper.registerModule(KotlinModule())
        val retrofit = Retrofit.Builder().baseUrl(CatService.url)
            .client(okHttpClient)
            .addConverterFactory(JacksonConverterFactory.create(mapper))
//            .addConverterFactory(Json.asConverterFactory(contentType))
            .build()
        return retrofit.create(CatService::class.java)
    }

    @Provides
    fun providesDogService(okHttpClient: OkHttpClient): DogService {
        val retrofit = Retrofit.Builder().baseUrl(DogService.url)
            .client(okHttpClient)
            .addConverterFactory(Json.asConverterFactory(contentType))
            .build()
        return retrofit.create(DogService::class.java)
    }

    @Provides
    fun providesSunriseService(okHttpClient: OkHttpClient): SunriseService {

        val retrofit = Retrofit.Builder().baseUrl(SunriseService.url)
            .client(okHttpClient)
            .addConverterFactory(Json{ignoreUnknownKeys = true}.asConverterFactory(contentType))
            .build()
        return retrofit.create(SunriseService::class.java)
    }

}
