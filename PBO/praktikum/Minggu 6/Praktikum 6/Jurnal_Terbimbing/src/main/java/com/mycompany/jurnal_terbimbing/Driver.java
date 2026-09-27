/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.mycompany.jurnal_terbimbing;

/**
 *
 * @author 7320
 */
public class Driver {

    public static void main(String[] args) {

        SegiEmpat kotak = new SegiEmpat();
        Balok kayu = new Balok();
        
        kotak.setPanjang(21);
        kotak.setLebar(4);
        System.out.println("Luas Kotak = " + kotak.Luas());
        System.out.println("Keliling Kotak = " + kotak.Keliling());
        
        kayu.setPanjang(17);
        kayu.setLebar(8);
        kayu.setTinggi(45);
        System.out.println("\nVolume Balok = " + kayu.Volume());
        
        int N = 5;
        SegiEmpat[] arrSegiEmpat = new SegiEmpat[N];
        Balok[] arrBalok = new Balok[N];
        
        for (int i = 0; i < N; i++) {
            arrSegiEmpat[i] = new SegiEmpat();
            arrSegiEmpat[i].setLebar(2);
            arrSegiEmpat[i].setPanjang(3);
        }
        
         for (int i = 0; i < N; i++) {
            arrBalok[i] = new Balok();
            arrBalok[i].setLebar(2);
            arrBalok[i].setPanjang(3);
            arrBalok[i].setTinggi(5);
        }
            
         arrSegiEmpat[2].setLebar(3.0);
         System.out.println("Volume Balok ke-1 : " + arrBalok[0].Volume());
         System.out.println("Luas SegiEmpat ke-1 : " + arrSegiEmpat[0].Luas());
         System.out.println("Keliling SegiEmpat ke-1 : " + arrSegiEmpat[0].Keliling());
    }
}
