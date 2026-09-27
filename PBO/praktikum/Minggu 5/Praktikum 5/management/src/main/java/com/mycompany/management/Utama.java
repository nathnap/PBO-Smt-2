/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.management;

/**
 *
 * @author 7320
 */
public class Utama {

    public static void main(String[] args) {

        String[] nameList = {"Skye Heredia", "Lucille Waltz", "Nichol Sutphin", "Vernia Caraway",
            "Rita Rangel", "Waldo Ontiveros", "Milton Grantham", "Loura Swilley",
            "Lola Duropan", "Kandis Mcnary", "Milford Kirts", "Denita Taniguchi",
            "Talia Fenderson", "Truman Daoust", "Alfonso Chaloux", "Fernanda Overby",
            "Cristy Yearby","Daniell Pabst","Bradley Newson","Renda Maffei"};

        Employee[] employeet = new Employee[nameList.length];

        Department teknis = new Department("DT", "Tim Teknis", 100);
        Department keamanan = new Department("DK", "Tim Keamanan", 5);
        Department sdm = new Department("DS", "Tim SDM", 5);

        for (int i = 0; i < nameList.length; i++) {
            String id;

            if (i + i < 10) {
                id = "EMP0" + (i + 1);
            } else {
                id = "EMP0" + (i + 1);
            }

            employeet[i] = new Employee(id, nameList[i]);

            employeet[i].setDepartment(teknis);
        }

        System.out.println("Status Penetapan Awal\n");

        teknis.printEmployeeList();
        keamanan.printEmployeeList();
        sdm.printEmployeeList();

        for (int i = 0; i < employeet.length; i++) {
            String id = employeet[i].getId();

            int num = Integer.parseInt(id.substring(3));

            if (num % 5 == 0) {
                employeet[i].setDepartment(keamanan);
            } else if (num % 2 == 0) {
                employeet[i].setDepartment(sdm);
            } 
        }

        System.out.println("Status Setelah Transfer\n");

        teknis.printEmployeeList();
        keamanan.printEmployeeList();
        sdm.printEmployeeList();
    }
}
