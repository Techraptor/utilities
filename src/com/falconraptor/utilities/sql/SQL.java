package com.falconraptor.utilities.sql;

import java.sql.*;

public class SQL {
	public static final int mssql = 1;
	public static final int mysql = 2;
	private static SQL instance = null;
	private Connection c = null;

	public static SQL getInstance () {
		if (instance == null) instance = new SQL();
		return instance;
	}

	public void connect (String sql, String u, String p, int s) throws Exception {
		if (s == mssql) Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		else if (s == mysql) Class.forName("com.mysql.jdbc.Driver");
		this.c = DriverManager.getConnection(sql, u, p);
	}

	public Statement getStatement () throws Exception {
		return this.c.createStatement(1004, 1007);
	}

	public void close () throws SQLException {
		c.close();
	}
}
