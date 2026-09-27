package fishlens.view;

import fishlens.model.*;
import fishlens.service.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

/**
 * MainFrame
 * Menu utama setelah login berhasil.
 * Routing ke semua fitur: Ensiklopedia, Forum, Artikel, Profil.
 *
 * Atribut:
 *   - currentUser : User   → user yang sedang login (menentukan menu)
 *
 * Anggota 5 — View / UI
 */
public class MainFrame extends JFrame {

    // ─── Atribut ───────────────────────────────────────────────────────────
    private User         currentUser;
    private LoginService loginService;

    // ─── Komponen GUI ──────────────────────────────────────────────────────
    private JPanel   panelKonten;
    private CardLayout cardLayout;
    private JLabel   lblSelamatDatang;

    // ─── Warna tema ────────────────────────────────────────────────────────
    private static final Color BIRU_TUA  = new Color(0x0A4080);
    private static final Color BIRU_NAV  = new Color(0x0D47A1);
    private static final Color BG_PUTIH  = new Color(0xF5F8FF);
    private static final Color AKSEN     = new Color(0x00838F);
    private static final Color HOVER     = new Color(0x1565C0);

    // ═══════════════════════════════════════════════════════════════════════
    //  Constructor
    // ═══════════════════════════════════════════════════════════════════════

    public MainFrame(User currentUser, LoginService loginService) {
        this.currentUser  = currentUser;
        this.loginService = loginService;
        initUI();
    }

    // ═══════════════════════════════════════════════════════════════════════
    //  Init UI
    // ═══════════════════════════════════════════════════════════════════════

    private void initUI() {
        setTitle("FishLens — Menu Utama");
        setSize(900, 640);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);
        setMinimumSize(new Dimension(800, 580));

        // Konfirmasi saat close
        addWindowListener(new WindowAdapter() {
            @Override public void windowClosing(WindowEvent e) {
                int pilih = JOptionPane.showConfirmDialog(MainFrame.this,
                        "Yakin ingin keluar dari FishLens?",
                        "Konfirmasi", JOptionPane.YES_NO_OPTION);
                if (pilih == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        });

        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(BG_PUTIH);

        root.add(buatHeader(), BorderLayout.NORTH);
        root.add(buatSidebar(), BorderLayout.WEST);

        // ── Area konten dengan CardLayout ──
        cardLayout  = new CardLayout();
        panelKonten = new JPanel(cardLayout);
        panelKonten.setBackground(BG_PUTIH);
        panelKonten.add(buatPanelDashboard(), "dashboard");
        panelKonten.add(buatPanelEnsiklopedia(), "ensiklopedia");
        panelKonten.add(buatPanelForum(), "forum");
        panelKonten.add(buatPanelArtikel(), "artikel");
        panelKonten.add(buatPanelProfil(), "profil");
        root.add(panelKonten, BorderLayout.CENTER);

        add(root);
    }

    // ═══════════════════════════════════════════════════════════════════════
    //  Header
    // ═══════════════════════════════════════════════════════════════════════

    private JPanel buatHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(BIRU_TUA);
        header.setPreferredSize(new Dimension(0, 56));
        header.setBorder(new EmptyBorder(8, 20, 8, 20));

        JLabel lblApp = new JLabel("🐟 FishLens");
        lblApp.setFont(new Font("SansSerif", Font.BOLD, 18));
        lblApp.setForeground(Color.WHITE);

        lblSelamatDatang = new JLabel("Selamat datang, " + currentUser.getNama()
                + "  [" + currentUser.getRole() + "]");
        lblSelamatDatang.setFont(new Font("SansSerif", Font.PLAIN, 13));
        lblSelamatDatang.setForeground(new Color(0xB3D1FF));
        lblSelamatDatang.setHorizontalAlignment(SwingConstants.RIGHT);

        header.add(lblApp, BorderLayout.WEST);
        header.add(lblSelamatDatang, BorderLayout.EAST);
        return header;
    }

    // ═══════════════════════════════════════════════════════════════════════
    //  Sidebar Navigasi
    // ═══════════════════════════════════════════════════════════════════════

