package fishlens.interfaces;

/**
 * Interface Displayable
 * Kontrak bahwa semua model wajib bisa menampilkan datanya sendiri ke console.
 * Diimplementasikan oleh: User (abstract), Ikan, Postingan, Artikel
 *
 * Anggota 3 — Interfaces + Service Auth
 */
public interface Displayable {

    /**
     * Menampilkan detail data objek ke console.
     * Setiap class yang implements wajib override method ini
     * dengan format tampilan yang sesuai datanya masing-masing.
     */
    void display();
}
