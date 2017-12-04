package com.jizhang.database.service;

import java.util.List;

import com.jizhang.JiZhangApplication;
import com.jizhang.bean.JiZhangDtl;
import com.jizhang.database.dao.JiZhangDtlDao;
import com.jizhang.database.dao.JiZhangDtlDao.Properties;
import com.jizhang.database.dao.DaoSession;

import android.content.Context;


// KEEP INCLUDES - put your custom includes here
// KEEP INCLUDES END
/** 
 * Service for  JI_ZHANG_DTL.
*/
public class JiZhangDtlService  {
	private static JiZhangDtlService instance;
	private DaoSession mDaoSession;
	private JiZhangDtlDao jiZhangDtlDao;

    // KEEP FIELDS - put your custom fields here
    // KEEP FIELDS END

    private JiZhangDtlService() { }
    /** Single instance */
    public static JiZhangDtlService getInstance(final Context context) {
    	if(instance == null) {
    		instance = new JiZhangDtlService();
    	}
    	instance.mDaoSession = JiZhangApplication.getDaoSession(context);
    	instance.jiZhangDtlDao = instance.mDaoSession.getJiZhangDtlDao();
    	return instance;
    }
    
    public JiZhangDtl load(long id) {
    	return jiZhangDtlDao.load(id);
    }
    
    public List<JiZhangDtl> loadAll() {
    	return jiZhangDtlDao.loadAll();
    }
    
    public List<JiZhangDtl> query(String where, String... params) {
		return jiZhangDtlDao.queryRaw(where, params);
	}

	public long save(JiZhangDtl jiZhangDtl) {
		return jiZhangDtlDao.insertOrReplace(jiZhangDtl);
	}

	public void deleteAll() {
		jiZhangDtlDao.deleteAll();
	}

	public void delete(long id) {
		jiZhangDtlDao.deleteByKey(id);
	}

	public void delete(JiZhangDtl jiZhangDtl) {
		jiZhangDtlDao.delete(jiZhangDtl);
	}

    // KEEP METHODS - put your custom methods here
    // KEEP METHODS END

}
