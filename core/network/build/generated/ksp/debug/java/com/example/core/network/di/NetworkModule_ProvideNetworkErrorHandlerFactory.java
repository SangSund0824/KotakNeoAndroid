package com.example.core.network.di;

import com.example.core.network.utils.NetworkErrorHandler;
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
public final class NetworkModule_ProvideNetworkErrorHandlerFactory implements Factory<NetworkErrorHandler> {
  private final Provider<DefaultNetworkErrorHandler> defaultNetworkErrorHandlerProvider;

  public NetworkModule_ProvideNetworkErrorHandlerFactory(
      Provider<DefaultNetworkErrorHandler> defaultNetworkErrorHandlerProvider) {
    this.defaultNetworkErrorHandlerProvider = defaultNetworkErrorHandlerProvider;
  }

  @Override
  public NetworkErrorHandler get() {
    return provideNetworkErrorHandler(defaultNetworkErrorHandlerProvider.get());
  }

  public static NetworkModule_ProvideNetworkErrorHandlerFactory create(
      javax.inject.Provider<DefaultNetworkErrorHandler> defaultNetworkErrorHandlerProvider) {
    return new NetworkModule_ProvideNetworkErrorHandlerFactory(Providers.asDaggerProvider(defaultNetworkErrorHandlerProvider));
  }

  public static NetworkModule_ProvideNetworkErrorHandlerFactory create(
      Provider<DefaultNetworkErrorHandler> defaultNetworkErrorHandlerProvider) {
    return new NetworkModule_ProvideNetworkErrorHandlerFactory(defaultNetworkErrorHandlerProvider);
  }

  public static NetworkErrorHandler provideNetworkErrorHandler(
      DefaultNetworkErrorHandler defaultNetworkErrorHandler) {
    return Preconditions.checkNotNullFromProvides(NetworkModule.INSTANCE.provideNetworkErrorHandler(defaultNetworkErrorHandler));
  }
}
