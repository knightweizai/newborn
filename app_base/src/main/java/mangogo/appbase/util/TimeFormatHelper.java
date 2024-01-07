package mangogo.appbase.util;

import android.text.TextUtils;
import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * @author chengang
 * @date 2019-06-15
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_public.helper
 * @describe
 */
public class TimeFormatHelper {

    private final static int MIN_SEC = 60;
    private final static int HOUR_SEC = 60 * MIN_SEC;
    private final static int DAY_SEC = 24 * HOUR_SEC;


    public static String getNoComplementMonthAndDay(long sec) {
        //设置日期格式
        SimpleDateFormat df = new SimpleDateFormat("M月d日");
        return df.format(new Date(sec * 1000));
    }

    public static String getMonthAndDay(long sec) {
        //设置日期格式
        SimpleDateFormat df = new SimpleDateFormat("MM月dd日");
        return df.format(new Date(sec * 1000));
    }


    public static String getYearMonthAndDayFormatChina(long sec) {
        //设置日期格式
        SimpleDateFormat df = new SimpleDateFormat("yyyy年MM月dd日");
        return df.format(new Date(sec * 1000));
    }

    public static String getYearMonthAndDayByPoint(long sec) {
        //设置日期格式
        SimpleDateFormat df = new SimpleDateFormat("yyyy.MM.dd");
        return df.format(new Date(sec * 1000));
    }

    public static String getHourAndMinute(long sec) {
        SimpleDateFormat df = new SimpleDateFormat("HH:mm");
        return df.format(new Date(sec * 1000));
    }

    public static long getMilliSecondByYearMonthDay(String time) {
        long milliSecond = 0;
        if (TextUtils.isEmpty(time)) return milliSecond;
        try {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            Date date = df.parse(time);
            milliSecond = date.getTime();
        } catch (Exception e) {

        }

        return milliSecond;

    }
    public static String formatDatetimeByYearMonthDaySecondMinutes(String starttime)  {

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat df2 = new SimpleDateFormat("yyyyMMddHHmmss");
        String startStr = "";
        try {
            Date d1 = df.parse(starttime);
             startStr = df2.format(d1);

        }
        catch (Exception e){}
        return startStr;
    }
    public static long getMilliSecondByHourMinute(String time) {
        long milliSecond = 0;
        if (TextUtils.isEmpty(time)) return milliSecond;
        try {
            SimpleDateFormat df = new SimpleDateFormat("HH:mm");
            Date date = df.parse(time);
            milliSecond = date.getTime();
        } catch (Exception e) {

        }

        return milliSecond;
    }

