package fishlens.service;

import fishlens.DatabaseConnection;
import fishlens.model.Mentor;
import fishlens.model.Penghobi;
import fishlens.model.User;

import javax.swing.*;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * LoginService
 * Bertanggung jawab atas autentikasi: login, register, logout.
 * Tidak implements Manageable karena hanya menangani sesi user, bukan CRUD penuh.
 *
 * Anggota 3 — Interfaces + Service Auth
 */
public class LoginService {

    /** Koneksi ke DB untuk cek email+password */
    private DatabaseConnection db;

    /** Menyimpan objek user yang sedang aktif login */
    private User currentUser;

    // ─────────────────────────────────────────
    //  Constructor
    // ─────────────────────────────────────────

    public LoginService() {
        this.db = new DatabaseConnection();
        this.currentUser = null;
    }

    // ─────────────────────────────────────────
    //  Public Methods
    // ─────────────────────────────────────────

    /**
     * Melakukan login user ke sistem.
     * Query ke DB, cocokkan email+password, buat objek Penghobi/Mentor sesuai role.
     *
     * @param email    email yang dimasukkan user
     * @param password password yang dimasukkan user
     * @return objek User (Penghobi atau Mentor) jika berhasil, null jika gagal
     */
    public User login(String email, String password) {
        db.openConnection();

        String query = "SELECT * FROM users WHERE email = '" + email
                + "' AND password = '" + password + "'";

        try {
            ResultSet rs = db.getData(query);
            if (rs != null && rs.next()) {
                String role = rs.getString("role");

                if ("PENGHOBI".equalsIgnoreCase(role)) {
                    Penghobi p = new Penghobi(
                            rs.getInt("id"),
                            rs.getString("nama"),
                            rs.getString("email"),
                            rs.getString("password"),
                            rs.getString("hobi_ikan"),
                            rs.getString("tingkat_keanggotaan"),
                            rs.getString("tanggal_daftar")
                    );
                    currentUser = p;

                } else if ("MENTOR".equalsIgnoreCase(role)) {
                    Mentor m = new Mentor(
                            rs.getInt("id"),
                            rs.getString("nama"),
                            rs.getString("email"),
                            rs.getString("password"),
                            rs.getString("keahlian"),
                            rs.getBoolean("status_verifikasi"),
                            rs.getInt("total_sesi")
                    );
                    currentUser = m;
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,
                    "Error saat login: " + e.getMessage(),
                    "Database Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            db.closeConnection();
        }

        return currentUser;
    }

    /**
     * Mendaftarkan user baru ke sistem (INSERT ke tabel users).
     *
     * @param user objek User baru yang sudah diisi datanya
     * @return true jika berhasil didaftarkan, false jika gagal
     */
    public boolean register(User user) {
        db.openConnection();
        boolean berhasil = false;

        String query;
        if (user instanceof Penghobi) {
            Penghobi p = (Penghobi) user;
            query = "INSERT INTO users (nama, email, password, role, hobi_ikan, tingkat_keanggotaan, tanggal_daftar) "
                    + "VALUES ('"  + p.getNama()              + "', '"
                    + p.getEmail()             + "', '"
                    + p.getPassword()          + "', 'PENGHOBI', '"
                    + p.getHobiIkan()          + "', '"
                    + p.getTingkatKeanggotaan()+ "', NOW())";

        } else if (user instanceof Mentor) {
            Mentor m = (Mentor) user;
            query = "INSERT INTO users (nama, email, password, role, keahlian, status_verifikasi, total_sesi) "
                    + "VALUES ('" + m.getNama()    + "', '"
                    + m.getEmail()   + "', '"
                    + m.getPassword()+ "', 'MENTOR', '"
                    + m.getKeahlian()+ "', false, 0)";
        } else {
            db.closeConnection();
            return false;
        }

        try {
            int rowsAffected = db.executeStatement(query);
            berhasil = rowsAffected > 0;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,
                    "Gagal register: " + e.getMessage(),
                    "Database Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            db.closeConnection();
        }

        return berhasil;
    }

    /**
     * Melakukan logout user dari sistem.
     * Set currentUser = null sehingga sesi aktif berakhir.
     */
    public void logout() {
        this.currentUser = null;
        System.out.println("Anda telah logout. Sampai jumpa!");
    }

    /**
     * Mengambil user yang sedang aktif login.
     * Dipakai MainFrame untuk menentukan menu yang ditampilkan.
     *
     * @return currentUser yang sedang login, atau null jika belum login
     */
    public User getCurrentUser() {
        return currentUser;
    }
}
