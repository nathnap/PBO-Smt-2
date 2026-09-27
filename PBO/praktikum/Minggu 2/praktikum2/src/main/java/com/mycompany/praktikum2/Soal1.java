/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.praktikum2;

/**
 *
 * @author 7320
 */
public class Soal1 {

    public static void main(String[] args) {
        int angka = 64;

        Soal1 i = new Soal1();
        i.chek(angka);

    }

    public void chek(int angka) {
        if (angka % 2 == 0) {
            System.out.println("Apakah itu bilangan genap? : Benar");
        } else {
            System.out.println("Apakah itu bilangan genap? : Salah");
        }

        if (angka % 3 == 0) {
            System.out.println("Apakah itu kelipatan 3? : Benar");
        } else {
            System.out.println("Apakah itu kelipatan 3? : Salah");
        }

        if (angka % 2 == 0 && angka % 7 == 0) {
            System.out.println("Apakah itu kelipatan 2 dan kelipatan 7? : Benar");
        } else {
            System.out.println("Apakah itu kelipatan 2 dan kelipatan 7? : Salah");
        }

        if (angka % 2 == 0 || angka % 7 == 0) {
            System.out.println("Apakah itu kelipatan 2 atau kelipatan 7? : Benar");
        } else {
            System.out.println("Apakah itu kelipatan 2 atau kelipatan 7? : Salah");
        }
    }
}
