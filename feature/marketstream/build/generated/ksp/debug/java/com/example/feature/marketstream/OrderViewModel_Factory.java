package com.example.feature.marketstream;

import com.example.core.data.repository.StockRepository;
import com.example.core.network.datasource.OrderDataSource;
import com.example.core.network.session.SessionManager;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Provider;
import dagger.internal.Providers;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;

@ScopeMetadata
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
public final class OrderViewModel_Factory implements Factory<OrderViewModel> {
  private final Provider<OrderDataSource> orderDataSourceProvider;

  private final Provider<StockRepository> stockRepositoryProvider;

  private final Provider<SessionManager> sessionManagerProvider;

  public OrderViewModel_Factory(Provider<OrderDataSource> orderDataSourceProvider,
      Provider<StockRepository> stockRepositoryProvider,
      Provider<SessionManager> sessionManagerProvider) {
    this.orderDataSourceProvider = orderDataSourceProvider;
    this.stockRepositoryProvider = stockRepositoryProvider;
    this.sessionManagerProvider = sessionManagerProvider;
  }

  @Override
  public OrderViewModel get() {
    return newInstance(orderDataSourceProvider.get(), stockRepositoryProvider.get(), sessionManagerProvider.get());
  }

  public static OrderViewModel_Factory create(
      javax.inject.Provider<OrderDataSource> orderDataSourceProvider,
      javax.inject.Provider<StockRepository> stockRepositoryProvider,
      javax.inject.Provider<SessionManager> sessionManagerProvider) {
    return new OrderViewModel_Factory(Providers.asDaggerProvider(orderDataSourceProvider), Providers.asDaggerProvider(stockRepositoryProvider), Providers.asDaggerProvider(sessionManagerProvider));
  }

  public static OrderViewModel_Factory create(Provider<OrderDataSource> orderDataSourceProvider,
      Provider<StockRepository> stockRepositoryProvider,
      Provider<SessionManager> sessionManagerProvider) {
    return new OrderViewModel_Factory(orderDataSourceProvider, stockRepositoryProvider, sessionManagerProvider);
  }

  public static OrderViewModel newInstance(OrderDataSource orderDataSource,
      StockRepository stockRepository, SessionManager sessionManager) {
    return new OrderViewModel(orderDataSource, stockRepository, sessionManager);
  }
}
