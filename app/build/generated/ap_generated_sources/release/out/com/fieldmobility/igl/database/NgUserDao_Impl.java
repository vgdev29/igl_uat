package com.fieldmobility.igl.database;

import android.database.Cursor;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.room.util.StringUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.fieldmobility.igl.Model.NguserListModel;
import java.lang.Boolean;
import java.lang.Integer;
import java.lang.Override;
import java.lang.String;
import java.lang.StringBuilder;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({"unchecked", "deprecation"})
public final class NgUserDao_Impl implements NgUserDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<NguserListModel> __insertionAdapterOfNguserListModel;

  private final EntityDeletionOrUpdateAdapter<NguserListModel> __deletionAdapterOfNguserListModel;

  private final EntityDeletionOrUpdateAdapter<NguserListModel> __updateAdapterOfNguserListModel;

  private final SharedSQLiteStatement __preparedStmtOfDeleteAll;

  public NgUserDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfNguserListModel = new EntityInsertionAdapter<NguserListModel>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `ng_users_table` (`id`,`jmr_no`,`bp_no`,`meter_no`,`customer_name`,`burner_details`,`ng_update_date`,`conversion_date`,`ca_no`,`meter_type`,`meter_make`,`initial_reading`,`mobile_no`,`rfc_date`,`amount_charged`,`house_no`,`block_qtr`,`society`,`city`,`zone`,`floor`,`street`,`area`,`home_address`,`meter_photo`,`service_photo`,`installation_photo`,`customer_sign`,`executive_sign`,`status`,`sub_status`,`remarks`,`delay_date`,`recording`,`hold_images`,`priority`,`claim`,`email_id`,`alt_number`,`landmark`,`contractor_id`,`tpi_id`,`zi_id`,`supervisor_id`,`crm_status`,`crm_reason`,`lead_no`,`start_job`,`code_group`,`old`,`pushed_to_crm`,`claim_date`,`cat_id`,`catalog`,`code`,`reason`,`control_room`,`rfc_initial_reading`,`supervisor_assigned_date`,`contractor_assigned_date`,`lattitude`,`longitude`,`corrected_meter_no`,`meter_status`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, NguserListModel value) {
        stmt.bindLong(1, value.getId());
        if (value.getJmr_no() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getJmr_no());
        }
        if (value.getBp_no() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getBp_no());
        }
        if (value.getMeter_no() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getMeter_no());
        }
        if (value.getCustomer_name() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getCustomer_name());
        }
        if (value.getBurner_details() == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, value.getBurner_details());
        }
        if (value.getNg_update_date() == null) {
          stmt.bindNull(7);
        } else {
          stmt.bindString(7, value.getNg_update_date());
        }
        if (value.getConversion_date() == null) {
          stmt.bindNull(8);
        } else {
          stmt.bindString(8, value.getConversion_date());
        }
        if (value.getCa_no() == null) {
          stmt.bindNull(9);
        } else {
          stmt.bindString(9, value.getCa_no());
        }
        if (value.getMeter_type() == null) {
          stmt.bindNull(10);
        } else {
          stmt.bindString(10, value.getMeter_type());
        }
        if (value.getMeter_make() == null) {
          stmt.bindNull(11);
        } else {
          stmt.bindString(11, value.getMeter_make());
        }
        if (value.getInitial_reading() == null) {
          stmt.bindNull(12);
        } else {
          stmt.bindString(12, value.getInitial_reading());
        }
        if (value.getMobile_no() == null) {
          stmt.bindNull(13);
        } else {
          stmt.bindString(13, value.getMobile_no());
        }
        if (value.getRfc_date() == null) {
          stmt.bindNull(14);
        } else {
          stmt.bindString(14, value.getRfc_date());
        }
        if (value.getAmount_charged() == null) {
          stmt.bindNull(15);
        } else {
          stmt.bindString(15, value.getAmount_charged());
        }
        if (value.getHouse_no() == null) {
          stmt.bindNull(16);
        } else {
          stmt.bindString(16, value.getHouse_no());
        }
        if (value.getBlock_qtr() == null) {
          stmt.bindNull(17);
        } else {
          stmt.bindString(17, value.getBlock_qtr());
        }
        if (value.getSociety() == null) {
          stmt.bindNull(18);
        } else {
          stmt.bindString(18, value.getSociety());
        }
        if (value.getCity() == null) {
          stmt.bindNull(19);
        } else {
          stmt.bindString(19, value.getCity());
        }
        if (value.getZone() == null) {
          stmt.bindNull(20);
        } else {
          stmt.bindString(20, value.getZone());
        }
        if (value.getFloor() == null) {
          stmt.bindNull(21);
        } else {
          stmt.bindString(21, value.getFloor());
        }
        if (value.getStreet() == null) {
          stmt.bindNull(22);
        } else {
          stmt.bindString(22, value.getStreet());
        }
        if (value.getArea() == null) {
          stmt.bindNull(23);
        } else {
          stmt.bindString(23, value.getArea());
        }
        if (value.getHome_address() == null) {
          stmt.bindNull(24);
        } else {
          stmt.bindString(24, value.getHome_address());
        }
        if (value.getMeter_photo() == null) {
          stmt.bindNull(25);
        } else {
          stmt.bindString(25, value.getMeter_photo());
        }
        if (value.getService_photo() == null) {
          stmt.bindNull(26);
        } else {
          stmt.bindString(26, value.getService_photo());
        }
        if (value.getInstallation_photo() == null) {
          stmt.bindNull(27);
        } else {
          stmt.bindString(27, value.getInstallation_photo());
        }
        if (value.getCustomer_sign() == null) {
          stmt.bindNull(28);
        } else {
          stmt.bindString(28, value.getCustomer_sign());
        }
        if (value.getExecutive_sign() == null) {
          stmt.bindNull(29);
        } else {
          stmt.bindString(29, value.getExecutive_sign());
        }
        if (value.getStatus() == null) {
          stmt.bindNull(30);
        } else {
          stmt.bindString(30, value.getStatus());
        }
        if (value.getSub_status() == null) {
          stmt.bindNull(31);
        } else {
          stmt.bindString(31, value.getSub_status());
        }
        if (value.getRemarks() == null) {
          stmt.bindNull(32);
        } else {
          stmt.bindString(32, value.getRemarks());
        }
        if (value.getDelay_date() == null) {
          stmt.bindNull(33);
        } else {
          stmt.bindString(33, value.getDelay_date());
        }
        if (value.getRecording() == null) {
          stmt.bindNull(34);
        } else {
          stmt.bindString(34, value.getRecording());
        }
        if (value.getHold_images() == null) {
          stmt.bindNull(35);
        } else {
          stmt.bindString(35, value.getHold_images());
        }
        if (value.getPriority() == null) {
          stmt.bindNull(36);
        } else {
          stmt.bindString(36, value.getPriority());
        }
        final Integer _tmp;
        _tmp = value.getClaim() == null ? null : (value.getClaim() ? 1 : 0);
        if (_tmp == null) {
          stmt.bindNull(37);
        } else {
          stmt.bindLong(37, _tmp);
        }
        if (value.getEmail_id() == null) {
          stmt.bindNull(38);
        } else {
          stmt.bindString(38, value.getEmail_id());
        }
        if (value.getAlt_number() == null) {
          stmt.bindNull(39);
        } else {
          stmt.bindString(39, value.getAlt_number());
        }
        if (value.getLandmark() == null) {
          stmt.bindNull(40);
        } else {
          stmt.bindString(40, value.getLandmark());
        }
        if (value.getContractor_id() == null) {
          stmt.bindNull(41);
        } else {
          stmt.bindString(41, value.getContractor_id());
        }
        if (value.getTpi_id() == null) {
          stmt.bindNull(42);
        } else {
          stmt.bindString(42, value.getTpi_id());
        }
        if (value.getZi_id() == null) {
          stmt.bindNull(43);
        } else {
          stmt.bindString(43, value.getZi_id());
        }
        if (value.getSupervisor_id() == null) {
          stmt.bindNull(44);
        } else {
          stmt.bindString(44, value.getSupervisor_id());
        }
        if (value.getCrm_status() == null) {
          stmt.bindNull(45);
        } else {
          stmt.bindString(45, value.getCrm_status());
        }
        if (value.getCrm_reason() == null) {
          stmt.bindNull(46);
        } else {
          stmt.bindString(46, value.getCrm_reason());
        }
        if (value.getLead_no() == null) {
          stmt.bindNull(47);
        } else {
          stmt.bindString(47, value.getLead_no());
        }
        final Integer _tmp_1;
        _tmp_1 = value.getStart_job() == null ? null : (value.getStart_job() ? 1 : 0);
        if (_tmp_1 == null) {
          stmt.bindNull(48);
        } else {
          stmt.bindLong(48, _tmp_1);
        }
        if (value.getCode_group() == null) {
          stmt.bindNull(49);
        } else {
          stmt.bindString(49, value.getCode_group());
        }
        final Integer _tmp_2;
        _tmp_2 = value.getOld() == null ? null : (value.getOld() ? 1 : 0);
        if (_tmp_2 == null) {
          stmt.bindNull(50);
        } else {
          stmt.bindLong(50, _tmp_2);
        }
        stmt.bindLong(51, value.getPushed_to_crm());
        if (value.getClaim_date() == null) {
          stmt.bindNull(52);
        } else {
          stmt.bindString(52, value.getClaim_date());
        }
        if (value.getCat_id() == null) {
          stmt.bindNull(53);
        } else {
          stmt.bindString(53, value.getCat_id());
        }
        if (value.getCatalog() == null) {
          stmt.bindNull(54);
        } else {
          stmt.bindString(54, value.getCatalog());
        }
        if (value.getCode() == null) {
          stmt.bindNull(55);
        } else {
          stmt.bindString(55, value.getCode());
        }
        if (value.getReason() == null) {
          stmt.bindNull(56);
        } else {
          stmt.bindString(56, value.getReason());
        }
        if (value.getControl_room() == null) {
          stmt.bindNull(57);
        } else {
          stmt.bindString(57, value.getControl_room());
        }
        if (value.getRfc_initial_reading() == null) {
          stmt.bindNull(58);
        } else {
          stmt.bindString(58, value.getRfc_initial_reading());
        }
        if (value.getSupervisor_assigned_date() == null) {
          stmt.bindNull(59);
        } else {
          stmt.bindString(59, value.getSupervisor_assigned_date());
        }
        if (value.getContractor_assigned_date() == null) {
          stmt.bindNull(60);
        } else {
          stmt.bindString(60, value.getContractor_assigned_date());
        }
        if (value.getLattitude() == null) {
          stmt.bindNull(61);
        } else {
          stmt.bindString(61, value.getLattitude());
        }
        if (value.getLongitude() == null) {
          stmt.bindNull(62);
        } else {
          stmt.bindString(62, value.getLongitude());
        }
        if (value.getCorrected_meter_no() == null) {
          stmt.bindNull(63);
        } else {
          stmt.bindString(63, value.getCorrected_meter_no());
        }
        final int _tmp_3;
        _tmp_3 = value.isMeter_status() ? 1 : 0;
        stmt.bindLong(64, _tmp_3);
      }
    };
    this.__deletionAdapterOfNguserListModel = new EntityDeletionOrUpdateAdapter<NguserListModel>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `ng_users_table` WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, NguserListModel value) {
        stmt.bindLong(1, value.getId());
      }
    };
    this.__updateAdapterOfNguserListModel = new EntityDeletionOrUpdateAdapter<NguserListModel>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `ng_users_table` SET `id` = ?,`jmr_no` = ?,`bp_no` = ?,`meter_no` = ?,`customer_name` = ?,`burner_details` = ?,`ng_update_date` = ?,`conversion_date` = ?,`ca_no` = ?,`meter_type` = ?,`meter_make` = ?,`initial_reading` = ?,`mobile_no` = ?,`rfc_date` = ?,`amount_charged` = ?,`house_no` = ?,`block_qtr` = ?,`society` = ?,`city` = ?,`zone` = ?,`floor` = ?,`street` = ?,`area` = ?,`home_address` = ?,`meter_photo` = ?,`service_photo` = ?,`installation_photo` = ?,`customer_sign` = ?,`executive_sign` = ?,`status` = ?,`sub_status` = ?,`remarks` = ?,`delay_date` = ?,`recording` = ?,`hold_images` = ?,`priority` = ?,`claim` = ?,`email_id` = ?,`alt_number` = ?,`landmark` = ?,`contractor_id` = ?,`tpi_id` = ?,`zi_id` = ?,`supervisor_id` = ?,`crm_status` = ?,`crm_reason` = ?,`lead_no` = ?,`start_job` = ?,`code_group` = ?,`old` = ?,`pushed_to_crm` = ?,`claim_date` = ?,`cat_id` = ?,`catalog` = ?,`code` = ?,`reason` = ?,`control_room` = ?,`rfc_initial_reading` = ?,`supervisor_assigned_date` = ?,`contractor_assigned_date` = ?,`lattitude` = ?,`longitude` = ?,`corrected_meter_no` = ?,`meter_status` = ? WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, NguserListModel value) {
        stmt.bindLong(1, value.getId());
        if (value.getJmr_no() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getJmr_no());
        }
        if (value.getBp_no() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getBp_no());
        }
        if (value.getMeter_no() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getMeter_no());
        }
        if (value.getCustomer_name() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getCustomer_name());
        }
        if (value.getBurner_details() == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, value.getBurner_details());
        }
        if (value.getNg_update_date() == null) {
          stmt.bindNull(7);
        } else {
          stmt.bindString(7, value.getNg_update_date());
        }
        if (value.getConversion_date() == null) {
          stmt.bindNull(8);
        } else {
          stmt.bindString(8, value.getConversion_date());
        }
        if (value.getCa_no() == null) {
          stmt.bindNull(9);
        } else {
          stmt.bindString(9, value.getCa_no());
        }
        if (value.getMeter_type() == null) {
          stmt.bindNull(10);
        } else {
          stmt.bindString(10, value.getMeter_type());
        }
        if (value.getMeter_make() == null) {
          stmt.bindNull(11);
        } else {
          stmt.bindString(11, value.getMeter_make());
        }
        if (value.getInitial_reading() == null) {
          stmt.bindNull(12);
        } else {
          stmt.bindString(12, value.getInitial_reading());
        }
        if (value.getMobile_no() == null) {
          stmt.bindNull(13);
        } else {
          stmt.bindString(13, value.getMobile_no());
        }
        if (value.getRfc_date() == null) {
          stmt.bindNull(14);
        } else {
          stmt.bindString(14, value.getRfc_date());
        }
        if (value.getAmount_charged() == null) {
          stmt.bindNull(15);
        } else {
          stmt.bindString(15, value.getAmount_charged());
        }
        if (value.getHouse_no() == null) {
          stmt.bindNull(16);
        } else {
          stmt.bindString(16, value.getHouse_no());
        }
        if (value.getBlock_qtr() == null) {
          stmt.bindNull(17);
        } else {
          stmt.bindString(17, value.getBlock_qtr());
        }
        if (value.getSociety() == null) {
          stmt.bindNull(18);
        } else {
          stmt.bindString(18, value.getSociety());
        }
        if (value.getCity() == null) {
          stmt.bindNull(19);
        } else {
          stmt.bindString(19, value.getCity());
        }
        if (value.getZone() == null) {
          stmt.bindNull(20);
        } else {
          stmt.bindString(20, value.getZone());
        }
        if (value.getFloor() == null) {
          stmt.bindNull(21);
        } else {
          stmt.bindString(21, value.getFloor());
        }
        if (value.getStreet() == null) {
          stmt.bindNull(22);
        } else {
          stmt.bindString(22, value.getStreet());
        }
        if (value.getArea() == null) {
          stmt.bindNull(23);
        } else {
          stmt.bindString(23, value.getArea());
        }
        if (value.getHome_address() == null) {
          stmt.bindNull(24);
        } else {
          stmt.bindString(24, value.getHome_address());
        }
        if (value.getMeter_photo() == null) {
          stmt.bindNull(25);
        } else {
          stmt.bindString(25, value.getMeter_photo());
        }
        if (value.getService_photo() == null) {
          stmt.bindNull(26);
        } else {
          stmt.bindString(26, value.getService_photo());
        }
        if (value.getInstallation_photo() == null) {
          stmt.bindNull(27);
        } else {
          stmt.bindString(27, value.getInstallation_photo());
        }
        if (value.getCustomer_sign() == null) {
          stmt.bindNull(28);
        } else {
          stmt.bindString(28, value.getCustomer_sign());
        }
        if (value.getExecutive_sign() == null) {
          stmt.bindNull(29);
        } else {
          stmt.bindString(29, value.getExecutive_sign());
        }
        if (value.getStatus() == null) {
          stmt.bindNull(30);
        } else {
          stmt.bindString(30, value.getStatus());
        }
        if (value.getSub_status() == null) {
          stmt.bindNull(31);
        } else {
          stmt.bindString(31, value.getSub_status());
        }
        if (value.getRemarks() == null) {
          stmt.bindNull(32);
        } else {
          stmt.bindString(32, value.getRemarks());
        }
        if (value.getDelay_date() == null) {
          stmt.bindNull(33);
        } else {
          stmt.bindString(33, value.getDelay_date());
        }
        if (value.getRecording() == null) {
          stmt.bindNull(34);
        } else {
          stmt.bindString(34, value.getRecording());
        }
        if (value.getHold_images() == null) {
          stmt.bindNull(35);
        } else {
          stmt.bindString(35, value.getHold_images());
        }
        if (value.getPriority() == null) {
          stmt.bindNull(36);
        } else {
          stmt.bindString(36, value.getPriority());
        }
        final Integer _tmp;
        _tmp = value.getClaim() == null ? null : (value.getClaim() ? 1 : 0);
        if (_tmp == null) {
          stmt.bindNull(37);
        } else {
          stmt.bindLong(37, _tmp);
        }
        if (value.getEmail_id() == null) {
          stmt.bindNull(38);
        } else {
          stmt.bindString(38, value.getEmail_id());
        }
        if (value.getAlt_number() == null) {
          stmt.bindNull(39);
        } else {
          stmt.bindString(39, value.getAlt_number());
        }
        if (value.getLandmark() == null) {
          stmt.bindNull(40);
        } else {
          stmt.bindString(40, value.getLandmark());
        }
        if (value.getContractor_id() == null) {
          stmt.bindNull(41);
        } else {
          stmt.bindString(41, value.getContractor_id());
        }
        if (value.getTpi_id() == null) {
          stmt.bindNull(42);
        } else {
          stmt.bindString(42, value.getTpi_id());
        }
        if (value.getZi_id() == null) {
          stmt.bindNull(43);
        } else {
          stmt.bindString(43, value.getZi_id());
        }
        if (value.getSupervisor_id() == null) {
          stmt.bindNull(44);
        } else {
          stmt.bindString(44, value.getSupervisor_id());
        }
        if (value.getCrm_status() == null) {
          stmt.bindNull(45);
        } else {
          stmt.bindString(45, value.getCrm_status());
        }
        if (value.getCrm_reason() == null) {
          stmt.bindNull(46);
        } else {
          stmt.bindString(46, value.getCrm_reason());
        }
        if (value.getLead_no() == null) {
          stmt.bindNull(47);
        } else {
          stmt.bindString(47, value.getLead_no());
        }
        final Integer _tmp_1;
        _tmp_1 = value.getStart_job() == null ? null : (value.getStart_job() ? 1 : 0);
        if (_tmp_1 == null) {
          stmt.bindNull(48);
        } else {
          stmt.bindLong(48, _tmp_1);
        }
        if (value.getCode_group() == null) {
          stmt.bindNull(49);
        } else {
          stmt.bindString(49, value.getCode_group());
        }
        final Integer _tmp_2;
        _tmp_2 = value.getOld() == null ? null : (value.getOld() ? 1 : 0);
        if (_tmp_2 == null) {
          stmt.bindNull(50);
        } else {
          stmt.bindLong(50, _tmp_2);
        }
        stmt.bindLong(51, value.getPushed_to_crm());
        if (value.getClaim_date() == null) {
          stmt.bindNull(52);
        } else {
          stmt.bindString(52, value.getClaim_date());
        }
        if (value.getCat_id() == null) {
          stmt.bindNull(53);
        } else {
          stmt.bindString(53, value.getCat_id());
        }
        if (value.getCatalog() == null) {
          stmt.bindNull(54);
        } else {
          stmt.bindString(54, value.getCatalog());
        }
        if (value.getCode() == null) {
          stmt.bindNull(55);
        } else {
          stmt.bindString(55, value.getCode());
        }
        if (value.getReason() == null) {
          stmt.bindNull(56);
        } else {
          stmt.bindString(56, value.getReason());
        }
        if (value.getControl_room() == null) {
          stmt.bindNull(57);
        } else {
          stmt.bindString(57, value.getControl_room());
        }
        if (value.getRfc_initial_reading() == null) {
          stmt.bindNull(58);
        } else {
          stmt.bindString(58, value.getRfc_initial_reading());
        }
        if (value.getSupervisor_assigned_date() == null) {
          stmt.bindNull(59);
        } else {
          stmt.bindString(59, value.getSupervisor_assigned_date());
        }
        if (value.getContractor_assigned_date() == null) {
          stmt.bindNull(60);
        } else {
          stmt.bindString(60, value.getContractor_assigned_date());
        }
        if (value.getLattitude() == null) {
          stmt.bindNull(61);
        } else {
          stmt.bindString(61, value.getLattitude());
        }
        if (value.getLongitude() == null) {
          stmt.bindNull(62);
        } else {
          stmt.bindString(62, value.getLongitude());
        }
        if (value.getCorrected_meter_no() == null) {
          stmt.bindNull(63);
        } else {
          stmt.bindString(63, value.getCorrected_meter_no());
        }
        final int _tmp_3;
        _tmp_3 = value.isMeter_status() ? 1 : 0;
        stmt.bindLong(64, _tmp_3);
        stmt.bindLong(65, value.getId());
      }
    };
    this.__preparedStmtOfDeleteAll = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE FROM ng_users_table";
        return _query;
      }
    };
  }

  @Override
  public void insert(final NguserListModel user) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfNguserListModel.insert(user);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void delete(final NguserListModel user) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __deletionAdapterOfNguserListModel.handle(user);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void update(final NguserListModel user) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __updateAdapterOfNguserListModel.handle(user);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void deleteAll() {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteAll.acquire();
    __db.beginTransaction();
    try {
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
      __preparedStmtOfDeleteAll.release(_stmt);
    }
  }

  @Override
  public List<NguserListModel> getAllUsers() {
    final String _sql = "SELECT * FROM ng_users_table";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfJmrNo = CursorUtil.getColumnIndexOrThrow(_cursor, "jmr_no");
      final int _cursorIndexOfBpNo = CursorUtil.getColumnIndexOrThrow(_cursor, "bp_no");
      final int _cursorIndexOfMeterNo = CursorUtil.getColumnIndexOrThrow(_cursor, "meter_no");
      final int _cursorIndexOfCustomerName = CursorUtil.getColumnIndexOrThrow(_cursor, "customer_name");
      final int _cursorIndexOfBurnerDetails = CursorUtil.getColumnIndexOrThrow(_cursor, "burner_details");
      final int _cursorIndexOfNgUpdateDate = CursorUtil.getColumnIndexOrThrow(_cursor, "ng_update_date");
      final int _cursorIndexOfConversionDate = CursorUtil.getColumnIndexOrThrow(_cursor, "conversion_date");
      final int _cursorIndexOfCaNo = CursorUtil.getColumnIndexOrThrow(_cursor, "ca_no");
      final int _cursorIndexOfMeterType = CursorUtil.getColumnIndexOrThrow(_cursor, "meter_type");
      final int _cursorIndexOfMeterMake = CursorUtil.getColumnIndexOrThrow(_cursor, "meter_make");
      final int _cursorIndexOfInitialReading = CursorUtil.getColumnIndexOrThrow(_cursor, "initial_reading");
      final int _cursorIndexOfMobileNo = CursorUtil.getColumnIndexOrThrow(_cursor, "mobile_no");
      final int _cursorIndexOfRfcDate = CursorUtil.getColumnIndexOrThrow(_cursor, "rfc_date");
      final int _cursorIndexOfAmountCharged = CursorUtil.getColumnIndexOrThrow(_cursor, "amount_charged");
      final int _cursorIndexOfHouseNo = CursorUtil.getColumnIndexOrThrow(_cursor, "house_no");
      final int _cursorIndexOfBlockQtr = CursorUtil.getColumnIndexOrThrow(_cursor, "block_qtr");
      final int _cursorIndexOfSociety = CursorUtil.getColumnIndexOrThrow(_cursor, "society");
      final int _cursorIndexOfCity = CursorUtil.getColumnIndexOrThrow(_cursor, "city");
      final int _cursorIndexOfZone = CursorUtil.getColumnIndexOrThrow(_cursor, "zone");
      final int _cursorIndexOfFloor = CursorUtil.getColumnIndexOrThrow(_cursor, "floor");
      final int _cursorIndexOfStreet = CursorUtil.getColumnIndexOrThrow(_cursor, "street");
      final int _cursorIndexOfArea = CursorUtil.getColumnIndexOrThrow(_cursor, "area");
      final int _cursorIndexOfHomeAddress = CursorUtil.getColumnIndexOrThrow(_cursor, "home_address");
      final int _cursorIndexOfMeterPhoto = CursorUtil.getColumnIndexOrThrow(_cursor, "meter_photo");
      final int _cursorIndexOfServicePhoto = CursorUtil.getColumnIndexOrThrow(_cursor, "service_photo");
      final int _cursorIndexOfInstallationPhoto = CursorUtil.getColumnIndexOrThrow(_cursor, "installation_photo");
      final int _cursorIndexOfCustomerSign = CursorUtil.getColumnIndexOrThrow(_cursor, "customer_sign");
      final int _cursorIndexOfExecutiveSign = CursorUtil.getColumnIndexOrThrow(_cursor, "executive_sign");
      final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
      final int _cursorIndexOfSubStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "sub_status");
      final int _cursorIndexOfRemarks = CursorUtil.getColumnIndexOrThrow(_cursor, "remarks");
      final int _cursorIndexOfDelayDate = CursorUtil.getColumnIndexOrThrow(_cursor, "delay_date");
      final int _cursorIndexOfRecording = CursorUtil.getColumnIndexOrThrow(_cursor, "recording");
      final int _cursorIndexOfHoldImages = CursorUtil.getColumnIndexOrThrow(_cursor, "hold_images");
      final int _cursorIndexOfPriority = CursorUtil.getColumnIndexOrThrow(_cursor, "priority");
      final int _cursorIndexOfClaim = CursorUtil.getColumnIndexOrThrow(_cursor, "claim");
      final int _cursorIndexOfEmailId = CursorUtil.getColumnIndexOrThrow(_cursor, "email_id");
      final int _cursorIndexOfAltNumber = CursorUtil.getColumnIndexOrThrow(_cursor, "alt_number");
      final int _cursorIndexOfLandmark = CursorUtil.getColumnIndexOrThrow(_cursor, "landmark");
      final int _cursorIndexOfContractorId = CursorUtil.getColumnIndexOrThrow(_cursor, "contractor_id");
      final int _cursorIndexOfTpiId = CursorUtil.getColumnIndexOrThrow(_cursor, "tpi_id");
      final int _cursorIndexOfZiId = CursorUtil.getColumnIndexOrThrow(_cursor, "zi_id");
      final int _cursorIndexOfSupervisorId = CursorUtil.getColumnIndexOrThrow(_cursor, "supervisor_id");
      final int _cursorIndexOfCrmStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "crm_status");
      final int _cursorIndexOfCrmReason = CursorUtil.getColumnIndexOrThrow(_cursor, "crm_reason");
      final int _cursorIndexOfLeadNo = CursorUtil.getColumnIndexOrThrow(_cursor, "lead_no");
      final int _cursorIndexOfStartJob = CursorUtil.getColumnIndexOrThrow(_cursor, "start_job");
      final int _cursorIndexOfCodeGroup = CursorUtil.getColumnIndexOrThrow(_cursor, "code_group");
      final int _cursorIndexOfOld = CursorUtil.getColumnIndexOrThrow(_cursor, "old");
      final int _cursorIndexOfPushedToCrm = CursorUtil.getColumnIndexOrThrow(_cursor, "pushed_to_crm");
      final int _cursorIndexOfClaimDate = CursorUtil.getColumnIndexOrThrow(_cursor, "claim_date");
      final int _cursorIndexOfCatId = CursorUtil.getColumnIndexOrThrow(_cursor, "cat_id");
      final int _cursorIndexOfCatalog = CursorUtil.getColumnIndexOrThrow(_cursor, "catalog");
      final int _cursorIndexOfCode = CursorUtil.getColumnIndexOrThrow(_cursor, "code");
      final int _cursorIndexOfReason = CursorUtil.getColumnIndexOrThrow(_cursor, "reason");
      final int _cursorIndexOfControlRoom = CursorUtil.getColumnIndexOrThrow(_cursor, "control_room");
      final int _cursorIndexOfRfcInitialReading = CursorUtil.getColumnIndexOrThrow(_cursor, "rfc_initial_reading");
      final int _cursorIndexOfSupervisorAssignedDate = CursorUtil.getColumnIndexOrThrow(_cursor, "supervisor_assigned_date");
      final int _cursorIndexOfContractorAssignedDate = CursorUtil.getColumnIndexOrThrow(_cursor, "contractor_assigned_date");
      final int _cursorIndexOfLattitude = CursorUtil.getColumnIndexOrThrow(_cursor, "lattitude");
      final int _cursorIndexOfLongitude = CursorUtil.getColumnIndexOrThrow(_cursor, "longitude");
      final int _cursorIndexOfCorrectedMeterNo = CursorUtil.getColumnIndexOrThrow(_cursor, "corrected_meter_no");
      final int _cursorIndexOfMeterStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "meter_status");
      final List<NguserListModel> _result = new ArrayList<NguserListModel>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final NguserListModel _item;
        _item = new NguserListModel();
        final int _tmpId;
        _tmpId = _cursor.getInt(_cursorIndexOfId);
        _item.setId(_tmpId);
        final String _tmpJmr_no;
        _tmpJmr_no = _cursor.getString(_cursorIndexOfJmrNo);
        _item.setJmr_no(_tmpJmr_no);
        final String _tmpBp_no;
        _tmpBp_no = _cursor.getString(_cursorIndexOfBpNo);
        _item.setBp_no(_tmpBp_no);
        final String _tmpMeter_no;
        _tmpMeter_no = _cursor.getString(_cursorIndexOfMeterNo);
        _item.setMeter_no(_tmpMeter_no);
        final String _tmpCustomer_name;
        _tmpCustomer_name = _cursor.getString(_cursorIndexOfCustomerName);
        _item.setCustomer_name(_tmpCustomer_name);
        final String _tmpBurner_details;
        _tmpBurner_details = _cursor.getString(_cursorIndexOfBurnerDetails);
        _item.setBurner_details(_tmpBurner_details);
        final String _tmpNg_update_date;
        _tmpNg_update_date = _cursor.getString(_cursorIndexOfNgUpdateDate);
        _item.setNg_update_date(_tmpNg_update_date);
        final String _tmpConversion_date;
        _tmpConversion_date = _cursor.getString(_cursorIndexOfConversionDate);
        _item.setConversion_date(_tmpConversion_date);
        final String _tmpCa_no;
        _tmpCa_no = _cursor.getString(_cursorIndexOfCaNo);
        _item.setCa_no(_tmpCa_no);
        final String _tmpMeter_type;
        _tmpMeter_type = _cursor.getString(_cursorIndexOfMeterType);
        _item.setMeter_type(_tmpMeter_type);
        final String _tmpMeter_make;
        _tmpMeter_make = _cursor.getString(_cursorIndexOfMeterMake);
        _item.setMeter_make(_tmpMeter_make);
        final String _tmpInitial_reading;
        _tmpInitial_reading = _cursor.getString(_cursorIndexOfInitialReading);
        _item.setInitial_reading(_tmpInitial_reading);
        final String _tmpMobile_no;
        _tmpMobile_no = _cursor.getString(_cursorIndexOfMobileNo);
        _item.setMobile_no(_tmpMobile_no);
        final String _tmpRfc_date;
        _tmpRfc_date = _cursor.getString(_cursorIndexOfRfcDate);
        _item.setRfc_date(_tmpRfc_date);
        final String _tmpAmount_charged;
        _tmpAmount_charged = _cursor.getString(_cursorIndexOfAmountCharged);
        _item.setAmount_charged(_tmpAmount_charged);
        final String _tmpHouse_no;
        _tmpHouse_no = _cursor.getString(_cursorIndexOfHouseNo);
        _item.setHouse_no(_tmpHouse_no);
        final String _tmpBlock_qtr;
        _tmpBlock_qtr = _cursor.getString(_cursorIndexOfBlockQtr);
        _item.setBlock_qtr(_tmpBlock_qtr);
        final String _tmpSociety;
        _tmpSociety = _cursor.getString(_cursorIndexOfSociety);
        _item.setSociety(_tmpSociety);
        final String _tmpCity;
        _tmpCity = _cursor.getString(_cursorIndexOfCity);
        _item.setCity(_tmpCity);
        final String _tmpZone;
        _tmpZone = _cursor.getString(_cursorIndexOfZone);
        _item.setZone(_tmpZone);
        final String _tmpFloor;
        _tmpFloor = _cursor.getString(_cursorIndexOfFloor);
        _item.setFloor(_tmpFloor);
        final String _tmpStreet;
        _tmpStreet = _cursor.getString(_cursorIndexOfStreet);
        _item.setStreet(_tmpStreet);
        final String _tmpArea;
        _tmpArea = _cursor.getString(_cursorIndexOfArea);
        _item.setArea(_tmpArea);
        final String _tmpHome_address;
        _tmpHome_address = _cursor.getString(_cursorIndexOfHomeAddress);
        _item.setHome_address(_tmpHome_address);
        final String _tmpMeter_photo;
        _tmpMeter_photo = _cursor.getString(_cursorIndexOfMeterPhoto);
        _item.setMeter_photo(_tmpMeter_photo);
        final String _tmpService_photo;
        _tmpService_photo = _cursor.getString(_cursorIndexOfServicePhoto);
        _item.setService_photo(_tmpService_photo);
        final String _tmpInstallation_photo;
        _tmpInstallation_photo = _cursor.getString(_cursorIndexOfInstallationPhoto);
        _item.setInstallation_photo(_tmpInstallation_photo);
        final String _tmpCustomer_sign;
        _tmpCustomer_sign = _cursor.getString(_cursorIndexOfCustomerSign);
        _item.setCustomer_sign(_tmpCustomer_sign);
        final String _tmpExecutive_sign;
        _tmpExecutive_sign = _cursor.getString(_cursorIndexOfExecutiveSign);
        _item.setExecutive_sign(_tmpExecutive_sign);
        final String _tmpStatus;
        _tmpStatus = _cursor.getString(_cursorIndexOfStatus);
        _item.setStatus(_tmpStatus);
        final String _tmpSub_status;
        _tmpSub_status = _cursor.getString(_cursorIndexOfSubStatus);
        _item.setSub_status(_tmpSub_status);
        final String _tmpRemarks;
        _tmpRemarks = _cursor.getString(_cursorIndexOfRemarks);
        _item.setRemarks(_tmpRemarks);
        final String _tmpDelay_date;
        _tmpDelay_date = _cursor.getString(_cursorIndexOfDelayDate);
        _item.setDelay_date(_tmpDelay_date);
        final String _tmpRecording;
        _tmpRecording = _cursor.getString(_cursorIndexOfRecording);
        _item.setRecording(_tmpRecording);
        final String _tmpHold_images;
        _tmpHold_images = _cursor.getString(_cursorIndexOfHoldImages);
        _item.setHold_images(_tmpHold_images);
        final String _tmpPriority;
        _tmpPriority = _cursor.getString(_cursorIndexOfPriority);
        _item.setPriority(_tmpPriority);
        final Boolean _tmpClaim;
        final Integer _tmp;
        if (_cursor.isNull(_cursorIndexOfClaim)) {
          _tmp = null;
        } else {
          _tmp = _cursor.getInt(_cursorIndexOfClaim);
        }
        _tmpClaim = _tmp == null ? null : _tmp != 0;
        _item.setClaim(_tmpClaim);
        final String _tmpEmail_id;
        _tmpEmail_id = _cursor.getString(_cursorIndexOfEmailId);
        _item.setEmail_id(_tmpEmail_id);
        final String _tmpAlt_number;
        _tmpAlt_number = _cursor.getString(_cursorIndexOfAltNumber);
        _item.setAlt_number(_tmpAlt_number);
        final String _tmpLandmark;
        _tmpLandmark = _cursor.getString(_cursorIndexOfLandmark);
        _item.setLandmark(_tmpLandmark);
        final String _tmpContractor_id;
        _tmpContractor_id = _cursor.getString(_cursorIndexOfContractorId);
        _item.setContractor_id(_tmpContractor_id);
        final String _tmpTpi_id;
        _tmpTpi_id = _cursor.getString(_cursorIndexOfTpiId);
        _item.setTpi_id(_tmpTpi_id);
        final String _tmpZi_id;
        _tmpZi_id = _cursor.getString(_cursorIndexOfZiId);
        _item.setZi_id(_tmpZi_id);
        final String _tmpSupervisor_id;
        _tmpSupervisor_id = _cursor.getString(_cursorIndexOfSupervisorId);
        _item.setSupervisor_id(_tmpSupervisor_id);
        final String _tmpCrm_status;
        _tmpCrm_status = _cursor.getString(_cursorIndexOfCrmStatus);
        _item.setCrm_status(_tmpCrm_status);
        final String _tmpCrm_reason;
        _tmpCrm_reason = _cursor.getString(_cursorIndexOfCrmReason);
        _item.setCrm_reason(_tmpCrm_reason);
        final String _tmpLead_no;
        _tmpLead_no = _cursor.getString(_cursorIndexOfLeadNo);
        _item.setLead_no(_tmpLead_no);
        final Boolean _tmpStart_job;
        final Integer _tmp_1;
        if (_cursor.isNull(_cursorIndexOfStartJob)) {
          _tmp_1 = null;
        } else {
          _tmp_1 = _cursor.getInt(_cursorIndexOfStartJob);
        }
        _tmpStart_job = _tmp_1 == null ? null : _tmp_1 != 0;
        _item.setStart_job(_tmpStart_job);
        final String _tmpCode_group;
        _tmpCode_group = _cursor.getString(_cursorIndexOfCodeGroup);
        _item.setCode_group(_tmpCode_group);
        final Boolean _tmpOld;
        final Integer _tmp_2;
        if (_cursor.isNull(_cursorIndexOfOld)) {
          _tmp_2 = null;
        } else {
          _tmp_2 = _cursor.getInt(_cursorIndexOfOld);
        }
        _tmpOld = _tmp_2 == null ? null : _tmp_2 != 0;
        _item.setOld(_tmpOld);
        final int _tmpPushed_to_crm;
        _tmpPushed_to_crm = _cursor.getInt(_cursorIndexOfPushedToCrm);
        _item.setPushed_to_crm(_tmpPushed_to_crm);
        final String _tmpClaim_date;
        _tmpClaim_date = _cursor.getString(_cursorIndexOfClaimDate);
        _item.setClaim_date(_tmpClaim_date);
        final String _tmpCat_id;
        _tmpCat_id = _cursor.getString(_cursorIndexOfCatId);
        _item.setCat_id(_tmpCat_id);
        final String _tmpCatalog;
        _tmpCatalog = _cursor.getString(_cursorIndexOfCatalog);
        _item.setCatalog(_tmpCatalog);
        final String _tmpCode;
        _tmpCode = _cursor.getString(_cursorIndexOfCode);
        _item.setCode(_tmpCode);
        final String _tmpReason;
        _tmpReason = _cursor.getString(_cursorIndexOfReason);
        _item.setReason(_tmpReason);
        final String _tmpControl_room;
        _tmpControl_room = _cursor.getString(_cursorIndexOfControlRoom);
        _item.setControl_room(_tmpControl_room);
        final String _tmpRfc_initial_reading;
        _tmpRfc_initial_reading = _cursor.getString(_cursorIndexOfRfcInitialReading);
        _item.setRfc_initial_reading(_tmpRfc_initial_reading);
        final String _tmpSupervisor_assigned_date;
        _tmpSupervisor_assigned_date = _cursor.getString(_cursorIndexOfSupervisorAssignedDate);
        _item.setSupervisor_assigned_date(_tmpSupervisor_assigned_date);
        final String _tmpContractor_assigned_date;
        _tmpContractor_assigned_date = _cursor.getString(_cursorIndexOfContractorAssignedDate);
        _item.setContractor_assigned_date(_tmpContractor_assigned_date);
        final String _tmpLattitude;
        _tmpLattitude = _cursor.getString(_cursorIndexOfLattitude);
        _item.setLattitude(_tmpLattitude);
        final String _tmpLongitude;
        _tmpLongitude = _cursor.getString(_cursorIndexOfLongitude);
        _item.setLongitude(_tmpLongitude);
        final String _tmpCorrected_meter_no;
        _tmpCorrected_meter_no = _cursor.getString(_cursorIndexOfCorrectedMeterNo);
        _item.setCorrected_meter_no(_tmpCorrected_meter_no);
        final boolean _tmpMeter_status;
        final int _tmp_3;
        _tmp_3 = _cursor.getInt(_cursorIndexOfMeterStatus);
        _tmpMeter_status = _tmp_3 != 0;
        _item.setMeter_status(_tmpMeter_status);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public List<NguserListModel> getDPUsers(final String DP) {
    final String _sql = "SELECT * FROM ng_users_table WHERE status LIKE ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (DP == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, DP);
    }
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfJmrNo = CursorUtil.getColumnIndexOrThrow(_cursor, "jmr_no");
      final int _cursorIndexOfBpNo = CursorUtil.getColumnIndexOrThrow(_cursor, "bp_no");
      final int _cursorIndexOfMeterNo = CursorUtil.getColumnIndexOrThrow(_cursor, "meter_no");
      final int _cursorIndexOfCustomerName = CursorUtil.getColumnIndexOrThrow(_cursor, "customer_name");
      final int _cursorIndexOfBurnerDetails = CursorUtil.getColumnIndexOrThrow(_cursor, "burner_details");
      final int _cursorIndexOfNgUpdateDate = CursorUtil.getColumnIndexOrThrow(_cursor, "ng_update_date");
      final int _cursorIndexOfConversionDate = CursorUtil.getColumnIndexOrThrow(_cursor, "conversion_date");
      final int _cursorIndexOfCaNo = CursorUtil.getColumnIndexOrThrow(_cursor, "ca_no");
      final int _cursorIndexOfMeterType = CursorUtil.getColumnIndexOrThrow(_cursor, "meter_type");
      final int _cursorIndexOfMeterMake = CursorUtil.getColumnIndexOrThrow(_cursor, "meter_make");
      final int _cursorIndexOfInitialReading = CursorUtil.getColumnIndexOrThrow(_cursor, "initial_reading");
      final int _cursorIndexOfMobileNo = CursorUtil.getColumnIndexOrThrow(_cursor, "mobile_no");
      final int _cursorIndexOfRfcDate = CursorUtil.getColumnIndexOrThrow(_cursor, "rfc_date");
      final int _cursorIndexOfAmountCharged = CursorUtil.getColumnIndexOrThrow(_cursor, "amount_charged");
      final int _cursorIndexOfHouseNo = CursorUtil.getColumnIndexOrThrow(_cursor, "house_no");
      final int _cursorIndexOfBlockQtr = CursorUtil.getColumnIndexOrThrow(_cursor, "block_qtr");
      final int _cursorIndexOfSociety = CursorUtil.getColumnIndexOrThrow(_cursor, "society");
      final int _cursorIndexOfCity = CursorUtil.getColumnIndexOrThrow(_cursor, "city");
      final int _cursorIndexOfZone = CursorUtil.getColumnIndexOrThrow(_cursor, "zone");
      final int _cursorIndexOfFloor = CursorUtil.getColumnIndexOrThrow(_cursor, "floor");
      final int _cursorIndexOfStreet = CursorUtil.getColumnIndexOrThrow(_cursor, "street");
      final int _cursorIndexOfArea = CursorUtil.getColumnIndexOrThrow(_cursor, "area");
      final int _cursorIndexOfHomeAddress = CursorUtil.getColumnIndexOrThrow(_cursor, "home_address");
      final int _cursorIndexOfMeterPhoto = CursorUtil.getColumnIndexOrThrow(_cursor, "meter_photo");
      final int _cursorIndexOfServicePhoto = CursorUtil.getColumnIndexOrThrow(_cursor, "service_photo");
      final int _cursorIndexOfInstallationPhoto = CursorUtil.getColumnIndexOrThrow(_cursor, "installation_photo");
      final int _cursorIndexOfCustomerSign = CursorUtil.getColumnIndexOrThrow(_cursor, "customer_sign");
      final int _cursorIndexOfExecutiveSign = CursorUtil.getColumnIndexOrThrow(_cursor, "executive_sign");
      final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
      final int _cursorIndexOfSubStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "sub_status");
      final int _cursorIndexOfRemarks = CursorUtil.getColumnIndexOrThrow(_cursor, "remarks");
      final int _cursorIndexOfDelayDate = CursorUtil.getColumnIndexOrThrow(_cursor, "delay_date");
      final int _cursorIndexOfRecording = CursorUtil.getColumnIndexOrThrow(_cursor, "recording");
      final int _cursorIndexOfHoldImages = CursorUtil.getColumnIndexOrThrow(_cursor, "hold_images");
      final int _cursorIndexOfPriority = CursorUtil.getColumnIndexOrThrow(_cursor, "priority");
      final int _cursorIndexOfClaim = CursorUtil.getColumnIndexOrThrow(_cursor, "claim");
      final int _cursorIndexOfEmailId = CursorUtil.getColumnIndexOrThrow(_cursor, "email_id");
      final int _cursorIndexOfAltNumber = CursorUtil.getColumnIndexOrThrow(_cursor, "alt_number");
      final int _cursorIndexOfLandmark = CursorUtil.getColumnIndexOrThrow(_cursor, "landmark");
      final int _cursorIndexOfContractorId = CursorUtil.getColumnIndexOrThrow(_cursor, "contractor_id");
      final int _cursorIndexOfTpiId = CursorUtil.getColumnIndexOrThrow(_cursor, "tpi_id");
      final int _cursorIndexOfZiId = CursorUtil.getColumnIndexOrThrow(_cursor, "zi_id");
      final int _cursorIndexOfSupervisorId = CursorUtil.getColumnIndexOrThrow(_cursor, "supervisor_id");
      final int _cursorIndexOfCrmStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "crm_status");
      final int _cursorIndexOfCrmReason = CursorUtil.getColumnIndexOrThrow(_cursor, "crm_reason");
      final int _cursorIndexOfLeadNo = CursorUtil.getColumnIndexOrThrow(_cursor, "lead_no");
      final int _cursorIndexOfStartJob = CursorUtil.getColumnIndexOrThrow(_cursor, "start_job");
      final int _cursorIndexOfCodeGroup = CursorUtil.getColumnIndexOrThrow(_cursor, "code_group");
      final int _cursorIndexOfOld = CursorUtil.getColumnIndexOrThrow(_cursor, "old");
      final int _cursorIndexOfPushedToCrm = CursorUtil.getColumnIndexOrThrow(_cursor, "pushed_to_crm");
      final int _cursorIndexOfClaimDate = CursorUtil.getColumnIndexOrThrow(_cursor, "claim_date");
      final int _cursorIndexOfCatId = CursorUtil.getColumnIndexOrThrow(_cursor, "cat_id");
      final int _cursorIndexOfCatalog = CursorUtil.getColumnIndexOrThrow(_cursor, "catalog");
      final int _cursorIndexOfCode = CursorUtil.getColumnIndexOrThrow(_cursor, "code");
      final int _cursorIndexOfReason = CursorUtil.getColumnIndexOrThrow(_cursor, "reason");
      final int _cursorIndexOfControlRoom = CursorUtil.getColumnIndexOrThrow(_cursor, "control_room");
      final int _cursorIndexOfRfcInitialReading = CursorUtil.getColumnIndexOrThrow(_cursor, "rfc_initial_reading");
      final int _cursorIndexOfSupervisorAssignedDate = CursorUtil.getColumnIndexOrThrow(_cursor, "supervisor_assigned_date");
      final int _cursorIndexOfContractorAssignedDate = CursorUtil.getColumnIndexOrThrow(_cursor, "contractor_assigned_date");
      final int _cursorIndexOfLattitude = CursorUtil.getColumnIndexOrThrow(_cursor, "lattitude");
      final int _cursorIndexOfLongitude = CursorUtil.getColumnIndexOrThrow(_cursor, "longitude");
      final int _cursorIndexOfCorrectedMeterNo = CursorUtil.getColumnIndexOrThrow(_cursor, "corrected_meter_no");
      final int _cursorIndexOfMeterStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "meter_status");
      final List<NguserListModel> _result = new ArrayList<NguserListModel>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final NguserListModel _item;
        _item = new NguserListModel();
        final int _tmpId;
        _tmpId = _cursor.getInt(_cursorIndexOfId);
        _item.setId(_tmpId);
        final String _tmpJmr_no;
        _tmpJmr_no = _cursor.getString(_cursorIndexOfJmrNo);
        _item.setJmr_no(_tmpJmr_no);
        final String _tmpBp_no;
        _tmpBp_no = _cursor.getString(_cursorIndexOfBpNo);
        _item.setBp_no(_tmpBp_no);
        final String _tmpMeter_no;
        _tmpMeter_no = _cursor.getString(_cursorIndexOfMeterNo);
        _item.setMeter_no(_tmpMeter_no);
        final String _tmpCustomer_name;
        _tmpCustomer_name = _cursor.getString(_cursorIndexOfCustomerName);
        _item.setCustomer_name(_tmpCustomer_name);
        final String _tmpBurner_details;
        _tmpBurner_details = _cursor.getString(_cursorIndexOfBurnerDetails);
        _item.setBurner_details(_tmpBurner_details);
        final String _tmpNg_update_date;
        _tmpNg_update_date = _cursor.getString(_cursorIndexOfNgUpdateDate);
        _item.setNg_update_date(_tmpNg_update_date);
        final String _tmpConversion_date;
        _tmpConversion_date = _cursor.getString(_cursorIndexOfConversionDate);
        _item.setConversion_date(_tmpConversion_date);
        final String _tmpCa_no;
        _tmpCa_no = _cursor.getString(_cursorIndexOfCaNo);
        _item.setCa_no(_tmpCa_no);
        final String _tmpMeter_type;
        _tmpMeter_type = _cursor.getString(_cursorIndexOfMeterType);
        _item.setMeter_type(_tmpMeter_type);
        final String _tmpMeter_make;
        _tmpMeter_make = _cursor.getString(_cursorIndexOfMeterMake);
        _item.setMeter_make(_tmpMeter_make);
        final String _tmpInitial_reading;
        _tmpInitial_reading = _cursor.getString(_cursorIndexOfInitialReading);
        _item.setInitial_reading(_tmpInitial_reading);
        final String _tmpMobile_no;
        _tmpMobile_no = _cursor.getString(_cursorIndexOfMobileNo);
        _item.setMobile_no(_tmpMobile_no);
        final String _tmpRfc_date;
        _tmpRfc_date = _cursor.getString(_cursorIndexOfRfcDate);
        _item.setRfc_date(_tmpRfc_date);
        final String _tmpAmount_charged;
        _tmpAmount_charged = _cursor.getString(_cursorIndexOfAmountCharged);
        _item.setAmount_charged(_tmpAmount_charged);
        final String _tmpHouse_no;
        _tmpHouse_no = _cursor.getString(_cursorIndexOfHouseNo);
        _item.setHouse_no(_tmpHouse_no);
        final String _tmpBlock_qtr;
        _tmpBlock_qtr = _cursor.getString(_cursorIndexOfBlockQtr);
        _item.setBlock_qtr(_tmpBlock_qtr);
        final String _tmpSociety;
        _tmpSociety = _cursor.getString(_cursorIndexOfSociety);
        _item.setSociety(_tmpSociety);
        final String _tmpCity;
        _tmpCity = _cursor.getString(_cursorIndexOfCity);
        _item.setCity(_tmpCity);
        final String _tmpZone;
        _tmpZone = _cursor.getString(_cursorIndexOfZone);
        _item.setZone(_tmpZone);
        final String _tmpFloor;
        _tmpFloor = _cursor.getString(_cursorIndexOfFloor);
        _item.setFloor(_tmpFloor);
        final String _tmpStreet;
        _tmpStreet = _cursor.getString(_cursorIndexOfStreet);
        _item.setStreet(_tmpStreet);
        final String _tmpArea;
        _tmpArea = _cursor.getString(_cursorIndexOfArea);
        _item.setArea(_tmpArea);
        final String _tmpHome_address;
        _tmpHome_address = _cursor.getString(_cursorIndexOfHomeAddress);
        _item.setHome_address(_tmpHome_address);
        final String _tmpMeter_photo;
        _tmpMeter_photo = _cursor.getString(_cursorIndexOfMeterPhoto);
        _item.setMeter_photo(_tmpMeter_photo);
        final String _tmpService_photo;
        _tmpService_photo = _cursor.getString(_cursorIndexOfServicePhoto);
        _item.setService_photo(_tmpService_photo);
        final String _tmpInstallation_photo;
        _tmpInstallation_photo = _cursor.getString(_cursorIndexOfInstallationPhoto);
        _item.setInstallation_photo(_tmpInstallation_photo);
        final String _tmpCustomer_sign;
        _tmpCustomer_sign = _cursor.getString(_cursorIndexOfCustomerSign);
        _item.setCustomer_sign(_tmpCustomer_sign);
        final String _tmpExecutive_sign;
        _tmpExecutive_sign = _cursor.getString(_cursorIndexOfExecutiveSign);
        _item.setExecutive_sign(_tmpExecutive_sign);
        final String _tmpStatus;
        _tmpStatus = _cursor.getString(_cursorIndexOfStatus);
        _item.setStatus(_tmpStatus);
        final String _tmpSub_status;
        _tmpSub_status = _cursor.getString(_cursorIndexOfSubStatus);
        _item.setSub_status(_tmpSub_status);
        final String _tmpRemarks;
        _tmpRemarks = _cursor.getString(_cursorIndexOfRemarks);
        _item.setRemarks(_tmpRemarks);
        final String _tmpDelay_date;
        _tmpDelay_date = _cursor.getString(_cursorIndexOfDelayDate);
        _item.setDelay_date(_tmpDelay_date);
        final String _tmpRecording;
        _tmpRecording = _cursor.getString(_cursorIndexOfRecording);
        _item.setRecording(_tmpRecording);
        final String _tmpHold_images;
        _tmpHold_images = _cursor.getString(_cursorIndexOfHoldImages);
        _item.setHold_images(_tmpHold_images);
        final String _tmpPriority;
        _tmpPriority = _cursor.getString(_cursorIndexOfPriority);
        _item.setPriority(_tmpPriority);
        final Boolean _tmpClaim;
        final Integer _tmp;
        if (_cursor.isNull(_cursorIndexOfClaim)) {
          _tmp = null;
        } else {
          _tmp = _cursor.getInt(_cursorIndexOfClaim);
        }
        _tmpClaim = _tmp == null ? null : _tmp != 0;
        _item.setClaim(_tmpClaim);
        final String _tmpEmail_id;
        _tmpEmail_id = _cursor.getString(_cursorIndexOfEmailId);
        _item.setEmail_id(_tmpEmail_id);
        final String _tmpAlt_number;
        _tmpAlt_number = _cursor.getString(_cursorIndexOfAltNumber);
        _item.setAlt_number(_tmpAlt_number);
        final String _tmpLandmark;
        _tmpLandmark = _cursor.getString(_cursorIndexOfLandmark);
        _item.setLandmark(_tmpLandmark);
        final String _tmpContractor_id;
        _tmpContractor_id = _cursor.getString(_cursorIndexOfContractorId);
        _item.setContractor_id(_tmpContractor_id);
        final String _tmpTpi_id;
        _tmpTpi_id = _cursor.getString(_cursorIndexOfTpiId);
        _item.setTpi_id(_tmpTpi_id);
        final String _tmpZi_id;
        _tmpZi_id = _cursor.getString(_cursorIndexOfZiId);
        _item.setZi_id(_tmpZi_id);
        final String _tmpSupervisor_id;
        _tmpSupervisor_id = _cursor.getString(_cursorIndexOfSupervisorId);
        _item.setSupervisor_id(_tmpSupervisor_id);
        final String _tmpCrm_status;
        _tmpCrm_status = _cursor.getString(_cursorIndexOfCrmStatus);
        _item.setCrm_status(_tmpCrm_status);
        final String _tmpCrm_reason;
        _tmpCrm_reason = _cursor.getString(_cursorIndexOfCrmReason);
        _item.setCrm_reason(_tmpCrm_reason);
        final String _tmpLead_no;
        _tmpLead_no = _cursor.getString(_cursorIndexOfLeadNo);
        _item.setLead_no(_tmpLead_no);
        final Boolean _tmpStart_job;
        final Integer _tmp_1;
        if (_cursor.isNull(_cursorIndexOfStartJob)) {
          _tmp_1 = null;
        } else {
          _tmp_1 = _cursor.getInt(_cursorIndexOfStartJob);
        }
        _tmpStart_job = _tmp_1 == null ? null : _tmp_1 != 0;
        _item.setStart_job(_tmpStart_job);
        final String _tmpCode_group;
        _tmpCode_group = _cursor.getString(_cursorIndexOfCodeGroup);
        _item.setCode_group(_tmpCode_group);
        final Boolean _tmpOld;
        final Integer _tmp_2;
        if (_cursor.isNull(_cursorIndexOfOld)) {
          _tmp_2 = null;
        } else {
          _tmp_2 = _cursor.getInt(_cursorIndexOfOld);
        }
        _tmpOld = _tmp_2 == null ? null : _tmp_2 != 0;
        _item.setOld(_tmpOld);
        final int _tmpPushed_to_crm;
        _tmpPushed_to_crm = _cursor.getInt(_cursorIndexOfPushedToCrm);
        _item.setPushed_to_crm(_tmpPushed_to_crm);
        final String _tmpClaim_date;
        _tmpClaim_date = _cursor.getString(_cursorIndexOfClaimDate);
        _item.setClaim_date(_tmpClaim_date);
        final String _tmpCat_id;
        _tmpCat_id = _cursor.getString(_cursorIndexOfCatId);
        _item.setCat_id(_tmpCat_id);
        final String _tmpCatalog;
        _tmpCatalog = _cursor.getString(_cursorIndexOfCatalog);
        _item.setCatalog(_tmpCatalog);
        final String _tmpCode;
        _tmpCode = _cursor.getString(_cursorIndexOfCode);
        _item.setCode(_tmpCode);
        final String _tmpReason;
        _tmpReason = _cursor.getString(_cursorIndexOfReason);
        _item.setReason(_tmpReason);
        final String _tmpControl_room;
        _tmpControl_room = _cursor.getString(_cursorIndexOfControlRoom);
        _item.setControl_room(_tmpControl_room);
        final String _tmpRfc_initial_reading;
        _tmpRfc_initial_reading = _cursor.getString(_cursorIndexOfRfcInitialReading);
        _item.setRfc_initial_reading(_tmpRfc_initial_reading);
        final String _tmpSupervisor_assigned_date;
        _tmpSupervisor_assigned_date = _cursor.getString(_cursorIndexOfSupervisorAssignedDate);
        _item.setSupervisor_assigned_date(_tmpSupervisor_assigned_date);
        final String _tmpContractor_assigned_date;
        _tmpContractor_assigned_date = _cursor.getString(_cursorIndexOfContractorAssignedDate);
        _item.setContractor_assigned_date(_tmpContractor_assigned_date);
        final String _tmpLattitude;
        _tmpLattitude = _cursor.getString(_cursorIndexOfLattitude);
        _item.setLattitude(_tmpLattitude);
        final String _tmpLongitude;
        _tmpLongitude = _cursor.getString(_cursorIndexOfLongitude);
        _item.setLongitude(_tmpLongitude);
        final String _tmpCorrected_meter_no;
        _tmpCorrected_meter_no = _cursor.getString(_cursorIndexOfCorrectedMeterNo);
        _item.setCorrected_meter_no(_tmpCorrected_meter_no);
        final boolean _tmpMeter_status;
        final int _tmp_3;
        _tmp_3 = _cursor.getInt(_cursorIndexOfMeterStatus);
        _tmpMeter_status = _tmp_3 != 0;
        _item.setMeter_status(_tmpMeter_status);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public List<NguserListModel> getOPUsers(final String OP) {
    final String _sql = "SELECT * FROM ng_users_table WHERE status LIKE ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (OP == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, OP);
    }
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfJmrNo = CursorUtil.getColumnIndexOrThrow(_cursor, "jmr_no");
      final int _cursorIndexOfBpNo = CursorUtil.getColumnIndexOrThrow(_cursor, "bp_no");
      final int _cursorIndexOfMeterNo = CursorUtil.getColumnIndexOrThrow(_cursor, "meter_no");
      final int _cursorIndexOfCustomerName = CursorUtil.getColumnIndexOrThrow(_cursor, "customer_name");
      final int _cursorIndexOfBurnerDetails = CursorUtil.getColumnIndexOrThrow(_cursor, "burner_details");
      final int _cursorIndexOfNgUpdateDate = CursorUtil.getColumnIndexOrThrow(_cursor, "ng_update_date");
      final int _cursorIndexOfConversionDate = CursorUtil.getColumnIndexOrThrow(_cursor, "conversion_date");
      final int _cursorIndexOfCaNo = CursorUtil.getColumnIndexOrThrow(_cursor, "ca_no");
      final int _cursorIndexOfMeterType = CursorUtil.getColumnIndexOrThrow(_cursor, "meter_type");
      final int _cursorIndexOfMeterMake = CursorUtil.getColumnIndexOrThrow(_cursor, "meter_make");
      final int _cursorIndexOfInitialReading = CursorUtil.getColumnIndexOrThrow(_cursor, "initial_reading");
      final int _cursorIndexOfMobileNo = CursorUtil.getColumnIndexOrThrow(_cursor, "mobile_no");
      final int _cursorIndexOfRfcDate = CursorUtil.getColumnIndexOrThrow(_cursor, "rfc_date");
      final int _cursorIndexOfAmountCharged = CursorUtil.getColumnIndexOrThrow(_cursor, "amount_charged");
      final int _cursorIndexOfHouseNo = CursorUtil.getColumnIndexOrThrow(_cursor, "house_no");
      final int _cursorIndexOfBlockQtr = CursorUtil.getColumnIndexOrThrow(_cursor, "block_qtr");
      final int _cursorIndexOfSociety = CursorUtil.getColumnIndexOrThrow(_cursor, "society");
      final int _cursorIndexOfCity = CursorUtil.getColumnIndexOrThrow(_cursor, "city");
      final int _cursorIndexOfZone = CursorUtil.getColumnIndexOrThrow(_cursor, "zone");
      final int _cursorIndexOfFloor = CursorUtil.getColumnIndexOrThrow(_cursor, "floor");
      final int _cursorIndexOfStreet = CursorUtil.getColumnIndexOrThrow(_cursor, "street");
      final int _cursorIndexOfArea = CursorUtil.getColumnIndexOrThrow(_cursor, "area");
      final int _cursorIndexOfHomeAddress = CursorUtil.getColumnIndexOrThrow(_cursor, "home_address");
      final int _cursorIndexOfMeterPhoto = CursorUtil.getColumnIndexOrThrow(_cursor, "meter_photo");
      final int _cursorIndexOfServicePhoto = CursorUtil.getColumnIndexOrThrow(_cursor, "service_photo");
      final int _cursorIndexOfInstallationPhoto = CursorUtil.getColumnIndexOrThrow(_cursor, "installation_photo");
      final int _cursorIndexOfCustomerSign = CursorUtil.getColumnIndexOrThrow(_cursor, "customer_sign");
      final int _cursorIndexOfExecutiveSign = CursorUtil.getColumnIndexOrThrow(_cursor, "executive_sign");
      final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
      final int _cursorIndexOfSubStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "sub_status");
      final int _cursorIndexOfRemarks = CursorUtil.getColumnIndexOrThrow(_cursor, "remarks");
      final int _cursorIndexOfDelayDate = CursorUtil.getColumnIndexOrThrow(_cursor, "delay_date");
      final int _cursorIndexOfRecording = CursorUtil.getColumnIndexOrThrow(_cursor, "recording");
      final int _cursorIndexOfHoldImages = CursorUtil.getColumnIndexOrThrow(_cursor, "hold_images");
      final int _cursorIndexOfPriority = CursorUtil.getColumnIndexOrThrow(_cursor, "priority");
      final int _cursorIndexOfClaim = CursorUtil.getColumnIndexOrThrow(_cursor, "claim");
      final int _cursorIndexOfEmailId = CursorUtil.getColumnIndexOrThrow(_cursor, "email_id");
      final int _cursorIndexOfAltNumber = CursorUtil.getColumnIndexOrThrow(_cursor, "alt_number");
      final int _cursorIndexOfLandmark = CursorUtil.getColumnIndexOrThrow(_cursor, "landmark");
      final int _cursorIndexOfContractorId = CursorUtil.getColumnIndexOrThrow(_cursor, "contractor_id");
      final int _cursorIndexOfTpiId = CursorUtil.getColumnIndexOrThrow(_cursor, "tpi_id");
      final int _cursorIndexOfZiId = CursorUtil.getColumnIndexOrThrow(_cursor, "zi_id");
      final int _cursorIndexOfSupervisorId = CursorUtil.getColumnIndexOrThrow(_cursor, "supervisor_id");
      final int _cursorIndexOfCrmStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "crm_status");
      final int _cursorIndexOfCrmReason = CursorUtil.getColumnIndexOrThrow(_cursor, "crm_reason");
      final int _cursorIndexOfLeadNo = CursorUtil.getColumnIndexOrThrow(_cursor, "lead_no");
      final int _cursorIndexOfStartJob = CursorUtil.getColumnIndexOrThrow(_cursor, "start_job");
      final int _cursorIndexOfCodeGroup = CursorUtil.getColumnIndexOrThrow(_cursor, "code_group");
      final int _cursorIndexOfOld = CursorUtil.getColumnIndexOrThrow(_cursor, "old");
      final int _cursorIndexOfPushedToCrm = CursorUtil.getColumnIndexOrThrow(_cursor, "pushed_to_crm");
      final int _cursorIndexOfClaimDate = CursorUtil.getColumnIndexOrThrow(_cursor, "claim_date");
      final int _cursorIndexOfCatId = CursorUtil.getColumnIndexOrThrow(_cursor, "cat_id");
      final int _cursorIndexOfCatalog = CursorUtil.getColumnIndexOrThrow(_cursor, "catalog");
      final int _cursorIndexOfCode = CursorUtil.getColumnIndexOrThrow(_cursor, "code");
      final int _cursorIndexOfReason = CursorUtil.getColumnIndexOrThrow(_cursor, "reason");
      final int _cursorIndexOfControlRoom = CursorUtil.getColumnIndexOrThrow(_cursor, "control_room");
      final int _cursorIndexOfRfcInitialReading = CursorUtil.getColumnIndexOrThrow(_cursor, "rfc_initial_reading");
      final int _cursorIndexOfSupervisorAssignedDate = CursorUtil.getColumnIndexOrThrow(_cursor, "supervisor_assigned_date");
      final int _cursorIndexOfContractorAssignedDate = CursorUtil.getColumnIndexOrThrow(_cursor, "contractor_assigned_date");
      final int _cursorIndexOfLattitude = CursorUtil.getColumnIndexOrThrow(_cursor, "lattitude");
      final int _cursorIndexOfLongitude = CursorUtil.getColumnIndexOrThrow(_cursor, "longitude");
      final int _cursorIndexOfCorrectedMeterNo = CursorUtil.getColumnIndexOrThrow(_cursor, "corrected_meter_no");
      final int _cursorIndexOfMeterStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "meter_status");
      final List<NguserListModel> _result = new ArrayList<NguserListModel>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final NguserListModel _item;
        _item = new NguserListModel();
        final int _tmpId;
        _tmpId = _cursor.getInt(_cursorIndexOfId);
        _item.setId(_tmpId);
        final String _tmpJmr_no;
        _tmpJmr_no = _cursor.getString(_cursorIndexOfJmrNo);
        _item.setJmr_no(_tmpJmr_no);
        final String _tmpBp_no;
        _tmpBp_no = _cursor.getString(_cursorIndexOfBpNo);
        _item.setBp_no(_tmpBp_no);
        final String _tmpMeter_no;
        _tmpMeter_no = _cursor.getString(_cursorIndexOfMeterNo);
        _item.setMeter_no(_tmpMeter_no);
        final String _tmpCustomer_name;
        _tmpCustomer_name = _cursor.getString(_cursorIndexOfCustomerName);
        _item.setCustomer_name(_tmpCustomer_name);
        final String _tmpBurner_details;
        _tmpBurner_details = _cursor.getString(_cursorIndexOfBurnerDetails);
        _item.setBurner_details(_tmpBurner_details);
        final String _tmpNg_update_date;
        _tmpNg_update_date = _cursor.getString(_cursorIndexOfNgUpdateDate);
        _item.setNg_update_date(_tmpNg_update_date);
        final String _tmpConversion_date;
        _tmpConversion_date = _cursor.getString(_cursorIndexOfConversionDate);
        _item.setConversion_date(_tmpConversion_date);
        final String _tmpCa_no;
        _tmpCa_no = _cursor.getString(_cursorIndexOfCaNo);
        _item.setCa_no(_tmpCa_no);
        final String _tmpMeter_type;
        _tmpMeter_type = _cursor.getString(_cursorIndexOfMeterType);
        _item.setMeter_type(_tmpMeter_type);
        final String _tmpMeter_make;
        _tmpMeter_make = _cursor.getString(_cursorIndexOfMeterMake);
        _item.setMeter_make(_tmpMeter_make);
        final String _tmpInitial_reading;
        _tmpInitial_reading = _cursor.getString(_cursorIndexOfInitialReading);
        _item.setInitial_reading(_tmpInitial_reading);
        final String _tmpMobile_no;
        _tmpMobile_no = _cursor.getString(_cursorIndexOfMobileNo);
        _item.setMobile_no(_tmpMobile_no);
        final String _tmpRfc_date;
        _tmpRfc_date = _cursor.getString(_cursorIndexOfRfcDate);
        _item.setRfc_date(_tmpRfc_date);
        final String _tmpAmount_charged;
        _tmpAmount_charged = _cursor.getString(_cursorIndexOfAmountCharged);
        _item.setAmount_charged(_tmpAmount_charged);
        final String _tmpHouse_no;
        _tmpHouse_no = _cursor.getString(_cursorIndexOfHouseNo);
        _item.setHouse_no(_tmpHouse_no);
        final String _tmpBlock_qtr;
        _tmpBlock_qtr = _cursor.getString(_cursorIndexOfBlockQtr);
        _item.setBlock_qtr(_tmpBlock_qtr);
        final String _tmpSociety;
        _tmpSociety = _cursor.getString(_cursorIndexOfSociety);
        _item.setSociety(_tmpSociety);
        final String _tmpCity;
        _tmpCity = _cursor.getString(_cursorIndexOfCity);
        _item.setCity(_tmpCity);
        final String _tmpZone;
        _tmpZone = _cursor.getString(_cursorIndexOfZone);
        _item.setZone(_tmpZone);
        final String _tmpFloor;
        _tmpFloor = _cursor.getString(_cursorIndexOfFloor);
        _item.setFloor(_tmpFloor);
        final String _tmpStreet;
        _tmpStreet = _cursor.getString(_cursorIndexOfStreet);
        _item.setStreet(_tmpStreet);
        final String _tmpArea;
        _tmpArea = _cursor.getString(_cursorIndexOfArea);
        _item.setArea(_tmpArea);
        final String _tmpHome_address;
        _tmpHome_address = _cursor.getString(_cursorIndexOfHomeAddress);
        _item.setHome_address(_tmpHome_address);
        final String _tmpMeter_photo;
        _tmpMeter_photo = _cursor.getString(_cursorIndexOfMeterPhoto);
        _item.setMeter_photo(_tmpMeter_photo);
        final String _tmpService_photo;
        _tmpService_photo = _cursor.getString(_cursorIndexOfServicePhoto);
        _item.setService_photo(_tmpService_photo);
        final String _tmpInstallation_photo;
        _tmpInstallation_photo = _cursor.getString(_cursorIndexOfInstallationPhoto);
        _item.setInstallation_photo(_tmpInstallation_photo);
        final String _tmpCustomer_sign;
        _tmpCustomer_sign = _cursor.getString(_cursorIndexOfCustomerSign);
        _item.setCustomer_sign(_tmpCustomer_sign);
        final String _tmpExecutive_sign;
        _tmpExecutive_sign = _cursor.getString(_cursorIndexOfExecutiveSign);
        _item.setExecutive_sign(_tmpExecutive_sign);
        final String _tmpStatus;
        _tmpStatus = _cursor.getString(_cursorIndexOfStatus);
        _item.setStatus(_tmpStatus);
        final String _tmpSub_status;
        _tmpSub_status = _cursor.getString(_cursorIndexOfSubStatus);
        _item.setSub_status(_tmpSub_status);
        final String _tmpRemarks;
        _tmpRemarks = _cursor.getString(_cursorIndexOfRemarks);
        _item.setRemarks(_tmpRemarks);
        final String _tmpDelay_date;
        _tmpDelay_date = _cursor.getString(_cursorIndexOfDelayDate);
        _item.setDelay_date(_tmpDelay_date);
        final String _tmpRecording;
        _tmpRecording = _cursor.getString(_cursorIndexOfRecording);
        _item.setRecording(_tmpRecording);
        final String _tmpHold_images;
        _tmpHold_images = _cursor.getString(_cursorIndexOfHoldImages);
        _item.setHold_images(_tmpHold_images);
        final String _tmpPriority;
        _tmpPriority = _cursor.getString(_cursorIndexOfPriority);
        _item.setPriority(_tmpPriority);
        final Boolean _tmpClaim;
        final Integer _tmp;
        if (_cursor.isNull(_cursorIndexOfClaim)) {
          _tmp = null;
        } else {
          _tmp = _cursor.getInt(_cursorIndexOfClaim);
        }
        _tmpClaim = _tmp == null ? null : _tmp != 0;
        _item.setClaim(_tmpClaim);
        final String _tmpEmail_id;
        _tmpEmail_id = _cursor.getString(_cursorIndexOfEmailId);
        _item.setEmail_id(_tmpEmail_id);
        final String _tmpAlt_number;
        _tmpAlt_number = _cursor.getString(_cursorIndexOfAltNumber);
        _item.setAlt_number(_tmpAlt_number);
        final String _tmpLandmark;
        _tmpLandmark = _cursor.getString(_cursorIndexOfLandmark);
        _item.setLandmark(_tmpLandmark);
        final String _tmpContractor_id;
        _tmpContractor_id = _cursor.getString(_cursorIndexOfContractorId);
        _item.setContractor_id(_tmpContractor_id);
        final String _tmpTpi_id;
        _tmpTpi_id = _cursor.getString(_cursorIndexOfTpiId);
        _item.setTpi_id(_tmpTpi_id);
        final String _tmpZi_id;
        _tmpZi_id = _cursor.getString(_cursorIndexOfZiId);
        _item.setZi_id(_tmpZi_id);
        final String _tmpSupervisor_id;
        _tmpSupervisor_id = _cursor.getString(_cursorIndexOfSupervisorId);
        _item.setSupervisor_id(_tmpSupervisor_id);
        final String _tmpCrm_status;
        _tmpCrm_status = _cursor.getString(_cursorIndexOfCrmStatus);
        _item.setCrm_status(_tmpCrm_status);
        final String _tmpCrm_reason;
        _tmpCrm_reason = _cursor.getString(_cursorIndexOfCrmReason);
        _item.setCrm_reason(_tmpCrm_reason);
        final String _tmpLead_no;
        _tmpLead_no = _cursor.getString(_cursorIndexOfLeadNo);
        _item.setLead_no(_tmpLead_no);
        final Boolean _tmpStart_job;
        final Integer _tmp_1;
        if (_cursor.isNull(_cursorIndexOfStartJob)) {
          _tmp_1 = null;
        } else {
          _tmp_1 = _cursor.getInt(_cursorIndexOfStartJob);
        }
        _tmpStart_job = _tmp_1 == null ? null : _tmp_1 != 0;
        _item.setStart_job(_tmpStart_job);
        final String _tmpCode_group;
        _tmpCode_group = _cursor.getString(_cursorIndexOfCodeGroup);
        _item.setCode_group(_tmpCode_group);
        final Boolean _tmpOld;
        final Integer _tmp_2;
        if (_cursor.isNull(_cursorIndexOfOld)) {
          _tmp_2 = null;
        } else {
          _tmp_2 = _cursor.getInt(_cursorIndexOfOld);
        }
        _tmpOld = _tmp_2 == null ? null : _tmp_2 != 0;
        _item.setOld(_tmpOld);
        final int _tmpPushed_to_crm;
        _tmpPushed_to_crm = _cursor.getInt(_cursorIndexOfPushedToCrm);
        _item.setPushed_to_crm(_tmpPushed_to_crm);
        final String _tmpClaim_date;
        _tmpClaim_date = _cursor.getString(_cursorIndexOfClaimDate);
        _item.setClaim_date(_tmpClaim_date);
        final String _tmpCat_id;
        _tmpCat_id = _cursor.getString(_cursorIndexOfCatId);
        _item.setCat_id(_tmpCat_id);
        final String _tmpCatalog;
        _tmpCatalog = _cursor.getString(_cursorIndexOfCatalog);
        _item.setCatalog(_tmpCatalog);
        final String _tmpCode;
        _tmpCode = _cursor.getString(_cursorIndexOfCode);
        _item.setCode(_tmpCode);
        final String _tmpReason;
        _tmpReason = _cursor.getString(_cursorIndexOfReason);
        _item.setReason(_tmpReason);
        final String _tmpControl_room;
        _tmpControl_room = _cursor.getString(_cursorIndexOfControlRoom);
        _item.setControl_room(_tmpControl_room);
        final String _tmpRfc_initial_reading;
        _tmpRfc_initial_reading = _cursor.getString(_cursorIndexOfRfcInitialReading);
        _item.setRfc_initial_reading(_tmpRfc_initial_reading);
        final String _tmpSupervisor_assigned_date;
        _tmpSupervisor_assigned_date = _cursor.getString(_cursorIndexOfSupervisorAssignedDate);
        _item.setSupervisor_assigned_date(_tmpSupervisor_assigned_date);
        final String _tmpContractor_assigned_date;
        _tmpContractor_assigned_date = _cursor.getString(_cursorIndexOfContractorAssignedDate);
        _item.setContractor_assigned_date(_tmpContractor_assigned_date);
        final String _tmpLattitude;
        _tmpLattitude = _cursor.getString(_cursorIndexOfLattitude);
        _item.setLattitude(_tmpLattitude);
        final String _tmpLongitude;
        _tmpLongitude = _cursor.getString(_cursorIndexOfLongitude);
        _item.setLongitude(_tmpLongitude);
        final String _tmpCorrected_meter_no;
        _tmpCorrected_meter_no = _cursor.getString(_cursorIndexOfCorrectedMeterNo);
        _item.setCorrected_meter_no(_tmpCorrected_meter_no);
        final boolean _tmpMeter_status;
        final int _tmp_3;
        _tmp_3 = _cursor.getInt(_cursorIndexOfMeterStatus);
        _tmpMeter_status = _tmp_3 != 0;
        _item.setMeter_status(_tmpMeter_status);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public void deleteUserByJmrNos(final List<String> jmr_nos) {
    __db.assertNotSuspendingTransaction();
    StringBuilder _stringBuilder = StringUtil.newStringBuilder();
    _stringBuilder.append("DELETE FROM ng_users_table WHERE jmr_no IN (");
    final int _inputSize = jmr_nos.size();
    StringUtil.appendPlaceholders(_stringBuilder, _inputSize);
    _stringBuilder.append(")");
    final String _sql = _stringBuilder.toString();
    final SupportSQLiteStatement _stmt = __db.compileStatement(_sql);
    int _argIndex = 1;
    for (String _item : jmr_nos) {
      if (_item == null) {
        _stmt.bindNull(_argIndex);
      } else {
        _stmt.bindString(_argIndex, _item);
      }
      _argIndex ++;
    }
    __db.beginTransaction();
    try {
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }
}
