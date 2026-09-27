/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.praktikum10;

/**
 *
 * @author 7320
 */
public abstract class PassengerGoods {

    private String name;
    protected int quantity;
    private double price;

    public PassengerGoods(String name, int quantity, double price) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;

    }

    protected String getName() {
        return name;
    }

    protected double getPrice() {
        return price;
    }

    protected abstract double calculatePrice();

    public abstract void displayDetail();

}
