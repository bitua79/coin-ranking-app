package com.example.coinRankingUpdate.di

import com.example.coinRankingUpdate.core.AppDatabase
import com.example.coinRankingUpdate.data.local.CryptocurrenciesDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DaoModule {
    @Provides
    @Singleton
    fun provideCryptocurrencyDao(db: AppDatabase): CryptocurrenciesDao = db.cryptocurrenciesDao()

}