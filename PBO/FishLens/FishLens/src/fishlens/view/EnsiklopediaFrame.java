package fishlens.view;

import fishlens.model.Ikan;
import fishlens.service.EnsiklopediaService;
import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * EnsiklopediaFrame — View pendukung untuk menu Ensiklopedia.
 * Dipanggil oleh MainFrame.bukaEnsiklopedia().
 *
 * Method yang dipakai dari EnsiklopediaService (signature sesuai class diagram):
 *   + getAllIkan() : List<Ikan>
 *   + cariIkan(keyword : String) : List<Ikan>
 *   + save(obj : Object) : boolean       {dari Manageable}
 *   + delete(id : int) : boolean         {dari Manageable}
 *   + findById(id : int) : Object        {dari Manageable}
 */
public class EnsiklopediaFrame extends JFrame {

    private EnsiklopediaService ensiklopediaService;

    private JTextField txtCari;
    private JButton     btnCari;
    private JList<String> listIkan;
    private DefaultListModel<String> modelList;
    private JButton btnTambah, btnEdit, btnHapus;

    private JLabel lblNamaVal, lblHabitatVal, lblPakanVal, lblDeskripsiVal;

    private List<Ikan> dataSaatIni;

    public EnsiklopediaFrame() {
        super("Ensiklopedia Ikan");
        this.ensiklopediaService = new EnsiklopediaService();
        buildUI();
    }

    private void buildUI() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(485, 700);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(null);

        // ---- Cari Ikan ----
        JLabel lblCari = new JLabel("Cari Ikan");
        lblCari.setFont(new Font("Tahoma", Font.BOLD, 13));
        lblCari.setBounds(20, 15, 150, 25);
        add(lblCari);

        txtCari = new JTextField();
        txtCari.setBounds(20, 45, 300, 30);
        add(txtCari);

        btnCari = new JButton("Cari");
        btnCari.setBounds(335, 45, 100, 30);
        add(btnCari);

        // ---- Daftar Ikan ----
        JLabel lblDaftar = new JLabel("Daftar Ikan");
        lblDaftar.setFont(new Font("Tahoma", Font.BOLD, 13));
        lblDaftar.setBounds(20, 95, 150, 25);
        add(lblDaftar);

        modelList = new DefaultListModel<>();
        listIkan  = new JList<>(modelList);
        JScrollPane scrollList = new JScrollPane(listIkan);
        scrollList.setBounds(20, 125, 300, 220);
        add(scrollList);

        btnTambah = new JButton("Tambah");
        btnTambah.setBounds(335, 125, 100, 35);
        add(btnTambah);

        btnEdit = new JButton("Edit");
        btnEdit.setBounds(335, 170, 100, 35);
        add(btnEdit);

        btnHapus = new JButton("Hapus");
        btnHapus.setBounds(335, 215, 100, 35);
        add(btnHapus);

        // ---- Detail Ikan ----
        JLabel lblDetail = new JLabel("Detail Ikan");
        lblDetail.setFont(new Font("Tahoma", Font.BOLD, 13));
        lblDetail.setBounds(20, 365, 150, 25);
        add(lblDetail);

        lblNamaVal      = buatBarisDetail("Nama",     400);
        lblHabitatVal   = buatBarisDetail("Habitat",  430);
        lblPakanVal     = buatBarisDetail("Pakan",    460);
        lblDeskripsiVal = buatBarisDetail("Deskripsi", 490);

        // ---- Aksi ----
        btnCari.addActionListener(e -> cariIkan());
        btnTambah.addActionListener(e -> tambahIkan());
        btnEdit.addActionListener(e -> editIkan());
        btnHapus.addActionListener(e -> hapusIkan());
        listIkan.addListSelectionListener(e -> tampilkanDetail());

