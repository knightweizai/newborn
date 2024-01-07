package mangogo.appbase.util;


import android.content.ContentResolver;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import mangogo.appbase.BaseApplication;


public class TimeUtils {
    public final static SimpleDateFormat DATE = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
    public final static SimpleDateFormat DATE1 = new SimpleDateFormat("yyyyMMdd", Locale.US);
    public final static SimpleDateFormat DATE2 = new SimpleDateFormat("MM月dd日", Locale.US);
    public final static SimpleDateFormat DATE3 = new SimpleDateFormat("MM月dd日 HH:mm", Locale.US);
    public final static SimpleDateFormat DATE_TIME = new SimpleDateFormat("MM-dd HH:mm", Locale.US);
    public final static SimpleDateFormat TOTAL_DATE_TIME = new SimpleDateFormat("yy/MM/dd HH:mm:ss", Locale.US);
    public final static SimpleDateFormat TOTAL_DATE_TIME_6 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
    public final static SimpleDateFormat TOTAL_DATE_TIME_1 = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.US);
    public final static SimpleDateFormat TOTAL_DATE_TIME_2 = new SimpleDateFormat("MM-dd", Locale.US);
    public final static SimpleDateFormat TOTAL_DATE_TIME_3 = new SimpleDateFormat("MM.dd", Locale.US);
    public final static SimpleDateFormat TOTAL_DATE_TIME_4 = new SimpleDateFormat("yyyy-MM-dd-HH:mm", Locale.US);
    public final static SimpleDateFormat TOTAL_DATE_TIME_5 = new SimpleDateFormat("yyyy.MM.dd", Locale.US);
    public final static SimpleDateFormat HOUR_MINUTE = new SimpleDateFormat("HH:mm", Locale.US);
    public final static SimpleDateFormat YEAR = new SimpleDateFormat("yyyy", Locale.US);
    public final static SimpleDateFormat MONTH_DAY = new SimpleDateFormat("MMdd", Locale.US);
    public final static SimpleDateFormat MONTH = new SimpleDateFormat("MM", Locale.US);
    public final static SimpleDateFormat DAY = new SimpleDateFormat("dd", Locale.US);
    public static int TIMEUTILS_TODAY = 0;
    public static int TIMEUTILS_TOMORROW = 1;
    public static int TIMEUTILS_AFTER_TOMORROW = 2;


    public static String getCurTime() {
        Date curDate = new Date(System.currentTimeMillis());
        String strDate = TOTAL_DATE_TIME_1.format(curDate);
        return strDate;
    }

    public static String getCurTimeForVideo() {
        Date curDate = new Date(System.currentTimeMillis());
        String strDate = TOTAL_DATE_TIME_4.format(curDate);
        return strDate;
    }

    public static String getCurTime(SimpleDateFormat format) {
        Date curDate = new Date(System.currentTimeMillis());
        String strDate = format.format(curDate);
        return strDate;
    }

    public static String getCurTimeDay() {
        Date curDate = new Date(System.currentTimeMillis());
        String strDate = DATE1.format(curDate);

        return strDate;
    }

    public static String getCurTimeMonth() {
        Date curDate = new Date(System.currentTimeMillis());
        String strDate = MONTH.format(curDate);
        return strDate;
    }

    public static String getDate() {
        Date curDate = new Date(System.currentTimeMillis());
        String strDate = DATE.format(curDate);

        return strDate;
    }

    public static String getDate(SimpleDateFormat format) {
        Date curDate = new Date(System.currentTimeMillis());
        Date date = addDateTime(curDate, 30);
        String strDate = format.format(date);

        return strDate;
    }

    /**
     * 对日期进行增加操作
     *
     * @param target 需要进行运算的日期
     * @param day    天
     * @return
     */
    public static Date addDateTime(Date target, double day) {
        if (null == target || day < 0) {
            return target;
        }

        return new Date(target.getTime() + (long) (day * 24 * 60 * 60 * 1000));
    }

    public static boolean is24TimeFormat() {
        boolean bResult = true;
        ContentResolver cv = BaseApplication.getGlobalContext().getContentResolver();
        String strTimeFormat = android.provider.Settings.System.getString(cv,
                android.provider.Settings.System.TIME_12_24);

        if (strTimeFormat != null && strTimeFormat.equals("24")) {
            bResult = true;
        } else {
            bResult = false;
        }

        return bResult;
    }

    public static String getHourAndMinuFromMillis(String strTimeMillis) {
        String time = "";
        long lTimeMillis = Long.valueOf(strTimeMillis);
        String hourMin = HOUR_MINUTE.format(new Date(lTimeMillis * 1000));
        int mon = Integer.valueOf(MONTH.format(new Date(lTimeMillis * 1000)));
        int day = Integer.valueOf(DAY.format(new Date(lTimeMillis * 1000)));

        Date curDate = new Date(System.currentTimeMillis());
        int curMon = Integer.valueOf(MONTH.format(curDate));
        int curDay = Integer.valueOf(DAY.format(curDate));
        if (curMon == mon) {
            if (day == curDay + 1) {
                time = "明 ";
            } else if (day == curDay + 2) {
                time = "后 ";
            }
        } else if (curMon < mon) {
            if (day == 1) {
                time = "明 ";
            } else if (day == 2) {
                time = "后 ";
            }
        }
        time += hourMin;

        return time;
    }


    /*
     **传入时间格式 MM-DD HH:mm
     * 返回  -1  小于   0 等于  1 大于
     */
    public static int CompareWithCurDate(String time) {
        Date curDate = new Date(System.currentTimeMillis());
        String strCurDate = DATE_TIME.format(curDate);

        return time.compareTo(strCurDate);
    }

    /**
     * 获取星期
     */
    public static String getOrderWeek(int week) {
        switch (week) {
            case 1:
                return "周一";
            case 2:
                return "周二";
            case 3:
                return "周三";
            case 4:
                return "周四";
            case 5:
                return "周五";
            case 6:
                return "周六";
            case 7:
                return "周日";
            default:
                return "";
        }
    }

    /**
     * 获取信息为多久之前的方法
     *
     * @param updateTime
     * @return
     */
    public static String getTimeAgo(long updateTime) {
        String ret = null;
        long time = updateTime / 1000;
        if (time > 0) {
            long value = System.currentTimeMillis() / 1000 - time;
            if (value >= 0 && value <= 60) {
                ret = "刚刚";
            } else if (value > 60 && value <= 60 * 60) {
                ret = value / 60 + "分钟前";
            } else if (value > 60 * 60 && value <= 60 * 60 * 24) {
                ret = value / (60 * 60) + "小时前";
            } else if (value > 60 * 60 * 24 && value <= 60 * 60 * 24 * 3) {
                ret = value / (60 * 60 * 24) + "天前";
            } else {
                ret = DATE.format(time * 1000);
            }
        }
        return ret;
    }

    public static String getTimeAgo1(long updateTime) {
        String ret = null;
        long time = updateTime / 1000;
        if (time > 0) {
            long value = System.currentTimeMillis() / 1000 - time;
            if (value >= 0 && value <= 60) {
                ret = "刚刚";
            } else if (value > 60 && value <= 60 * 60) {
                ret = value / 60 + "分钟前";
            } else if (value > 60 * 60 && value <= 60 * 60 * 24) {
                ret = value / (60 * 60) + "小时前";
            } else if (value > 60 * 60 * 24 && value <= 60 * 60 * 24 * 3) {
                ret = value / (60 * 60 * 24) + "天前";
            } else {
                ret = TOTAL_DATE_TIME_3.format(time * 1000);
            }
        }
        return ret;
    }

    /**
     * 获取信息为多久之前的方法  动态
     *
     * @param updateTime
     * @return
     */
    public static String getTimeAgoDynamic(long updateTime) {
        String ret = null;
        long time = updateTime / 1000;
        if (time > 0) {
            long value = System.currentTimeMillis() / 1000 - time;
            if (value >= 0 && value <= 60) {
                ret = "刚刚";
            } else if (value > 60 && value <= 60 * 60) {
                ret = value / 60 + "分钟前";
            } else if (value > 60 * 60 && value <= 60 * 60 * 24) {
                ret = value / (60 * 60) + "小时前";
            } else if (value > 60 * 60 * 24 && value < 60 * 60 * 24 * 2) {
                ret = "昨天 " + HOUR_MINUTE.format(new Date(updateTime));
            } else if (value > 60 * 60 * 24 && value < 60 * 60 * 24 * 3) {
                ret = "前天 " + HOUR_MINUTE.format(new Date(updateTime));
            } else if (value > 60 * 60 * 24 * 3) {
                if (isYear(updateTime)) {
                    ret = DATE_TIME.format(new Date(updateTime));
                } else {
                    ret = TOTAL_DATE_TIME_1.format(new Date(updateTime));
                }
            } else {
                ret = DATE.format(time * 1000);
            }
        }
        return ret;
    }

    /**
     * 获取今天日期和周几
     */
    public static String getCurrentDate() {
        Date curDate = new Date(System.currentTimeMillis());
        String strDate = TOTAL_DATE_TIME_3.format(curDate);
        String week = getWeek(TIMEUTILS_TODAY);
        return strDate + " " + week;
    }


    /**
     * @param type TIMEUTILS_TODAY  今天星期几
     *             TIMEUTILS_TOMORROW 明天星期几
     * @return TIMEUTILS_AFTER_TOMORROW 后天星期几
     */
    public static String getWeek(int type) {
        Calendar instance = Calendar.getInstance();
        String way = String.valueOf(instance.get(Calendar.DAY_OF_WEEK));
        if ("1".equals(way)) {
            if (type == TIMEUTILS_TODAY) {
                way = "日";
            } else if (type == TIMEUTILS_TOMORROW) {
                way = "一";
            } else if (type == TIMEUTILS_AFTER_TOMORROW) {
                way = "二";
            }
        } else if ("2".equals(way)) {
            if (type == TIMEUTILS_TODAY) {
                way = "一";
            } else if (type == TIMEUTILS_TOMORROW) {
                way = "二";
            } else if (type == TIMEUTILS_AFTER_TOMORROW) {
                way = "三";
            }
        } else if ("3".equals(way)) {
            if (type == TIMEUTILS_TODAY) {
                way = "二";
            } else if (type == TIMEUTILS_TOMORROW) {
                way = "三";
            } else if (type == TIMEUTILS_AFTER_TOMORROW) {
                way = "四";
            }
        } else if ("4".equals(way)) {
            if (type == TIMEUTILS_TODAY) {
                way = "三";
            } else if (type == TIMEUTILS_TOMORROW) {
                way = "四";
            } else if (type == TIMEUTILS_AFTER_TOMORROW) {
                way = "五";
            }
        } else if ("5".equals(way)) {
            if (type == TIMEUTILS_TODAY) {

            } else if (type == TIMEUTILS_TOMORROW) {

            } else if (type == TIMEUTILS_AFTER_TOMORROW) {

            }
            way = "四";
        } else if ("6".equals(way)) {
            if (type == TIMEUTILS_TODAY) {
                way = "五";
            } else if (type == TIMEUTILS_TOMORROW) {
                way = "六";
            } else if (type == TIMEUTILS_AFTER_TOMORROW) {
                way = "日";
            }
        } else if ("7".equals(way)) {
            if (type == TIMEUTILS_TODAY) {
                way = "六";
            } else if (type == TIMEUTILS_TOMORROW) {
                way = "天";
            } else if (type == TIMEUTILS_AFTER_TOMORROW) {
                way = "一";
            }
        }
        return "周" + way;
    }


    /**
     * 获取年
     *
     * @return
     */
    public static int getYear() {
        Calendar cd = Calendar.getInstance();
        return cd.get(Calendar.YEAR);
    }

    public static boolean isYear(long time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date(time));
        int year = calendar.get(Calendar.YEAR);

        return year == getYear();
    }


    public static String getTime(Date time, SimpleDateFormat format) {
        String strDate = format.format(time);
        return strDate;
    }

    public static Long getTime(String time, SimpleDateFormat format) {
        Date date = null;
        try {
            date = format.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date.getTime();
    }

    public static String conversion(String strTime, String formatType, String toFormatType)
            throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat(formatType);
        SimpleDateFormat toformatType = new SimpleDateFormat(toFormatType);
        Date date = null;
        date = formatter.parse(strTime);
        return toformatType.format(date);
    }

    public static String getTime(long timeStamp, SimpleDateFormat format) {
        Date date = new Date(timeStamp);
        String dateStr = format.format(date);
        return dateStr;
    }


    public static String timeStampToMouth(long timeStamp) {
        Date date = new Date(timeStamp);
        String dateStr = DATE3.format(date);
        return dateStr;
    }

    /*
     * 将秒数转为时分秒
     * */
    public static String change(int second) {
        int h = 0;
        int d = 0;
        int s = 0;
        int temp = second % 3600;
        if (second > 3600) {
            h = second / 3600;
            if (temp != 0) {
                if (temp > 60) {
                    d = temp / 60;
                    if (temp % 60 != 0) {
                        s = temp % 60;
                    }
                } else {
                    s = temp;
                }
            }
        } else {
            d = second / 60;
            if (second % 60 != 0) {
                s = second % 60;
            }
        }

        return h + ":" + d + ":" + s + "";
    }

    public static String getAstro(int month, int day) {
        String[] constellation = new String[]{"摩羯座", "水瓶座", "双鱼座", "白羊座", "金牛座",
                "双子座", "巨蟹座", "狮子座", "处女座", "天秤座", "天蝎座", "射手座", "摩羯座"};
        int[] arr = new int[]{20, 19, 21, 21, 21, 22, 23, 23, 23, 23, 22, 22};
        int index = month;
        if (day < arr[month - 1]) {
            index = index - 1;
        }
        return constellation[index];
    }

    //是否是同一天
    public static boolean isSameDay(long time) {
        Date curDate = new Date(System.currentTimeMillis());
        Date curDate1 = new Date(time);
        String strDate = DATE1.format(curDate);
        String strDate1 = DATE1.format(curDate1);
        return strDate.equals(strDate1);
    }

    public static String getFormattedDateByTimeStamp(long timeStamp) {

        Date date = new Date(timeStamp);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
        return formatter.format(date);

    }

    public static String secToTime(int seconds) {
        int hour = seconds / 3600;
        int minute = (seconds - hour * 3600) / 60;
        int second = (seconds - hour * 3600 - minute * 60);

        StringBuffer sb = new StringBuffer();
        if (hour > 0) {
            sb.append(hour + "小时");
        }
        if (minute > 0) {
            sb.append(minute + "分");
        }
        if (second > 0) {
            sb.append(second + "秒");
        }
        if (second == 0) {
        }
        return sb.toString();
    }
}
