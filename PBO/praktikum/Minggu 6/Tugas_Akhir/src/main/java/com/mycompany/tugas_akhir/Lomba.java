/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tugas_akhir;

/**
 *
 * @author 7320
 */
class Lomba {

    protected String namaKelompok;
    protected String kategori;
    protected double nilai;

    public Lomba(String namaKelompok, String kategori) {
        this.namaKelompok = namaKelompok;
        this.kategori = kategori;
    }

    public String getNamaKelompok() {
        return namaKelompok;
    }

    public String getKategori() {
        return kategori;
    }

    public double getNilai() {
        return nilai;
    }
}
