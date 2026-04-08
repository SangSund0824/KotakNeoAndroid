package com.example.kotakneoapp.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton
import com.example.kotakneoapp.BuildConfig

@Module
@InstallIn(SingletonComponent::class)
object NetworkConfigModule {

    @Provides
    @Singleton
    @Named("base_url")
    fun provideBaseUrl(): String = BuildConfig.BASE_URL

    @Provides
    @Singleton
    @Named("ws_market_url")
    fun provideMarketWebSocketUrl(): String = BuildConfig.WS_MARKET_URL

    @Provides
    @Singleton
    @Named("ws_order_url")
    fun provideOrderWebSocketUrl(): String = BuildConfig.WS_ORDER_URL
}