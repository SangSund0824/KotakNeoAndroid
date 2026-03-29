package com.example.core.data.di;

import com.example.core.data.db.AppDatabase;
import com.example.core.data.db.StockDao;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
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
public final class DatabaseModule_ProvideStockDaoFactory implements Factory<StockDao> {
  private final Provider<AppDatabase> dbProvider;

  public DatabaseModule_ProvideStockDaoFactory(Provider<AppDatabase> dbProvider) {
    this.dbProvider = dbProvider;
  }

  @Override
  public StockDao get() {
    return provideStockDao(dbProvider.get());
  }

  public static DatabaseModule_ProvideStockDaoFactory create(
      javax.inject.Provider<AppDatabase> dbProvider) {
    return new DatabaseModule_ProvideStockDaoFactory(Providers.asDaggerProvider(dbProvider));
  }

  public static DatabaseModule_ProvideStockDaoFactory create(Provider<AppDatabase> dbProvider) {
    return new DatabaseModule_ProvideStockDaoFactory(dbProvider);
  }

  public static StockDao provideStockDao(AppDatabase db) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideStockDao(db));
  }
}