    private JPanel buatSidebar() {
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBackground(BIRU_NAV);
        sidebar.setPreferredSize(new Dimension(190, 0));
        sidebar.setBorder(new EmptyBorder(16, 0, 16, 0));

        String[] menu = {"🏠 Dashboard", "🐠 Ensiklopedia", "💬 Forum", "📚 Artikel", "👤 Profil"};
        String[] cards = {"dashboard", "ensiklopedia", "forum", "artikel", "profil"};

        for (int i = 0; i < menu.length; i++) {
            final String card = cards[i];
            JButton btn = buatNavButton(menu[i]);
            btn.addActionListener(e -> {
                cardLayout.show(panelKonten, card);
                if (card.equals("ensiklopedia")) muatDataIkan(null);
                if (card.equals("forum"))        muatDataForum();
                if (card.equals("artikel"))      muatDataArtikel();
            });
            sidebar.add(btn);
            sidebar.add(Box.createVerticalStrut(4));
        }

        sidebar.add(Box.createVerticalGlue());

        // Tombol Logout di bawah
        JButton btnLogout = buatNavButton("🚪 Logout");
        btnLogout.setBackground(new Color(0x7B1FA2));
        btnLogout.addActionListener(e -> prosesLogout());
        sidebar.add(btnLogout);

        return sidebar;
    }

