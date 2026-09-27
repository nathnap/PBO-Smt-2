package fishlens.view;

import fishlens.model.User;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Scanner;

/**
 * MainFrame — View Layer (Anggota 5)
 *
 * Atribut sesuai class diagram:
 *   - currentUser : User
 *   - scanner     : Scanner
 *
 * Method sesuai class diagram:
 *   + tampilkanMenu() : void
 *   + jalankan() : void
 *   - bukaEnsiklopedia() : void
 *   - bukaForum() : void
 *   - bukaArtikel() : void
 *   - bukaProfil() : void
 */
public class MainFrame extends JFrame {

    // =========================================================
    // ATRIBUT (sesuai class diagram)
    // =========================================================

    /** User yang sedang login — menentukan menu yang ditampilkan */
    private User currentUser;

    /** Membaca pilihan menu dari console (dipakai versi CLI; tidak aktif di versi GUI) */
    private Scanner scanner;

    // ---- Komponen GUI ----
    private JPanel    panelKonten;
    private CardLayout cardLayout;
    private JButton[]  tombolMenu;

    private static final String CARD_BERANDA = "BERANDA";

    // =========================================================
    // CONSTRUCTOR
    // =========================================================

    public MainFrame(User currentUser) {
        super("Dashboard - FishLens");
        this.currentUser = currentUser;
        this.scanner      = new Scanner(System.in);
        buildUI();
    }

    // =========================================================
    // BUILD UI — sesuai referensi gambar (sidebar + konten)
    // =========================================================

