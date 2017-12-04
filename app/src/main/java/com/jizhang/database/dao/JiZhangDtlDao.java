package com.jizhang.database.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

import com.jizhang.bean.JiZhangDtl;

/** 
 * DAO for table JI_ZHANG_DTL.
*/
public class JiZhangDtlDao extends AbstractDao<JiZhangDtl, Long> {

    public static final String TABLENAME = "JI_ZHANG_DTL";

    /**
     * Properties of entity JiZhangDtl.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Name = new Property(1, String.class, "name", false, "NAME");
        public final static Property PrdtName = new Property(2, String.class, "prdtName", false, "PRDT_NAME");
        public final static Property PrdtSpec = new Property(3, int.class, "prdtSpec", false, "PRDT_SPEC");
        public final static Property PrdtPrice = new Property(4, float.class, "prdtPrice", false, "PRDT_PRICE");
        public final static Property PrdtTotalPrice = new Property(5, float.class, "prdtTotalPrice", false, "PRDT_TOTAL_PRICE");
        public final static Property PrdtAmount = new Property(6, int.class, "prdtAmount", false, "PRDT_AMOUNT");
        public final static Property Type = new Property(7, int.class, "type", false, "TYPE");
    };


    public JiZhangDtlDao(DaoConfig config) {
        super(config);
    }
    
    public JiZhangDtlDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "'JI_ZHANG_DTL' (" + //
                "'_id' INTEGER PRIMARY KEY ," + // 0: id
                "'NAME' TEXT," + // 1: name
                "'PRDT_NAME' TEXT," + // 2: prdtName
                "'PRDT_SPEC' INTEGER NOT NULL ," + // 3: prdtSpec
                "'PRDT_PRICE' REAL NOT NULL ," + // 4: prdtPrice
                "'PRDT_TOTAL_PRICE' REAL NOT NULL ," + // 5: prdtTotalPrice
                "'PRDT_AMOUNT' INTEGER NOT NULL ," + // 6: prdtAmount
                "'TYPE' INTEGER NOT NULL );"); // 7: type
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'JI_ZHANG_DTL'";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, JiZhangDtl entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String name = entity.getName();
        if (name != null) {
            stmt.bindString(2, name);
        }
 
        String prdtName = entity.getPrdtName();
        if (prdtName != null) {
            stmt.bindString(3, prdtName);
        }
        stmt.bindLong(4, entity.getPrdtSpec());
        stmt.bindDouble(5, entity.getPrdtPrice());
        stmt.bindDouble(6, entity.getPrdtTotalPrice());
        stmt.bindLong(7, entity.getPrdtAmount());
        stmt.bindLong(8, entity.getType());
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public JiZhangDtl readEntity(Cursor cursor, int offset) {
        JiZhangDtl entity = new JiZhangDtl( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // name
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // prdtName
            cursor.getInt(offset + 3), // prdtSpec
            cursor.getFloat(offset + 4), // prdtPrice
            cursor.getFloat(offset + 5), // prdtTotalPrice
            cursor.getInt(offset + 6), // prdtAmount
            cursor.getInt(offset + 7) // type
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, JiZhangDtl entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setName(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setPrdtName(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setPrdtSpec(cursor.getInt(offset + 3));
        entity.setPrdtPrice(cursor.getFloat(offset + 4));
        entity.setPrdtTotalPrice(cursor.getFloat(offset + 5));
        entity.setPrdtAmount(cursor.getInt(offset + 6));
        entity.setType(cursor.getInt(offset + 7));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(JiZhangDtl entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(JiZhangDtl entity) {
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
