package com.falconraptor.utilities;

import java.time.LocalDateTime;
import java.time.ZoneId;

public class Random {
    public static int[] parseStringArray(String[] s) {
        int[] temp = new int[s.length];
        for (int i = 0; i < s.length; i++) temp[i] = Integer.parseInt(s[i]);
        return temp;
    }

    public static int[] calcTimeLeft(int[] time, int[] date) {
        LocalDateTime order, now = LocalDateTime.now(ZoneId.of("GMT")), temp;
        try {
            order = LocalDateTime.of(date[0], date[1], date[2], time[0], time[1], time[2]);
        } catch (Exception e) {
            e.printStackTrace();
            return new int[]{0, 0, 0};
        }
        double minutes = 0;
        temp = order;
        while (!(temp.getYear() == now.getYear() && temp.getDayOfMonth() == now.getDayOfMonth() && temp.getMonthValue() == now.getMonthValue() && temp.getHour() == now.getHour() && temp.getMinute() == now.getMinute() && temp.getSecond() == now.getSecond())) {
            temp = temp.plusSeconds(1);
            minutes += 1;
        }
        int minute, hour, second;
        hour = (int) minutes / 3600;
        minutes = minutes % 3600;
        minute = (int) minutes / 60;
        second = (int) minutes % 60;
        return new int[]{hour, minute, second};
    }
}
