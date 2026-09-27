package fishlens.view;

import fishlens.model.Postingan;
import fishlens.model.User;
import fishlens.service.ForumService;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * ForumFrame — View pendukung untuk menu Forum.
 * Dipanggil oleh MainFrame.bukaForum().
 *
 * Method yang dipakai dari ForumService (signature sesuai class diagram):
 *   + getAllPostingan() : List<Postingan>
 *   + buatPostingan(p : Postingan) : boolean
 *   + save(obj : Object) : boolean   {dari Manageable, alias buatPostingan}
 *   + delete(id : int) : boolean     {dari Manageable}
 *   + findById(id : int) : Object    {dari Manageable}
 */
public class ForumFrame extends JFrame {

    private ForumService forumService;
    private User currentUser;

    private JTextField txtJudul;
    private JTextArea  txtIsi;
    private JButton     btnKirim;

    private JTable tabelPostingan;
    private DefaultTableModel modelTabel;
    private JButton btnRefresh, btnLihat;

    private List<Postingan> dataSaatIni;

    public ForumFrame(User currentUser) {
        super("Forum");
        this.currentUser  = currentUser;
        this.forumService = new ForumService();
        buildUI();
    }

    private void buildUI() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(975, 1015);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(null);

        JLabel lblBuatPostingan = new JLabel("Buat Postingan");
        lblBuatPostingan.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblBuatPostingan.setBounds(20, 15, 200, 25);
        add(lblBuatPostingan);

        JLabel lblJudul = new JLabel("Judul");
        lblJudul.setBounds(20, 55, 80, 25);
        add(lblJudul);

        txtJudul = new JTextField();
        txtJudul.setBounds(120, 55, 700, 30);
        add(txtJudul);

        JLabel lblIsi = new JLabel("Isi");
        lblIsi.setBounds(20, 100, 80, 25);
        add(lblIsi);

        txtIsi = new JTextArea();
        txtIsi.setLineWrap(true);
        txtIsi.setWrapStyleWord(true);
        JScrollPane scrollIsi = new JScrollPane(txtIsi);
        scrollIsi.setBounds(120, 100, 700, 250);
        add(scrollIsi);

        btnKirim = new JButton("Kirim");
        btnKirim.setBounds(720, 365, 100, 35);
        add(btnKirim);

        JLabel lblDaftarPostingan = new JLabel("Daftar Postingan");
        lblDaftarPostingan.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblDaftarPostingan.setBounds(20, 425, 200, 25);
        add(lblDaftarPostingan);

        String[] kolom = {"Judul", "Pengguna", "Tanggal"};
        modelTabel = new DefaultTableModel(kolom, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        tabelPostingan = new JTable(modelTabel);
        JScrollPane scrollTabel = new JScrollPane(tabelPostingan);
        scrollTabel.setBounds(20, 460, 700, 480);
        add(scrollTabel);

        btnRefresh = new JButton("Refresh");
        btnRefresh.setBounds(740, 460, 120, 35);
        add(btnRefresh);

        btnLihat = new JButton("Lihat");
        btnLihat.setBounds(740, 505, 120, 35);
        add(btnLihat);

        btnKirim.addActionListener(e -> kirimPostingan());
        btnRefresh.addActionListener(e -> muatDaftarPostingan());
        btnLihat.addActionListener(e -> lihatDetailPostingan());

        muatDaftarPostingan();
    }

    public void tampilkanMenu() {
        setVisible(true);
    }

    private void muatDaftarPostingan() {
        dataSaatIni = forumService.getAllPostingan();
        modelTabel.setRowCount(0);
        for (Postingan p : dataSaatIni) {
            String tgl = p.getTanggal();
            if (tgl.length() > 10) tgl = tgl.substring(0, 10);
            modelTabel.addRow(new Object[]{p.getJudul(), p.getNamaPembuat(), tgl});
        }
    }

    private void kirimPostingan() {
        String judul = txtJudul.getText().trim();
        String isi   = txtIsi.getText().trim();

        if (judul.isEmpty() || isi.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Judul dan isi tidak boleh kosong!");
            return;
        }

        Postingan postBaru = new Postingan(judul, isi, currentUser.getId());
        boolean ok = forumService.buatPostingan(postBaru);

        JOptionPane.showMessageDialog(this,
            ok ? "Postingan berhasil dikirim!" : "Gagal mengirim postingan.");

        if (ok) {
            txtJudul.setText("");
            txtIsi.setText("");
            muatDaftarPostingan();
        }
    }

    private void lihatDetailPostingan() {
        int row = tabelPostingan.getSelectedRow();
        if (row < 0 || row >= dataSaatIni.size()) {
            JOptionPane.showMessageDialog(this, "Pilih postingan yang ingin dilihat!");
            return;
        }

        Postingan p = dataSaatIni.get(row);
        Postingan pLengkap = (Postingan) forumService.findById(p.getId());
        if (pLengkap == null) pLengkap = p;

        pLengkap.display();

        JOptionPane.showMessageDialog(this,
            "Judul   : " + pLengkap.getJudul() + "\n" +
            "Penulis : " + pLengkap.getNamaPembuat() + "\n" +
            "Tanggal : " + pLengkap.getTanggal() + "\n\n" +
            pLengkap.getIsi(),
            "Detail Postingan", JOptionPane.PLAIN_MESSAGE);
    }
}
