package com.zaozhuang.newborn.db.dao;

import android.content.Context;
import android.database.sqlite.SQLiteException;

import com.j256.ormlite.dao.Dao;
import com.zaozhuang.newborn.db.entity.Baby;
import com.zaozhuang.newborn.db.entity.User;
import com.zaozhuang.newborn.db.helper.DBHelper;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BabyDao {
    private static final String TAG = "BabyDao";
    private Context context;
    private Dao<Baby, Integer> babyDao;
    private DBHelper helper;

    private BabyDao(Context context) {
        this.context = context;
        try {
            helper = DBHelper.getHelper(context);
            babyDao = helper.getDao(Baby.class);
        } catch (SQLiteException e) {
            e.printStackTrace();
        }
    }

    private static BabyDao instance = null;

    public static BabyDao getInstance(Context context) {
        if (instance == null) {
            synchronized (BabyDao.class) {
                if (instance == null) {
                    instance = new BabyDao(context);
                }
            }
        }
        return instance;
    }


    /**
     * 增加一个记录
     *
     * @param baby
     */
    public void add(Baby baby) {
        try {
            babyDao.create(baby);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(int id) {
        try {
            babyDao.deleteById(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /**
     * 更新一个记录
     *
     * @param user
     */
    public void update(Baby user) {
        try {
            babyDao.update(user);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取数据库中最新的版本
     *
     * @return
     */
    public Baby getLastOne() {
        try {
            List<Baby> users = babyDao.queryBuilder().orderBy("id", false).query();
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
    public Baby getUserByOderId(String orderId) {
        try {
            List<Baby> users = babyDao.queryBuilder().where().eq("orderId", orderId).and()
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
    public Baby getUser(String customID, String businessId) {
        try {
            List<Baby> users = babyDao.queryBuilder().where().eq("customId", customID).and().eq
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
    public List<Baby> getUser(String businessId) {
        try {
            List<Baby> users = babyDao.queryBuilder().where().eq("businessId", businessId).and()
                    .eq("flag", 0).query();
            if (users != null && users.size() > 0) {
                return users;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Baby> getBabyList() {
        try {
            List<Baby> users = babyDao.queryBuilder().query();
            if (users != null && users.size() > 0) {
                return users;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public Baby getBabyByName(String name) {
        try {
            List<Baby> babyList = babyDao.queryBuilder().query();
            if (babyList != null && babyList.size() > 0) {
                for (int i = 0; i < babyList.size(); i++) {
                    if (name.equals(babyList.get(i).name)) {
                        return babyList.get(i);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取数据库中所有版本信息
     *
     * @return
     */
    public Baby getFistOne() {
        try {
            List<Baby> users = babyDao.queryBuilder().orderBy("id", true).query();
            if (users != null && users.size() > 0) {
                return users.get(0);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}

