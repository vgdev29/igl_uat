package com.fieldmobility.igl.database;

import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomOpenHelper;
import androidx.room.RoomOpenHelper.Delegate;
import androidx.room.RoomOpenHelper.ValidationResult;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.room.util.TableInfo.Column;
import androidx.room.util.TableInfo.ForeignKey;
import androidx.room.util.TableInfo.Index;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import androidx.sqlite.db.SupportSQLiteOpenHelper.Callback;
import androidx.sqlite.db.SupportSQLiteOpenHelper.Configuration;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

@SuppressWarnings({"unchecked", "deprecation"})
public final class MyDatabse_Impl extends MyDatabse {
  private volatile NgUserDao _ngUserDao;

  @Override
  protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration configuration) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(configuration, new RoomOpenHelper.Delegate(1) {
      @Override
      public void createAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("CREATE TABLE IF NOT EXISTS `ng_users_table` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `jmr_no` TEXT, `bp_no` TEXT, `meter_no` TEXT, `customer_name` TEXT, `burner_details` TEXT, `ng_update_date` TEXT, `conversion_date` TEXT, `ca_no` TEXT, `meter_type` TEXT, `meter_make` TEXT, `initial_reading` TEXT, `mobile_no` TEXT, `rfc_date` TEXT, `amount_charged` TEXT, `house_no` TEXT, `block_qtr` TEXT, `society` TEXT, `city` TEXT, `zone` TEXT, `floor` TEXT, `street` TEXT, `area` TEXT, `home_address` TEXT, `meter_photo` TEXT, `service_photo` TEXT, `installation_photo` TEXT, `customer_sign` TEXT, `executive_sign` TEXT, `status` TEXT, `sub_status` TEXT, `remarks` TEXT, `delay_date` TEXT, `recording` TEXT, `hold_images` TEXT, `priority` TEXT, `claim` INTEGER, `email_id` TEXT, `alt_number` TEXT, `landmark` TEXT, `contractor_id` TEXT, `tpi_id` TEXT, `zi_id` TEXT, `supervisor_id` TEXT, `crm_status` TEXT, `crm_reason` TEXT, `lead_no` TEXT, `start_job` INTEGER, `code_group` TEXT, `old` INTEGER, `pushed_to_crm` INTEGER NOT NULL, `claim_date` TEXT, `cat_id` TEXT, `catalog` TEXT, `code` TEXT, `reason` TEXT, `control_room` TEXT, `rfc_initial_reading` TEXT, `supervisor_assigned_date` TEXT, `contractor_assigned_date` TEXT, `lattitude` TEXT, `longitude` TEXT, `corrected_meter_no` TEXT, `meter_status` INTEGER NOT NULL)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        _db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, 'b2f54b527b4a8739176f3ab8265d0d57')");
      }

      @Override
      public void dropAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("DROP TABLE IF EXISTS `ng_users_table`");
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onDestructiveMigration(_db);
          }
        }
      }

      @Override
      protected void onCreate(SupportSQLiteDatabase _db) {
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onCreate(_db);
          }
        }
      }

      @Override
      public void onOpen(SupportSQLiteDatabase _db) {
        mDatabase = _db;
        internalInitInvalidationTracker(_db);
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onOpen(_db);
          }
        }
      }

      @Override
      public void onPreMigrate(SupportSQLiteDatabase _db) {
        DBUtil.dropFtsSyncTriggers(_db);
      }

      @Override
      public void onPostMigrate(SupportSQLiteDatabase _db) {
      }

      @Override
      protected RoomOpenHelper.ValidationResult onValidateSchema(SupportSQLiteDatabase _db) {
        final HashMap<String, TableInfo.Column> _columnsNgUsersTable = new HashMap<String, TableInfo.Column>(64);
        _columnsNgUsersTable.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNgUsersTable.put("jmr_no", new TableInfo.Column("jmr_no", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNgUsersTable.put("bp_no", new TableInfo.Column("bp_no", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNgUsersTable.put("meter_no", new TableInfo.Column("meter_no", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNgUsersTable.put("customer_name", new TableInfo.Column("customer_name", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNgUsersTable.put("burner_details", new TableInfo.Column("burner_details", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNgUsersTable.put("ng_update_date", new TableInfo.Column("ng_update_date", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNgUsersTable.put("conversion_date", new TableInfo.Column("conversion_date", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNgUsersTable.put("ca_no", new TableInfo.Column("ca_no", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNgUsersTable.put("meter_type", new TableInfo.Column("meter_type", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNgUsersTable.put("meter_make", new TableInfo.Column("meter_make", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNgUsersTable.put("initial_reading", new TableInfo.Column("initial_reading", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNgUsersTable.put("mobile_no", new TableInfo.Column("mobile_no", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNgUsersTable.put("rfc_date", new TableInfo.Column("rfc_date", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNgUsersTable.put("amount_charged", new TableInfo.Column("amount_charged", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNgUsersTable.put("house_no", new TableInfo.Column("house_no", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNgUsersTable.put("block_qtr", new TableInfo.Column("block_qtr", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNgUsersTable.put("society", new TableInfo.Column("society", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNgUsersTable.put("city", new TableInfo.Column("city", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNgUsersTable.put("zone", new TableInfo.Column("zone", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNgUsersTable.put("floor", new TableInfo.Column("floor", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNgUsersTable.put("street", new TableInfo.Column("street", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNgUsersTable.put("area", new TableInfo.Column("area", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNgUsersTable.put("home_address", new TableInfo.Column("home_address", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNgUsersTable.put("meter_photo", new TableInfo.Column("meter_photo", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNgUsersTable.put("service_photo", new TableInfo.Column("service_photo", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNgUsersTable.put("installation_photo", new TableInfo.Column("installation_photo", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNgUsersTable.put("customer_sign", new TableInfo.Column("customer_sign", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNgUsersTable.put("executive_sign", new TableInfo.Column("executive_sign", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNgUsersTable.put("status", new TableInfo.Column("status", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNgUsersTable.put("sub_status", new TableInfo.Column("sub_status", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNgUsersTable.put("remarks", new TableInfo.Column("remarks", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNgUsersTable.put("delay_date", new TableInfo.Column("delay_date", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNgUsersTable.put("recording", new TableInfo.Column("recording", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNgUsersTable.put("hold_images", new TableInfo.Column("hold_images", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNgUsersTable.put("priority", new TableInfo.Column("priority", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNgUsersTable.put("claim", new TableInfo.Column("claim", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNgUsersTable.put("email_id", new TableInfo.Column("email_id", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNgUsersTable.put("alt_number", new TableInfo.Column("alt_number", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNgUsersTable.put("landmark", new TableInfo.Column("landmark", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNgUsersTable.put("contractor_id", new TableInfo.Column("contractor_id", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNgUsersTable.put("tpi_id", new TableInfo.Column("tpi_id", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNgUsersTable.put("zi_id", new TableInfo.Column("zi_id", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNgUsersTable.put("supervisor_id", new TableInfo.Column("supervisor_id", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNgUsersTable.put("crm_status", new TableInfo.Column("crm_status", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNgUsersTable.put("crm_reason", new TableInfo.Column("crm_reason", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNgUsersTable.put("lead_no", new TableInfo.Column("lead_no", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNgUsersTable.put("start_job", new TableInfo.Column("start_job", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNgUsersTable.put("code_group", new TableInfo.Column("code_group", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNgUsersTable.put("old", new TableInfo.Column("old", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNgUsersTable.put("pushed_to_crm", new TableInfo.Column("pushed_to_crm", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNgUsersTable.put("claim_date", new TableInfo.Column("claim_date", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNgUsersTable.put("cat_id", new TableInfo.Column("cat_id", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNgUsersTable.put("catalog", new TableInfo.Column("catalog", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNgUsersTable.put("code", new TableInfo.Column("code", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNgUsersTable.put("reason", new TableInfo.Column("reason", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNgUsersTable.put("control_room", new TableInfo.Column("control_room", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNgUsersTable.put("rfc_initial_reading", new TableInfo.Column("rfc_initial_reading", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNgUsersTable.put("supervisor_assigned_date", new TableInfo.Column("supervisor_assigned_date", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNgUsersTable.put("contractor_assigned_date", new TableInfo.Column("contractor_assigned_date", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNgUsersTable.put("lattitude", new TableInfo.Column("lattitude", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNgUsersTable.put("longitude", new TableInfo.Column("longitude", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNgUsersTable.put("corrected_meter_no", new TableInfo.Column("corrected_meter_no", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNgUsersTable.put("meter_status", new TableInfo.Column("meter_status", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysNgUsersTable = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesNgUsersTable = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoNgUsersTable = new TableInfo("ng_users_table", _columnsNgUsersTable, _foreignKeysNgUsersTable, _indicesNgUsersTable);
        final TableInfo _existingNgUsersTable = TableInfo.read(_db, "ng_users_table");
        if (! _infoNgUsersTable.equals(_existingNgUsersTable)) {
          return new RoomOpenHelper.ValidationResult(false, "ng_users_table(com.fieldmobility.igl.Model.NguserListModel).\n"
                  + " Expected:\n" + _infoNgUsersTable + "\n"
                  + " Found:\n" + _existingNgUsersTable);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "b2f54b527b4a8739176f3ab8265d0d57", "13437da261e2b6f26e2b3ae85edc07b1");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(configuration.context)
        .name(configuration.name)
        .callback(_openCallback)
        .build();
    final SupportSQLiteOpenHelper _helper = configuration.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "ng_users_table");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    try {
      super.beginTransaction();
      _db.execSQL("DELETE FROM `ng_users_table`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  public NgUserDao ngUserDao() {
    if (_ngUserDao != null) {
      return _ngUserDao;
    } else {
      synchronized(this) {
        if(_ngUserDao == null) {
          _ngUserDao = new NgUserDao_Impl(this);
        }
        return _ngUserDao;
      }
    }
  }
}
