package com.falconraptor.utilities

import com.falconraptor.utilities.logger.Logger

object Colors {
  def checkErrors(test: String): Array[Int] = {
    Array[Int](findNum(test, "r="), findNum(test, "g="), findNum(test, "b="))
  }

  def findNum(abc: String, find: String): Int = {
    var a = 5
    var i = 0
    while (i < 4) {
      try {
        abc.substring(abc.indexOf(find) + 2, abc.indexOf(find) + a).toInt
        i = 4
      } catch {
        case e: Exception =>
          val log: String = "[com.falconraptor.utilities.Colors."
          if (Logger.level <= 5) Logger.logERROR(log + "findNum] " + e)
          a -= 1
      }
      i += 1
    }
    a
  }
}