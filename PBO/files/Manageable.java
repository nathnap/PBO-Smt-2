package fishlens.interfaces;

/**
 * Interface Manageable
 * Kontrak operasi CRUD dasar yang wajib dimiliki semua Service.
 * Diimplementasikan oleh: UserService, EnsiklopediaService,
 *                         ForumService, ArtikelService
 *
 * Anggota 3 — Interfaces + Service Auth
 */
public interface Manageable {

    /**
     * Menyimpan (INSERT) objek baru ke database.
     *
     * @param obj objek yang akan disimpan (User, Ikan, Postingan, atau Artikel)
     * @return true jika berhasil disimpan, false jika gagal
     */
    boolean save(Object obj);

    /**
     * Menghapus (DELETE) data dari database berdasarkan ID.
     *
     * @param id primary key data yang akan dihapus
     * @return true jika berhasil dihapus, false jika gagal
     */
    boolean delete(int id);

    /**
     * Mencari (SELECT) satu data spesifik dari database berdasarkan ID.
     *
     * @param id primary key data yang dicari
     * @return Object yang ditemukan, atau null jika tidak ada
     */
    Object findById(int id);
}
