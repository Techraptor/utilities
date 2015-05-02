package com.falconraptor.utilities.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class SQL {
    private static SQL instance = null;
    private Connection c = null;

    private SQL() {
    }

    public static SQL getInstance() {
        if (instance == null) instance = new SQL();
        return instance;
    }

    public void connect(String sql, String u, String p) throws Exception {
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        c = DriverManager.getConnection(sql, u, p);
    }

    public Statement getStatement() throws Exception {
        return c.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
    }

    public void close() throws Exception {
        c.close();
    }
}
