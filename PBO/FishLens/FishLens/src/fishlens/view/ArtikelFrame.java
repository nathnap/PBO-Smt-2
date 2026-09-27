package fishlens.view;

import fishlens.model.Artikel;
import fishlens.model.Mentor;
import fishlens.model.User;
import fishlens.service.ArtikelService;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * ArtikelFrame — View pendukung untuk menu Artikel.
 * Dipanggil oleh MainFrame.bukaArtikel().
 *
 * Method yang dipakai dari ArtikelService (signature sesuai class diagram):
 *   + getArtikelApproved() : List<Artikel>
 *   + submitArtikel(a : Artikel) : boolean
 *   + approveArtikel(id : int) : boolean
 *   + save(obj : Object) : boolean   {dari Manageable}
 *   + delete(id : int) : boolean     {dari Manageable}
 *   + findById(id : int) : Object    {dari Manageable}
 */
public class ArtikelFrame extends JFrame {

    private ArtikelService artikelService;
    private User currentUser;

    private JTextField txtCari;
    private JButton     btnCari;

    private JTable tabelArtikel;
    private DefaultTableModel modelTabel;
    private JButton btnTambah, btnEdit, btnHapus;

    private JLabel lblJudulVal, lblPenulisVal, lblStatusVal;

    private List<Artikel> dataSaatIni;

    public ArtikelFrame(User currentUser) {
        super("Artikel");
        this.currentUser    = currentUser;
        this.artikelService = new ArtikelService();
        buildUI();
    }

    private void buildUI() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(540, 720);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(null);

        JLabel lblCari = new JLabel("Cari Artikel");
        lblCari.setFont(new Font("Tahoma", Font.BOLD, 13));
        lblCari.setBounds(20, 15, 150, 25);
        add(lblCari);

        txtCari = new JTextField();
        txtCari.setBounds(20, 45, 350, 30);
        add(txtCari);

        btnCari = new JButton("Cari");
        btnCari.setBounds(385, 45, 100, 30);
        add(btnCari);

        JLabel lblDaftar = new JLabel("Daftar Artikel");
        lblDaftar.setFont(new Font("Tahoma", Font.BOLD, 13));
        lblDaftar.setBounds(20, 95, 200, 25);
        add(lblDaftar);

        String[] kolom = {"Judul", "Penulis", "Status"};
        modelTabel = new DefaultTableModel(kolom, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        tabelArtikel = new JTable(modelTabel);
        JScrollPane scrollTabel = new JScrollPane(tabelArtikel);
        scrollTabel.setBounds(20, 125, 350, 250);
        add(scrollTabel);

        btnTambah = new JButton("Tambah");
        btnTambah.setBounds(385, 125, 110, 35);
        add(btnTambah);

        btnEdit = new JButton("Edit");
        btnEdit.setBounds(385, 170, 110, 35);
        add(btnEdit);

        btnHapus = new JButton("Hapus");
        btnHapus.setBounds(385, 215, 110, 35);
        add(btnHapus);

        JLabel lblDetail = new JLabel("Detail Artikel");
        lblDetail.setFont(new Font("Tahoma", Font.BOLD, 13));
        lblDetail.setBounds(20, 405, 200, 25);
        add(lblDetail);

        lblJudulVal   = buatBarisDetail("Judul",   440);
        lblPenulisVal = buatBarisDetail("Penulis", 470);
        lblStatusVal  = buatBarisDetail("Status",  500);

        btnCari.addActionListener(e -> cariArtikel());
        btnTambah.addActionListener(e -> tambahArtikel());
        btnEdit.addActionListener(e -> editArtikel());
        btnHapus.addActionListener(e -> hapusArtikel());
        tabelArtikel.getSelectionModel().addListSelectionListener(e -> tampilkanDetail());

        muatArtikel();
    }

    private JLabel buatBarisDetail(String label, int y) {
        JLabel lblLabel = new JLabel(label);
        lblLabel.setBounds(20, y, 90, 25);
        add(lblLabel);

        JLabel lblTitikDua = new JLabel(":");
        lblTitikDua.setBounds(115, y, 10, 25);
        add(lblTitikDua);

        JLabel lblValue = new JLabel("-");
        lblValue.setBounds(130, y, 380, 25);
        add(lblValue);
        return lblValue;
    }

