package com.falconraptor.utilities.logger;

import com.falconraptor.utilities.files.Write;

import java.util.ArrayList;
import java.util.Calendar;

public class Logger {
    public static final int ALL = 1;
    public static final int DEBUG = 3;
    public static final int ERROR = 5;
    public static final int WARNING = 4;
    public static final int INFO = 2;
    public static ArrayList<String> log = new ArrayList<String>(0);
    public static int level = 3;

    private static Calendar getCalendar() {
        return Calendar.getInstance();
    }

    private static String formatCalender(Calendar c) {
        return "[" + c.get(Calendar.YEAR) + "-" + c.get(Calendar.MONTH) + 1 + "-" + c.get(Calendar.DAY_OF_MONTH) + "]" + " [" + c.get(Calendar.HOUR_OF_DAY) + ":" + c.get(Calendar.MINUTE) + ":" + c.get(Calendar.SECOND) + "] ";
    }

    public static void logALL(Object object) {
        log("[ALL] ", object);
    }

    public static void logDEBUG(Object object) {
        log("[DEBUG] ", object);
    }

    public static void logERROR(Object object) {
        log("[ERROR] ", object);
    }

    public static void logWARNING(Object object) {
        log("[WARNING] ", object);
    }

    public static void logINFO(Object object) {
        log("[INFO] ", object);
    }

    private static void log(String level, Object object) {
        System.out.println(formatCalender(getCalendar()) + level + object.toString());
        log.add(formatCalender(getCalendar()) + level + object.toString());
    }

    public static void saveLog(String file) {
        Write.Write(file, false);
        Write.write(log);
        Write.close();
    }
}
