package com.payroll.util;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public final class DB {
    private static final Properties CONFIG = new Properties();

    static {
        try (InputStream input = DB.class.getClassLoader().getResourceAsStream("db.properties")) {
            if (input == null) {
                throw new IllegalStateException("db.properties was not found.");
            }
            CONFIG.load(input);
            Class.forName("oracle.jdbc.OracleDriver");
        } catch (Exception e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    private DB() {}

    public static Connection getConnection() throws Exception {
        String url = System.getenv().getOrDefault("PAYROLL_DB_URL", CONFIG.getProperty("db.url"));
        String user = System.getenv().getOrDefault("PAYROLL_DB_USER", CONFIG.getProperty("db.username"));
        String password = System.getenv().getOrDefault("PAYROLL_DB_PASSWORD", CONFIG.getProperty("db.password"));
        return DriverManager.getConnection(url, user, password);
    }
}
