/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.praktikum2;

import java.awt.BorderLayout;

/**
 *
 * @author 7320
 */
public class Soal4 {

    public static void main(String[] args) {
        Soal4 n = new Soal4();

        int[] numbers = {10, 21, 33, 42, 51, 64, 79, 80};
        int hasil = 0;

        for (int i = 0; i < numbers.length; i++) {

            if (n.cekGanjil(numbers[i])) {
                System.out.print(numbers[i] + " | ");
                hasil += numbers[i];
            }
        }
        System.out.println(" ");
        System.out.println("Jumlah bilangan ganjil adalah " + hasil);
    }

    public boolean cekGanjil(int angka) {
        if (angka % 2 != 0) {
            return true;
        }
        return false;
    }
}
