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
 * LoginService — Service Layer (Anggota 3)
 * Menangani autentikasi: login, register, logout.
 * Tidak implements Manageable karena tidak melakukan CRUD tunggal ke satu tabel.
 *
 * Alur login():
 *   1. openConnection()
 *   2. SELECT dari tabel users WHERE email=? AND password=?
 *   3. Baca kolom 'role' → buat objek Penghobi atau Mentor
 *   4. closeConnection()
 *   5. Return objek User (atau null jika gagal)
 */
public class LoginService {

    // =========================================================
    // ATRIBUT (private)
    // =========================================================

    /** Koneksi ke DB untuk cek email+password */
    private DatabaseConnection db;

    /** Menyimpan user yang sedang aktif login */
    private User currentUser;

    // =========================================================
    // CONSTRUCTOR
    // =========================================================

    public LoginService() {
        this.db          = new DatabaseConnection();
        this.currentUser = null;
    }

    // =========================================================
    // METHOD PUBLIC
    // =========================================================

    /**
     * Memvalidasi kredensial user ke database.
     * Mengembalikan objek Penghobi atau Mentor sesuai role, atau null jika gagal.
     *
     * @param email    email user
     * @param password password user (raw)
     * @return User yang berhasil login, atau null
     */
    public User login(String email, String password) {
        if (email == null || password == null || email.trim().isEmpty()) {
            return null;
        }

        User user = null;
        db.openConnection();
        try {
            String query = "SELECT * FROM users WHERE email = '" +
                           email.trim() + "' AND password = '" + password + "'";
            ResultSet rs = db.getData(query);

            if (rs != null && rs.next()) {
                String role = rs.getString("role");
                int    id   = rs.getInt("id");
                String nama = rs.getString("nama");
                String em   = rs.getString("email");
                String pass = rs.getString("password");

                if ("PENGHOBI".equals(role)) {
                    user = new Penghobi(
                        id, nama, em, pass,
                        rs.getString("hobi_ikan"),
                        rs.getString("tingkat_keanggotaan"),
                        rs.getString("tanggal_daftar") != null
                            ? rs.getString("tanggal_daftar") : ""
                    );
                } else if ("MENTOR".equals(role)) {
                    user = new Mentor(
                        id, nama, em, pass,
                        rs.getString("keahlian"),
                        rs.getBoolean("status_verifikasi"),
                        rs.getInt("total_sesi")
                    );
                }

                if (rs.getStatement() != null) rs.getStatement().close();
            }
        } catch (SQLException e) {
            System.err.println("[LoginService] Error saat login: " + e.getMessage());
        } finally {
            db.closeConnection();
        }

        this.currentUser = user;
        if (user != null) {
            System.out.println("[LoginService] Login berhasil: " + user.getNama() + " (" + user.getRole() + ")");
        } else {
            System.out.println("[LoginService] Login gagal: email/password salah.");
        }
        return user;
    }

    /**
     * Mendaftarkan user baru ke database.
     * Mendukung Penghobi dan Mentor via instanceof check.
     *
     * @param user objek User yang akan di-INSERT
     * @return true jika berhasil
     */
    public boolean register(User user) {
        if (user == null) return false;

        db.openConnection();
        boolean berhasil = false;
        try {
            String query;
            if (user instanceof Penghobi) {
                Penghobi p = (Penghobi) user;
                query = "INSERT INTO users (nama, email, password, role, hobi_ikan, " +
                        "tingkat_keanggotaan, tanggal_daftar) VALUES ('" +
                        p.getNama() + "','" + p.getEmail() + "','" + p.getPassword() +
                        "','PENGHOBI','" + p.getHobiIkan() + "','" +
                        p.getTingkatKeanggotaan() + "','" + p.getTanggalDaftar() + "')";
            } else if (user instanceof Mentor) {
                Mentor m = (Mentor) user;
                query = "INSERT INTO users (nama, email, password, role, keahlian, " +
                        "status_verifikasi, total_sesi) VALUES ('" +
                        m.getNama() + "','" + m.getEmail() + "','" + m.getPassword() +
                        "','MENTOR','" + m.getKeahlian() + "'," +
                        (m.isVerified() ? 1 : 0) + ",0)";
            } else {
                db.closeConnection();
                return false;
            }

            int rows = db.executeStatement(query);
            berhasil  = rows > 0;
        } catch (Exception e) {
            System.err.println("[LoginService] Error saat register: " + e.getMessage());
        } finally {
            db.closeConnection();
        }

        if (berhasil) {
            System.out.println("[LoginService] Registrasi berhasil: " + user.getNama());
        }
        return berhasil;
    }

    /**
     * Logout — hapus session currentUser.
     */
    public void logout() {
        System.out.println("[LoginService] User '" +
            (currentUser != null ? currentUser.getNama() : "?") + "' telah logout.");
        this.currentUser = null;
    }

    /**
     * Getter currentUser — dipakai MainFrame untuk menentukan menu.
     * @return User yang sedang login, atau null jika belum login
     */
    public User getCurrentUser() {
        return currentUser;
    }

    /**
     * Cek apakah ada user yang sedang login.
     * @return true jika currentUser != null
     */
    public boolean isLoggedIn() {
        return currentUser != null;
    }
}

