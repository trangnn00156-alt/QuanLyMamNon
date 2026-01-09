package com.qlmn.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String URL = "jdbc:sqlite:C:/Users/LENOVO/OneDrive/Documents/NetBeansProjects/KTPM/QLMamNon/QLMN_DB.db"; // đường dẫn tới file SQLite
    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL);
        } catch (SQLException e) {
            System.out.println("❌ Lỗi kết nối DB: " + e.getMessage());
            return null;
        }
    }
    // Ví dụ trong class DatabaseConnection
public static Connection getConnection(boolean isTest) throws SQLException {
    if (isTest) {
        return DriverManager.getConnection("jdbc:sqlite::memory:");
    }
    return DriverManager.getConnection("jdbc:sqlite:qlmn.db");
}

}
