/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package db;

/**
 *
 * @author Fardan
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Koneksi {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/bonas_studio";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";
    
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    
    private static Connection connection = null;

    public static Connection getConnection() {
        try {
            // Load MySQL JDBC Driver
            Class.forName(DRIVER);
            
            // Buat koneksi baru setiap kali dipanggil (safe untuk multi-thread)
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            
            return connection;
            
        } catch (ClassNotFoundException e) {
            System.err.println("MySQL JDBC Driver tidak ditemukan!");
            e.printStackTrace();
            return null;
            
        } catch (SQLException e) {
            System.err.println("Koneksi database gagal!");
            System.err.println("Pesan error: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public static void closeConnection(Connection con) {
        try {
            if (con != null && !con.isClosed()) {
                con.close();
                System.out.println("Koneksi database ditutup.");
            }
        } catch (SQLException e) {
            System.err.println("Error saat menutup koneksi: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void testConnection() {
        Connection con = getConnection();
        
        if (con != null) {
            System.out.println("✅ Koneksi database BERHASIL!");
            System.out.println("Database: " + DB_URL);
            System.out.println("User: " + DB_USER);
            closeConnection(con);
        } else {
            System.out.println("❌ Koneksi database GAGAL!");
            System.out.println("Cek konfigurasi database Anda.");
        }
    }

    public static void main(String[] args) {
        System.out.println("=== TEST KONEKSI DATABASE ===");
        testConnection();
    }
}
