/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.praktikum2;

/**
 *
 * @author 7320
 */
public class Soal2 {

    public static void main(String[] args) {
        Soal2 n = new Soal2();

        for (double i = 1.00; i <= 10; i++) {
            System.out.println(i + " " + n.konversiSentimeter(i));
        }
    }

    public double konversiSentimeter(double angka) {
        return (angka * 254) / 100;
    }
}
