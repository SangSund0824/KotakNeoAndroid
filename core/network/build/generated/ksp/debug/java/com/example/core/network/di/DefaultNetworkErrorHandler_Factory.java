package com.example.core.network.di;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
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
public final class DefaultNetworkErrorHandler_Factory implements Factory<DefaultNetworkErrorHandler> {
  @Override
  public DefaultNetworkErrorHandler get() {
    return newInstance();
  }

  public static DefaultNetworkErrorHandler_Factory create() {
    return InstanceHolder.INSTANCE;
  }

  public static DefaultNetworkErrorHandler newInstance() {
    return new DefaultNetworkErrorHandler();
  }

  private static final class InstanceHolder {
    static final DefaultNetworkErrorHandler_Factory INSTANCE = new DefaultNetworkErrorHandler_Factory();
  }
}
