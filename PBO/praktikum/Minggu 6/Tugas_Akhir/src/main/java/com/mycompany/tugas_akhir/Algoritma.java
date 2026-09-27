/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tugas_akhir;

/**
 *
 * @author 7320
 */
class Algoritma extends Lomba {

    double ketepatanHasil;
    double waktuEksekusi;
    double pemanfaatanResource;

    public Algoritma(String namaKelompok) {
        super(namaKelompok, "Algoritma");
    }

    public void inputNilai(double ketepatanHasil, 
            double waktuEksekusi, 
            double pemanfaatanResource) {
        
        this.ketepatanHasil = ketepatanHasil;
        this.waktuEksekusi = waktuEksekusi;
        this.pemanfaatanResource = pemanfaatanResource;

        nilai = (ketepatanHasil + waktuEksekusi + pemanfaatanResource) / 3;
    }
}
