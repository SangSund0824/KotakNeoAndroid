package com.example.core.data.db;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Integer;
import java.lang.Long;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class StockDao_Impl implements StockDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<StockEntity> __insertionAdapterOfStockEntity;

  private final EntityInsertionAdapter<SyncMetadata> __insertionAdapterOfSyncMetadata;

  private final SharedSQLiteStatement __preparedStmtOfDeleteAll;

  public StockDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfStockEntity = new EntityInsertionAdapter<StockEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `stocks` (`pTrdSymbol`,`pSymbol`,`pSymbolName`,`pGroup`,`pExSeg`,`pInstType`) VALUES (?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final StockEntity entity) {
        statement.bindString(1, entity.getPTrdSymbol());
        if (entity.getPSymbol() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getPSymbol());
        }
        if (entity.getPSymbolName() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getPSymbolName());
        }
        if (entity.getPGroup() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getPGroup());
        }
        if (entity.getPExSeg() == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.getPExSeg());
        }
        if (entity.getPInstType() == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.getPInstType());
        }
      }
    };
    this.__insertionAdapterOfSyncMetadata = new EntityInsertionAdapter<SyncMetadata>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `sync_metadata` (`key`,`timestamp`) VALUES (?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final SyncMetadata entity) {
        statement.bindString(1, entity.getKey());
        statement.bindLong(2, entity.getTimestamp());
      }
    };
    this.__preparedStmtOfDeleteAll = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM stocks";
        return _query;
      }
    };
  }

  @Override
  public Object insertAll(final List<StockEntity> stocks,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfStockEntity.insert(stocks);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object updateSyncTime(final SyncMetadata metadata,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfSyncMetadata.insert(metadata);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteAll(final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteAll.acquire();
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfDeleteAll.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object search(final String query,
      final Continuation<? super List<StockEntity>> $completion) {
    final String _sql = "SELECT * FROM stocks WHERE pTrdSymbol LIKE '%' || ? || '%' OR pSymbolName LIKE '%' || ? || '%' LIMIT 20";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    _statement.bindString(_argIndex, query);
    _argIndex = 2;
    _statement.bindString(_argIndex, query);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<StockEntity>>() {
      @Override
      @NonNull
      public List<StockEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfPTrdSymbol = CursorUtil.getColumnIndexOrThrow(_cursor, "pTrdSymbol");
          final int _cursorIndexOfPSymbol = CursorUtil.getColumnIndexOrThrow(_cursor, "pSymbol");
          final int _cursorIndexOfPSymbolName = CursorUtil.getColumnIndexOrThrow(_cursor, "pSymbolName");
          final int _cursorIndexOfPGroup = CursorUtil.getColumnIndexOrThrow(_cursor, "pGroup");
          final int _cursorIndexOfPExSeg = CursorUtil.getColumnIndexOrThrow(_cursor, "pExSeg");
          final int _cursorIndexOfPInstType = CursorUtil.getColumnIndexOrThrow(_cursor, "pInstType");
          final List<StockEntity> _result = new ArrayList<StockEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final StockEntity _item;
            final String _tmpPTrdSymbol;
            _tmpPTrdSymbol = _cursor.getString(_cursorIndexOfPTrdSymbol);
            final String _tmpPSymbol;
            if (_cursor.isNull(_cursorIndexOfPSymbol)) {
              _tmpPSymbol = null;
            } else {
              _tmpPSymbol = _cursor.getString(_cursorIndexOfPSymbol);
            }
            final String _tmpPSymbolName;
            if (_cursor.isNull(_cursorIndexOfPSymbolName)) {
              _tmpPSymbolName = null;
            } else {
              _tmpPSymbolName = _cursor.getString(_cursorIndexOfPSymbolName);
            }
            final String _tmpPGroup;
            if (_cursor.isNull(_cursorIndexOfPGroup)) {
              _tmpPGroup = null;
            } else {
              _tmpPGroup = _cursor.getString(_cursorIndexOfPGroup);
            }
            final String _tmpPExSeg;
            if (_cursor.isNull(_cursorIndexOfPExSeg)) {
              _tmpPExSeg = null;
            } else {
              _tmpPExSeg = _cursor.getString(_cursorIndexOfPExSeg);
            }
            final String _tmpPInstType;
            if (_cursor.isNull(_cursorIndexOfPInstType)) {
              _tmpPInstType = null;
            } else {
              _tmpPInstType = _cursor.getString(_cursorIndexOfPInstType);
            }
            _item = new StockEntity(_tmpPTrdSymbol,_tmpPSymbol,_tmpPSymbolName,_tmpPGroup,_tmpPExSeg,_tmpPInstType);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object count(final Continuation<? super Integer> $completion) {
    final String _sql = "SELECT COUNT(*) FROM stocks";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Integer>() {
      @Override
      @NonNull
      public Integer call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Integer _result;
          if (_cursor.moveToFirst()) {
            final int _tmp;
            _tmp = _cursor.getInt(0);
            _result = _tmp;
          } else {
            _result = 0;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object getLastSyncTime(final String key, final Continuation<? super Long> $completion) {
    final String _sql = "SELECT timestamp FROM sync_metadata WHERE `key` = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, key);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Long>() {
      @Override
      @Nullable
      public Long call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Long _result;
          if (_cursor.moveToFirst()) {
            if (_cursor.isNull(0)) {
              _result = null;
            } else {
              _result = _cursor.getLong(0);
            }
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
