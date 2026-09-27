package fishlens.service;

import fishlens.DatabaseConnection;
import fishlens.interfaces.Manageable;
import fishlens.model.Postingan;

import javax.swing.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * ForumService
 * Menangani data forum: tampilkan semua postingan dan buat postingan baru.
 * Implements Manageable → wajib punya save, delete, findById.
 *
 * Anggota 4 — Service Konten
 */
public class ForumService implements Manageable {

    /** Koneksi ke DB tabel postingan */
    private DatabaseConnection db;

    // ─────────────────────────────────────────
    //  Constructor
    // ─────────────────────────────────────────

    public ForumService() {
        this.db = new DatabaseConnection();
    }

    // ─────────────────────────────────────────
    //  Business Methods
    // ─────────────────────────────────────────

    /**
     * Mengambil semua postingan dari database.
     * Dipanggil saat user membuka menu Forum.
     *
     * @return List berisi semua objek Postingan
     */
    public List<Postingan> getAllPostingan() {
        db.openConnection();
        List<Postingan> daftarPostingan = new ArrayList<>();

        String query = "SELECT * FROM postingan ORDER BY tanggal DESC";

        try {
            ResultSet rs = db.getData(query);
            while (rs != null && rs.next()) {
                Postingan p = new Postingan(
                        rs.getInt("id"),
                        rs.getString("judul"),
                        rs.getString("isi"),
                        rs.getInt("user_id"),
                        rs.getString("tanggal")
                );
                daftarPostingan.add(p);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,
                    "Gagal ambil postingan: " + e.getMessage(),
                    "Database Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            db.closeConnection();
        }

        return daftarPostingan;
    }

    /**
     * Membuat postingan baru di forum.
     * Dipanggil saat user submit form postingan baru.
     *
     * @param postingan objek Postingan yang sudah diisi judul, isi, userId
     * @return true jika berhasil dibuat, false jika gagal
     */
    public boolean buatPostingan(Postingan postingan) {
        return save(postingan);
    }

    // ─────────────────────────────────────────
    //  Override Manageable
    // ─────────────────────────────────────────

    /**
     * INSERT postingan baru ke database.
     * Override dari Manageable.save() — ini adalah alias dari buatPostingan().
     */
    @Override
    public boolean save(Object obj) {
        if (!(obj instanceof Postingan)) return false;
        Postingan p = (Postingan) obj;

        db.openConnection();
        boolean berhasil = false;

        String query = "INSERT INTO postingan (judul, isi, user_id, tanggal) VALUES ('"
                + p.getJudul()  + "', '"
                + p.getIsi()    + "', "
                + p.getUserId() + ", NOW())";

        try {
            berhasil = db.executeStatement(query) > 0;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,
                    "Gagal buat postingan: " + e.getMessage(),
                    "Database Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            db.closeConnection();
        }

        return berhasil;
    }

    /**
     * DELETE postingan dari database berdasarkan ID.
     * Override dari Manageable.delete().
     */
    @Override
    public boolean delete(int id) {
        db.openConnection();
        boolean berhasil = false;

        String query = "DELETE FROM postingan WHERE id=" + id;

        try {
            berhasil = db.executeStatement(query) > 0;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,
                    "Gagal hapus postingan: " + e.getMessage(),
                    "Database Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            db.closeConnection();
        }

        return berhasil;
    }

    /**
     * SELECT satu postingan berdasarkan ID.
     * Override dari Manageable.findById().
     */
    @Override
    public Object findById(int id) {
        db.openConnection();
        Postingan postingan = null;

        String query = "SELECT * FROM postingan WHERE id=" + id;

        try {
            ResultSet rs = db.getData(query);
            if (rs != null && rs.next()) {
                postingan = new Postingan(
                        rs.getInt("id"),
                        rs.getString("judul"),
                        rs.getString("isi"),
                        rs.getInt("user_id"),
                        rs.getString("tanggal")
                );
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,
                    "Gagal cari postingan by ID: " + e.getMessage(),
                    "Database Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            db.closeConnection();
        }

        return postingan;
    }
}
