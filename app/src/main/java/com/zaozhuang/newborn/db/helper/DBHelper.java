package com.zaozhuang.newborn.db.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.zaozhuang.newborn.db.entity.Baby;
import com.zaozhuang.newborn.db.entity.User;

import java.util.HashMap;
import java.util.Map;

public class DBHelper extends OrmLiteSqliteOpenHelper {

    private static DBHelper instance;
    private Map<String, Dao> daos = new HashMap<>();

    private Dao<User, Integer> userDao = null;
    private static final String DATABASE_NAME = "zaozhuang.db";
    private static final int DATABASE_VERSION = 1;

    private DBHelper(Context context) {
        this(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    private DBHelper(Context context, String databaseName, SQLiteDatabase.CursorFactory factory, int databaseVersion) {
        super(context, databaseName, factory, databaseVersion);
    }

    /**
     * 单例获取Helper
     *
     * @param context
     * @return
     */
    public static synchronized DBHelper getHelper(Context context) {
        context = context.getApplicationContext();
        if (instance == null) {
            synchronized (DBHelper.class) {
                if (instance == null)
                    instance = new DBHelper(context);
            }
        }
        return instance;
    }

    //数据库的创建
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, User.class);
            TableUtils.createTable(connectionSource, Baby.class);
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
    }

    //版本更新
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            TableUtils.dropTable(connectionSource, User.class, true);
            TableUtils.dropTable(connectionSource, Baby.class, true);
            onCreate(sqLiteDatabase, connectionSource);
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public ConnectionSource getConnectionSource() {
        return super.getConnectionSource();
    }

    @Override
    public void close() {
        super.close();
        for (String key : daos.keySet()) {
            Dao dao = daos.get(key);
            dao = null;
        }
    }

    public synchronized Dao getDao(Class clazz) {
        Dao dao = null;
        String className = clazz.getSimpleName();

        if (daos.containsKey(className)) {
            dao = daos.get(className);
        }

        if (dao == null) {
            try {
                dao = super.getDao(clazz);
            } catch (java.sql.SQLException e) {
                e.printStackTrace();
            }
            daos.put(className, dao);
        }
        return dao;
    }


}
