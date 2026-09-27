/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tugas_akhir;

/**
 *
 * @author 7320
 */
class UI_UX extends Lomba {

    double latarBelakang;
    double skenario;
    double desain;
    double konsistensi;

    public UI_UX(String namaKelompok){
        super(namaKelompok,"UIUX");
    }

    public void inputNilai(double latar, double skenario, double desain, double konsistensi){
        latarBelakang = latar;
        this.skenario = skenario;
        this.desain = desain;
        this.konsistensi = konsistensi;

        nilai = (latar + skenario + desain + konsistensi) / 4;
    }
}
