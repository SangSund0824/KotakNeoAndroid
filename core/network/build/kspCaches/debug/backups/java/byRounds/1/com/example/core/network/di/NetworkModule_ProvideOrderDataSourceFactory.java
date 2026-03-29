package com.example.core.network.di;

import com.example.core.network.datasource.OrderDataSource;
import com.example.core.network.datasource.OrderRetrofitDataSource;
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
public final class NetworkModule_ProvideOrderDataSourceFactory implements Factory<OrderDataSource> {
  private final Provider<OrderRetrofitDataSource> orderRetrofitDataSourceProvider;

  public NetworkModule_ProvideOrderDataSourceFactory(
      Provider<OrderRetrofitDataSource> orderRetrofitDataSourceProvider) {
    this.orderRetrofitDataSourceProvider = orderRetrofitDataSourceProvider;
  }

  @Override
  public OrderDataSource get() {
    return provideOrderDataSource(orderRetrofitDataSourceProvider.get());
  }

  public static NetworkModule_ProvideOrderDataSourceFactory create(
      javax.inject.Provider<OrderRetrofitDataSource> orderRetrofitDataSourceProvider) {
    return new NetworkModule_ProvideOrderDataSourceFactory(Providers.asDaggerProvider(orderRetrofitDataSourceProvider));
  }

  public static NetworkModule_ProvideOrderDataSourceFactory create(
      Provider<OrderRetrofitDataSource> orderRetrofitDataSourceProvider) {
    return new NetworkModule_ProvideOrderDataSourceFactory(orderRetrofitDataSourceProvider);
  }

  public static OrderDataSource provideOrderDataSource(
      OrderRetrofitDataSource orderRetrofitDataSource) {
    return Preconditions.checkNotNullFromProvides(NetworkModule.INSTANCE.provideOrderDataSource(orderRetrofitDataSource));
  }
}
