package com.example.core.network.websocket;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
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
public final class MarketWebSocketClient_Factory implements Factory<MarketWebSocketClient> {
  private final Provider<OkHttpClient> okHttpClientProvider;

  public MarketWebSocketClient_Factory(Provider<OkHttpClient> okHttpClientProvider) {
    this.okHttpClientProvider = okHttpClientProvider;
  }

  @Override
  public MarketWebSocketClient get() {
    return newInstance(okHttpClientProvider.get());
  }

  public static MarketWebSocketClient_Factory create(
      javax.inject.Provider<OkHttpClient> okHttpClientProvider) {
    return new MarketWebSocketClient_Factory(Providers.asDaggerProvider(okHttpClientProvider));
  }

  public static MarketWebSocketClient_Factory create(Provider<OkHttpClient> okHttpClientProvider) {
    return new MarketWebSocketClient_Factory(okHttpClientProvider);
  }

  public static MarketWebSocketClient newInstance(OkHttpClient okHttpClient) {
    return new MarketWebSocketClient(okHttpClient);
  }
}
