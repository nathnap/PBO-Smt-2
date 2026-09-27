/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.mycompany.praktikum10;

/**
 *
 * @author 7320
 */
public class Driver {

    public static void main(String[] args) {
        Electronic electronic = new Electronic(
                "Smartphone", 2, 18000000.0, "Iphone 14"
        );

        Food food = new Food(
                "Lamington", 4, 75.0, 350.0
        );

        Cigarette cigarette = new Cigarette(
                "Dunhill Blue", 30, 60000.0, 20
        );

        electronic.displayDetail();
        food.displayDetail();
        cigarette.displayDetail();

    }
}