    private void buildUI() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1010, 510);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(new BorderLayout());

        add(buildSidebar(), BorderLayout.WEST);

        cardLayout  = new CardLayout();
        panelKonten = new JPanel(cardLayout);
        panelKonten.add(buildPanelBeranda(), CARD_BERANDA);
        add(panelKonten, BorderLayout.CENTER);
    }

    private JPanel buildSidebar() {
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBorder(BorderFactory.createEmptyBorder(20, 15, 20, 15));
        sidebar.setPreferredSize(new Dimension(180, 0));

        JLabel lblMenu = new JLabel("Menu");
        lblMenu.setFont(new Font("Tahoma", Font.BOLD, 16));
        lblMenu.setAlignmentX(Component.LEFT_ALIGNMENT);
        sidebar.add(lblMenu);
        sidebar.add(Box.createVerticalStrut(15));

        String[] labelMenu = {"Beranda", "Ensiklopedia", "Forum", "Artikel", "Profil", "Pengaturan", "Logout"};
        tombolMenu = new JButton[labelMenu.length];

        for (int i = 0; i < labelMenu.length; i++) {
            JButton btn = new JButton(labelMenu[i]);
            btn.setAlignmentX(Component.LEFT_ALIGNMENT);
            btn.setMaximumSize(new Dimension(150, 36));
            btn.setFocusPainted(false);
            tombolMenu[i] = btn;
            sidebar.add(btn);
            sidebar.add(Box.createVerticalStrut(8));
        }

        // Tombol Beranda aktif (tersorot biru) sesuai gambar referensi
        tombolMenu[0].setBackground(new Color(0x4A90D9));
        tombolMenu[0].setForeground(Color.WHITE);
        tombolMenu[0].setOpaque(true);

        tombolMenu[0].addActionListener(e -> tampilkanMenu());
        tombolMenu[1].addActionListener(e -> bukaEnsiklopedia());
        tombolMenu[2].addActionListener(e -> bukaForum());
        tombolMenu[3].addActionListener(e -> bukaArtikel());
        tombolMenu[4].addActionListener(e -> bukaProfil());
        tombolMenu[6].addActionListener(e -> {
            int ok = JOptionPane.showConfirmDialog(this,
                "Yakin ingin logout?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
            if (ok == JOptionPane.YES_OPTION) {
                this.dispose();
                LoginFrame loginFrame = new LoginFrame();
                loginFrame.jalankan();
            }
        });

        sidebar.add(Box.createVerticalGlue());
        return sidebar;
    }

    // =========================================================
    // PANEL BERANDA — sesuai referensi gambar
    // =========================================================

    private JPanel buildPanelBeranda() {
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel lblJudul = new JLabel("Beranda");
        lblJudul.setFont(new Font("Tahoma", Font.BOLD, 18));
        lblJudul.setBounds(20, 10, 200, 30);
        panel.add(lblJudul);

        JLabel lblRingkasan = new JLabel("Ringkasan");
        lblRingkasan.setFont(new Font("Tahoma", Font.BOLD, 13));
        lblRingkasan.setBounds(20, 50, 200, 25);
        panel.add(lblRingkasan);

        panel.add(buatKartuRingkasan("12", "Ikan Favorit", 20));
        panel.add(buatKartuRingkasan("8",  "Postingan",    230));
        panel.add(buatKartuRingkasan("5",  "Artikel Disimpan", 440));
        panel.add(buatKartuRingkasan("3",  "Mentoring",    650));

        // ---- Tabel Ikan Populer ----
        JLabel lblIkanPopuler = new JLabel("Ikan Populer");
        lblIkanPopuler.setFont(new Font("Tahoma", Font.BOLD, 13));
        lblIkanPopuler.setBounds(20, 200, 200, 25);
        panel.add(lblIkanPopuler);

        String[] kolomIkan = {"Nama Ikan", "Habitat"};
        String[][] dataIkan = {
            {"Cupang", "Air Tawar"}, {"Guppy", "Air Tawar"},
            {"Arwana", "Air Tawar"}, {"Neon Tetra", "Air Tawar"}
        };
        JTable tabelIkan = new JTable(dataIkan, kolomIkan);
        JScrollPane scrollIkan = new JScrollPane(tabelIkan);
        scrollIkan.setBounds(20, 230, 410, 110);
        panel.add(scrollIkan);

        JButton btnLihatIkan = new JButton("Lihat Semua");
        btnLihatIkan.setBounds(310, 350, 120, 30);
        btnLihatIkan.addActionListener(e -> bukaEnsiklopedia());
        panel.add(btnLihatIkan);

        // ---- Tabel Postingan Terbaru ----
        JLabel lblPostingan = new JLabel("Postingan Terbaru");
        lblPostingan.setFont(new Font("Tahoma", Font.BOLD, 13));
        lblPostingan.setBounds(450, 200, 200, 25);
        panel.add(lblPostingan);

        String[] kolomPost = {"Judul", "Pengguna"};
        String[][] dataPost = {
            {"Cara merawat Cupang", "Budi99"},
            {"Penyakit pada Arwana", "AquaMentor"},
            {"Pakan terbaik untuk Guppy", "IkanLover"}
        };
        JTable tabelPost = new JTable(dataPost, kolomPost);
        JScrollPane scrollPost = new JScrollPane(tabelPost);
        scrollPost.setBounds(450, 230, 400, 110);
        panel.add(scrollPost);

        JButton btnLihatPost = new JButton("Lihat Semua");
        btnLihatPost.setBounds(730, 350, 120, 30);
        btnLihatPost.addActionListener(e -> bukaForum());
        panel.add(btnLihatPost);

        return panel;
    }

    private JPanel buatKartuRingkasan(String angka, String label, int x) {
        JPanel kartu = new JPanel();
        kartu.setLayout(null);
        kartu.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        kartu.setBounds(x, 80, 195, 100);

        JLabel lblAngka = new JLabel(angka, SwingConstants.CENTER);
        lblAngka.setFont(new Font("Tahoma", Font.BOLD, 26));
        lblAngka.setBounds(0, 15, 195, 40);
        kartu.add(lblAngka);

        JLabel lblLabel = new JLabel(label, SwingConstants.CENTER);
        lblLabel.setBounds(0, 60, 195, 25);
        kartu.add(lblLabel);

        return kartu;
    }

    // =========================================================
    // METHOD SESUAI CLASS DIAGRAM
    // =========================================================

    /**
     * Cetak menu sesuai role — menampilkan halaman Beranda.
     */
    public void tampilkanMenu() {
        cardLayout.show(panelKonten, CARD_BERANDA);
    }

    /**
     * Loop menu utama — routing ke Ensiklopedia/Forum/Artikel/Profil.
     */
    public void jalankan() {
        setVisible(true);
        tampilkanMenu();
    }

    /**
     * Buat EnsiklopediaService → tampilkan sub-menu ikan.
     */
    private void bukaEnsiklopedia() {
        EnsiklopediaFrame frame = new EnsiklopediaFrame();
        frame.tampilkanMenu();
    }

    /**
     * Buat ForumService → tampilkan sub-menu forum.
     */
    private void bukaForum() {
        ForumFrame frame = new ForumFrame(currentUser);
        frame.tampilkanMenu();
    }

    /**
     * Buat ArtikelService → tampilkan sub-menu artikel.
     */
    private void bukaArtikel() {
        ArtikelFrame frame = new ArtikelFrame(currentUser);
        frame.tampilkanMenu();
    }

    /**
     * Buat UserService → tampilkan dan edit profil.
     */
    private void bukaProfil() {
        JOptionPane.showMessageDialog(this,
            "Nama  : " + currentUser.getNama() + "\n" +
            "Email : " + currentUser.getEmail() + "\n" +
            "Role  : " + currentUser.getRole(),
            "Profil Saya", JOptionPane.INFORMATION_MESSAGE);
    }
}
