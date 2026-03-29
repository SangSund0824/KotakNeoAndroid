package com.example.core.network.datasource;

import com.example.core.network.api.KotakBackendApi;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Provider;
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
public final class AuthRetrofitDataSource_Factory implements Factory<AuthRetrofitDataSource> {
  private final Provider<KotakBackendApi> apiProvider;

  public AuthRetrofitDataSource_Factory(Provider<KotakBackendApi> apiProvider) {
    this.apiProvider = apiProvider;
  }

  @Override
  public AuthRetrofitDataSource get() {
    return newInstance(apiProvider.get());
  }

  public static AuthRetrofitDataSource_Factory create(Provider<KotakBackendApi> apiProvider) {
    return new AuthRetrofitDataSource_Factory(apiProvider);
  }

  public static AuthRetrofitDataSource newInstance(KotakBackendApi api) {
    return new AuthRetrofitDataSource(api);
  }
}
