/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.praktikum10;

/**
 *
 * @author 7320
 */
class Food extends PassengerGoods {

    private double weight;

    public Food(String name, int quantity, double price, double weight) {
        super(name, quantity, price);
        this.weight = weight;
    }

    protected double calculatePrice() {
        return quantity * getPrice() * weight;
    }

    public void displayDetail() {
        double totalHarga = calculatePrice();

        System.out.println("Makanan " + getName()
                + " berat " + weight + "-gram berjumlah " + quantity
                + ", dengan total harga Rp " + totalHarga);
    }
}
