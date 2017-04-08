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
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
    private static final SimpleDateFormat DATE_NORMAL = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()); // 某年
    private static final SimpleDateFormat DATE_THIS_YEAR = new SimpleDateFormat("MM-dd HH:mm", Locale.getDefault()); // 今年
    private static final SimpleDateFormat DATE_TODAY = new SimpleDateFormat("HH:mm", Locale.getDefault()); // 今天

    private TimeHelper() {
        throw new AssertionError("not instance.");
    }

    /** 检测某个时间戳是不是[今年] */
    public static GregorianCalendar isThisYear(long time) {
        GregorianCalendar nowCalendar = new GregorianCalendar();
        int year = nowCalendar.get(Calendar.YEAR);

        nowCalendar.setTimeInMillis(time);
        if (nowCalendar.get(Calendar.YEAR) == year) {
            return nowCalendar;
        }
        return null;
    }

    /** 检测某个时间戳是不是[今天] */
    public static GregorianCalendar isToday(long time) {
        GregorianCalendar year = isThisYear(time);

        if (year != null) {
            GregorianCalendar nowCalendar = new GregorianCalendar();
            int day = nowCalendar.get(Calendar.DAY_OF_YEAR);

            if (year.get(Calendar.DAY_OF_YEAR) == day) {
                return year;
            }
        }
        return null;
    }

    /** 检测某个时间点是不是[刚刚] */
    public static GregorianCalendar isNow(long time) {
        GregorianCalendar today = isToday(time);

        if (today != null) {
            GregorianCalendar nowCalendar = new GregorianCalendar();
            int hour = nowCalendar.get(Calendar.HOUR_OF_DAY);
            int min = nowCalendar.get(Calendar.MINUTE);

            if (today.get(Calendar.HOUR_OF_DAY) == hour && today.get(Calendar.MINUTE) == min) {
                return today;
            }
        }
        return null;
    }

    /** 返回某个时间戳的星期（有效范围不超过当前时间7天） */
    public static String isWeekPeriod(long time) {
        GregorianCalendar year = isThisYear(time);

        if (year != null) {
            GregorianCalendar nowCalendar = new GregorianCalendar();
            int nowDay = nowCalendar.get(Calendar.DAY_OF_YEAR);
            int day = year.get(Calendar.DAY_OF_YEAR);

            if (Math.abs(nowDay - day) < 7) {
                int week = year.get(Calendar.DAY_OF_WEEK);
                switch (week) {
                    case Calendar.SUNDAY:
                        return "星期天";
                    case Calendar.MONDAY:
                        return "星期一";
                    case Calendar.TUESDAY:
                        return "星期二";
                    case Calendar.WEDNESDAY:
                        return "星期三";
                    case Calendar.THURSDAY:
                        return "星期四";
                    case Calendar.FRIDAY:
                        return "星期五";
                    case Calendar.SATURDAY:
                        return "星期六";
                }
            }
        }
        return "";
    }

    /** 找到一个合适的方式显示传入的时间戳 */
    public static String showTime(long time) {
        GregorianCalendar now = isNow(time);
        if (now != null) {
            return "刚刚";
        }

        GregorianCalendar today = isToday(time);
        if (today != null) {
            return DATE_TODAY.format(today.getTime());
        }

        GregorianCalendar thisYear = isThisYear(time);
        if (thisYear != null) {
            String week = isWeekPeriod(time);
            if (!week.equals("")) {
                return week + " " + DATE_TODAY.format(thisYear.getTime());
            }
            return DATE_THIS_YEAR.format(thisYear.getTime());
        }
        return DATE_NORMAL.format(new Date(time));
    }

    public static String showAllTime(long time) {
        return DATE_FORMAT.format(new Date(time));
    }

}
