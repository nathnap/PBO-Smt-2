/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.jurnal_terbimbing;

/**
 *
 * @author 7320
 */
class Balok extends SegiEmpat {

    private double tinggi;

    public Balok() {
        
        super();
        tinggi = 0;
    }

    public void setTinggi(double t) {
        this.tinggi = t;
    }

    public double Volume() {
        return (this.panjang * this.lebar * this.tinggi);
    }
}
    
