/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.jurnal_mandiri;

/**
 *
 * @author 7320
 */
public class Pegawai {

    private int hariKerja;
    private int gajiPokok = 1000000;
    private int tunjanganTransport = 50000;
    private String nama;

    public Pegawai(String nama, int hariKerja) {
        this.nama = nama;
        this.hariKerja = hariKerja;
    }

    public String getNama() {
        return nama;
    }
    
    public int getGajiPokok() {
        return gajiPokok;
    }
    
    public int getHariKerja() {
        return hariKerja;
    }
    
    public int getTunjanganTransport() {
        return tunjanganTransport;
    }
    
    public double hitungGaji(){
        return 0;
    }
}
