package com.example.core.network.di;

import com.example.core.network.utils.NetworkErrorHandler;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
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
  @Override
  public NetworkErrorHandler get() {
    return provideNetworkErrorHandler();
  }

  public static NetworkModule_ProvideNetworkErrorHandlerFactory create() {
    return InstanceHolder.INSTANCE;
  }

  public static NetworkErrorHandler provideNetworkErrorHandler() {
    return Preconditions.checkNotNullFromProvides(NetworkModule.INSTANCE.provideNetworkErrorHandler());
  }

  private static final class InstanceHolder {
    static final NetworkModule_ProvideNetworkErrorHandlerFactory INSTANCE = new NetworkModule_ProvideNetworkErrorHandlerFactory();
  }
}
