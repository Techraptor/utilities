package com.falconraptor.utilities.sql

import java.sql.{Connection, DriverManager, ResultSet, Statement}

object SQL {
  private var c: Connection = null

  @throws(classOf[Exception]) def connect(sql: String, u: String, p: String) {
    Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver")
    c = DriverManager.getConnection(sql, u, p)
  }

  @throws(classOf[Exception]) def getStatement: Statement = c.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)

  @throws(classOf[Exception]) def close() = c.close
}