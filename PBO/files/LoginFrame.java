package fishlens.view;

import fishlens.model.Penghobi;
import fishlens.model.User;
import fishlens.service.LoginService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * LoginFrame
 * GUI window pertama yang muncul saat program dijalankan.
 * Menangani Login dan Register user.
 *
 * Atribut:
 *   - loginService : LoginService  → memanggil login() dan register()
 *
 * Method publik  : tampilkanMenu(), jalankan()
 * Method privat  : prosesLogin(), prosesRegister(), buatPanelLogin(),
 *                  buatPanelRegister(), styleButton()
 *
 * Anggota 5 — View / UI
 */
public class LoginFrame extends JFrame {

    // ─── Atribut ───────────────────────────────────────────────────────────
    private LoginService loginService;

    // ─── Komponen GUI ──────────────────────────────────────────────────────
    private JTabbedPane tabbedPane;

    // Panel Login
    private JTextField  txtLoginEmail;
    private JPasswordField txtLoginPassword;
    private JButton btnLogin;

    // Panel Register
    private JTextField  txtRegNama;
    private JTextField  txtRegEmail;
    private JPasswordField txtRegPassword;
    private JTextField  txtRegHobi;
    private JButton     btnRegister;

    // ─── Warna tema ────────────────────────────────────────────────────────
    private static final Color BIRU_TUA    = new Color(0x0A4080);
    private static final Color BIRU_MUDA   = new Color(0x1976D2);
    private static final Color BG_PUTIH    = new Color(0xF5F8FF);
    private static final Color AKSEN_IKAN  = new Color(0x00838F);

    // ═══════════════════════════════════════════════════════════════════════
    //  Constructor
    // ═══════════════════════════════════════════════════════════════════════

    public LoginFrame() {
        this.loginService = new LoginService();
        initUI();
    }

    // ═══════════════════════════════════════════════════════════════════════
    //  Init UI
    // ═══════════════════════════════════════════════════════════════════════

    private void initUI() {
        setTitle("FishLens — Selamat Datang");
        setSize(440, 560);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        getContentPane().setBackground(BG_PUTIH);

        JPanel panelUtama = new JPanel(new BorderLayout(0, 0));
        panelUtama.setBackground(BG_PUTIH);

        // ── Header ──
        JPanel header = buatHeader();
        panelUtama.add(header, BorderLayout.NORTH);

        // ── Tab Login / Register ──
        tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("SansSerif", Font.PLAIN, 14));
        tabbedPane.setBackground(BG_PUTIH);
        tabbedPane.addTab("  Login  ",  buatPanelLogin());
        tabbedPane.addTab("  Daftar  ", buatPanelRegister());
        panelUtama.add(tabbedPane, BorderLayout.CENTER);

        // ── Footer ──
        JLabel footer = new JLabel("FishLens v1.0 — Ensiklopedia Ikan Hias", SwingConstants.CENTER);
        footer.setFont(new Font("SansSerif", Font.ITALIC, 11));
        footer.setForeground(Color.GRAY);
        footer.setBorder(new EmptyBorder(6, 0, 8, 0));
        panelUtama.add(footer, BorderLayout.SOUTH);

