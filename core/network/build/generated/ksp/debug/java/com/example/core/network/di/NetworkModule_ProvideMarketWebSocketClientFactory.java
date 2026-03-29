package com.example.core.network.di;

import com.example.core.network.websocket.MarketWebSocketClient;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.Provider;
import dagger.internal.Providers;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import okhttp3.OkHttpClient;

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
public final class NetworkModule_ProvideMarketWebSocketClientFactory implements Factory<MarketWebSocketClient> {
  private final Provider<OkHttpClient> okHttpClientProvider;

  public NetworkModule_ProvideMarketWebSocketClientFactory(
      Provider<OkHttpClient> okHttpClientProvider) {
    this.okHttpClientProvider = okHttpClientProvider;
  }

  @Override
  public MarketWebSocketClient get() {
    return provideMarketWebSocketClient(okHttpClientProvider.get());
  }

  public static NetworkModule_ProvideMarketWebSocketClientFactory create(
      javax.inject.Provider<OkHttpClient> okHttpClientProvider) {
    return new NetworkModule_ProvideMarketWebSocketClientFactory(Providers.asDaggerProvider(okHttpClientProvider));
  }

  public static NetworkModule_ProvideMarketWebSocketClientFactory create(
      Provider<OkHttpClient> okHttpClientProvider) {
    return new NetworkModule_ProvideMarketWebSocketClientFactory(okHttpClientProvider);
  }

  public static MarketWebSocketClient provideMarketWebSocketClient(OkHttpClient okHttpClient) {
    return Preconditions.checkNotNullFromProvides(NetworkModule.INSTANCE.provideMarketWebSocketClient(okHttpClient));
  }
}
