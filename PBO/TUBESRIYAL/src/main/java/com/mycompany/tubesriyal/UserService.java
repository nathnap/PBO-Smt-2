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
import java.util.*;

/**
 * UserService — Service Layer (Anggota 3)
 * Implements Manageable — menangani CRUD data profil user.
 * Dipakai oleh MainFrame saat buka menu Profil.
 */
public class UserService implements Manageable {

    // =========================================================
    // ATRIBUT (private)
    // =========================================================

    /** Koneksi ke DB untuk ambil dan update data profil */
    private DatabaseConnection db;

    // =========================================================
    // CONSTRUCTOR
    // =========================================================

    public UserService() {
        this.db = new DatabaseConnection();
    }

    // =========================================================
    // METHOD UTAMA
    // =========================================================

    /**
     * Mengambil profil user berdasarkan ID.
     * Membangun objek Penghobi atau Mentor tergantung role di DB.
     *
     * @param id ID user
     * @return objek User (Penghobi/Mentor), atau null jika tidak ditemukan
     */
    public User getProfil(int id) {
        User user = null;
        db.openConnection();
        try {
            ResultSet rs = db.getData("SELECT * FROM users WHERE id = " + id);
            if (rs != null && rs.next()) {
                String role = rs.getString("role");
                String nama = rs.getString("nama");
                String em   = rs.getString("email");
                String pass = rs.getString("password");

                if ("PENGHOBI".equals(role)) {
                    user = new Penghobi(
                        id, nama, em, pass,
                        rs.getString("hobi_ikan"),
                        rs.getString("tingkat_keanggotaan"),
                        rs.getString("tanggal_daftar") != null ? rs.getString("tanggal_daftar") : ""
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
            System.err.println("[UserService] Error getProfil: " + e.getMessage());
        } finally {
            db.closeConnection();
        }
        return user;
    }

    /**
     * Memperbarui nama dan data spesifik role di database.
     * Untuk Penghobi: update hobi_ikan. Untuk Mentor: update keahlian.
     *
     * @param user objek User dengan data baru
     * @return true jika berhasil diupdate
     */
    public boolean updateProfil(User user) {
        if (user == null) return false;
        db.openConnection();
        boolean ok = false;
        try {
            String query;
            if (user instanceof Penghobi) {
                Penghobi p = (Penghobi) user;
                query = "UPDATE users SET nama='" + p.getNama() + "', email='" + p.getEmail() +
                        "', hobi_ikan='" + p.getHobiIkan() + "' WHERE id=" + p.getId();
            } else if (user instanceof Mentor) {
                Mentor m = (Mentor) user;
                query = "UPDATE users SET nama='" + m.getNama() + "', email='" + m.getEmail() +
                        "', keahlian='" + m.getKeahlian() + "' WHERE id=" + m.getId();
            } else {
                db.closeConnection();
                return false;
            }
            ok = db.executeStatement(query) > 0;
        } catch (Exception e) {
            System.err.println("[UserService] Error updateProfil: " + e.getMessage());
        } finally {
            db.closeConnection();
        }
        return ok;
    }

    /**
     * Mengambil semua user dari database.
     * @return List berisi semua User (Penghobi dan Mentor)
     */
    public List<User> getAllUsers() {
        List<User> list = new ArrayList<>();
        db.openConnection();
        try {
            ResultSet rs = db.getData("SELECT * FROM users ORDER BY id");
            while (rs != null && rs.next()) {
                String role = rs.getString("role");
                int    id   = rs.getInt("id");
                String nama = rs.getString("nama");
                String em   = rs.getString("email");
                String pass = rs.getString("password");

                if ("PENGHOBI".equals(role)) {
                    list.add(new Penghobi(id, nama, em, pass,
                        rs.getString("hobi_ikan"),
                        rs.getString("tingkat_keanggotaan"),
                        rs.getString("tanggal_daftar") != null ? rs.getString("tanggal_daftar") : ""));
                } else if ("MENTOR".equals(role)) {
                    list.add(new Mentor(id, nama, em, pass,
                        rs.getString("keahlian"),
                        rs.getBoolean("status_verifikasi"),
                        rs.getInt("total_sesi")));
                }
            }
            if (rs != null && rs.getStatement() != null) rs.getStatement().close();
        } catch (SQLException e) {
            System.err.println("[UserService] Error getAllUsers: " + e.getMessage());
        } finally {
            db.closeConnection();
        }
        return list;
    }

    // =========================================================
    // OVERRIDE MANAGEABLE
    // =========================================================

    @Override
    public boolean save(Object obj) {
        if (!(obj instanceof User)) return false;
        User u = (User) obj;
        db.openConnection();
        boolean ok = false;
        try {
            String query = "INSERT INTO users (nama, email, password, role) VALUES ('" +
                u.getNama() + "','" + u.getEmail() + "','" + u.getPassword() +
                "','" + u.getRole() + "')";
            ok = db.executeStatement(query) > 0;
        } catch (Exception e) {
            System.err.println("[UserService] Error save: " + e.getMessage());
        } finally {
            db.closeConnection();
        }
        return ok;
    }

    @Override
    public boolean delete(int id) {
        db.openConnection();
        boolean ok = false;
        try {
            ok = db.executeStatement("DELETE FROM users WHERE id=" + id) > 0;
        } catch (Exception e) {
            System.err.println("[UserService] Error delete: " + e.getMessage());
        } finally {
            db.closeConnection();
        }
        return ok;
    }

    @Override
    public Object findById(int id) {
        return getProfil(id);
    }
}
