package fishlens.service;

import fishlens.DatabaseConnection;
import fishlens.interfaces.Manageable;
import fishlens.model.Ikan;

import javax.swing.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * EnsiklopediaService
 * Menangani data ikan: tampilkan semua ikan, cari ikan berdasarkan keyword.
 * Implements Manageable → wajib punya save, delete, findById.
 *
 * Anggota 4 — Service Konten
 */
public class EnsiklopediaService implements Manageable {

    /** Koneksi ke DB tabel ikan */
    private DatabaseConnection db;

    // ─────────────────────────────────────────
    //  Constructor
    // ─────────────────────────────────────────

    public EnsiklopediaService() {
        this.db = new DatabaseConnection();
    }

    // ─────────────────────────────────────────
    //  Business Methods
    // ─────────────────────────────────────────

    /**
     * Mengambil semua data ikan dari database.
     * Dipanggil saat user membuka menu Ensiklopedia.
     *
     * @return List berisi semua objek Ikan
     */
    public List<Ikan> getAllIkan() {
        db.openConnection();
        List<Ikan> daftarIkan = new ArrayList<>();

        String query = "SELECT * FROM ikan ORDER BY nama_spesies ASC";

        try {
            ResultSet rs = db.getData(query);
            while (rs != null && rs.next()) {
                Ikan ikan = new Ikan(
                        rs.getInt("id"),
                        rs.getString("nama_spesies"),
                        rs.getString("habitat"),
                        rs.getString("pakan"),
                        rs.getString("penyakit_umum")
                );
                daftarIkan.add(ikan);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,
                    "Gagal ambil data ikan: " + e.getMessage(),
                    "Database Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            db.closeConnection();
        }

        return daftarIkan;
    }

    /**
     * Mencari ikan berdasarkan kata kunci nama spesies.
     * Menggunakan query LIKE untuk pencarian parsial.
     * Dipanggil saat user mengetikkan keyword di menu Cari Ikan.
     *
     * @param keyword kata kunci yang ingin dicari (bisa sebagian nama)
     * @return List berisi Ikan yang nama spesiesnya cocok dengan keyword
     */
    public List<Ikan> cariIkan(String keyword) {
        db.openConnection();
        List<Ikan> hasil = new ArrayList<>();

        String query = "SELECT * FROM ikan WHERE nama_spesies LIKE '%" + keyword + "%'";

        try {
            ResultSet rs = db.getData(query);
            while (rs != null && rs.next()) {
                Ikan ikan = new Ikan(
                        rs.getInt("id"),
                        rs.getString("nama_spesies"),
                        rs.getString("habitat"),
                        rs.getString("pakan"),
                        rs.getString("penyakit_umum")
                );
                hasil.add(ikan);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,
                    "Gagal cari ikan: " + e.getMessage(),
                    "Database Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            db.closeConnection();
        }

        return hasil;
    }

    // ─────────────────────────────────────────
    //  Override Manageable
    // ─────────────────────────────────────────

    /**
     * INSERT ikan baru ke database.
     * Override dari Manageable.save().
     */
    @Override
    public boolean save(Object obj) {
        if (!(obj instanceof Ikan)) return false;
        Ikan ikan = (Ikan) obj;

        db.openConnection();
        boolean berhasil = false;

        String query = "INSERT INTO ikan (nama_spesies, habitat, pakan, penyakit_umum) VALUES ('"
                + ikan.getNamaSpesies()  + "', '"
                + ikan.getHabitat()      + "', '"
                + ikan.getPakan()        + "', '"
                + ikan.getPenyakitUmum() + "')";

        try {
            berhasil = db.executeStatement(query) > 0;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,
                    "Gagal tambah ikan: " + e.getMessage(),
                    "Database Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            db.closeConnection();
        }

        return berhasil;
    }

    /**
     * DELETE ikan dari database berdasarkan ID.
     * Override dari Manageable.delete().
     */
    @Override
    public boolean delete(int id) {
        db.openConnection();
        boolean berhasil = false;

        String query = "DELETE FROM ikan WHERE id=" + id;

        try {
            berhasil = db.executeStatement(query) > 0;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,
                    "Gagal hapus ikan: " + e.getMessage(),
                    "Database Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            db.closeConnection();
        }

        return berhasil;
    }

    /**
     * SELECT satu ikan berdasarkan ID.
     * Override dari Manageable.findById().
     */
    @Override
    public Object findById(int id) {
        db.openConnection();
        Ikan ikan = null;

        String query = "SELECT * FROM ikan WHERE id=" + id;

        try {
            ResultSet rs = db.getData(query);
            if (rs != null && rs.next()) {
                ikan = new Ikan(
                        rs.getInt("id"),
                        rs.getString("nama_spesies"),
                        rs.getString("habitat"),
                        rs.getString("pakan"),
                        rs.getString("penyakit_umum")
                );
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,
                    "Gagal cari ikan by ID: " + e.getMessage(),
                    "Database Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            db.closeConnection();
        }

        return ikan;
    }
}
