package com.jizhang.database.service;

import java.util.List;

import com.jizhang.JiZhangApplication;
import com.jizhang.bean.User;
import com.jizhang.database.dao.UserDao;
import com.jizhang.database.dao.DaoSession;

import android.content.Context;


// KEEP INCLUDES - put your custom includes here
// KEEP INCLUDES END
/** 
 * Service for  USER.
*/
public class UserService  {
	private static UserService instance;
	private DaoSession mDaoSession;
	private UserDao userDao;

    // KEEP FIELDS - put your custom fields here
    // KEEP FIELDS END

    private UserService() { }
    /** Single instance */
    public static UserService getInstance(final Context context) {
    	if(instance == null) {
    		instance = new UserService();
    	}
    	instance.mDaoSession = JiZhangApplication.getDaoSession(context);
    	instance.userDao = instance.mDaoSession.getUserDao();
    	return instance;
    }
    
    public User load(long id) {
    	return userDao.load(id);
    }
    
    public List<User> loadAll() {
    	return userDao.loadAll();
    }
    
    public List<User> query(String where, String... params) {
		return userDao.queryRaw(where, params);
	}

	public long save(User user) {
		return userDao.insertOrReplace(user);
	}

	public void deleteAll() {
		userDao.deleteAll();
	}

	public void delete(long id) {
		userDao.deleteByKey(id);
	}

	public void delete(User user) {
		userDao.delete(user);
	}

    // KEEP METHODS - put your custom methods here
    // KEEP METHODS END

}
