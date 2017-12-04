package com.jizhang.database.dao;

import android.database.sqlite.SQLiteDatabase;

import java.util.Map;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.AbstractDaoSession;
import de.greenrobot.dao.identityscope.IdentityScopeType;
import de.greenrobot.dao.internal.DaoConfig;

import com.jizhang.bean.JiZhangDtl;
import com.jizhang.bean.TotalMoney;
import com.jizhang.bean.ExpendReason;
import com.jizhang.bean.Product;
import com.jizhang.bean.User;

import com.jizhang.database.dao.JiZhangDtlDao;
import com.jizhang.database.dao.TotalMoneyDao;
import com.jizhang.database.dao.ExpendReasonDao;
import com.jizhang.database.dao.ProductDao;
import com.jizhang.database.dao.UserDao;


/**
 * {@inheritDoc}
 * 
 * @see de.greenrobot.dao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig jiZhangDtlDaoConfig;
    private final DaoConfig totalMoneyDaoConfig;
    private final DaoConfig expendReasonDaoConfig;
    private final DaoConfig productDaoConfig;
    private final DaoConfig userDaoConfig;

    private final JiZhangDtlDao jiZhangDtlDao;
    private final TotalMoneyDao totalMoneyDao;
    private final ExpendReasonDao expendReasonDao;
    private final ProductDao productDao;
    private final UserDao userDao;

    public DaoSession(SQLiteDatabase db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        jiZhangDtlDaoConfig = daoConfigMap.get(JiZhangDtlDao.class).clone();
        jiZhangDtlDaoConfig.initIdentityScope(type);

        totalMoneyDaoConfig = daoConfigMap.get(TotalMoneyDao.class).clone();
        totalMoneyDaoConfig.initIdentityScope(type);

        expendReasonDaoConfig = daoConfigMap.get(ExpendReasonDao.class).clone();
        expendReasonDaoConfig.initIdentityScope(type);

        productDaoConfig = daoConfigMap.get(ProductDao.class).clone();
        productDaoConfig.initIdentityScope(type);

        userDaoConfig = daoConfigMap.get(UserDao.class).clone();
        userDaoConfig.initIdentityScope(type);

        jiZhangDtlDao = new JiZhangDtlDao(jiZhangDtlDaoConfig, this);
        totalMoneyDao = new TotalMoneyDao(totalMoneyDaoConfig, this);
        expendReasonDao = new ExpendReasonDao(expendReasonDaoConfig, this);
        productDao = new ProductDao(productDaoConfig, this);
        userDao = new UserDao(userDaoConfig, this);

        registerDao(JiZhangDtl.class, jiZhangDtlDao);
        registerDao(TotalMoney.class, totalMoneyDao);
        registerDao(ExpendReason.class, expendReasonDao);
        registerDao(Product.class, productDao);
        registerDao(User.class, userDao);
    }
    
    public void clear() {
        jiZhangDtlDaoConfig.getIdentityScope().clear();
        totalMoneyDaoConfig.getIdentityScope().clear();
        expendReasonDaoConfig.getIdentityScope().clear();
        productDaoConfig.getIdentityScope().clear();
        userDaoConfig.getIdentityScope().clear();
    }

    public JiZhangDtlDao getJiZhangDtlDao() {
        return jiZhangDtlDao;
    }

    public TotalMoneyDao getTotalMoneyDao() {
        return totalMoneyDao;
    }

    public ExpendReasonDao getExpendReasonDao() {
        return expendReasonDao;
    }

    public ProductDao getProductDao() {
        return productDao;
    }

    public UserDao getUserDao() {
        return userDao;
    }

}
