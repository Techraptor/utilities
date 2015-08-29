package com.falconraptor.utilities.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class SQL {
    private static SQL instance = null;
    private Connection c = null;

    public static SQL getInstance() {
        if (instance == null) instance = new SQL();
        return instance;
    }

    public void connect(String sql, String u, String p, SqlType s) throws Exception {
        if (s == SqlType.MSSQL) Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        else if (s == SqlType.MYSQL) Class.forName("com.mysql.jdbc.Driver");
        else if (s == SqlType.ACCESS) Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
        c = DriverManager.getConnection(sql, u, p);
    }

    public Statement getStatement() throws Exception {
        return c.createStatement(1004, 1007);
    }

    public void close() throws SQLException {
        c.close();
    }

    public enum SqlType {MSSQL, MYSQL, ACCESS}
}
