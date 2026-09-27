/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.mavenproject1;

/**
 *
 * @author 7320
 */
import java.util.Scanner;

public class manajemenKendaraan {

    public static void main(String[] args) {

        double waktuKendaraan1, waktuKendaraan2, waktuKendaraan3;

        Vehicle kendaraan1 = new Vehicle("B1234ABC", 100);
        Vehicle kendaraan2 = new Vehicle("T5678XYZ", 80, 6, "Truck");
        Vehicle kendaraan3 = new Vehicle("J0KO1WI", 90, 4, "Car");
        
        kendaraan1.informasiKendaraan();
        waktuKendaraan1 = kendaraan1.calculateTravelTime(500);
        System.out.println("Travel time for 500 km: " + waktuKendaraan1 + " hours");

        System.out.println();
        waktuKendaraan2 = kendaraan2.calculateTravelTime(800);
        kendaraan2.informasiKendaraan();
        System.out.println("Travel time for 800 km: " + kendaraan2.calculateTravelTime(800) + " hours");

        System.out.println("Travel time for 800 km with custom speed 60 km/h: " + kendaraan2.calculateTravelTime(800, 60) + " hours");
        
        System.out.println();
        

        kendaraan3.informasiKendaraan();
        waktuKendaraan3 = kendaraan3.calculateTravelTime(750);
        System.out.println("Travel time for 750 km: " + kendaraan3.calculateTravelTime(750) + " hours");

        System.out.println("Travel time for 750 km with custom speed 100 km/h: " + kendaraan3.calculateTravelTime(750, 100) + " hours");
        
    }
}
