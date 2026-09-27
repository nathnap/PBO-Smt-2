package fishlens.model;

/**
 * Mentor — extends User
 * Dipakai saat: login sebagai MENTOR, nama mentor tampil di daftar artikel,
 * hanya mentor terverifikasi yang bisa publish artikel.
 */
public class Mentor extends User {

    // =========================================================
    // ATRIBUT KHUSUS MENTOR (private)
    // =========================================================

    /** Bidang keahlian mentor, contoh: "Ikan Koi", "Akuakultur", "Penyakit Ikan" */
    private String keahlian;

    /** Status verifikasi: hanya mentor terverifikasi yang bisa publish artikel */
    private boolean statusVerifikasi;

    /** Jumlah total sesi aktif mentor */
    private int totalSesi;

    // =========================================================
    // CONSTRUCTOR
    // =========================================================

    /**
     * Constructor lengkap — digunakan LoginService saat login berhasil
     */
    public Mentor(int id, String nama, String email, String password,
                  String keahlian, boolean statusVerifikasi) {
        super(id, nama, email, password, "MENTOR");
        this.keahlian          = keahlian;
        this.statusVerifikasi  = statusVerifikasi;
        this.totalSesi         = 0;
    }

    /**
     * Constructor lengkap dengan totalSesi — digunakan saat load dari DB
     */
    public Mentor(int id, String nama, String email, String password,
                  String keahlian, boolean statusVerifikasi, int totalSesi) {
        super(id, nama, email, password, "MENTOR");
        this.keahlian         = keahlian;
        this.statusVerifikasi = statusVerifikasi;
        this.totalSesi        = totalSesi;
    }

    /**
     * Constructor untuk registrasi mentor baru — status default belum terverifikasi
     */
    public Mentor(String nama, String email, String password, String keahlian) {
        super(0, nama, email, password, "MENTOR");
        this.keahlian         = keahlian;
        this.statusVerifikasi = false;
        this.totalSesi        = 0;
    }

    // =========================================================
    // GETTER & SETTER
    // =========================================================

    /** @return Bidang keahlian mentor */
    public String getKeahlian() {
        return keahlian;
    }

    /**
     * Cek apakah mentor sudah terverifikasi.
     * Dipanggil oleh ArtikelService sebelum izinkan publish.
     * @return true jika terverifikasi
     */
    public boolean isVerified() {
        return statusVerifikasi;
    }

    /**
     * Setter status verifikasi — dipakai Admin untuk verifikasi mentor
     * @param status true = terverifikasi
     */
    public void setStatusVerifikasi(boolean status) {
        this.statusVerifikasi = status;
    }

    /** @return Total sesi aktif mentor */
    public int getTotalSesi() {
        return totalSesi;
    }

    /**
     * Increment totalSesi setiap kali mentor aktif (publish artikel, dll).
     */
    public void tambahSesi() {
        this.totalSesi++;
    }

    // =========================================================
    // OVERRIDE METHOD
    // =========================================================

    /**
     * Menampilkan profil lengkap versi Mentor ke console.
     */
    @Override
    public void display() {
        System.out.println("╔══════════════════════════════════╗");
        System.out.println("║           PROFIL MENTOR          ║");
        System.out.println("╚══════════════════════════════════╝");
        System.out.println("  ID               : " + id);
        System.out.println("  Nama             : " + nama);
        System.out.println("  Email            : " + email);
        System.out.println("  Role             : " + role);
        System.out.println("  Keahlian         : " + keahlian);
        System.out.println("  Status Verifikasi: " + (statusVerifikasi ? "TERVERIFIKASI ✓" : "BELUM TERVERIFIKASI"));
        System.out.println("  Total Sesi       : " + totalSesi);
        System.out.println("──────────────────────────────────");
    }

    /**
     * Ringkasan objek untuk debugging
     */
    @Override
    public String toString() {
        return "Mentor{id=" + id + ", nama='" + nama + "', keahlian='" + keahlian +
               "', verified=" + statusVerifikasi + ", sesi=" + totalSesi + "}";
    }
}
