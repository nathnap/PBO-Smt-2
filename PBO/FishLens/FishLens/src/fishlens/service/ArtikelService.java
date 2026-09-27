package fishlens.service;

import fishlens.foundation.DatabaseConnection;
import fishlens.model.*;
import java.sql.*;
import java.util.*;

/**
 * ArtikelService — Service Layer (Anggota 4)
 * Implements Manageable — menangani artikel edukasi dari tabel 'artikel'.
 * Hanya artikel berstatus APPROVED yang ditampilkan ke Penghobi.
 * Mentor bisa submit (status PENDING), Admin bisa approve.
 */
public class ArtikelService implements fishlens.model.Manageable {

    /** Koneksi ke DB tabel artikel */
    private DatabaseConnection db;

    public ArtikelService() {
        this.db = new DatabaseConnection();
    }

    /**
     * Mengambil artikel yang sudah disetujui Admin.
     * SELECT WHERE status='APPROVED' — filter utama agar konten aman.
     *
     * @return List Artikel dengan status APPROVED
     */
    public List<Artikel> getArtikelApproved() {
        List<Artikel> list = new ArrayList<>();
        db.openConnection();
        try {
            String query = "SELECT a.*, u.nama AS nama_mentor FROM artikel a " +
                           "LEFT JOIN users u ON a.mentor_id = u.id " +
                           "WHERE a.status='APPROVED' ORDER BY a.created_at DESC";
            ResultSet rs = db.getData(query);
            while (rs != null && rs.next()) {
                Artikel a = new Artikel(
                    rs.getInt("id"),
                    rs.getString("judul"),
                    rs.getString("isi"),
                    rs.getInt("mentor_id"),
                    rs.getString("status")
                );
                list.add(a);
            }
            if (rs != null && rs.getStatement() != null) rs.getStatement().close();
        } catch (SQLException e) {
            System.err.println("[ArtikelService] Error getArtikelApproved: " + e.getMessage());
        } finally {
            db.closeConnection();
        }
        System.out.println("[ArtikelService] Loaded " + list.size() + " artikel APPROVED.");
        return list;
    }

    /**
     * Mengambil semua artikel (PENDING + APPROVED) — untuk Admin/Mentor.
     *
     * @return List semua Artikel
     */
    public List<Artikel> getAllArtikel() {
        List<Artikel> list = new ArrayList<>();
        db.openConnection();
        try {
            ResultSet rs = db.getData(
                "SELECT * FROM artikel ORDER BY created_at DESC");
            while (rs != null && rs.next()) {
                list.add(new Artikel(
                    rs.getInt("id"), rs.getString("judul"), rs.getString("isi"),
                    rs.getInt("mentor_id"), rs.getString("status")));
            }
            if (rs != null && rs.getStatement() != null) rs.getStatement().close();
        } catch (SQLException e) {
            System.err.println("[ArtikelService] Error getAllArtikel: " + e.getMessage());
        } finally {
            db.closeConnection();
        }
        return list;
    }

    /**
     * Mentor submit artikel baru — status otomatis PENDING.
     * Mengecek apakah mentor terverifikasi sebelum mengizinkan submit.
     *
     * @param artikel objek Artikel baru
     * @return true jika INSERT berhasil
     */
    public boolean submitArtikel(Artikel artikel) {
        if (artikel == null) return false;
        db.openConnection();
        boolean ok = false;
        try {
            String q = "INSERT INTO artikel (judul, isi, mentor_id, status) VALUES ('" +
                       artikel.getJudul().replace("'", "\\'") + "','" +
                       artikel.getIsi().replace("'", "\\'") + "'," +
                       artikel.getMentorId() + ",'PENDING')";
            ok = db.executeStatement(q) > 0;
        } catch (Exception e) {
            System.err.println("[ArtikelService] Error submitArtikel: " + e.getMessage());
        } finally {
            db.closeConnection();
        }
        if (ok) System.out.println("[ArtikelService] Artikel baru disubmit (PENDING).");
        return ok;
    }

    /**
     * Admin menyetujui artikel — UPDATE status menjadi APPROVED.
     *
     * @param id ID artikel yang akan diapprove
     * @return true jika UPDATE berhasil
     */
    public boolean approveArtikel(int id) {
        db.openConnection();
        boolean ok = false;
        try {
            ok = db.executeStatement(
                "UPDATE artikel SET status='APPROVED' WHERE id=" + id) > 0;
        } catch (Exception e) {
            System.err.println("[ArtikelService] Error approveArtikel: " + e.getMessage());
        } finally {
            db.closeConnection();
        }
        if (ok) System.out.println("[ArtikelService] Artikel #" + id + " disetujui (APPROVED).");
        return ok;
    }

    @Override
    public boolean save(Object obj) {
        if (!(obj instanceof Artikel)) return false;
        return submitArtikel((Artikel) obj);
    }

    @Override
    public boolean delete(int id) {
        db.openConnection();
        boolean ok = false;
        try {
            ok = db.executeStatement("DELETE FROM artikel WHERE id=" + id) > 0;
        } catch (Exception e) {
            System.err.println("[ArtikelService] Error delete: " + e.getMessage());
        } finally {
            db.closeConnection();
        }
        return ok;
    }

    @Override
    public Object findById(int id) {
        Artikel a = null;
        db.openConnection();
        try {
            ResultSet rs = db.getData("SELECT * FROM artikel WHERE id=" + id);
            if (rs != null && rs.next()) {
                a = new Artikel(rs.getInt("id"), rs.getString("judul"), rs.getString("isi"),
                    rs.getInt("mentor_id"), rs.getString("status"));
                if (rs.getStatement() != null) rs.getStatement().close();
            }
        } catch (SQLException e) {
            System.err.println("[ArtikelService] Error findById: " + e.getMessage());
        } finally {
            db.closeConnection();
        }
        return a;
    }
}
