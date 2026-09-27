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
 * EnsiklopediaService — Service Layer (Anggota 4)
 * Implements Manageable — menangani data ikan hias dari tabel 'ikan'.
 * Dipakai oleh MainFrame saat buka menu Ensiklopedia.
 */
public class EnsiklopediaService implements Manageable {

    /** Koneksi ke DB tabel ikan */
    private DatabaseConnection db;

    public EnsiklopediaService() {
        this.db = new DatabaseConnection();
    }

    /**
     * Mengambil semua data ikan dari database.
     * Dipakai untuk menampilkan daftar di tabel Ensiklopedia.
     *
     * @return List berisi semua objek Ikan
     */
    public List<Ikan> getAllIkan() {
        List<Ikan> list = new ArrayList<>();
        db.openConnection();
        try {
            ResultSet rs = db.getData("SELECT * FROM ikan ORDER BY nama_spesies");
            while (rs != null && rs.next()) {
                list.add(new Ikan(
                    rs.getInt("id"),
                    rs.getString("nama_spesies"),
                    rs.getString("habitat"),
                    rs.getString("pakan"),
                    rs.getString("penyakit_umum")
                ));
            }
            if (rs != null && rs.getStatement() != null) rs.getStatement().close();
        } catch (SQLException e) {
            System.err.println("[EnsiklopediaService] Error getAllIkan: " + e.getMessage());
        } finally {
            db.closeConnection();
        }
        System.out.println("[EnsiklopediaService] Loaded " + list.size() + " ikan.");
        return list;
    }

    /**
     * Mencari ikan berdasarkan keyword nama spesies.
     * Menggunakan SQL LIKE untuk partial match.
     *
     * @param keyword kata kunci pencarian
     * @return List ikan yang cocok
     */
    public List<Ikan> cariIkan(String keyword) {
        List<Ikan> list = new ArrayList<>();
        if (keyword == null || keyword.trim().isEmpty()) return getAllIkan();

        db.openConnection();
        try {
            String query = "SELECT * FROM ikan WHERE nama_spesies LIKE '%" +
                           keyword.trim() + "%' OR habitat LIKE '%" + keyword.trim() + "%'";
            ResultSet rs = db.getData(query);
            while (rs != null && rs.next()) {
                list.add(new Ikan(
                    rs.getInt("id"),
                    rs.getString("nama_spesies"),
                    rs.getString("habitat"),
                    rs.getString("pakan"),
                    rs.getString("penyakit_umum")
                ));
            }
            if (rs != null && rs.getStatement() != null) rs.getStatement().close();
        } catch (SQLException e) {
            System.err.println("[EnsiklopediaService] Error cariIkan: " + e.getMessage());
        } finally {
            db.closeConnection();
        }
        System.out.println("[EnsiklopediaService] cariIkan('" + keyword + "') → " + list.size() + " hasil.");
        return list;
    }

    @Override
    public boolean save(Object obj) {
        if (!(obj instanceof Ikan)) return false;
        Ikan ikan = (Ikan) obj;
        db.openConnection();
        boolean ok = false;
        try {
            String q = "INSERT INTO ikan (nama_spesies, habitat, pakan, penyakit_umum) VALUES ('" +
                       ikan.getNamaSpesies() + "','" + ikan.getHabitat() + "','" +
                       ikan.getPakan() + "','" + ikan.getPenyakitUmum() + "')";
            ok = db.executeStatement(q) > 0;
        } catch (Exception e) {
            System.err.println("[EnsiklopediaService] Error save: " + e.getMessage());
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
            ok = db.executeStatement("DELETE FROM ikan WHERE id=" + id) > 0;
        } catch (Exception e) {
            System.err.println("[EnsiklopediaService] Error delete: " + e.getMessage());
        } finally {
            db.closeConnection();
        }
        return ok;
    }

    @Override
    public Object findById(int id) {
        Ikan ikan = null;
        db.openConnection();
        try {
            ResultSet rs = db.getData("SELECT * FROM ikan WHERE id=" + id);
            if (rs != null && rs.next()) {
                ikan = new Ikan(id,
                    rs.getString("nama_spesies"),
                    rs.getString("habitat"),
                    rs.getString("pakan"),
                    rs.getString("penyakit_umum"));
                if (rs.getStatement() != null) rs.getStatement().close();
            }
        } catch (SQLException e) {
            System.err.println("[EnsiklopediaService] Error findById: " + e.getMessage());
        } finally {
            db.closeConnection();
        }
        return ikan;
    }
}
