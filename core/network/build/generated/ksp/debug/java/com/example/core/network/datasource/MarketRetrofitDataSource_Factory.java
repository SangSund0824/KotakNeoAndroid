package com.example.core.network.datasource;

import com.example.core.network.api.KotakBackendApi;
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
public final class MarketRetrofitDataSource_Factory implements Factory<MarketRetrofitDataSource> {
  private final Provider<KotakBackendApi> apiProvider;

  public MarketRetrofitDataSource_Factory(Provider<KotakBackendApi> apiProvider) {
    this.apiProvider = apiProvider;
  }

  @Override
  public MarketRetrofitDataSource get() {
    return newInstance(apiProvider.get());
  }

  public static MarketRetrofitDataSource_Factory create(
      javax.inject.Provider<KotakBackendApi> apiProvider) {
    return new MarketRetrofitDataSource_Factory(Providers.asDaggerProvider(apiProvider));
  }

  public static MarketRetrofitDataSource_Factory create(Provider<KotakBackendApi> apiProvider) {
    return new MarketRetrofitDataSource_Factory(apiProvider);
  }

  public static MarketRetrofitDataSource newInstance(KotakBackendApi api) {
    return new MarketRetrofitDataSource(api);
  }
}