        muatSemuaIkan();
    }

    private JLabel buatBarisDetail(String label, int y) {
        JLabel lblLabel = new JLabel(label);
        lblLabel.setBounds(20, y, 90, 25);
        add(lblLabel);

        JLabel lblTitikDua = new JLabel(":");
        lblTitikDua.setBounds(115, y, 10, 25);
        add(lblTitikDua);

        JLabel lblValue = new JLabel("-");
        lblValue.setBounds(130, y, 320, 25);
        add(lblValue);
        return lblValue;
    }

    public void tampilkanMenu() {
        setVisible(true);
    }

    private void muatSemuaIkan() {
        dataSaatIni = ensiklopediaService.getAllIkan();
        refreshList();
    }

    private void cariIkan() {
        String keyword = txtCari.getText().trim();
        dataSaatIni = ensiklopediaService.cariIkan(keyword);
        refreshList();
    }

    private void refreshList() {
        modelList.clear();
        for (Ikan ikan : dataSaatIni) {
            modelList.addElement(ikan.getNamaSpesies());
        }
    }

    private void tampilkanDetail() {
        int idx = listIkan.getSelectedIndex();
        if (idx < 0 || idx >= dataSaatIni.size()) return;

        Ikan ikan = dataSaatIni.get(idx);
        lblNamaVal.setText(ikan.getNamaSpesies());
        lblHabitatVal.setText(ikan.getHabitat());
        lblPakanVal.setText(ikan.getPakan());
        lblDeskripsiVal.setText(ikan.getPenyakitUmum());

        // display() dari Displayable interface — cetak ke console
        ikan.display();
    }

    private void tambahIkan() {
        JTextField fNama = new JTextField();
        JTextField fHabitat = new JTextField();
        JTextField fPakan = new JTextField();
        JTextField fPenyakit = new JTextField();

        JPanel panel = new JPanel(new GridLayout(4, 2, 8, 8));
        panel.add(new JLabel("Nama Spesies:")); panel.add(fNama);
        panel.add(new JLabel("Habitat:"));      panel.add(fHabitat);
        panel.add(new JLabel("Pakan:"));        panel.add(fPakan);
        panel.add(new JLabel("Penyakit Umum:")); panel.add(fPenyakit);

        int hasil = JOptionPane.showConfirmDialog(this, panel,
            "Tambah Ikan Baru", JOptionPane.OK_CANCEL_OPTION);

        if (hasil == JOptionPane.OK_OPTION) {
            Ikan ikanBaru = new Ikan(fNama.getText(), fHabitat.getText(),
                fPakan.getText(), fPenyakit.getText());
            boolean ok = ensiklopediaService.save(ikanBaru);
            JOptionPane.showMessageDialog(this,
                ok ? "Ikan berhasil ditambahkan!" : "Gagal menambahkan ikan.");
            if (ok) muatSemuaIkan();
        }
    }

    private void editIkan() {
        int idx = listIkan.getSelectedIndex();
        if (idx < 0) {
            JOptionPane.showMessageDialog(this, "Pilih ikan yang akan diedit!");
            return;
        }
        Ikan ikan = dataSaatIni.get(idx);
        Ikan ikanLengkap = (Ikan) ensiklopediaService.findById(ikan.getId());
        if (ikanLengkap == null) ikanLengkap = ikan;

        JTextField fHabitat  = new JTextField(ikanLengkap.getHabitat());
        JTextField fPakan    = new JTextField(ikanLengkap.getPakan());
        JTextField fPenyakit = new JTextField(ikanLengkap.getPenyakitUmum());

        JPanel panel = new JPanel(new GridLayout(3, 2, 8, 8));
        panel.add(new JLabel("Habitat:"));       panel.add(fHabitat);
        panel.add(new JLabel("Pakan:"));         panel.add(fPakan);
        panel.add(new JLabel("Penyakit Umum:")); panel.add(fPenyakit);

        int hasil = JOptionPane.showConfirmDialog(this, panel,
            "Edit Ikan: " + ikanLengkap.getNamaSpesies(), JOptionPane.OK_CANCEL_OPTION);

        if (hasil == JOptionPane.OK_OPTION) {
            ikanLengkap.setHabitat(fHabitat.getText());
            ikanLengkap.setPakan(fPakan.getText());
            ikanLengkap.setPenyakitUmum(fPenyakit.getText());
            JOptionPane.showMessageDialog(this, "Data ikan berhasil diperbarui!");
            muatSemuaIkan();
        }
    }

    private void hapusIkan() {
        int idx = listIkan.getSelectedIndex();
        if (idx < 0) {
            JOptionPane.showMessageDialog(this, "Pilih ikan yang akan dihapus!");
            return;
        }
        Ikan ikan = dataSaatIni.get(idx);
        int konfirmasi = JOptionPane.showConfirmDialog(this,
            "Hapus ikan '" + ikan.getNamaSpesies() + "'?",
            "Konfirmasi Hapus", JOptionPane.YES_NO_OPTION);

        if (konfirmasi == JOptionPane.YES_OPTION) {
            boolean ok = ensiklopediaService.delete(ikan.getId());
            JOptionPane.showMessageDialog(this,
                ok ? "Ikan berhasil dihapus!" : "Gagal menghapus ikan.");
            if (ok) muatSemuaIkan();
        }
    }
}
