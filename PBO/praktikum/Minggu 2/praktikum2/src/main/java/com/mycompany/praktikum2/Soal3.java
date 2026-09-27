/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.praktikum2;

import java.util.Iterator;

/**
 *
 * @author 7320
 */
public class Soal3 {

    public static void main(String[] args) {
        Soal3 n = new Soal3();

        System.out.println("Celcius Fahrenheit");
        for (int i = 0; i <= 98; i += 2) {
            System.out.println(i + " " + n.konversiCelciusToFahrenheit(i));
        }

        System.out.println("Fahrenheit Celcius");
        for (int i = 20; i <= 265; i+=5) {
                System.out.println(i + " " + n.konversiFahrenheitToCelcius(i));
            }
    }
    
    public double konversiCelciusToFahrenheit(double angka) {
        return (9.0 / 5) * angka + 32;
    }

    public double konversiFahrenheitToCelcius(double angka) {
        return(5.0 / 9) * (angka - 32);
    }
}
