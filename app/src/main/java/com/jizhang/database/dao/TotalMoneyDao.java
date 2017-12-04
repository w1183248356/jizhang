package com.jizhang.database.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

import com.jizhang.bean.TotalMoney;

/** 
 * DAO for table TOTAL_MONEY.
*/
public class TotalMoneyDao extends AbstractDao<TotalMoney, Long> {

    public static final String TABLENAME = "TOTAL_MONEY";

    /**
     * Properties of entity TotalMoney.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property MoneyUncollected = new Property(1, float.class, "moneyUncollected", false, "MONEY_UNCOLLECTED");
        public final static Property MoneyExpenditure = new Property(2, float.class, "moneyExpenditure", false, "MONEY_EXPENDITURE");
        public final static Property MoneyReceive = new Property(3, float.class, "moneyReceive", false, "MONEY_RECEIVE");
        public final static Property MoneyGain = new Property(4, float.class, "moneyGain", false, "MONEY_GAIN");
    };


    public TotalMoneyDao(DaoConfig config) {
        super(config);
    }
    
    public TotalMoneyDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "'TOTAL_MONEY' (" + //
                "'_id' INTEGER PRIMARY KEY ," + // 0: id
                "'MONEY_UNCOLLECTED' REAL NOT NULL ," + // 1: moneyUncollected
                "'MONEY_EXPENDITURE' REAL NOT NULL ," + // 2: moneyExpenditure
                "'MONEY_RECEIVE' REAL NOT NULL ," + // 3: moneyReceive
                "'MONEY_GAIN' REAL NOT NULL );"); // 4: moneyGain
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'TOTAL_MONEY'";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, TotalMoney entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindDouble(2, entity.getMoneyUncollected());
        stmt.bindDouble(3, entity.getMoneyExpenditure());
        stmt.bindDouble(4, entity.getMoneyReceive());
        stmt.bindDouble(5, entity.getMoneyGain());
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public TotalMoney readEntity(Cursor cursor, int offset) {
        TotalMoney entity = new TotalMoney( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.getFloat(offset + 1), // moneyUncollected
            cursor.getFloat(offset + 2), // moneyExpenditure
            cursor.getFloat(offset + 3), // moneyReceive
            cursor.getFloat(offset + 4) // moneyGain
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, TotalMoney entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setMoneyUncollected(cursor.getFloat(offset + 1));
        entity.setMoneyExpenditure(cursor.getFloat(offset + 2));
        entity.setMoneyReceive(cursor.getFloat(offset + 3));
        entity.setMoneyGain(cursor.getFloat(offset + 4));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(TotalMoney entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(TotalMoney entity) {
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
