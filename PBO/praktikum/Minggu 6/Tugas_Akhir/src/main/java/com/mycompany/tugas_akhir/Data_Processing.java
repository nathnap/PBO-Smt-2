/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tugas_akhir;

/**
 *
 * @author 7320
 */
class Data_Processing extends Lomba {

    double ketepatanHasil;
    double waktuEksekusi;
    double pemanfaatanResource;
    
    public Data_Processing(String namaKelompok) {
        super(namaKelompok, "Data Processing");
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
