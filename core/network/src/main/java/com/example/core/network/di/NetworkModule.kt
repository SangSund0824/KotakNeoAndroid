package com.example.core.network.di

import com.example.core.network.api.KotakBackendApi
import com.example.core.network.datasource.AuthNetworkDataSource
import com.example.core.network.datasource.AuthRetrofitDataSource
import com.example.core.network.retrofit.adapter.NetworkResponseAdapterFactory
import com.example.core.network.utils.NetworkErrorHandler
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Qualifier
import javax.inject.Singleton
import javax.inject.Named

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class ApplicationScope

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideJson(): Json = Json {
        ignoreUnknownKeys = true
        isLenient = true
    }

    @Provides
    @Singleton
    @ApplicationScope
    fun provideApplicationScope(): CoroutineScope =
        CoroutineScope(SupervisorJob() + Dispatchers.Default)

    @Provides
    @Singleton
    fun provideNetworkErrorHandler(
        defaultNetworkErrorHandler: DefaultNetworkErrorHandler
    ): NetworkErrorHandler = defaultNetworkErrorHandler

    @Provides
    @Singleton
    fun provideMarketWebSocketClient(
        okHttpClient: OkHttpClient,
        @Named("ws_market_url") wsUrl: String
    ): com.example.core.network.websocket.MarketWebSocketClient {
        return com.example.core.network.websocket.MarketWebSocketClient(okHttpClient, wsUrl)
    }

    @Provides
    @Singleton
    fun provideOrderFeedWebSocketClient(
        okHttpClient: OkHttpClient,
        @Named("ws_order_url") wsUrl: String
    ): com.example.core.network.websocket.OrderFeedWebSocketClient {
        return com.example.core.network.websocket.OrderFeedWebSocketClient(okHttpClient, wsUrl)
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun provideNetworkResponseAdapterFactory(
        @ApplicationScope scope: CoroutineScope,
        networkErrorHandler: NetworkErrorHandler,
        json: Json
    ): NetworkResponseAdapterFactory {
        return NetworkResponseAdapterFactory(scope, networkErrorHandler, json)
    }

    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        networkResponseAdapterFactory: NetworkResponseAdapterFactory,
        @Named("base_url") baseUrl: String
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(networkResponseAdapterFactory)
            .build()
    }

    @Provides
    @Singleton
    fun provideKotakBackendApi(retrofit: Retrofit): KotakBackendApi {
        return retrofit.create(KotakBackendApi::class.java)
    }

    @Provides
    @Singleton
    fun provideAuthNetworkDataSource(
        authRetrofitDataSource: AuthRetrofitDataSource
    ): AuthNetworkDataSource = authRetrofitDataSource
    
    @Provides
    @Singleton
    fun provideMarketDataSource(
        marketRetrofitDataSource: com.example.core.network.datasource.MarketRetrofitDataSource
    ): com.example.core.network.datasource.MarketDataSource = marketRetrofitDataSource

    @Provides
    @Singleton
    fun provideOrderDataSource(
        orderRetrofitDataSource: com.example.core.network.datasource.OrderRetrofitDataSource
    ): com.example.core.network.datasource.OrderDataSource = orderRetrofitDataSource

    @Provides
    @Singleton
    fun providePortfolioDataSource(
        portfolioRetrofitDataSource: com.example.core.network.datasource.PortfolioRetrofitDataSource
    ): com.example.core.network.datasource.PortfolioDataSource = portfolioRetrofitDataSource

    @Provides
    @Singleton
    fun providePositionsDataSource(
        positionsRetrofitDataSource: com.example.core.network.datasource.PositionsRetrofitDataSource
    ): com.example.core.network.datasource.PositionsDataSource = positionsRetrofitDataSource
}
