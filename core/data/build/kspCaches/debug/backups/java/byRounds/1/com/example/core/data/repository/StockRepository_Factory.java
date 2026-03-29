package com.example.core.data.repository;

import com.example.core.data.db.StockDao;
import com.example.core.network.datasource.MarketDataSource;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
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
public final class StockRepository_Factory implements Factory<StockRepository> {
  private final Provider<StockDao> stockDaoProvider;

  private final Provider<MarketDataSource> marketDataSourceProvider;

  public StockRepository_Factory(Provider<StockDao> stockDaoProvider,
      Provider<MarketDataSource> marketDataSourceProvider) {
    this.stockDaoProvider = stockDaoProvider;
    this.marketDataSourceProvider = marketDataSourceProvider;
  }

  @Override
  public StockRepository get() {
    return newInstance(stockDaoProvider.get(), marketDataSourceProvider.get());
  }

  public static StockRepository_Factory create(javax.inject.Provider<StockDao> stockDaoProvider,
      javax.inject.Provider<MarketDataSource> marketDataSourceProvider) {
    return new StockRepository_Factory(Providers.asDaggerProvider(stockDaoProvider), Providers.asDaggerProvider(marketDataSourceProvider));
  }

  public static StockRepository_Factory create(Provider<StockDao> stockDaoProvider,
      Provider<MarketDataSource> marketDataSourceProvider) {
    return new StockRepository_Factory(stockDaoProvider, marketDataSourceProvider);
  }

  public static StockRepository newInstance(StockDao stockDao, MarketDataSource marketDataSource) {
    return new StockRepository(stockDao, marketDataSource);
  }
}
