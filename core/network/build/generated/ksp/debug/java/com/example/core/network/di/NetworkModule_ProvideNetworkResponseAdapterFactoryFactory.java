package com.example.core.network.di;

import com.example.core.network.retrofit.adapter.NetworkResponseAdapterFactory;
import com.example.core.network.utils.NetworkErrorHandler;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.Provider;
import dagger.internal.Providers;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.serialization.json.Json;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata("com.example.core.network.di.ApplicationScope")
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
public final class NetworkModule_ProvideNetworkResponseAdapterFactoryFactory implements Factory<NetworkResponseAdapterFactory> {
  private final Provider<CoroutineScope> scopeProvider;

  private final Provider<NetworkErrorHandler> networkErrorHandlerProvider;

  private final Provider<Json> jsonProvider;

  public NetworkModule_ProvideNetworkResponseAdapterFactoryFactory(
      Provider<CoroutineScope> scopeProvider,
      Provider<NetworkErrorHandler> networkErrorHandlerProvider, Provider<Json> jsonProvider) {
    this.scopeProvider = scopeProvider;
    this.networkErrorHandlerProvider = networkErrorHandlerProvider;
    this.jsonProvider = jsonProvider;
  }

  @Override
  public NetworkResponseAdapterFactory get() {
    return provideNetworkResponseAdapterFactory(scopeProvider.get(), networkErrorHandlerProvider.get(), jsonProvider.get());
  }

  public static NetworkModule_ProvideNetworkResponseAdapterFactoryFactory create(
      javax.inject.Provider<CoroutineScope> scopeProvider,
      javax.inject.Provider<NetworkErrorHandler> networkErrorHandlerProvider,
      javax.inject.Provider<Json> jsonProvider) {
    return new NetworkModule_ProvideNetworkResponseAdapterFactoryFactory(Providers.asDaggerProvider(scopeProvider), Providers.asDaggerProvider(networkErrorHandlerProvider), Providers.asDaggerProvider(jsonProvider));
  }

  public static NetworkModule_ProvideNetworkResponseAdapterFactoryFactory create(
      Provider<CoroutineScope> scopeProvider,
      Provider<NetworkErrorHandler> networkErrorHandlerProvider, Provider<Json> jsonProvider) {
    return new NetworkModule_ProvideNetworkResponseAdapterFactoryFactory(scopeProvider, networkErrorHandlerProvider, jsonProvider);
  }

  public static NetworkResponseAdapterFactory provideNetworkResponseAdapterFactory(
      CoroutineScope scope, NetworkErrorHandler networkErrorHandler, Json json) {
    return Preconditions.checkNotNullFromProvides(NetworkModule.INSTANCE.provideNetworkResponseAdapterFactory(scope, networkErrorHandler, json));
  }
}