    private JButton buatNavButton(String teks) {
        JButton btn = new JButton(teks);
        btn.setMaximumSize(new Dimension(190, 44));
        btn.setPreferredSize(new Dimension(190, 44));
        btn.setFont(new Font("SansSerif", Font.PLAIN, 14));
        btn.setBackground(BIRU_NAV);
        btn.setForeground(Color.WHITE);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setOpaque(true);
        btn.setAlignmentX(Component.LEFT_ALIGNMENT);
        btn.setBorder(new EmptyBorder(0, 20, 0, 0));
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent e) { btn.setBackground(HOVER); }
            public void mouseExited(java.awt.event.MouseEvent e)  { btn.setBackground(
                    btn.getText().contains("Logout") ? new Color(0x7B1FA2) : BIRU_NAV); }
        });
        return btn;
    }

    // ═══════════════════════════════════════════════════════════════════════
    //  Panel Dashboard
    // ═══════════════════════════════════════════════════════════════════════

    private JPanel buatPanelDashboard() {
        JPanel p = new JPanel(new GridBagLayout());
        p.setBackground(BG_PUTIH);

        JLabel lbl = new JLabel("<html><div style='text-align:center'>"
                + "<span style='font-size:40px'>🐟</span><br/>"
                + "<b style='font-size:18px'>Selamat Datang di FishLens!</b><br/><br/>"
                + "Halo, <b>" + currentUser.getNama() + "</b><br/>"
                + "Role: " + currentUser.getRole() + "<br/><br/>"
                + "<span style='color:gray'>Pilih menu di sebelah kiri untuk mulai.</span>"
                + "</div></html>");
        lbl.setHorizontalAlignment(SwingConstants.CENTER);
        p.add(lbl);
        return p;
    }

    // ═══════════════════════════════════════════════════════════════════════
    //  Panel Ensiklopedia  (bukaEnsiklopedia)
    // ═══════════════════════════════════════════════════════════════════════

    private JTable tblIkan;
    private DefaultTableModel modelIkan;
    private JTextField txtCariIkan;

    private JPanel buatPanelEnsiklopedia() {
        JPanel p = new JPanel(new BorderLayout(0, 0));
        p.setBackground(BG_PUTIH);
        p.setBorder(new EmptyBorder(16, 20, 16, 20));

        // ── Top bar ──
        JPanel topBar = new JPanel(new BorderLayout(10, 0));
        topBar.setOpaque(false);
        JLabel judul = new JLabel("🐠 Ensiklopedia Ikan");
        judul.setFont(new Font("SansSerif", Font.BOLD, 17));
        judul.setForeground(BIRU_TUA);
        topBar.add(judul, BorderLayout.WEST);

        JPanel cariPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        cariPanel.setOpaque(false);
        txtCariIkan = new JTextField(16);
        txtCariIkan.setFont(new Font("SansSerif", Font.PLAIN, 13));
        JButton btnCari = new JButton("Cari");
        styleSmallButton(btnCari, AKSEN);
        btnCari.addActionListener(e -> muatDataIkan(txtCariIkan.getText().trim()));
        JButton btnReset = new JButton("Reset");
        styleSmallButton(btnReset, Color.GRAY);
        btnReset.addActionListener(e -> { txtCariIkan.setText(""); muatDataIkan(null); });
        cariPanel.add(new JLabel("Cari ikan:"));
        cariPanel.add(txtCariIkan);
        cariPanel.add(btnCari);
        cariPanel.add(btnReset);
        topBar.add(cariPanel, BorderLayout.EAST);
        p.add(topBar, BorderLayout.NORTH);

        // ── Tabel ──
        String[] kolom = {"ID", "Nama Spesies", "Habitat", "Pakan", "Penyakit Umum"};
        modelIkan = new DefaultTableModel(kolom, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        tblIkan = new JTable(modelIkan);
        tblIkan.setRowHeight(28);
        tblIkan.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 13));
        tblIkan.setFont(new Font("SansSerif", Font.PLAIN, 13));
        tblIkan.setSelectionBackground(new Color(0xBBDEFB));
        tblIkan.getColumnModel().getColumn(0).setPreferredWidth(40);
        tblIkan.getColumnModel().getColumn(1).setPreferredWidth(180);
        p.add(new JScrollPane(tblIkan), BorderLayout.CENTER);

        return p;
    }

    private void muatDataIkan(String keyword) {
        EnsiklopediaService svc = new EnsiklopediaService();
        List<Ikan> daftar = (keyword == null || keyword.isEmpty())
                ? svc.getAllIkan()
                : svc.cariIkan(keyword);

        modelIkan.setRowCount(0);
        for (Ikan ikan : daftar) {
            modelIkan.addRow(new Object[]{
                    ikan.getId(), ikan.getNamaSpesies(),
                    ikan.getHabitat(), ikan.getPakan(), ikan.getPenyakitUmum()
            });
        }
    }

    // ═══════════════════════════════════════════════════════════════════════
    //  Panel Forum  (bukaForum)
    // ═══════════════════════════════════════════════════════════════════════

    private JTable tblForum;
    private DefaultTableModel modelForum;

    private JPanel buatPanelForum() {
        JPanel p = new JPanel(new BorderLayout(0, 10));
        p.setBackground(BG_PUTIH);
        p.setBorder(new EmptyBorder(16, 20, 16, 20));

        // ── Top bar ──
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setOpaque(false);
        JLabel judul = new JLabel("💬 Forum Diskusi");
        judul.setFont(new Font("SansSerif", Font.BOLD, 17));
        judul.setForeground(BIRU_TUA);
        topBar.add(judul, BorderLayout.WEST);

        JButton btnBuat = new JButton("+ Buat Postingan");
        styleSmallButton(btnBuat, AKSEN);
        btnBuat.addActionListener(e -> dialogBuatPostingan());
        topBar.add(btnBuat, BorderLayout.EAST);
        p.add(topBar, BorderLayout.NORTH);

        // ── Tabel ──
        String[] kolom = {"ID", "Judul", "Oleh (User ID)", "Tanggal"};
        modelForum = new DefaultTableModel(kolom, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        tblForum = new JTable(modelForum);
        tblForum.setRowHeight(28);
        tblForum.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 13));
        tblForum.setFont(new Font("SansSerif", Font.PLAIN, 13));
        tblForum.setSelectionBackground(new Color(0xBBDEFB));
        p.add(new JScrollPane(tblForum), BorderLayout.CENTER);

        return p;
    }

    private void muatDataForum() {
        ForumService svc = new ForumService();
        List<Postingan> daftar = svc.getAllPostingan();
        modelForum.setRowCount(0);
        for (Postingan post : daftar) {
            modelForum.addRow(new Object[]{
                    post.getId(), post.getJudul(), post.getUserId(), post.getTanggal()
            });
        }
    }

    private void dialogBuatPostingan() {
        JTextField txtJudul = new JTextField(25);
        JTextArea  txtIsi   = new JTextArea(5, 25);
        txtIsi.setLineWrap(true); txtIsi.setWrapStyleWord(true);

        JPanel form = new JPanel(new GridLayout(0, 1, 0, 6));
        form.add(new JLabel("Judul:")); form.add(txtJudul);
        form.add(new JLabel("Isi:"));  form.add(new JScrollPane(txtIsi));

        int hasil = JOptionPane.showConfirmDialog(this, form,
                "Buat Postingan Baru", JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE);

        if (hasil == JOptionPane.OK_OPTION) {
            String judul = txtJudul.getText().trim();
            String isi   = txtIsi.getText().trim();
            if (!judul.isEmpty() && !isi.isEmpty()) {
                Postingan p = new Postingan(0, judul, isi, currentUser.getId(), "");
                ForumService svc = new ForumService();
                if (svc.buatPostingan(p)) {
                    JOptionPane.showMessageDialog(this, "Postingan berhasil dibuat!");
                    muatDataForum();
                }
            }
        }
    }

    // ═══════════════════════════════════════════════════════════════════════
    //  Panel Artikel  (bukaArtikel)
    // ═══════════════════════════════════════════════════════════════════════

    private JTable tblArtikel;
    private DefaultTableModel modelArtikel;

    private JPanel buatPanelArtikel() {
        JPanel p = new JPanel(new BorderLayout(0, 10));
        p.setBackground(BG_PUTIH);
        p.setBorder(new EmptyBorder(16, 20, 16, 20));

        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setOpaque(false);
        JLabel judul = new JLabel("📚 Artikel Edukasi");
        judul.setFont(new Font("SansSerif", Font.BOLD, 17));
        judul.setForeground(BIRU_TUA);
        topBar.add(judul, BorderLayout.WEST);

        // Mentor bisa submit artikel
        if ("MENTOR".equalsIgnoreCase(currentUser.getRole())) {
            JButton btnSubmit = new JButton("+ Submit Artikel");
            styleSmallButton(btnSubmit, new Color(0x1976D2));
            btnSubmit.addActionListener(e -> dialogSubmitArtikel());
            topBar.add(btnSubmit, BorderLayout.EAST);
        }
        p.add(topBar, BorderLayout.NORTH);

        String[] kolom = {"ID", "Judul", "Mentor ID", "Status"};
        modelArtikel = new DefaultTableModel(kolom, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        tblArtikel = new JTable(modelArtikel);
        tblArtikel.setRowHeight(28);
        tblArtikel.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 13));
        tblArtikel.setFont(new Font("SansSerif", Font.PLAIN, 13));
        tblArtikel.setSelectionBackground(new Color(0xBBDEFB));
        p.add(new JScrollPane(tblArtikel), BorderLayout.CENTER);

        return p;
    }

    private void muatDataArtikel() {
        ArtikelService svc = new ArtikelService();
        List<Artikel> daftar = svc.getArtikelApproved();
        modelArtikel.setRowCount(0);
        for (Artikel a : daftar) {
            modelArtikel.addRow(new Object[]{
                    a.getId(), a.getJudul(), a.getMentorId(), a.getStatus()
            });
        }
    }

    private void dialogSubmitArtikel() {
        JTextField txtJudul = new JTextField(25);
        JTextArea  txtIsi   = new JTextArea(6, 25);
        txtIsi.setLineWrap(true); txtIsi.setWrapStyleWord(true);

        JPanel form = new JPanel(new GridLayout(0, 1, 0, 6));
        form.add(new JLabel("Judul Artikel:")); form.add(txtJudul);
        form.add(new JLabel("Isi Artikel:")); form.add(new JScrollPane(txtIsi));

        int hasil = JOptionPane.showConfirmDialog(this, form,
                "Submit Artikel Edukasi", JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE);

        if (hasil == JOptionPane.OK_OPTION) {
            String judul = txtJudul.getText().trim();
            String isi   = txtIsi.getText().trim();
            if (!judul.isEmpty() && !isi.isEmpty()) {
                Artikel a = new Artikel(0, judul, isi, currentUser.getId(), "PENDING");
                ArtikelService svc = new ArtikelService();
                if (svc.submitArtikel(a)) {
                    JOptionPane.showMessageDialog(this,
                            "Artikel berhasil disubmit!\nMenunggu persetujuan Admin.",
                            "Submit Berhasil", JOptionPane.INFORMATION_MESSAGE);
                    muatDataArtikel();
                }
            }
        }
    }

    // ═══════════════════════════════════════════════════════════════════════
    //  Panel Profil  (bukaProfil)
    // ═══════════════════════════════════════════════════════════════════════

    private JTextField txtProfilNama, txtProfilEmail, txtProfilHobi;

    private JPanel buatPanelProfil() {
        JPanel p = new JPanel(null);
        p.setBackground(BG_PUTIH);

        JLabel judul = new JLabel("👤 Profil Saya");
        judul.setFont(new Font("SansSerif", Font.BOLD, 17));
        judul.setForeground(BIRU_TUA);
        judul.setBounds(30, 20, 300, 28);
        p.add(judul);

        int lx = 30, fw = 300, y = 68, gap = 52;

        p.add(buatLabelForm("Nama", lx, y));
        txtProfilNama = buatFieldForm(lx, y + 22, fw);
        txtProfilNama.setText(currentUser.getNama());
        p.add(txtProfilNama);
        y += gap;

        p.add(buatLabelForm("Email", lx, y));
        txtProfilEmail = buatFieldForm(lx, y + 22, fw);
        txtProfilEmail.setText(currentUser.getEmail());
        p.add(txtProfilEmail);
        y += gap;

        if (currentUser instanceof Penghobi) {
            p.add(buatLabelForm("Hobi Ikan", lx, y));
            txtProfilHobi = buatFieldForm(lx, y + 22, fw);
            txtProfilHobi.setText(((Penghobi) currentUser).getHobiIkan());
            p.add(txtProfilHobi);
            y += gap;

            JLabel lblTingkat = buatLabelForm("Tingkat Keanggotaan", lx, y);
            p.add(lblTingkat);
            JLabel lblTingkatVal = new JLabel(((Penghobi) currentUser).getTingkatKeanggotaan());
            lblTingkatVal.setBounds(lx, y + 22, fw, 34);
            lblTingkatVal.setFont(new Font("SansSerif", Font.BOLD, 14));
            lblTingkatVal.setForeground(AKSEN);
            p.add(lblTingkatVal);
            y += gap;
        }

        JButton btnSimpan = new JButton("Simpan Perubahan");
        btnSimpan.setBounds(lx, y + 10, 200, 38);
        styleSmallButton(btnSimpan, BIRU_NAV);
        btnSimpan.addActionListener(e -> simpanProfil());
        p.add(btnSimpan);

        return p;
    }

    private void simpanProfil() {
        currentUser.setNama(txtProfilNama.getText().trim());
        currentUser.setEmail(txtProfilEmail.getText().trim());
        if (currentUser instanceof Penghobi && txtProfilHobi != null) {
            ((Penghobi) currentUser).setHobiIkan(txtProfilHobi.getText().trim());
        }

        UserService svc = new UserService();
        if (svc.updateProfil(currentUser)) {
            lblSelamatDatang.setText("Selamat datang, " + currentUser.getNama()
                    + "  [" + currentUser.getRole() + "]");
            JOptionPane.showMessageDialog(this, "Profil berhasil disimpan!");
        } else {
            JOptionPane.showMessageDialog(this, "Gagal menyimpan profil.",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // ═══════════════════════════════════════════════════════════════════════
    //  Public Methods
    // ═══════════════════════════════════════════════════════════════════════

    /**
     * Menampilkan menu sesuai role.
     */
    public void tampilkanMenu() {
        setVisible(true);
    }

    /**
     * Loop menu utama — routing ke sub-panel sesuai pilihan.
     * Dalam GUI Swing, navigasi ditangani event listener tombol Sidebar.
     */
    public void jalankan() {
        SwingUtilities.invokeLater(() -> setVisible(true));
    }

    // ─── Routing private methods ────────────────────────────────────────

    /** Membuka panel Ensiklopedia dan memuat data ikan */
    private void bukaEnsiklopedia() {
        cardLayout.show(panelKonten, "ensiklopedia");
        muatDataIkan(null);
    }

    /** Membuka panel Forum dan memuat data postingan */
    private void bukaForum() {
        cardLayout.show(panelKonten, "forum");
        muatDataForum();
    }

    /** Membuka panel Artikel dan memuat data artikel */
    private void bukaArtikel() {
        cardLayout.show(panelKonten, "artikel");
        muatDataArtikel();
    }

    /** Membuka panel Profil */
    private void bukaProfil() {
        cardLayout.show(panelKonten, "profil");
    }

    /** Proses logout: kembali ke LoginFrame */
    private void prosesLogout() {
        int konfirmasi = JOptionPane.showConfirmDialog(this,
                "Yakin ingin logout?", "Logout", JOptionPane.YES_NO_OPTION);
        if (konfirmasi == JOptionPane.YES_OPTION) {
            loginService.logout();
            dispose();
            LoginFrame loginFrame = new LoginFrame();
            loginFrame.tampilkanMenu();
        }
    }

    // ═══════════════════════════════════════════════════════════════════════
    //  Helper UI
    // ═══════════════════════════════════════════════════════════════════════

    private void styleSmallButton(JButton btn, Color bg) {
        btn.setBackground(bg);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("SansSerif", Font.BOLD, 13));
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setOpaque(true);
    }

    private JLabel buatLabelForm(String teks, int x, int y) {
        JLabel lbl = new JLabel(teks);
        lbl.setFont(new Font("SansSerif", Font.PLAIN, 12));
        lbl.setForeground(new Color(0x37474F));
        lbl.setBounds(x, y, 300, 18);
        return lbl;
    }

    private JTextField buatFieldForm(int x, int y, int w) {
        JTextField tf = new JTextField();
        tf.setBounds(x, y, w, 34);
        tf.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(0xB0C4DE), 1, true),
                new EmptyBorder(4, 8, 4, 8)));
        tf.setFont(new Font("SansSerif", Font.PLAIN, 14));
        return tf;
    }
}
