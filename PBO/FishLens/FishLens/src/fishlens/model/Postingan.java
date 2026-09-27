package fishlens.model;

/**
 * Postingan — implements Displayable
 * Dipakai saat: menu Forum — lihat daftar postingan & buat postingan baru.
 */
public class Postingan implements Displayable {

    private int id;
    private String judul;
    private String isi;
    private int userId;
    private String tanggal;
    private String namaPembuat;

    public Postingan(int id, String judul, String isi, int userId, String tanggal) {
        this.id          = id;
        this.judul       = judul;
        this.isi         = isi;
        this.userId      = userId;
        this.tanggal     = tanggal;
        this.namaPembuat = "";
    }

    public Postingan(String judul, String isi, int userId) {
        this(0, judul, isi, userId, java.time.LocalDateTime.now().toString());
    }

    public int getId()           { return id; }
    public String getJudul()     { return judul; }
    public String getIsi()       { return isi; }
    public void setIsi(String s) { if (s != null && !s.trim().isEmpty()) this.isi = s.trim(); }
    public int getUserId()       { return userId; }
    public String getTanggal()   { return tanggal; }
    public String getNamaPembuat() { return namaPembuat; }
    public void setNamaPembuat(String n) { this.namaPembuat = n; }

    @Override
    public void display() {
        System.out.println("╔══════════════════════════════════╗");
        System.out.println("║       DETAIL POSTINGAN           ║");
        System.out.println("╚══════════════════════════════════╝");
        System.out.println("  ID       : " + id);
        System.out.println("  Judul    : " + judul);
        System.out.println("  Penulis  : " + (namaPembuat.isEmpty() ? "User #" + userId : namaPembuat));
        System.out.println("  Tanggal  : " + tanggal);
        System.out.println("──────────────────────────────────");
        System.out.println("  " + isi);
        System.out.println("──────────────────────────────────");
    }

    @Override
    public String toString() {
        return "Postingan{id=" + id + ", judul='" + judul + "', userId=" + userId + "}";
    }
}
