package com.example.feature.login;

import com.example.core.network.datasource.AuthNetworkDataSource;
import com.example.core.network.session.SessionManager;
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
public final class LoginViewModel_Factory implements Factory<LoginViewModel> {
  private final Provider<AuthNetworkDataSource> authNetworkDataSourceProvider;

  private final Provider<SessionManager> sessionManagerProvider;

  public LoginViewModel_Factory(Provider<AuthNetworkDataSource> authNetworkDataSourceProvider,
      Provider<SessionManager> sessionManagerProvider) {
    this.authNetworkDataSourceProvider = authNetworkDataSourceProvider;
    this.sessionManagerProvider = sessionManagerProvider;
  }

  @Override
  public LoginViewModel get() {
    return newInstance(authNetworkDataSourceProvider.get(), sessionManagerProvider.get());
  }

  public static LoginViewModel_Factory create(
      javax.inject.Provider<AuthNetworkDataSource> authNetworkDataSourceProvider,
      javax.inject.Provider<SessionManager> sessionManagerProvider) {
    return new LoginViewModel_Factory(Providers.asDaggerProvider(authNetworkDataSourceProvider), Providers.asDaggerProvider(sessionManagerProvider));
  }

  public static LoginViewModel_Factory create(
      Provider<AuthNetworkDataSource> authNetworkDataSourceProvider,
      Provider<SessionManager> sessionManagerProvider) {
    return new LoginViewModel_Factory(authNetworkDataSourceProvider, sessionManagerProvider);
  }

  public static LoginViewModel newInstance(AuthNetworkDataSource authNetworkDataSource,
      SessionManager sessionManager) {
    return new LoginViewModel(authNetworkDataSource, sessionManager);
  }
}
