package com.zaozhuang.newborn.logic;

import java.util.Calendar;

public class BabyLogic {

    public static String calculateAge(int year, int month, int day) {
        Calendar dob = Calendar.getInstance();
        Calendar today = Calendar.getInstance();

        dob.set(year, month, day);

        int ageYears = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);
        int ageMonths = today.get(Calendar.MONTH) + 1 - dob.get(Calendar.MONTH);
        int ageDays = today.get(Calendar.DAY_OF_MONTH) - dob.get(Calendar.DAY_OF_MONTH);

        if (ageMonths < 0) {
            ageYears--;
            ageMonths += 12;
        }

        if (ageDays < 0) {
            ageMonths--;
            ageDays += today.getActualMaximum(Calendar.DAY_OF_MONTH);
        }

        if (ageYears == 0 && ageMonths == 0) {
            return ageDays + "天";
        } else if (ageYears == 0) {
            if (ageDays == 0) {
                return ageMonths + "个月";
            } else {
                return ageMonths + "个月" + ageDays + "天";
            }

        } else {
            if (ageMonths == 0 && ageDays == 0) {
                return ageYears + "岁";
            } else if (ageDays == 0) {
                return ageYears + "岁" + ageMonths + "个月";
            } else if (ageMonths == 0) {
                return ageYears + "岁" + ageDays + "天";
            } else {
                return ageYears + "岁" + ageMonths + "个月" + ageDays + "天";
            }

        }
    }
}
