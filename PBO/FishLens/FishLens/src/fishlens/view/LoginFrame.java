package fishlens.view;

import fishlens.model.User;
import fishlens.model.Penghobi;
import fishlens.service.LoginService;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Scanner;

/**
 * LoginFrame — View Layer (Anggota 5)
 *
 * Atribut sesuai class diagram:
 *   - loginService : LoginService
 *   - scanner      : Scanner
 *
 * Method sesuai class diagram:
 *   + tampilkanMenu() : void
 *   - prosesLogin() : void
 *   - prosesRegister() : void
 *   + jalankan() : void
 */
public class LoginFrame extends JFrame {

    // =========================================================
    // ATRIBUT (sesuai class diagram)
    // =========================================================

    /** Memanggil login() dan register() */
    private LoginService loginService;

    /** Membaca input user dari console (dipakai versi CLI; tidak aktif di versi GUI) */
    private Scanner scanner;

    // ---- Komponen GUI ----
    private JTextField     txtEmail;
    private JPasswordField txtPassword;
    private JCheckBox      chkTampilkanPassword;
    private JButton        btnLogin;
    private JButton        btnBatal;
    private JLabel         lblDaftar;

    // =========================================================
    // CONSTRUCTOR
    // =========================================================

    public LoginFrame() {
        super("Login");
        this.loginService = new LoginService();
        this.scanner      = new Scanner(System.in);
        buildUI();
    }

    // =========================================================
    // BUILD UI — sesuai referensi gambar
    // =========================================================

    private void buildUI() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(475, 420);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(null);

        JLabel lblJudul = new JLabel("FishLens - Login");
        lblJudul.setFont(new Font("Tahoma", Font.BOLD, 20));
        lblJudul.setBounds(120, 30, 300, 30);
        add(lblJudul);

        JLabel lblEmail = new JLabel("E-mail");
        lblEmail.setBounds(30, 95, 80, 25);
        add(lblEmail);

        txtEmail = new JTextField();
        txtEmail.setBounds(150, 95, 290, 28);
        add(txtEmail);

        JLabel lblPassword = new JLabel("Password");
        lblPassword.setBounds(30, 140, 80, 25);
        add(lblPassword);

        txtPassword = new JPasswordField();
        txtPassword.setBounds(150, 140, 290, 28);
        add(txtPassword);

        chkTampilkanPassword = new JCheckBox("Tampilkan Password");
        chkTampilkanPassword.setBounds(150, 175, 200, 25);
        add(chkTampilkanPassword);

        btnLogin = new JButton("Login");
        btnLogin.setBounds(150, 215, 130, 35);
        add(btnLogin);

        btnBatal = new JButton("Batal");
        btnBatal.setBounds(290, 215, 130, 35);
        add(btnBatal);

        JSeparator sep = new JSeparator();
        sep.setBounds(30, 280, 410, 2);
        add(sep);

        JLabel lblBelumPunya = new JLabel("Belum punya akun?");
        lblBelumPunya.setBounds(115, 300, 130, 25);
        add(lblBelumPunya);

        lblDaftar = new JLabel("<html><a href=''>Daftar di sini</a></html>");
        lblDaftar.setForeground(Color.BLUE);
        lblDaftar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        lblDaftar.setBounds(245, 300, 100, 25);
        add(lblDaftar);

        // ---- Aksi ----
        chkTampilkanPassword.addActionListener(e ->
            txtPassword.setEchoChar(chkTampilkanPassword.isSelected() ? (char) 0 : '\u2022'));

        btnLogin.addActionListener(e -> prosesLogin());
        btnBatal.addActionListener(e -> System.exit(0));
        lblDaftar.addMouseListener(new MouseAdapter() {
            @Override public void mouseClicked(MouseEvent e) { prosesRegister(); }
        });
    }

    // =========================================================
    // METHOD SESUAI CLASS DIAGRAM
    // =========================================================

    /**
     * Cetak menu awal FishLens.
     * Di versi GUI, method ini menampilkan window login.
     */
    public void tampilkanMenu() {
        setVisible(true);
    }

    /**
     * Baca email+password lalu panggil loginService.login() dan arahkan ke MainFrame.
     */
    private void prosesLogin() {
        String email = txtEmail.getText().trim();
        String pass  = new String(txtPassword.getPassword());

        if (email.isEmpty() || pass.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "E-mail dan Password tidak boleh kosong!",
                "Validasi", JOptionPane.WARNING_MESSAGE);
            return;
        }

        User user = loginService.login(email, pass);
        if (user != null) {
            JOptionPane.showMessageDialog(this,
                "Login berhasil! Selamat datang, " + user.getNama(),
                "Sukses", JOptionPane.INFORMATION_MESSAGE);

            MainFrame mainFrame = new MainFrame(user);
            mainFrame.jalankan();
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(this,
                "E-mail atau password salah!",
                "Login Gagal", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Baca data user baru lalu panggil loginService.register().
     */
    private void prosesRegister() {
        JTextField fNama  = new JTextField();
        JTextField fEmail = new JTextField();
        JPasswordField fPass = new JPasswordField();
        JTextField fHobi  = new JTextField();

        JPanel panel = new JPanel(new GridLayout(4, 2, 8, 8));
        panel.add(new JLabel("Nama:"));      panel.add(fNama);
        panel.add(new JLabel("Email:"));     panel.add(fEmail);
        panel.add(new JLabel("Password:"));  panel.add(fPass);
        panel.add(new JLabel("Hobi Ikan:")); panel.add(fHobi);

        int hasil = JOptionPane.showConfirmDialog(this, panel,
            "Daftar Akun Baru", JOptionPane.OK_CANCEL_OPTION);

        if (hasil == JOptionPane.OK_OPTION) {
            String nama  = fNama.getText().trim();
            String email = fEmail.getText().trim();
            String pass  = new String(fPass.getPassword());
            String hobi  = fHobi.getText().trim();

            if (nama.isEmpty() || email.isEmpty() || pass.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Semua field wajib diisi!");
                return;
            }

            Penghobi userBaru = new Penghobi(nama, email, pass, hobi.isEmpty() ? "Umum" : hobi);
            boolean ok = loginService.register(userBaru);

            JOptionPane.showMessageDialog(this,
                ok ? "Registrasi berhasil! Silakan login." : "Registrasi gagal.",
                ok ? "Sukses" : "Gagal",
                ok ? JOptionPane.INFORMATION_MESSAGE : JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Loop utama menu awal sampai user pilih keluar.
     * Di versi GUI: menampilkan window dan menjaga aplikasi tetap berjalan.
     */
    public void jalankan() {
        tampilkanMenu();
    }
}
