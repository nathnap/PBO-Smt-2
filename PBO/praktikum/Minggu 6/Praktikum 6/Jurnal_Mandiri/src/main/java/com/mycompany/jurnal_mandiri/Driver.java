/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.mycompany.jurnal_mandiri;

/**
 *
 * @author 7320
 */
public class Driver {

    public static void main(String[] args) {
        Pegawai[] pegawai = {
            new Manager("Tono", 23),
            new Manager("Joko", 23),
            new Direktur("Budi", 24),
            new Staf("Asep", 20),
            new Staf("Tini", 20)
        };

        for (int i = 0; i < pegawai.length; i++) {
            System.out.println("Nama : " + pegawai[i].getNama());
            System.out.println("Hari Kerja : " + pegawai[i].getHariKerja());
            System.out.println("Gaji : " + pegawai[i].hitungGaji());
            System.out.println();
        }
    }
}
