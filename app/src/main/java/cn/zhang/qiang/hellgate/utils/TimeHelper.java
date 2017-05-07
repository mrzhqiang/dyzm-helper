package cn.zhang.qiang.hellgate.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * <p>
 * Created by mrZQ on 2017/4/8.
 */

public final class TimeHelper {
    // 通用的全时间格式可以用 Date.toLocaleString() 这里将使用中文显示
    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒 E", Locale.getDefault());
    // 默认（全格式，隐藏秒钟）、今年、周几（通常间隔少于一周）、今天
    public static final SimpleDateFormat DATE_NORMAL = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
    public static final SimpleDateFormat DATE_THIS_YEAR = new SimpleDateFormat("MM-dd HH:mm", Locale.getDefault());
    public static final SimpleDateFormat DATE_WEEK_DAY = new SimpleDateFormat("HH:mm E", Locale.getDefault());
    public static final SimpleDateFormat DATE_TODAY = new SimpleDateFormat("HH:mm", Locale.getDefault());

    /** 检测某个时间戳是不是[今年] */
    public static GregorianCalendar isThisYear(long time) {
        GregorianCalendar thisYear = new GregorianCalendar();
        int year = thisYear.get(Calendar.YEAR);

        thisYear.setTimeInMillis(time);
        if (thisYear.get(Calendar.YEAR) == year) {
            return thisYear;
        }
        return null;
    }

    /** 检测某个时间戳是不是[今天] */
    public static GregorianCalendar isToday(long time) {
        GregorianCalendar today = isThisYear(time);

        if (today != null) {
            GregorianCalendar nowCalendar = new GregorianCalendar();
            int day = nowCalendar.get(Calendar.DAY_OF_YEAR);

            if (today.get(Calendar.DAY_OF_YEAR) == day) {
                return today;
            }
        }
        return null;
    }

    /** 检测某个时间点是不是[刚刚] */
    public static boolean isNow(long time) {
        GregorianCalendar now = isToday(time);

        if (now != null) {
            GregorianCalendar calendar = new GregorianCalendar();
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            int min = calendar.get(Calendar.MINUTE);

            if (now.get(Calendar.HOUR_OF_DAY) == hour && now.get(Calendar.MINUTE) == min) {
                return true;
            }
        }
        return false;
    }

    /** 找到一个合适的方式显示传入的时间戳 */
    public static String showTime(long time) {
        if (isNow(time)) {
            return "刚刚";
        }

        GregorianCalendar calendar = isToday(time);
        if (calendar != null) {
            return DATE_TODAY.format(calendar.getTime());// 今天
        }

        calendar = isThisYear(time);
        if (calendar != null) {
            GregorianCalendar now = new GregorianCalendar();
            int day = now.get(Calendar.DAY_OF_YEAR);

            int dayOffset = Math.abs(now.get(Calendar.DAY_OF_YEAR) - day);
            if (dayOffset < 7) {
                return DATE_WEEK_DAY.format(now.getTime());
            }
            return DATE_THIS_YEAR.format(now.getTime());
        }

        return DATE_NORMAL.format(new Date(time));
    }

    private TimeHelper() {
        throw new AssertionError("not instance.");
    }
}
