package fishlens.service;

import fishlens.foundation.DatabaseConnection;
import fishlens.model.*;
import java.sql.*;
import java.util.*;

/**
 * ForumService — Service Layer (Anggota 4)
 * Implements Manageable — menangani postingan forum dari tabel 'postingan'.
 * Dipakai oleh MainFrame saat buka menu Forum.
 */
public class ForumService implements fishlens.model.Manageable {

    /** Koneksi ke DB tabel postingan */
    private DatabaseConnection db;

    public ForumService() {
        this.db = new DatabaseConnection();
    }

    /**
     * Mengambil semua postingan forum, diurutkan terbaru di atas.
     * Juga JOIN ke tabel users untuk mendapatkan nama pembuat.
     *
     * @return List semua Postingan
     */
    public List<Postingan> getAllPostingan() {
        List<Postingan> list = new ArrayList<>();
        db.openConnection();
        try {
            String query = "SELECT p.*, u.nama AS nama_pembuat FROM postingan p " +
                           "LEFT JOIN users u ON p.user_id = u.id " +
                           "ORDER BY p.tanggal DESC";
            ResultSet rs = db.getData(query);
            while (rs != null && rs.next()) {
                Postingan post = new Postingan(
                    rs.getInt("id"),
                    rs.getString("judul"),
                    rs.getString("isi"),
                    rs.getInt("user_id"),
                    rs.getString("tanggal") != null ? rs.getString("tanggal") : ""
                );
                post.setNamaPembuat(rs.getString("nama_pembuat") != null
                    ? rs.getString("nama_pembuat") : "Unknown");
                list.add(post);
            }
            if (rs != null && rs.getStatement() != null) rs.getStatement().close();
        } catch (SQLException e) {
            System.err.println("[ForumService] Error getAllPostingan: " + e.getMessage());
        } finally {
            db.closeConnection();
        }
        System.out.println("[ForumService] Loaded " + list.size() + " postingan.");
        return list;
    }

    /**
     * Membuat postingan baru di forum.
     * Dipanggil saat user submit postingan dari ForumFrame.
     *
     * @param postingan objek Postingan baru
     * @return true jika INSERT berhasil
     */
    public boolean buatPostingan(Postingan postingan) {
        if (postingan == null) return false;
        db.openConnection();
        boolean ok = false;
        try {
            String q = "INSERT INTO postingan (judul, isi, user_id) VALUES ('" +
                       postingan.getJudul().replace("'", "\\'") + "','" +
                       postingan.getIsi().replace("'", "\\'") + "'," +
                       postingan.getUserId() + ")";
            ok = db.executeStatement(q) > 0;
        } catch (Exception e) {
            System.err.println("[ForumService] Error buatPostingan: " + e.getMessage());
        } finally {
            db.closeConnection();
        }
        if (ok) System.out.println("[ForumService] Postingan baru berhasil dibuat.");
        return ok;
    }

    @Override
    public boolean save(Object obj) {
        if (!(obj instanceof Postingan)) return false;
        return buatPostingan((Postingan) obj);
    }

    @Override
    public boolean delete(int id) {
        db.openConnection();
        boolean ok = false;
        try {
            ok = db.executeStatement("DELETE FROM postingan WHERE id=" + id) > 0;
        } catch (Exception e) {
            System.err.println("[ForumService] Error delete: " + e.getMessage());
        } finally {
            db.closeConnection();
        }
        return ok;
    }

    @Override
    public Object findById(int id) {
        Postingan post = null;
        db.openConnection();
        try {
            ResultSet rs = db.getData(
                "SELECT p.*, u.nama AS nama_pembuat FROM postingan p " +
                "LEFT JOIN users u ON p.user_id = u.id WHERE p.id=" + id);
            if (rs != null && rs.next()) {
                post = new Postingan(
                    rs.getInt("id"), rs.getString("judul"), rs.getString("isi"),
                    rs.getInt("user_id"), rs.getString("tanggal") != null ? rs.getString("tanggal") : "");
                post.setNamaPembuat(rs.getString("nama_pembuat") != null ? rs.getString("nama_pembuat") : "");
                if (rs.getStatement() != null) rs.getStatement().close();
            }
        } catch (SQLException e) {
            System.err.println("[ForumService] Error findById: " + e.getMessage());
        } finally {
            db.closeConnection();
        }
        return post;
    }
}
