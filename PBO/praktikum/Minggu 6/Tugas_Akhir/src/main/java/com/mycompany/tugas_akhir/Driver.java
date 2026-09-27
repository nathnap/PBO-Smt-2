package com.mycompany.tugas_akhir;

public class Driver {

    static Lomba[] daftar = new Lomba[10];
    static int jumlah = 0;

    static void RegistrasiPeserta(String namaKelompok, String kategoriLomba) {

        for (int i = 0; i < jumlah; i++) {
            if (daftar[i].getNamaKelompok().equalsIgnoreCase(namaKelompok)) {
                System.out.println("Nama kelompok " + daftar[i].getNamaKelompok() +
                        " sudah digunakan");
                return;
            }
        }

        if (kategoriLomba.equalsIgnoreCase("UIUX")) {
            daftar[jumlah] = new UI_UX(namaKelompok);
        } else if (kategoriLomba.equalsIgnoreCase("Algoritma")) {
            daftar[jumlah] = new Algoritma(namaKelompok);
        } else if (kategoriLomba.equalsIgnoreCase("Data Processing")) {
            daftar[jumlah] = new Data_Processing(namaKelompok);
        }

        jumlah++;
    }

    static void InputNilaiHasilLomba(String namaKelompok,
            double latar, double skenario, double desain, double konsistensi) {

        for (int i = 0; i < jumlah; i++) {
            if (daftar[i].getNamaKelompok().equalsIgnoreCase(namaKelompok)) {

                ((UI_UX) daftar[i]).inputNilai(latar, skenario, desain, konsistensi);
                break;
            }
        }
    }

    static void InputNilaiHasilLomba(String namaKelompok,
            double ketepatanHasil, double waktuEksekusi, double pemanfaatanResource) {

        for (int i = 0; i < jumlah; i++) {
            if (daftar[i].getNamaKelompok().equalsIgnoreCase(namaKelompok)
                    && daftar[i].getKategori().equalsIgnoreCase("Algoritma")) {

                ((Algoritma) daftar[i]).inputNilai(
                        ketepatanHasil,
                        waktuEksekusi,
                        pemanfaatanResource);
            }
        }
    }

    static void InputNilaiHasilLomba(String namaKelompok,
            double ketepatanHasil, double waktuEksekusi, double pemanfaatanResource, int dummy) {

        for (int i = 0; i < jumlah; i++) {
            if (daftar[i].getNamaKelompok().equalsIgnoreCase(namaKelompok)
                    && daftar[i].getKategori().equalsIgnoreCase("Data Processing")) {

                ((Data_Processing) daftar[i]).inputNilai(
                        ketepatanHasil,
                        waktuEksekusi,
                        pemanfaatanResource);
            }
        }
    }

    static void InfoKelompok(String nama) {

        boolean ditemukan = false;

        for (int i = 0; i < jumlah; i++) {

            if (daftar[i].getNamaKelompok().toLowerCase().contains(nama.toLowerCase())) {

                ditemukan = true;

                System.out.println("Nama Kelompok : " + daftar[i].getNamaKelompok());
                System.out.println("Kategori : " + daftar[i].getKategori());

                if (daftar[i].getNilai() == 0) {
                    System.out.println("Nilai : Belum dimasukkan");
                } else {
                    System.out.println("Nilai Rata : " + daftar[i].getNilai());
                }

                System.out.println();
            }
        }

        if (!ditemukan) {
            System.out.println("Kelompok tidak ditemukan");
        }
    }

    static void ViewAllKelompok() {

        for (int i = 0; i < jumlah; i++) {

            System.out.println("Nama Kelompok : " + daftar[i].getNamaKelompok());
            System.out.println("Kategori : " + daftar[i].getKategori());

            if (daftar[i].getNilai() == 0) {
                System.out.println("Nilai : Belum dimasukkan");
            } else {
                System.out.println("Nilai Rata : " + daftar[i].getNilai());
            }

            System.out.println();
        }
    }

    public static void main(String[] args) {

        System.out.println("=== INFO KELOMPOK ===");
        
        
        RegistrasiPeserta("SixSeven", "UIUX");
        RegistrasiPeserta("ManaBukti", "Algoritma");
        RegistrasiPeserta("Ijazahnya", "Data Processing");

        System.out.println();
        
        InputNilaiHasilLomba("SixSeven", 67.0, 67.0, 67.0, 67.0);
        InputNilaiHasilLomba("ManaBukti", 90.0, 80.0, 85.0);
        InputNilaiHasilLomba("Ijazahnya", 70.0, 75.0, 80.0, 1);
        
        InfoKelompok("19juta");

        System.out.println("\n=== SEMUA KELOMPOK ===\n");
        ViewAllKelompok();
    }
}
