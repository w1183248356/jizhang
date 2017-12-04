package com.jizhang.database.service;

import java.util.List;

import com.jizhang.JiZhangApplication;
import com.jizhang.bean.Product;
import com.jizhang.database.dao.ProductDao;
import com.jizhang.database.dao.DaoSession;

import android.content.Context;


// KEEP INCLUDES - put your custom includes here
// KEEP INCLUDES END
/** 
 * Service for  PRODUCT.
*/
public class ProductService  {
	private static ProductService instance;
	private DaoSession mDaoSession;
	private ProductDao productDao;

    // KEEP FIELDS - put your custom fields here
    // KEEP FIELDS END

    private ProductService() { }
    /** Single instance */
    public static ProductService getInstance(final Context context) {
    	if(instance == null) {
    		instance = new ProductService();
    	}
    	instance.mDaoSession = JiZhangApplication.getDaoSession(context);
    	instance.productDao = instance.mDaoSession.getProductDao();
    	return instance;
    }
    
    public Product load(long id) {
    	return productDao.load(id);
    }
    
    public List<Product> loadAll() {
    	return productDao.loadAll();
    }
    
    public List<Product> query(String where, String... params) {
		return productDao.queryRaw(where, params);
	}

	public long save(Product product) {
		return productDao.insertOrReplace(product);
	}

	public void deleteAll() {
		productDao.deleteAll();
	}

	public void delete(long id) {
		productDao.deleteByKey(id);
	}

	public void delete(Product product) {
		productDao.delete(product);
	}

    // KEEP METHODS - put your custom methods here
    // KEEP METHODS END

}
