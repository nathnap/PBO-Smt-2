package fishlens.service;

import fishlens.DatabaseConnection;
import fishlens.interfaces.Manageable;
import fishlens.model.Artikel;

import javax.swing.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * ArtikelService
 * Menangani data artikel edukasi: lihat daftar artikel approved,
 * submit artikel baru (oleh Mentor), approve artikel (oleh Admin).
 * Implements Manageable → wajib punya save, delete, findById.
 *
 * Anggota 4 — Service Konten
 */
public class ArtikelService implements Manageable {

    /** Koneksi ke DB tabel artikel */
    private DatabaseConnection db;

    // ─────────────────────────────────────────
    //  Constructor
    // ─────────────────────────────────────────

    public ArtikelService() {
        this.db = new DatabaseConnection();
    }

    // ─────────────────────────────────────────
    //  Business Methods
    // ─────────────────────────────────────────

    /**
     * Mengambil semua artikel yang sudah di-approve.
     * Hanya artikel berstatus 'APPROVED' yang ditampilkan ke user.
     * Dipanggil saat user membuka menu Artikel Edukasi.
     *
     * @return List berisi objek Artikel dengan status APPROVED
     */
    public List<Artikel> getArtikelApproved() {
        db.openConnection();
        List<Artikel> daftarArtikel = new ArrayList<>();

        String query = "SELECT * FROM artikel WHERE status='APPROVED' ORDER BY id DESC";

        try {
            ResultSet rs = db.getData(query);
            while (rs != null && rs.next()) {
                Artikel a = new Artikel(
                        rs.getInt("id"),
                        rs.getString("judul"),
                        rs.getString("isi"),
                        rs.getInt("mentor_id"),
                        rs.getString("status")
                );
                daftarArtikel.add(a);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,
                    "Gagal ambil artikel: " + e.getMessage(),
                    "Database Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            db.closeConnection();
        }

        return daftarArtikel;
    }

    /**
     * Mentor submit artikel baru.
     * Artikel baru otomatis masuk dengan status PENDING,
     * menunggu persetujuan Admin sebelum bisa dilihat user lain.
     *
     * @param artikel objek Artikel baru yang ditulis Mentor
     * @return true jika berhasil disubmit, false jika gagal
     */
    public boolean submitArtikel(Artikel artikel) {
        artikel.setStatus("PENDING");
        return save(artikel);
    }

    /**
     * Admin menyetujui artikel dari Mentor.
     * UPDATE status artikel dari PENDING menjadi APPROVED.
     * Setelah di-approve, artikel langsung tampil di daftar.
     *
     * @param id ID artikel yang akan di-approve
     * @return true jika berhasil di-approve, false jika gagal
     */
    public boolean approveArtikel(int id) {
        db.openConnection();
        boolean berhasil = false;

        String query = "UPDATE artikel SET status='APPROVED' WHERE id=" + id;

        try {
            berhasil = db.executeStatement(query) > 0;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,
                    "Gagal approve artikel: " + e.getMessage(),
                    "Database Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            db.closeConnection();
        }

        return berhasil;
    }

    // ─────────────────────────────────────────
    //  Override Manageable
    // ─────────────────────────────────────────

    /**
     * INSERT artikel baru ke database.
     * Override dari Manageable.save() — alias dari submitArtikel internal.
     */
    @Override
    public boolean save(Object obj) {
        if (!(obj instanceof Artikel)) return false;
        Artikel a = (Artikel) obj;

        db.openConnection();
        boolean berhasil = false;

        String query = "INSERT INTO artikel (judul, isi, mentor_id, status) VALUES ('"
                + a.getJudul()    + "', '"
                + a.getIsi()      + "', "
                + a.getMentorId() + ", '"
                + a.getStatus()   + "')";

        try {
            berhasil = db.executeStatement(query) > 0;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,
                    "Gagal simpan artikel: " + e.getMessage(),
                    "Database Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            db.closeConnection();
        }

        return berhasil;
    }

    /**
     * DELETE artikel dari database berdasarkan ID.
     * Override dari Manageable.delete().
     */
    @Override
    public boolean delete(int id) {
        db.openConnection();
        boolean berhasil = false;

        String query = "DELETE FROM artikel WHERE id=" + id;

        try {
            berhasil = db.executeStatement(query) > 0;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,
                    "Gagal hapus artikel: " + e.getMessage(),
                    "Database Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            db.closeConnection();
        }

        return berhasil;
    }

    /**
     * SELECT satu artikel berdasarkan ID.
     * Override dari Manageable.findById().
     */
    @Override
    public Object findById(int id) {
        db.openConnection();
        Artikel artikel = null;

        String query = "SELECT * FROM artikel WHERE id=" + id;

        try {
            ResultSet rs = db.getData(query);
            if (rs != null && rs.next()) {
                artikel = new Artikel(
                        rs.getInt("id"),
                        rs.getString("judul"),
                        rs.getString("isi"),
                        rs.getInt("mentor_id"),
                        rs.getString("status")
                );
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,
                    "Gagal cari artikel by ID: " + e.getMessage(),
                    "Database Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            db.closeConnection();
        }

        return artikel;
    }
}
