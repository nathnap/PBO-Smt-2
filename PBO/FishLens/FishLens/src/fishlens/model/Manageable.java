package fishlens.model;

/**
 * Interface Manageable
 * Kontrak CRUD dasar untuk semua Service layer.
 * Diimplementasikan oleh: UserService, EnsiklopediaService, ForumService, ArtikelService
 */
public interface Manageable {
    /**
     * Menyimpan objek ke database
     * @param obj objek yang akan disimpan
     * @return true jika berhasil
     */
    boolean save(Object obj);

    /**
     * Menghapus data dari database berdasarkan id
     * @param id primary key data yang akan dihapus
     * @return true jika berhasil
     */
    boolean delete(int id);

    /**
     * Mencari satu data spesifik dari database
     * @param id primary key data yang dicari
     * @return objek yang ditemukan, atau null jika tidak ada
     */
    Object findById(int id);
}
