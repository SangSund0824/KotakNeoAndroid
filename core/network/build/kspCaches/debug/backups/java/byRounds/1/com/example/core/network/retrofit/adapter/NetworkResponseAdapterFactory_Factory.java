package com.example.core.network.retrofit.adapter;

import com.example.core.network.utils.NetworkErrorHandler;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Provider;
import dagger.internal.Providers;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.serialization.json.Json;

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
public final class NetworkResponseAdapterFactory_Factory implements Factory<NetworkResponseAdapterFactory> {
  private final Provider<CoroutineScope> scopeProvider;

  private final Provider<NetworkErrorHandler> networkErrorHandlerProvider;

  private final Provider<Json> jsonProvider;

  public NetworkResponseAdapterFactory_Factory(Provider<CoroutineScope> scopeProvider,
      Provider<NetworkErrorHandler> networkErrorHandlerProvider, Provider<Json> jsonProvider) {
    this.scopeProvider = scopeProvider;
    this.networkErrorHandlerProvider = networkErrorHandlerProvider;
    this.jsonProvider = jsonProvider;
  }

  @Override
  public NetworkResponseAdapterFactory get() {
    return newInstance(scopeProvider.get(), networkErrorHandlerProvider.get(), jsonProvider.get());
  }

  public static NetworkResponseAdapterFactory_Factory create(
      javax.inject.Provider<CoroutineScope> scopeProvider,
      javax.inject.Provider<NetworkErrorHandler> networkErrorHandlerProvider,
      javax.inject.Provider<Json> jsonProvider) {
    return new NetworkResponseAdapterFactory_Factory(Providers.asDaggerProvider(scopeProvider), Providers.asDaggerProvider(networkErrorHandlerProvider), Providers.asDaggerProvider(jsonProvider));
  }

  public static NetworkResponseAdapterFactory_Factory create(Provider<CoroutineScope> scopeProvider,
      Provider<NetworkErrorHandler> networkErrorHandlerProvider, Provider<Json> jsonProvider) {
    return new NetworkResponseAdapterFactory_Factory(scopeProvider, networkErrorHandlerProvider, jsonProvider);
  }

  public static NetworkResponseAdapterFactory newInstance(CoroutineScope scope,
      NetworkErrorHandler networkErrorHandler, Json json) {
    return new NetworkResponseAdapterFactory(scope, networkErrorHandler, json);
  }
}
