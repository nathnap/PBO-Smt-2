package fishlens.view;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 * FishLensApp — Entry Point (Anggota 5)
 *
 * Method sesuai class diagram:
 *   + main(String[] args) : void
 *
 * Atribut sesuai class diagram: tidak ada (hanya entry point).
 *
 * Cara Run di NetBeans:
 *   1. Klik kanan FishLensApp.java → Set as Main Class
 *   2. Tambahkan mysql-connector-j-x.x.x.jar ke Libraries
 *   3. Jalankan fishlens_setup.sql di MySQL terlebih dahulu
 *   4. Run (F6)
 */
public class FishLensApp {

    /**
     * Method pertama yang jalan saat program dieksekusi.
     * Buat LoginFrame dan jalankan.
     */
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {}

        SwingUtilities.invokeLater(() -> {
            LoginFrame loginFrame = new LoginFrame();
            loginFrame.jalankan();
        });
    }
}
