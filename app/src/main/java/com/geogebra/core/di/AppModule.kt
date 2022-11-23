package com.geogebra.core.di

import com.geogebra.core.api.ApiMaterial
import com.geogebra.core.repository.MaterialsRepository
import com.geogebra.core.repository.impl.MaterialsRepositoryImpl
import com.geogebra.core.utils.DateTypeDeserializer

import com.geogebra.ui.material.viewModels.MaterialViewModelFactory
import com.google.gson.GsonBuilder

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.Date
import java.util.concurrent.TimeUnit


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideOkHttp() : OkHttpClient {
        return OkHttpClient().newBuilder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .readTimeout(15, TimeUnit.SECONDS)
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()
    }

    @Provides
    fun provideRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.geogebra.org/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder()
                .registerTypeAdapter(Date::class.java, DateTypeDeserializer())
                .create()))
            .build()
    }

    @Provides
    fun provideApiMaterial(retrofit: Retrofit) : ApiMaterial {
        return retrofit.create(ApiMaterial::class.java)
    }

    @Provides
    fun provideMaterialsRepository(apiMaterial: ApiMaterial): MaterialsRepository {
        return MaterialsRepositoryImpl(apiMaterial)
    }

    @Provides
    fun provideMaterialViewModelFactory(repository: MaterialsRepository) : MaterialViewModelFactory {
        return MaterialViewModelFactory(repository)
    }
}