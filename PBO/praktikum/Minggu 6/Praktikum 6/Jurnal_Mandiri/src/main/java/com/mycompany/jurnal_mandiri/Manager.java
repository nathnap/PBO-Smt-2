/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.jurnal_mandiri;

/**
 *
 * @author 7320
 */
class Manager extends Pegawai{
    private int tunjanganKesehatan = 1000000;
    
    public Manager(String nama, int hariKerja) {
        super(nama, hariKerja);
    }

    public double hitungGaji() {
        return (2 * getGajiPokok()) + (getHariKerja() * getTunjanganTransport()) + tunjanganKesehatan;
    }
}
