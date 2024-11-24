package org.example;

import java.sql.*;

public class ConnectTest {
    private static final String URL = "jdbc:postgresql://aws-0-us-west-1.pooler.supabase.com:6543/postgres";
    private static final String USER = "postgres.jjxrfxvoadgbjxhpxavd";
    private static final String PASSWORD = "kotzyc-4peBka-viqnuv";

    public static void main(String[] args) throws SQLException {

    }

    public static void updateAuthor(int id, String firstName) throws SQLException {
        String query = "UPDATE author SET first_name = ? WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, firstName);
            stmt.setInt(2, id);

            int rowsAffected = stmt.executeUpdate();
            System.out.println("Rows updated: " + rowsAffected);
        }
    }

    // 插入作者
    public static void insertAuthor(int id, String firstName, String lastName) throws SQLException {
        String query = "INSERT INTO author (id, first_name, last_name) VALUES (?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            // 设置参数
            stmt.setInt(1, id);
            stmt.setString(2, firstName);
            stmt.setString(3, lastName);

            // 执行插入操作
            int rowsAffected = stmt.executeUpdate();
            System.out.println("Rows inserted: " + rowsAffected);
        }
    }

    public static void getAuthorByFirstName(String firstName) throws SQLException {
        String query = "SELECT id, last_name FROM author WHERE first_name = ?";  // 查询 id 和 last_name
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            // 设置参数
            stmt.setString(1, firstName);

            // 执行查询操作
            ResultSet rs = stmt.executeQuery();

            // 如果有结果，打印出每一条记录
            while (rs.next()) {
                int id = rs.getInt("id");
                String lastName = rs.getString("last_name");
                System.out.printf("ID: %d, LastName: %s%n", id, lastName);
            }

            // 如果没有找到记录
            if (!rs.isBeforeFirst()) {
                System.out.println("No author found with first name: " + firstName);
            }

        }

    }
}
