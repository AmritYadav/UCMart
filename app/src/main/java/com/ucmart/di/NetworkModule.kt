package com.ucmart.di

import com.ucmart.BuildConfig
import com.ucmart.data.category.api.CategoryApi
import com.ucmart.data.product.api.ProductApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

val networkModule = module {
    single { provideLoggingInterceptor() }
    single { provideOkHttpClient(logging = get()) }
    single { provideRetrofit(okHttpClient = get()) }
    factory { provideCategoryApi(retrofit = get()) }
    factory { provideProductApi(retrofit = get()) }
}

private fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder()
        .client(okHttpClient)
        .baseUrl("http://finkaro.com/ucmart/")
        .addConverterFactory(MoshiConverterFactory.create())
        .build()
}

private fun provideLoggingInterceptor(): HttpLoggingInterceptor {
    val logging = HttpLoggingInterceptor()
    if (BuildConfig.DEBUG)
        logging.level = (HttpLoggingInterceptor.Level.BODY)
    return logging
}

private fun provideOkHttpClient(logging: HttpLoggingInterceptor): OkHttpClient {
    return OkHttpClient.Builder()
        .addInterceptor(logging)
        .connectTimeout(60, TimeUnit.SECONDS)
        .build()
}

private fun provideCategoryApi(retrofit: Retrofit): CategoryApi =
    retrofit.create(CategoryApi::class.java)

private fun provideProductApi(retrofit: Retrofit): ProductApi =
    retrofit.create(ProductApi::class.java)