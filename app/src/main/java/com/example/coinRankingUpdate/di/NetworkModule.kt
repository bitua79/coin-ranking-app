package com.example.coinRankingUpdate.di

import com.example.coinRankingUpdate.data.remote.WebService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Suppress("unused")
@InstallIn(SingletonComponent::class)
@Module
object NetworkModule {

    // Okhttp
    @Provides
    @Singleton
    fun provideOkhttpClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)

        val client = OkHttpClient.Builder()
            .addNetworkInterceptor { chain ->
                var request = chain.request()

                val url = request.url.newBuilder().build()

                request = request.newBuilder().url(url).build()
                chain.proceed(request)
            }

        client.addNetworkInterceptor(logging)
        return client.build()
    }

    // Retrofit
    @Provides
    @Singleton
    fun provideRetrofit(
        client: OkHttpClient
    ): Retrofit = Retrofit.Builder()
        .baseUrl("https://api.coinranking.com/v2/").client(client)
        .addConverterFactory(GsonConverterFactory.create()).build()

}