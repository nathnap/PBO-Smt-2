/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.jurnal_terbimbing;

/**
 *
 * @author 7320
 */
public class SegiEmpat {

    protected double panjang, lebar;

    public SegiEmpat() {
        panjang = 0;
        lebar = 0;
    }

    public void setPanjang(double p) {
        panjang = p;
    }

    public void setLebar(double l) {
        lebar = l;
    }

    public double Luas() {
        return (panjang * lebar);
    }
    
    public double Keliling() {
        return (2 * (panjang + lebar));
    }
}
