package com.burrowsapps.example.gif.di.module

import android.app.Application
import com.burrowsapps.example.gif.BuildConfig
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapters.Rfc3339DateJsonAdapter
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.io.File
import java.util.Date
import java.util.concurrent.TimeUnit

/**
 * Creates services based on Retrofit interfaces.
 */
@Module
class NetModule {
  @Module
  companion object {
    private const val CLIENT_TIME_OUT = 10L
    private const val CLIENT_CACHE_SIZE = 10 * 1024 * 1024L // 10 MiB
    private const val CLIENT_CACHE_DIRECTORY = "http"

    @JvmStatic @Provides fun provideRetrofit(application: Application): Retrofit.Builder =
      Retrofit.Builder()
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(MoshiConverterFactory.create(createMoshi()))
        .client(createOkHttpClient(application))

    private fun createHttpLoggingInterceptor(): HttpLoggingInterceptor {
      return HttpLoggingInterceptor().apply {
        level = if (BuildConfig.DEBUG) Level.BODY else Level.NONE
      }
    }

    private fun createCache(application: Application): Cache =
      Cache(File(application.cacheDir, CLIENT_CACHE_DIRECTORY), CLIENT_CACHE_SIZE)

    private fun createMoshi(): Moshi = Moshi.Builder()
      .add(Date::class.java, Rfc3339DateJsonAdapter().nullSafe())
      .build()

    private fun createOkHttpClient(
      application: Application
    ): OkHttpClient = OkHttpClient.Builder()
      .addInterceptor(createHttpLoggingInterceptor())
      .connectTimeout(CLIENT_TIME_OUT, TimeUnit.SECONDS)
      .writeTimeout(CLIENT_TIME_OUT, TimeUnit.SECONDS)
      .readTimeout(CLIENT_TIME_OUT, TimeUnit.SECONDS)
      .cache(createCache(application))
      .build()
  }
}
