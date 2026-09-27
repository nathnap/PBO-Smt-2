/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.jurnal_terbimbing;

/**
 *
 * @author 7320
 */
class Kubus extends SegiEmpat {

    private double tinggi;

    public Kubus() {
        super();
        tinggi = 0;
    }

    public void setTinggi(double t) {
        tinggi = t;
    }

    public double Volume() {
        return (panjang * lebar * tinggi);
    }
}
