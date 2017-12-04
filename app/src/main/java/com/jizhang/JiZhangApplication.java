package com.jizhang;

import android.app.Application;
import android.content.Context;

import com.jizhang.database.dao.DaoMaster;
import com.jizhang.database.dao.DaoSession;

import de.greenrobot.dao.identityscope.IdentityScopeType;

/**
 * Created by huntero on 17-8-10.
 */

public class JiZhangApplication extends Application {
    static JiZhangApplication instance;
    private static DaoMaster daoMaster;
    private static DaoSession daoSession;

    public JiZhangApplication() {
    }

    public JiZhangApplication(final Context context) {
        this();
        attachBaseContext(context);
    }

    /**
     * 取得DaoMaster
     *
     * @param context
     * @return
     */
    public static DaoMaster getDaoMaster(Context context) {
        if (daoMaster == null) {
            final String dbPath = Constants.Database.DB_NAME;
            DaoMaster.OpenHelper helper = new DaoMaster.DevOpenHelper(context,
                    dbPath, null);
            daoMaster = new DaoMaster(helper.getWritableDatabase());
        }
        return daoMaster;
    }

    /**
     * 取得DaoSession
     *
     * @param context
     * @return
     */
    public static DaoSession getDaoSession(Context context) {
        if (daoSession == null) {
            if (daoMaster == null) {
                daoMaster = getDaoMaster(context);
            }
            daoSession = daoMaster.newSession(IdentityScopeType.None);
        }
        return daoSession;
    }

    public static JiZhangApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

    }
}