        add(panelUtama);
    }

    private JPanel buatHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(BIRU_TUA);
        header.setBorder(new EmptyBorder(20, 24, 20, 24));

        JLabel lblJudul = new JLabel("🐟 FishLens");
        lblJudul.setFont(new Font("SansSerif", Font.BOLD, 26));
        lblJudul.setForeground(Color.WHITE);

        JLabel lblSub = new JLabel("Aplikasi Ensiklopedia Ikan Hias");
        lblSub.setFont(new Font("SansSerif", Font.PLAIN, 13));
        lblSub.setForeground(new Color(0xB3D1FF));

        JPanel teks = new JPanel(new GridLayout(2, 1, 0, 2));
        teks.setOpaque(false);
        teks.add(lblJudul);
        teks.add(lblSub);
        header.add(teks, BorderLayout.CENTER);
        return header;
    }

    // ═══════════════════════════════════════════════════════════════════════
    //  Panel Login
    // ═══════════════════════════════════════════════════════════════════════

    private JPanel buatPanelLogin() {
        JPanel panel = new JPanel(null);
        panel.setBackground(BG_PUTIH);
        panel.setBorder(new EmptyBorder(10, 0, 0, 0));

        int lx = 50, lw = 320, gap = 56;
        int y = 40;

        // Email
        panel.add(buatLabel("Email", lx, y));
        txtLoginEmail = buatTextField(lx, y + 22, lw);
        panel.add(txtLoginEmail);

        y += gap;

        // Password
        panel.add(buatLabel("Password", lx, y));
        txtLoginPassword = new JPasswordField();
        txtLoginPassword.setBounds(lx, y + 22, lw, 34);
        txtLoginPassword.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(0xB0C4DE), 1, true),
                new EmptyBorder(4, 8, 4, 8)));
        txtLoginPassword.setFont(new Font("SansSerif", Font.PLAIN, 14));
        panel.add(txtLoginPassword);

        y += gap + 16;

        // Tombol Login
        btnLogin = new JButton("Masuk");
        styleButton(btnLogin, BIRU_MUDA, lx, y, lw, 40);
        btnLogin.addActionListener((ActionEvent e) -> prosesLogin());
        panel.add(btnLogin);

        // Enter di password field = klik tombol login
        txtLoginPassword.addActionListener(e -> prosesLogin());

        return panel;
    }

    // ═══════════════════════════════════════════════════════════════════════
    //  Panel Register
    // ═══════════════════════════════════════════════════════════════════════

    private JPanel buatPanelRegister() {
        JPanel panel = new JPanel(null);
        panel.setBackground(BG_PUTIH);

        int lx = 50, lw = 320, gap = 54;
        int y = 24;

        // Nama
        panel.add(buatLabel("Nama Lengkap", lx, y));
        txtRegNama = buatTextField(lx, y + 22, lw);
        panel.add(txtRegNama);
        y += gap;

        // Email
        panel.add(buatLabel("Email", lx, y));
        txtRegEmail = buatTextField(lx, y + 22, lw);
        panel.add(txtRegEmail);
        y += gap;

        // Password
        panel.add(buatLabel("Password", lx, y));
        txtRegPassword = new JPasswordField();
        txtRegPassword.setBounds(lx, y + 22, lw, 34);
        txtRegPassword.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(0xB0C4DE), 1, true),
                new EmptyBorder(4, 8, 4, 8)));
        txtRegPassword.setFont(new Font("SansSerif", Font.PLAIN, 14));
        panel.add(txtRegPassword);
        y += gap;

        // Hobi Ikan
        panel.add(buatLabel("Hobi Ikan (mis: Cupang Hias)", lx, y));
        txtRegHobi = buatTextField(lx, y + 22, lw);
        panel.add(txtRegHobi);
        y += gap;

        // Tombol Daftar
        btnRegister = new JButton("Daftar Sekarang");
        styleButton(btnRegister, AKSEN_IKAN, lx, y, lw, 40);
        btnRegister.addActionListener(e -> prosesRegister());
        panel.add(btnRegister);

        return panel;
    }

    // ═══════════════════════════════════════════════════════════════════════
    //  Private Methods — Logika
    // ═══════════════════════════════════════════════════════════════════════

    /**
     * Baca email+password → panggil loginService.login() → arahkan ke MainFrame.
     */
    private void prosesLogin() {
        String email    = txtLoginEmail.getText().trim();
        String password = new String(txtLoginPassword.getPassword()).trim();

        if (email.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Email dan password tidak boleh kosong.",
                    "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        User user = loginService.login(email, password);

        if (user != null) {
            JOptionPane.showMessageDialog(this,
                    "Selamat datang, " + user.getNama() + "! 🐟",
                    "Login Berhasil", JOptionPane.INFORMATION_MESSAGE);

            // Tutup LoginFrame, buka MainFrame
            dispose();
            MainFrame mainFrame = new MainFrame(user, loginService);
            mainFrame.setVisible(true);

        } else {
            JOptionPane.showMessageDialog(this,
                    "Email atau password salah. Silakan coba lagi.",
                    "Login Gagal", JOptionPane.ERROR_MESSAGE);
            txtLoginPassword.setText("");
        }
    }

    /**
     * Baca data user baru → panggil loginService.register().
     */
    private void prosesRegister() {
        String nama     = txtRegNama.getText().trim();
        String email    = txtRegEmail.getText().trim();
        String password = new String(txtRegPassword.getPassword()).trim();
        String hobi     = txtRegHobi.getText().trim();

        if (nama.isEmpty() || email.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Nama, email, dan password wajib diisi.",
                    "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Buat objek Penghobi baru dengan tingkat awal PEMULA
        Penghobi penghobi = new Penghobi(0, nama, email, password,
                hobi.isEmpty() ? "-" : hobi,
                "PEMULA",
                java.time.LocalDate.now().toString());

        boolean berhasil = loginService.register(penghobi);

        if (berhasil) {
            JOptionPane.showMessageDialog(this,
                    "Registrasi berhasil! Silakan login.",
                    "Daftar Berhasil", JOptionPane.INFORMATION_MESSAGE);

            // Bersihkan field dan pindah ke tab Login
            txtRegNama.setText(""); txtRegEmail.setText("");
            txtRegPassword.setText(""); txtRegHobi.setText("");
            tabbedPane.setSelectedIndex(0);
            txtLoginEmail.setText(email);
            txtLoginEmail.requestFocus();

        } else {
            JOptionPane.showMessageDialog(this,
                    "Registrasi gagal. Email mungkin sudah digunakan.",
                    "Registrasi Gagal", JOptionPane.ERROR_MESSAGE);
        }
    }

    // ═══════════════════════════════════════════════════════════════════════
    //  Public Methods
    // ═══════════════════════════════════════════════════════════════════════

    /**
     * Menampilkan window LoginFrame ke layar.
     * Dipanggil oleh FishLensApp.main().
     */
    public void tampilkanMenu() {
        setVisible(true);
    }

    /**
     * Entry point untuk menjalankan LoginFrame.
     * Dipanggil oleh FishLensApp.main() setelah membuat objek LoginFrame.
     */
    public void jalankan() {
        SwingUtilities.invokeLater(() -> setVisible(true));
    }

    // ═══════════════════════════════════════════════════════════════════════
    //  Helper UI
    // ═══════════════════════════════════════════════════════════════════════

    private JLabel buatLabel(String teks, int x, int y) {
        JLabel lbl = new JLabel(teks);
        lbl.setFont(new Font("SansSerif", Font.PLAIN, 13));
        lbl.setForeground(new Color(0x37474F));
        lbl.setBounds(x, y, 300, 18);
        return lbl;
    }

    private JTextField buatTextField(int x, int y, int w) {
        JTextField tf = new JTextField();
        tf.setBounds(x, y, w, 34);
        tf.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(0xB0C4DE), 1, true),
                new EmptyBorder(4, 8, 4, 8)));
        tf.setFont(new Font("SansSerif", Font.PLAIN, 14));
        return tf;
    }

    private void styleButton(JButton btn, Color bg, int x, int y, int w, int h) {
        btn.setBounds(x, y, w, h);
        btn.setBackground(bg);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("SansSerif", Font.BOLD, 14));
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setOpaque(true);
    }
}
