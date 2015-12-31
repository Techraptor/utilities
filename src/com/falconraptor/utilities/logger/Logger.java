package com.falconraptor.utilities.logger;

import com.falconraptor.utilities.files.Write;

import java.util.ArrayList;
import java.util.Calendar;

public class Logger {
    public static final Console console = new Console();
    private static final int ALL = 1;
    private static final int DEBUG = 3;
    private static final int ERROR = 5;
    private static final int WARNING = 4;
    private static final int INFO = 2;
    private static final ArrayList<String> log = new ArrayList<>(0);
    public static int level = 3;

    private static Calendar getCalendar() {
        return Calendar.getInstance();
    }

    private static String formatCalender(Calendar c) {
        return "[" + c.get(Calendar.YEAR) + "-" + (c.get(Calendar.MONTH) + 1) + "-" + c.get(Calendar.DAY_OF_MONTH) + "]" + " [" + c.get(Calendar.HOUR_OF_DAY) + ":" + c.get(Calendar.MINUTE) + ":" + c.get(Calendar.SECOND) + "] ";
    }

    public static void logALL(Object object) {
        if (level <= ALL) log("[ALL] ", object);
    }

    public static void logDEBUG(Object object) {
        if (level <= DEBUG) log("[DEBUG] ", object);
    }

    public static void logERROR(Object object) {
        if (level <= ERROR) log("[ERROR] ", object);
    }

    public static void logWARNING(Object object) {
        if (level <= WARNING) log("[WARNING] ", object);
    }

    public static void logINFO(Object object) {
        if (level <= INFO) log("[INFO] ", object);
    }

    private static void log(String level, Object object) {
        String logitem = formatCalender(getCalendar()) + level + object.toString();
        System.out.println(logitem);
        log.add(logitem);
        if (!console.closed) console.updateConsole(logitem);
    }

    public static void saveLog(String file) {
        Write write = new Write(file.substring(0, file.lastIndexOf(".")) + formatCalender(getCalendar()).substring(0, formatCalender(getCalendar()).lastIndexOf(":")).replaceAll(" ", "").replaceAll(":", ";") + "]" + file.substring(file.lastIndexOf(".")), false);
        write.write(log);
        write.close();
    }
}
