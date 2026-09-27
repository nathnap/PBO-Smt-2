package fishlens.model;

/**
 * Interface Displayable
 * Kontrak bahwa semua model wajib bisa menampilkan datanya sendiri.
 * Diimplementasikan oleh: User, Ikan, Postingan, Artikel
 */
public interface Displayable {
    /**
     * Menampilkan data objek ke console / GUI
     */
    void display();
}
