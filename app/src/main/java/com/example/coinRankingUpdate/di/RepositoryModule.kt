package com.example.coinRankingUpdate.di

import com.example.coinRankingUpdate.data.repository.CryptocurrencyRepository
import com.example.coinRankingUpdate.data.repository.DefaultCryptocurrencyRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun bindsAppRepository(repos: DefaultCryptocurrencyRepository): CryptocurrencyRepository

}