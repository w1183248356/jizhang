package com.jizhang.database.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

import com.jizhang.bean.ExpendReason;

/** 
 * DAO for table EXPEND_REASON.
*/
public class ExpendReasonDao extends AbstractDao<ExpendReason, Long> {

    public static final String TABLENAME = "EXPEND_REASON";

    /**
     * Properties of entity ExpendReason.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Reason = new Property(1, String.class, "reason", false, "REASON");
        public final static Property UseNum = new Property(2, Integer.class, "useNum", false, "USE_NUM");
    };


    public ExpendReasonDao(DaoConfig config) {
        super(config);
    }
    
    public ExpendReasonDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "'EXPEND_REASON' (" + //
                "'_id' INTEGER PRIMARY KEY ," + // 0: id
                "'REASON' TEXT," + // 1: reason
                "'USE_NUM' INTEGER);"); // 2: useNum
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'EXPEND_REASON'";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, ExpendReason entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String reason = entity.getReason();
        if (reason != null) {
            stmt.bindString(2, reason);
        }
 
        Integer useNum = entity.getUseNum();
        if (useNum != null) {
            stmt.bindLong(3, useNum);
        }
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public ExpendReason readEntity(Cursor cursor, int offset) {
        ExpendReason entity = new ExpendReason( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // reason
            cursor.isNull(offset + 2) ? null : cursor.getInt(offset + 2) // useNum
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, ExpendReason entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setReason(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setUseNum(cursor.isNull(offset + 2) ? null : cursor.getInt(offset + 2));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(ExpendReason entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(ExpendReason entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    /** @inheritdoc */
    @Override    
    protected boolean isEntityUpdateable() {
        return true;
    }
    
}
