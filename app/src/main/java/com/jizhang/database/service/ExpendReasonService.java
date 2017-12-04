package com.jizhang.database.service;

import java.util.List;

import com.jizhang.JiZhangApplication;
import com.jizhang.bean.ExpendReason;
import com.jizhang.database.dao.ExpendReasonDao;
import com.jizhang.database.dao.DaoSession;

import android.content.Context;


// KEEP INCLUDES - put your custom includes here
// KEEP INCLUDES END
/** 
 * Service for  EXPEND_REASON.
*/
public class ExpendReasonService  {
	private static ExpendReasonService instance;
	private DaoSession mDaoSession;
	private ExpendReasonDao expendReasonDao;

    // KEEP FIELDS - put your custom fields here
    // KEEP FIELDS END

    private ExpendReasonService() { }
    /** Single instance */
    public static ExpendReasonService getInstance(final Context context) {
    	if(instance == null) {
    		instance = new ExpendReasonService();
    	}
    	instance.mDaoSession = JiZhangApplication.getDaoSession(context);
    	instance.expendReasonDao = instance.mDaoSession.getExpendReasonDao();
    	return instance;
    }
    
    public ExpendReason load(long id) {
    	return expendReasonDao.load(id);
    }
    
    public List<ExpendReason> loadAll() {
    	return expendReasonDao.loadAll();
    }
    
    public List<ExpendReason> query(String where, String... params) {
		return expendReasonDao.queryRaw(where, params);
	}

	public long save(ExpendReason expendReason) {
		return expendReasonDao.insertOrReplace(expendReason);
	}

	public void deleteAll() {
		expendReasonDao.deleteAll();
	}

	public void delete(long id) {
		expendReasonDao.deleteByKey(id);
	}

	public void delete(ExpendReason expendReason) {
		expendReasonDao.delete(expendReason);
	}

    // KEEP METHODS - put your custom methods here
    // KEEP METHODS END

}
