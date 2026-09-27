/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.praktikum10;

/**
 *
 * @author 7320
 */
class Cigarette extends PassengerGoods implements Taxable {

    private int piecesPerPack;

    public Cigarette(String name, int quantity, double price, int piecesPerPack) {
        super(name, quantity, price);
        this.piecesPerPack = piecesPerPack;
    }

    protected double calculatePrice() {
        if (piecesPerPack < 12) {
            return quantity * getPrice();
        } else if (piecesPerPack <= 24) {
            return quantity * getPrice() * 1.5;
        } else {
            return quantity * getPrice() * (piecesPerPack / 10.0);
        }
    }
    
    public double calculateTax() {
        return calculatePrice() * TAX_RATE;
    }

    public void displayDetail() {
        double totalHarga = calculatePrice();
        double tax = calculateTax();

        System.out.println("Rokok " + getName()
                + " berjumlah " + quantity
                + " bungkus @" + piecesPerPack + " batang, harga Rp " + totalHarga
                + " dan total pajak Rp " + tax);
    }

    
}
