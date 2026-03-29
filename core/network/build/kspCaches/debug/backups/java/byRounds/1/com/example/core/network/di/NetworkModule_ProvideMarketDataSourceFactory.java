package com.example.core.network.di;

import com.example.core.network.datasource.MarketDataSource;
import com.example.core.network.datasource.MarketRetrofitDataSource;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.Provider;
import dagger.internal.Providers;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava",
    "cast",
    "deprecation",
    "nullness:initialization.field.uninitialized"
})
public final class NetworkModule_ProvideMarketDataSourceFactory implements Factory<MarketDataSource> {
  private final Provider<MarketRetrofitDataSource> marketRetrofitDataSourceProvider;

  public NetworkModule_ProvideMarketDataSourceFactory(
      Provider<MarketRetrofitDataSource> marketRetrofitDataSourceProvider) {
    this.marketRetrofitDataSourceProvider = marketRetrofitDataSourceProvider;
  }

  @Override
  public MarketDataSource get() {
    return provideMarketDataSource(marketRetrofitDataSourceProvider.get());
  }

  public static NetworkModule_ProvideMarketDataSourceFactory create(
      javax.inject.Provider<MarketRetrofitDataSource> marketRetrofitDataSourceProvider) {
    return new NetworkModule_ProvideMarketDataSourceFactory(Providers.asDaggerProvider(marketRetrofitDataSourceProvider));
  }

  public static NetworkModule_ProvideMarketDataSourceFactory create(
      Provider<MarketRetrofitDataSource> marketRetrofitDataSourceProvider) {
    return new NetworkModule_ProvideMarketDataSourceFactory(marketRetrofitDataSourceProvider);
  }

  public static MarketDataSource provideMarketDataSource(
      MarketRetrofitDataSource marketRetrofitDataSource) {
    return Preconditions.checkNotNullFromProvides(NetworkModule.INSTANCE.provideMarketDataSource(marketRetrofitDataSource));
  }
}
