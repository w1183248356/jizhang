package com.jizhang.database.service;

import java.util.List;

import com.jizhang.JiZhangApplication;
import com.jizhang.bean.TotalMoney;
import com.jizhang.database.dao.TotalMoneyDao;
import com.jizhang.database.dao.DaoSession;

import android.content.Context;


// KEEP INCLUDES - put your custom includes here
// KEEP INCLUDES END
/** 
 * Service for  TOTAL_MONEY.
*/
public class TotalMoneyService  {
	private static TotalMoneyService instance;
	private DaoSession mDaoSession;
	private TotalMoneyDao totalMoneyDao;

    // KEEP FIELDS - put your custom fields here
    // KEEP FIELDS END

    private TotalMoneyService() { }
    /** Single instance */
    public static TotalMoneyService getInstance(final Context context) {
    	if(instance == null) {
    		instance = new TotalMoneyService();
    	}
    	instance.mDaoSession = JiZhangApplication.getDaoSession(context);
    	instance.totalMoneyDao = instance.mDaoSession.getTotalMoneyDao();
    	return instance;
    }
    
    public TotalMoney load(long id) {
    	return totalMoneyDao.load(id);
    }
    
    public List<TotalMoney> loadAll() {
    	return totalMoneyDao.loadAll();
    }
    
    public List<TotalMoney> query(String where, String... params) {
		return totalMoneyDao.queryRaw(where, params);
	}

	public long save(TotalMoney totalMoney) {
		return totalMoneyDao.insertOrReplace(totalMoney);
	}

	public void deleteAll() {
		totalMoneyDao.deleteAll();
	}

	public void delete(long id) {
		totalMoneyDao.deleteByKey(id);
	}

	public void delete(TotalMoney totalMoney) {
		totalMoneyDao.delete(totalMoney);
	}

    // KEEP METHODS - put your custom methods here
    // KEEP METHODS END

}