    public static String getYearMonthDayRange(long start, long end) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
        String startStr = df.format(new Date(start * 1000));
        String endStr = df.format(new Date(end * 1000));
        if (startStr.equals(endStr)) {
            return startStr;
        }
        return startStr + "~" + endStr;
    }

    public static String getYearMonthDayRange(long start, long end,String splitstring) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String startStr = df.format(new Date(start * 1000));
        String endStr = df.format(new Date(end * 1000));
        if (startStr.equals(endStr)) {
            return startStr;
        }
        return startStr + splitstring + endStr;

    }

    public static String getYearMonthDayHourMinuteMillSecondsRange(long start, long end,String splitstring) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String startStr = df.format(new Date(start * 1000));
        String endStr = df.format(new Date(end * 1000));
        if (startStr.equals(endStr)) {
            return startStr;
        }
        return startStr + splitstring + endStr;

    }

    public static String getYearMonthDayRange(long start) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String startStr = df.format(new Date(start));
        return startStr ;

    }

    public static String getYearMonthDayHourMinRangeByLine(long start, long end) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        String startStr = df.format(new Date(start * 1000));
        String endStr = df.format(new Date(end * 1000));
        if (startStr.equals(endStr)) {
            return startStr;
        }
        return startStr + "~" + endStr;

    }

    public static String getYearMonthDayHourMinRangeByPoint(long start, long end) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy.MM.dd HH:mm");
        String startStr = df.format(new Date(start * 1000));
        String endStr = df.format(new Date(end * 1000));
        if (startStr.equals(endStr)) {
            return startStr;
        }
        return startStr + "-" + endStr;

    }


    public static String getMonthDayRange(long start, long end) {
        SimpleDateFormat df = new SimpleDateFormat("MM/dd HH:mm:ss");
        String startStr = df.format(new Date(start * 1000));
        String endStr = df.format(new Date(end * 1000));
        return startStr + "~" + endStr;
    }

    public static String getMonthDayFull(long start) {
        SimpleDateFormat df = new SimpleDateFormat("MM/dd HH:mm:ss");
        return df.format(new Date(start * 1000));
    }

    public static String getFullDate(long sec) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return df.format(new Date(sec * 1000));
    }

    public static String getFullDateSplitByPoint(long sec) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
        return df.format(new Date(sec * 1000));
    }


    public static String getYearMonthDayHourMinSplitByPoint(long sec) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy.MM.dd HH:mm");
        return df.format(new Date(sec * 1000));
    }

    public static String getYearMonthDayHourMinSplitByPointCN(long sec) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
        return df.format(new Date(sec * 1000));
    }

    public static String getYearMonthDayHourMinSplitByCross(long sec) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return df.format(new Date(sec * 1000));
    }

    public static String getFullDateSplitByLine(long sec) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        return df.format(new Date(sec * 1000));
    }

    public static String getFullDateSplitByCross(long sec) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return df.format(new Date(sec));
    }

    public static String formatSeconds(long seconds) {
        String standardTime;
        if (seconds <= 0) {
            standardTime = "00:00";
        } else if (seconds < 60) {
            standardTime = String.format(Locale.getDefault(), "00:%02d", seconds % 60);
        } else if (seconds < 3600) {
            standardTime = String.format(Locale.getDefault(), "%02d:%02d", seconds / 60, seconds % 60);
        } else {
            standardTime = String.format(Locale.getDefault(), "%02d:%02d:%02d", seconds / 3600, seconds % 3600 / 60, seconds % 60);
        }
        return standardTime;
    }

    public static String formatSecondsChinese(long seconds) {
        String standardTime;
        if (seconds <= 0) {
            standardTime = "";
        } else if (seconds < 60) {
            standardTime = String.format(Locale.getDefault(), "%02d秒", seconds % 60);
        } else if (seconds < 3600) {
            standardTime = String.format(Locale.getDefault(), "%02d分%02d秒", seconds / 60, seconds % 60);
        } else {
            standardTime = String.format(Locale.getDefault(), "%02d时%02d分%02d秒", seconds / 3600, seconds % 3600 / 60, seconds % 60);
        }
        return standardTime;
    }


    public static String getDayHourMinToCnBySec(long sec) {
        StringBuilder result = new StringBuilder("");
        int day = (int) (sec / DAY_SEC);
        int hour = (int) ((sec - day * DAY_SEC) / HOUR_SEC);
        int min = (int) ((sec - day * DAY_SEC - hour * HOUR_SEC) / 60);

        if (day > 0) {
            result.append(day).append("天");
        }
        if (hour > 0) {
            result.append(hour).append("小时");
        }
        if (min > 0) {
            result.append(min).append("分");
        }

        return result.toString();


    }

    public static String getYearBySecond(long second) {
        //设置日期格式
        SimpleDateFormat df = new SimpleDateFormat("yyyy");
        return df.format(new Date(second * 1000));
    }

    public static String getMonth(long second) {
        //设置日期格式
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM");
        return df.format(new Date(second * 1000));
    }

    public static String getDayBySecond(long second) {
        //设置日期格式
        SimpleDateFormat df = new SimpleDateFormat("dd");
        return df.format(new Date(second * 1000));
    }

    public static String getDate(long second) {
        //设置日期格式
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.format(new Date(second * 1000));
    }

    public static String getDateByMillisecond(long millisecond) {
        //设置日期格式
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.format(new Date(millisecond));
    }

    public static String getDateByFormat(long second, String format) {
        //设置日期格式
        SimpleDateFormat df = new SimpleDateFormat(format);
        return df.format(new Date(second * 1000));
    }

    public static String formatTimeMonthChinese(Date d) {
        if (d == null) {
            d = new Date(0L);
        }

        DateFormat f = new SimpleDateFormat("yyyy年MM月", Locale.getDefault());
        return f.format(d);
    }

    public static int getTimeInSecond(int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();//日历类的实例化
        calendar.set(year, month - 1, day);//设置日历时间，月份必须减一
        return (int) (calendar.getTimeInMillis() / 1000);
    }

    public static String formatDatetimeByYearMonthDay(String starttime,String endTime) {

        try {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            Date d1 = df.parse(starttime);
            Date d2 = df.parse(endTime);
            String startStr = df.format(d1);
            String endStr = df.format(d2);
            if (startStr.equals(endStr)) {
                return startStr;
            }
            return startStr + "至" + endStr;

        } catch (Exception e) {
            Log.e("wutianrong",e.getMessage());
        }
        return "";
    }

    public static String formatDatetimeByYearMonthDayHourMinuteMillseconds(String starttime,String endTime) {

        try {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date d1 = df.parse(starttime);
            Date d2 = df.parse(endTime);
            String startStr = df.format(d1);
            String endStr = df.format(d2);
            if (startStr.equals(endStr)) {
                return startStr;
            }
            return startStr + "至" + endStr;

        } catch (Exception e) {
            Log.e("wutianrong",e.getMessage());
        }
        return "";
    }
    public static String formatDatetimeByYearMonthDay(String starttime) {

        try {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            Date d1 = df.parse(starttime);

            String startStr = df.format(d1);


            return startStr;

        } catch (Exception e) {

        }
        return "";
    }


    public static long getStringToDateBYYMD_HMS(String dateString){
        return getStringToDate(dateString,"yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 将字符串转为时间戳
     * @param dateString
     * @param pattern
     * @return
     */
    public static long getStringToDate(String dateString, String pattern) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        Date date = new Date();
        try{
            date = dateFormat.parse(dateString);
        } catch(ParseException e) {
            e.printStackTrace();
        }
        return date.getTime();
    }



}
