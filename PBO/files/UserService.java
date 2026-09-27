package fishlens.service;

import fishlens.DatabaseConnection;
import fishlens.interfaces.Manageable;
import fishlens.model.Mentor;
import fishlens.model.Penghobi;
import fishlens.model.User;

import javax.swing.*;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * UserService
 * Menangani operasi profil user: lihat profil dan edit profil.
 * Implements Manageable → wajib punya save, delete, findById.
 *
 * Anggota 3 — Interfaces + Service Auth
 */
public class UserService implements Manageable {

    /** Koneksi ke DB untuk ambil dan update data profil */
    private DatabaseConnection db;

    // ─────────────────────────────────────────
    //  Constructor
    // ─────────────────────────────────────────

    public UserService() {
        this.db = new DatabaseConnection();
    }

    // ─────────────────────────────────────────
    //  Business Methods
    // ─────────────────────────────────────────

    /**
     * Mengambil data profil user dari database berdasarkan ID.
     * Dipanggil saat user membuka menu Lihat Profil.
     *
     * @param id ID user yang ingin dilihat profilnya
     * @return objek User (Penghobi/Mentor), atau null jika tidak ditemukan
     */
    public User getProfil(int id) {
        return (User) findById(id);
    }

    /**
     * Memperbarui data profil user di database.
     * Dipanggil setelah user menyimpan perubahan di menu Edit Profil.
     *
     * @param user objek User dengan data baru yang sudah diubah
     * @return true jika berhasil diupdate, false jika gagal
     */
    public boolean updateProfil(User user) {
        db.openConnection();
        boolean berhasil = false;
        String query;

        if (user instanceof Penghobi) {
            Penghobi p = (Penghobi) user;
            query = "UPDATE users SET nama='" + p.getNama()
                    + "', email='" + p.getEmail()
                    + "', hobi_ikan='" + p.getHobiIkan()
                    + "', tingkat_keanggotaan='" + p.getTingkatKeanggotaan()
                    + "' WHERE id=" + p.getId();

        } else if (user instanceof Mentor) {
            Mentor m = (Mentor) user;
            query = "UPDATE users SET nama='" + m.getNama()
                    + "', email='" + m.getEmail()
                    + "', keahlian='" + m.getKeahlian()
                    + "' WHERE id=" + m.getId();
        } else {
            db.closeConnection();
            return false;
        }

        try {
            int rowsAffected = db.executeStatement(query);
            berhasil = rowsAffected > 0;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,
                    "Gagal update profil: " + e.getMessage(),
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
     * INSERT user baru ke database.
     * Override dari Manageable.save().
     */
    @Override
    public boolean save(Object obj) {
        if (!(obj instanceof User)) return false;
        User user = (User) obj;

        db.openConnection();
        boolean berhasil = false;

        String query = "INSERT INTO users (nama, email, password, role) VALUES ('"
                + user.getNama()     + "', '"
                + user.getEmail()    + "', '"
                + user.getPassword() + "', '"
                + user.getRole()     + "')";

        try {
            berhasil = db.executeStatement(query) > 0;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,
                    "Gagal menyimpan user: " + e.getMessage(),
                    "Database Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            db.closeConnection();
        }

        return berhasil;
    }

    /**
     * DELETE user dari database berdasarkan ID.
     * Override dari Manageable.delete().
     */
    @Override
    public boolean delete(int id) {
        db.openConnection();
        boolean berhasil = false;

        String query = "DELETE FROM users WHERE id=" + id;

        try {
            berhasil = db.executeStatement(query) > 0;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,
                    "Gagal hapus user: " + e.getMessage(),
                    "Database Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            db.closeConnection();
        }

        return berhasil;
    }

    /**
     * SELECT user berdasarkan ID.
     * Override dari Manageable.findById().
     * Dipakai getProfil() dan dari luar service.
     */
    @Override
    public Object findById(int id) {
        db.openConnection();
        User user = null;

        String query = "SELECT * FROM users WHERE id=" + id;

        try {
            ResultSet rs = db.getData(query);
            if (rs != null && rs.next()) {
                String role = rs.getString("role");

                if ("PENGHOBI".equalsIgnoreCase(role)) {
                    user = new Penghobi(
                            rs.getInt("id"),
                            rs.getString("nama"),
                            rs.getString("email"),
                            rs.getString("password"),
                            rs.getString("hobi_ikan"),
                            rs.getString("tingkat_keanggotaan"),
                            rs.getString("tanggal_daftar")
                    );
                } else if ("MENTOR".equalsIgnoreCase(role)) {
                    user = new Mentor(
                            rs.getInt("id"),
                            rs.getString("nama"),
                            rs.getString("email"),
                            rs.getString("password"),
                            rs.getString("keahlian"),
                            rs.getBoolean("status_verifikasi"),
                            rs.getInt("total_sesi")
                    );
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,
                    "Gagal ambil user: " + e.getMessage(),
                    "Database Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            db.closeConnection();
        }

        return user;
    }
}
