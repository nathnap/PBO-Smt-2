package fishlens.view;

import javax.swing.*;

/**
 * FishLensApp — Entry Point Program
 * ═══════════════════════════════════════════════════════════════════════
 * Class ini adalah titik masuk (main class) yang pertama kali dijalankan
 * ketika program FishLens dieksekusi di NetBeans.
 *
 * Tidak memiliki atribut — hanya entry point.
 *
 * Cara run di NetBeans:
 *   Klik kanan project → Properties → Run → Main Class → fishlens.view.FishLensApp
 *
 * Anggota 5 — View / UI
 */
public class FishLensApp {

    /**
     * Method pertama yang dijalankan saat program dieksekusi.
     * Mengatur Look & Feel, kemudian membuat LoginFrame dan menjalankannya.
     *
     * @param args argumen command line (tidak digunakan)
     */
    public static void main(String[] args) {

        // ── Atur Look and Feel (FlatLaf jika tersedia, fallback ke Nimbus) ──
        try {
            // Coba FlatLaf (modern look) — tambahkan dependency di NetBeans jika ada
            UIManager.setLookAndFeel("com.formdev.flatlaf.FlatLightLaf");
        } catch (Exception e1) {
            try {
                // Fallback ke Nimbus
                for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                    if ("Nimbus".equals(info.getName())) {
                        UIManager.setLookAndFeel(info.getClassName());
                        break;
                    }
                }
            } catch (Exception e2) {
                // Fallback ke default sistem
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (Exception e3) {
                    e3.printStackTrace();
                }
            }
        }

        // ── Jalankan GUI di Event Dispatch Thread (EDT) — best practice Swing ──
        SwingUtilities.invokeLater(() -> {
            LoginFrame loginFrame = new LoginFrame();
            loginFrame.jalankan();
        });
    }
}
