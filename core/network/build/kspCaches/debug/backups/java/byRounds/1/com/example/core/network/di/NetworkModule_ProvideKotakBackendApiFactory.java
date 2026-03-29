package com.example.core.network.di;

import com.example.core.network.api.KotakBackendApi;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.Provider;
import dagger.internal.Providers;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import retrofit2.Retrofit;

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
public final class NetworkModule_ProvideKotakBackendApiFactory implements Factory<KotakBackendApi> {
  private final Provider<Retrofit> retrofitProvider;

  public NetworkModule_ProvideKotakBackendApiFactory(Provider<Retrofit> retrofitProvider) {
    this.retrofitProvider = retrofitProvider;
  }

  @Override
  public KotakBackendApi get() {
    return provideKotakBackendApi(retrofitProvider.get());
  }

  public static NetworkModule_ProvideKotakBackendApiFactory create(
      javax.inject.Provider<Retrofit> retrofitProvider) {
    return new NetworkModule_ProvideKotakBackendApiFactory(Providers.asDaggerProvider(retrofitProvider));
  }

  public static NetworkModule_ProvideKotakBackendApiFactory create(
      Provider<Retrofit> retrofitProvider) {
    return new NetworkModule_ProvideKotakBackendApiFactory(retrofitProvider);
  }

  public static KotakBackendApi provideKotakBackendApi(Retrofit retrofit) {
    return Preconditions.checkNotNullFromProvides(NetworkModule.INSTANCE.provideKotakBackendApi(retrofit));
  }
}
