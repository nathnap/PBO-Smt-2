package fishlens.model;

/**
 * Artikel — implements Displayable
 * Dipakai saat: menu Artikel Edukasi — lihat daftar & baca artikel.
 * Hanya artikel berstatus APPROVED yang tampil ke user biasa.
 */
public class Artikel implements Displayable {

    // =========================================================
    // KONSTANTA STATUS
    // =========================================================

    public static final String STATUS_PENDING  = "PENDING";
    public static final String STATUS_APPROVED = "APPROVED";

    // =========================================================
    // ATRIBUT (private)
    // =========================================================

    /** Primary key di tabel artikel */
    private int id;

    /** Judul yang muncul di daftar artikel */
    private String judul;

    /** Isi artikel saat dibaca */
    private String isi;

    /** ID mentor penulis artikel — foreign key ke tabel users */
    private int mentorId;

    /** Status: "PENDING" (baru submit) atau "APPROVED" (sudah disetujui Admin) */
    private String status;

    // =========================================================
    // CONSTRUCTOR
    // =========================================================

    /**
     * Constructor lengkap — digunakan ArtikelService saat load dari DB
     */
    public Artikel(int id, String judul, String isi, int mentorId, String status) {
        this.id       = id;
        this.judul    = judul;
        this.isi      = isi;
        this.mentorId = mentorId;
        this.status   = status;
    }

    /**
     * Constructor untuk submit artikel baru oleh Mentor.
     * Status otomatis PENDING, id 0 (belum ada di DB).
     */
    public Artikel(String judul, String isi, int mentorId) {
        this(0, judul, isi, mentorId, STATUS_PENDING);
    }

    // =========================================================
    // GETTER & SETTER
    // =========================================================

    /** @return Primary key artikel */
    public int getId() {
        return id;
    }

    /** @return Judul artikel */
    public String getJudul() {
        return judul;
    }

    /** @return Isi lengkap artikel */
    public String getIsi() {
        return isi;
    }

    /**
     * Setter isi artikel — untuk edit konten
     * @param isi isi artikel baru
     */
    public void setIsi(String isi) {
        if (isi != null && !isi.trim().isEmpty()) {
            this.isi = isi.trim();
        }
    }

    /** @return ID mentor penulis */
    public int getMentorId() {
        return mentorId;
    }

    /** @return Status artikel: "PENDING" atau "APPROVED" */
    public String getStatus() {
        return status;
    }

    /**
     * Setter status — dipanggil Admin saat approve artikel
     * @param status status baru ("PENDING" atau "APPROVED")
     */
    public void setStatus(String status) {
        if (STATUS_PENDING.equals(status) || STATUS_APPROVED.equals(status)) {
            this.status = status;
        }
    }

    /**
     * Cek apakah artikel sudah disetujui Admin.
     * Dipakai sebagai filter di ArtikelService.getArtikelApproved().
     * @return true jika status == APPROVED
     */
    public boolean isApproved() {
        return STATUS_APPROVED.equals(this.status);
    }

    // =========================================================
    // OVERRIDE METHOD
    // =========================================================

    /**
     * Menampilkan detail artikel ke console / ArtikelFrame.
     */
    @Override
    public void display() {
        System.out.println("╔══════════════════════════════════╗");
        System.out.println("║         ARTIKEL EDUKASI          ║");
        System.out.println("╚══════════════════════════════════╝");
        System.out.println("  ID        : " + id);
        System.out.println("  Judul     : " + judul);
        System.out.println("  Mentor ID : " + mentorId);
        System.out.println("  Status    : " + status);
        System.out.println("──────────────────────────────────");
        System.out.println("  ISI ARTIKEL:");
        System.out.println("  " + isi);
        System.out.println("──────────────────────────────────");
    }

    /**
     * Ringkasan objek untuk debugging
     */
    @Override
    public String toString() {
        return "Artikel{id=" + id + ", judul='" + judul + "', mentorId=" + mentorId +
               ", status='" + status + "'}";
    }
}
