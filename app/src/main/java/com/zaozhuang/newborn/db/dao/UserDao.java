package com.zaozhuang.newborn.db.dao;

import android.content.Context;
import android.database.sqlite.SQLiteException;

import com.j256.ormlite.dao.Dao;
import com.zaozhuang.newborn.db.entity.User;
import com.zaozhuang.newborn.db.helper.DBHelper;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDao {
    private static final String TAG = "UserDao";
    private Context context;
    private Dao<User, Integer> userDao;
    private DBHelper helper;

    private UserDao(Context context) {
        this.context = context;
        try {
            helper = DBHelper.getHelper(context);
            userDao = helper.getDao(User.class);
        } catch (SQLiteException e) {
            e.printStackTrace();
        }
    }

    private static UserDao instance = null;

    public static UserDao getInstance(Context context) {
        if (instance == null) {
            synchronized (UserDao.class) {
                if (instance == null) {
                    instance = new UserDao(context);
                }
            }
        }
        return instance;
    }


    /**
     * 增加一个记录
     *
     * @param user
     */
    public void add(User user) {
        try {
            userDao.create(user);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /**
     * 跟新一个记录
     *
     * @param user
     */
    public void update(User user) {
        try {
            userDao.update(user);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /**
     * 获取数据库中最新的版本
     *
     * @return
     */
    public User getLastOne() {
        try {
            List<User> users = userDao.queryBuilder().orderBy("id", false).query();
            if (users != null && users.size() > 0) {
                return users.get(0);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 根据订单ID查
     *
     * @return
     */
    public User getUserByOderId(String orderId) {
        try {
            List<User> users = userDao.queryBuilder().where().eq("orderId", orderId).and()
                    .eq("flag", 0).query();
            if (users != null && users.size() > 0) {
                return users.get(0);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 根据用户ID和经纪人ID查记录
     *
     * @return
     */
    public User getUser(String customID, String businessId) {
        try {
            List<User> users = userDao.queryBuilder().where().eq("customId", customID).and().eq
                    ("businessId", businessId).query();
            if (users != null && users.size() > 0) {
                return users.get(0);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 根据经济人ID查询所有的为上传的订单
     *
     * @return
     */
    public List<User> getUser(String businessId) {
        try {
            List<User> users = userDao.queryBuilder().where().eq("businessId", businessId).and()
                    .eq("flag", 0).query();
            if (users != null && users.size() > 0) {
                return users;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<User> getUserList() {
        try {
            List<User> users = userDao.queryBuilder().query();
            if (users != null && users.size() > 0) {
                return users;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    /**
     * 获取数据库中所有版本信息
     *
     * @return
     */
    public User getFistOne() {
        try {
            List<User> users = userDao.queryBuilder().orderBy("id", true).query();
            if (users != null && users.size() > 0) {
                return users.get(0);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}

