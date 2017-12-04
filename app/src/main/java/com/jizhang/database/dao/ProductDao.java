package com.jizhang.database.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

import com.jizhang.bean.Product;

/** 
 * DAO for table PRODUCT.
*/
public class ProductDao extends AbstractDao<Product, Long> {

    public static final String TABLENAME = "PRODUCT";

    /**
     * Properties of entity Product.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Name = new Property(1, String.class, "name", false, "NAME");
        public final static Property Spec = new Property(2, int.class, "spec", false, "SPEC");
        public final static Property UseNum = new Property(3, Integer.class, "useNum", false, "USE_NUM");
        public final static Property OldPrice = new Property(4, float.class, "oldPrice", false, "OLD_PRICE");
        public final static Property OldTotalPrice = new Property(5, float.class, "oldTotalPrice", false, "OLD_TOTAL_PRICE");
    };


    public ProductDao(DaoConfig config) {
        super(config);
    }
    
    public ProductDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "'PRODUCT' (" + //
                "'_id' INTEGER PRIMARY KEY ," + // 0: id
                "'NAME' TEXT," + // 1: name
                "'SPEC' INTEGER NOT NULL ," + // 2: spec
                "'USE_NUM' INTEGER," + // 3: useNum
                "'OLD_PRICE' REAL NOT NULL ," + // 4: oldPrice
                "'OLD_TOTAL_PRICE' REAL NOT NULL );"); // 5: oldTotalPrice
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'PRODUCT'";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, Product entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String name = entity.getName();
        if (name != null) {
            stmt.bindString(2, name);
        }
        stmt.bindLong(3, entity.getSpec());
 
        Integer useNum = entity.getUseNum();
        if (useNum != null) {
            stmt.bindLong(4, useNum);
        }
        stmt.bindDouble(5, entity.getOldPrice());
        stmt.bindDouble(6, entity.getOldTotalPrice());
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public Product readEntity(Cursor cursor, int offset) {
        Product entity = new Product( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // name
            cursor.getInt(offset + 2), // spec
            cursor.isNull(offset + 3) ? null : cursor.getInt(offset + 3), // useNum
            cursor.getFloat(offset + 4), // oldPrice
            cursor.getFloat(offset + 5) // oldTotalPrice
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, Product entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setName(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setSpec(cursor.getInt(offset + 2));
        entity.setUseNum(cursor.isNull(offset + 3) ? null : cursor.getInt(offset + 3));
        entity.setOldPrice(cursor.getFloat(offset + 4));
        entity.setOldTotalPrice(cursor.getFloat(offset + 5));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(Product entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(Product entity) {
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
