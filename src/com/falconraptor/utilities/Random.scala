package com.falconraptor.utilities

import java.time.{LocalDateTime, ZoneId}

object Random {
  def parseStringArray(s: Array[String]): Array[Int] = {
    val temp: Array[Int] = new Array[Int](s.length)
    var i: Int = 0
    while (i < s.length) {
      temp(i) = s(i).toInt
      i += 1
    }
    temp
  }

  def calcTimeLeft(time: Array[Int], date: Array[Int]): Array[Int] = {
    var order: LocalDateTime = null
    val now = LocalDateTime.now(ZoneId.of("GMT"))
    var temp: LocalDateTime = null
    try {
      order = LocalDateTime.of(date(0), date(1), date(2), time(0), time(1), time(2))
    } catch {
      case e: Exception =>
        e.printStackTrace
        return Array[Int](0, 0, 0)
    }
    var minutes: Double = 0
    temp = order
    while (!(temp.getYear == now.getYear && temp.getDayOfMonth == now.getDayOfMonth && temp.getMonthValue == now.getMonthValue && temp.getHour == now.getHour && temp.getMinute == now.getMinute && temp.getSecond == now.getSecond)) {
      temp = temp.plusSeconds(1)
      minutes += 1
    }
    var minute = 0
    var hour = 0
    var second = 0
    hour = minutes.toInt / 3600
    minutes = minutes % 3600
    minute = minutes.toInt / 60
    second = minutes.toInt % 60
    Array[Int](hour, minute, second)
  }
}