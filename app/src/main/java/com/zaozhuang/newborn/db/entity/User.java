package com.zaozhuang.newborn.db.entity;

import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;
import java.util.ArrayList;

@DatabaseTable(tableName = "tb_user")
public class User implements Serializable {

    @DatabaseField(generatedId = true)
    public int id;

    @DatabaseField
    public String phone;//手机号

    @DatabaseField
    public String name;//用户名

    @DatabaseField
    public String pwd;








}

