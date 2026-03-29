package com.example.core.network.di;

import com.example.core.network.datasource.AuthNetworkDataSource;
import com.example.core.network.datasource.AuthRetrofitDataSource;
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
public final class NetworkModule_ProvideAuthNetworkDataSourceFactory implements Factory<AuthNetworkDataSource> {
  private final Provider<AuthRetrofitDataSource> authRetrofitDataSourceProvider;

  public NetworkModule_ProvideAuthNetworkDataSourceFactory(
      Provider<AuthRetrofitDataSource> authRetrofitDataSourceProvider) {
    this.authRetrofitDataSourceProvider = authRetrofitDataSourceProvider;
  }

  @Override
  public AuthNetworkDataSource get() {
    return provideAuthNetworkDataSource(authRetrofitDataSourceProvider.get());
  }

  public static NetworkModule_ProvideAuthNetworkDataSourceFactory create(
      javax.inject.Provider<AuthRetrofitDataSource> authRetrofitDataSourceProvider) {
    return new NetworkModule_ProvideAuthNetworkDataSourceFactory(Providers.asDaggerProvider(authRetrofitDataSourceProvider));
  }

  public static NetworkModule_ProvideAuthNetworkDataSourceFactory create(
      Provider<AuthRetrofitDataSource> authRetrofitDataSourceProvider) {
    return new NetworkModule_ProvideAuthNetworkDataSourceFactory(authRetrofitDataSourceProvider);
  }

  public static AuthNetworkDataSource provideAuthNetworkDataSource(
      AuthRetrofitDataSource authRetrofitDataSource) {
    return Preconditions.checkNotNullFromProvides(NetworkModule.INSTANCE.provideAuthNetworkDataSource(authRetrofitDataSource));
  }
}