    public void tampilkanMenu() {
        setVisible(true);
    }

    private void muatArtikel() {
        dataSaatIni = artikelService.getArtikelApproved();
        refreshTabel();
    }

    private void cariArtikel() {
        String keyword = txtCari.getText().trim().toLowerCase();
        if (keyword.isEmpty()) { muatArtikel(); return; }

        dataSaatIni = artikelService.getArtikelApproved().stream()
            .filter(a -> a.getJudul().toLowerCase().contains(keyword))
            .collect(java.util.stream.Collectors.toList());
        refreshTabel();
    }

    private void refreshTabel() {
        modelTabel.setRowCount(0);
        for (Artikel a : dataSaatIni) {
            modelTabel.addRow(new Object[]{a.getJudul(), "Mentor #" + a.getMentorId(), a.getStatus()});
        }
    }

    private void tampilkanDetail() {
        int row = tabelArtikel.getSelectedRow();
        if (row < 0 || row >= dataSaatIni.size()) return;

        Artikel a = dataSaatIni.get(row);
        lblJudulVal.setText(a.getJudul());
        lblPenulisVal.setText("Mentor #" + a.getMentorId());
        lblStatusVal.setText(a.getStatus());

        a.display();
    }

    private void tambahArtikel() {
        if (!(currentUser instanceof Mentor)) {
            JOptionPane.showMessageDialog(this, "Hanya Mentor yang bisa menambah artikel!");
            return;
        }

        JTextField fJudul = new JTextField();
        JTextArea  fIsi   = new JTextArea(5, 20);

        JPanel panel = new JPanel(new BorderLayout(8, 8));
        JPanel atas = new JPanel(new BorderLayout(8, 8));
        atas.add(new JLabel("Judul:"), BorderLayout.WEST);
        atas.add(fJudul, BorderLayout.CENTER);
        panel.add(atas, BorderLayout.NORTH);
        panel.add(new JScrollPane(fIsi), BorderLayout.CENTER);

        int hasil = JOptionPane.showConfirmDialog(this, panel,
            "Tambah Artikel Baru", JOptionPane.OK_CANCEL_OPTION);

        if (hasil == JOptionPane.OK_OPTION) {
            Artikel artikelBaru = new Artikel(fJudul.getText(), fIsi.getText(), currentUser.getId());
            boolean ok = artikelService.submitArtikel(artikelBaru);
            JOptionPane.showMessageDialog(this,
                ok ? "Artikel disubmit! Menunggu APPROVED dari Admin." : "Gagal submit artikel.");
            if (ok) muatArtikel();
        }
    }

    private void editArtikel() {
        int row = tabelArtikel.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Pilih artikel yang akan diedit!");
            return;
        }
        Artikel a = dataSaatIni.get(row);
        Artikel aLengkap = (Artikel) artikelService.findById(a.getId());
        if (aLengkap == null) aLengkap = a;

        JTextArea fIsi = new JTextArea(aLengkap.getIsi(), 8, 30);
        fIsi.setLineWrap(true);

        int hasil = JOptionPane.showConfirmDialog(this, new JScrollPane(fIsi),
            "Edit Isi Artikel: " + aLengkap.getJudul(), JOptionPane.OK_CANCEL_OPTION);

        if (hasil == JOptionPane.OK_OPTION) {
            aLengkap.setIsi(fIsi.getText());
            JOptionPane.showMessageDialog(this, "Isi artikel berhasil diperbarui!");
            muatArtikel();
        }
    }

    private void hapusArtikel() {
        int row = tabelArtikel.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Pilih artikel yang akan dihapus!");
            return;
        }
        Artikel a = dataSaatIni.get(row);
        int konfirmasi = JOptionPane.showConfirmDialog(this,
            "Hapus artikel '" + a.getJudul() + "'?",
            "Konfirmasi Hapus", JOptionPane.YES_NO_OPTION);

        if (konfirmasi == JOptionPane.YES_OPTION) {
            boolean ok = artikelService.delete(a.getId());
            JOptionPane.showMessageDialog(this,
                ok ? "Artikel berhasil dihapus!" : "Gagal menghapus artikel.");
            if (ok) muatArtikel();
        }
    }
}
