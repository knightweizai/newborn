package com.zaozhuang.newborn.db.entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

@DatabaseTable(tableName = "tb_baby")
public class Baby implements Serializable {

    @DatabaseField(generatedId = true)
    public int id;


    @DatabaseField
    public int userId;//关联的用户id

    @DatabaseField
    public String height;//身长 是一个json字符串
    @DatabaseField
    public String weight;//体重 是一个json字符串
    @DatabaseField
    public String huangdan;//黄疸值 是一个json字符串
    @DatabaseField
    public String name;//

    @DatabaseField
    public String imgPath = "";//

    @DatabaseField
    public int gender;// 0 男孩，1女孩
    @DatabaseField
    public String birthday;//出生日期

    @DatabaseField
    public String weeks;//孕周


    @Override
    public String toString() {
        return "Baby{" +
                "id=" + id +
                ", userId=" + userId +
                "\n"+
                ", height='" + height + '\'' +
                "\n"+
                ", weight='" + weight + '\'' +
                "\n"+
                ", huangdan='" + huangdan + '\'' +
                "\n"+
                ", name='" + name + '\'' +
                ", imgPath='" + imgPath + '\'' +
                ", gender=" + gender +
                ", birthday='" + birthday + '\'' +
                ", weeks='" + weeks + '\'' +
                '}';
    }
}

