/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tubesriyal;

/**
 *
 * @author 7320
 */
import java.sql.*;

/**
 * DatabaseConnection — Foundation Layer
 * Satu-satunya class yang berinteraksi langsung dengan MySQL.
 * Semua Service menggunakan class ini untuk query.
 *
 * Setup MySQL yang diperlukan:
 *   CREATE DATABASE fishlens;
 *   - Tambahkan mysql-connector-j-x.x.x.jar ke Libraries di NetBeans
 */
public class DatabaseConnection {

    // =========================================================
    // ATRIBUT (semua private)
    // =========================================================

    /** URL koneksi JDBC ke MySQL lokal */
    private final String URL      = "jdbc:mysql://localhost:3306/fishlens";

    /** Username MySQL */
    private final String USERNAME = "root";

    /** Password MySQL — sesuaikan dengan konfigurasi lokal */
    private final String PASSWORD = "";

    /** Objek koneksi aktif ke database */
    private Connection connection;

    // =========================================================
    // CONSTRUCTOR
    // =========================================================

    public DatabaseConnection() {
        this.connection = null;
    }

    // =========================================================
    // METHOD PUBLIC
    // =========================================================

    /**
     * Membuka koneksi ke MySQL menggunakan JDBC Driver.
     * Dipanggil tiap kali Service mau melakukan query.
     */
    public void openConnection() {
        try {
            // Pastikan driver terdaftar
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            System.out.println("[DB] Koneksi berhasil dibuka.");
        } catch (ClassNotFoundException e) {
            System.err.println("[DB ERROR] Driver MySQL tidak ditemukan: " + e.getMessage());
            System.err.println("[DB ERROR] Pastikan mysql-connector-j sudah ditambahkan ke Libraries!");
        } catch (SQLException e) {
            System.err.println("[DB ERROR] Gagal membuka koneksi: " + e.getMessage());
        }
    }

    /**
     * Menutup koneksi setelah query selesai.
     * Wajib dipanggil setelah openConnection() untuk menghindari memory leak.
     */
    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("[DB] Koneksi ditutup.");
            }
        } catch (SQLException e) {
            System.err.println("[DB ERROR] Gagal menutup koneksi: " + e.getMessage());
        }
    }

    /**
     * Menjalankan query SELECT.
     * @param query String SQL SELECT yang akan dijalankan
     * @return ResultSet berisi baris-baris data, atau null jika gagal
     *
     * PENTING: Caller bertanggung jawab menutup ResultSet & Statement setelah selesai.
     * Koneksi TIDAK ditutup di sini karena ResultSet masih perlu dibaca.
     */
    public ResultSet getData(String query) {
        try {
            if (connection == null || connection.isClosed()) {
                System.err.println("[DB ERROR] Koneksi belum dibuka. Panggil openConnection() dulu.");
                return null;
            }
            Statement stmt = connection.createStatement();
            return stmt.executeQuery(query);
        } catch (SQLException e) {
            System.err.println("[DB ERROR] Gagal menjalankan SELECT: " + e.getMessage());
            return null;
        }
    }

    /**
     * Menjalankan query INSERT, UPDATE, atau DELETE.
     * @param query String SQL DML yang akan dijalankan
     * @return jumlah baris yang terpengaruh, atau -1 jika gagal
     */
    public int executeStatement(String query) {
        try {
            if (connection == null || connection.isClosed()) {
                System.err.println("[DB ERROR] Koneksi belum dibuka. Panggil openConnection() dulu.");
                return -1;
            }
            Statement stmt = connection.createStatement();
            int rows = stmt.executeUpdate(query);
            stmt.close();
            return rows;
        } catch (SQLException e) {
            System.err.println("[DB ERROR] Gagal menjalankan statement: " + e.getMessage());
            return -1;
        }
    }

    /**
     * Getter connection — digunakan Service yang butuh PreparedStatement
     */
    public Connection getConnection() {
        return connection;
    }

    /**
     * Cek apakah koneksi saat ini aktif
     */
    public boolean isConnected() {
        try {
            return connection != null && !connection.isClosed();
        } catch (SQLException e) {
            return false;
        }
    }
}

