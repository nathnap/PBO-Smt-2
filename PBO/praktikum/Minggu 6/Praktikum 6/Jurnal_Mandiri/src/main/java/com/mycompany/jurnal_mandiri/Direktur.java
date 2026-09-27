/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.jurnal_mandiri;

/**
 *
 * @author 7320
 */
class Direktur extends Pegawai{
    private int tunjanganKesehatan = 2000000;
    private int tunjanganBensin = 500000;
    
    public Direktur(String nama, int hariKerja) {
        super(nama, hariKerja);
    }

    public double hitungGaji() {
        return (4 * getGajiPokok()) + (getHariKerja() * getTunjanganTransport()) + tunjanganKesehatan + tunjanganBensin;
    }
}
