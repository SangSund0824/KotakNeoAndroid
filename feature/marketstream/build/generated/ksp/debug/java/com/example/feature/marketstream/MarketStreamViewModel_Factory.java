package com.example.feature.marketstream;

import com.example.core.network.api.KotakBackendApi;
import com.example.core.network.websocket.MarketWebSocketClient;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Provider;
import dagger.internal.Providers;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;

@ScopeMetadata
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
public final class MarketStreamViewModel_Factory implements Factory<MarketStreamViewModel> {
  private final Provider<KotakBackendApi> apiProvider;

  private final Provider<MarketWebSocketClient> webSocketClientProvider;

  public MarketStreamViewModel_Factory(Provider<KotakBackendApi> apiProvider,
      Provider<MarketWebSocketClient> webSocketClientProvider) {
    this.apiProvider = apiProvider;
    this.webSocketClientProvider = webSocketClientProvider;
  }

  @Override
  public MarketStreamViewModel get() {
    return newInstance(apiProvider.get(), webSocketClientProvider.get());
  }

  public static MarketStreamViewModel_Factory create(
      javax.inject.Provider<KotakBackendApi> apiProvider,
      javax.inject.Provider<MarketWebSocketClient> webSocketClientProvider) {
    return new MarketStreamViewModel_Factory(Providers.asDaggerProvider(apiProvider), Providers.asDaggerProvider(webSocketClientProvider));
  }

  public static MarketStreamViewModel_Factory create(Provider<KotakBackendApi> apiProvider,
      Provider<MarketWebSocketClient> webSocketClientProvider) {
    return new MarketStreamViewModel_Factory(apiProvider, webSocketClientProvider);
  }

  public static MarketStreamViewModel newInstance(KotakBackendApi api,
      MarketWebSocketClient webSocketClient) {
    return new MarketStreamViewModel(api, webSocketClient);
  }
}
