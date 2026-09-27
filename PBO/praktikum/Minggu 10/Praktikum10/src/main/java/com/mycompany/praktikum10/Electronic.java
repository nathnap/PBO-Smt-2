/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.praktikum10;

/**
 *
 * @author 7320
 */
class Electronic extends PassengerGoods implements Taxable {

    private String type;

    public Electronic(String name, int quantity, double price, String type) {
        super(name, quantity, price);
        this.type = type;
    }

    protected double calculatePrice() {
        return quantity * getPrice();
    }
    
    public double calculateTax() {
        return (calculatePrice() - 7500000) * TAX_RATE;
    }

    public void displayDetail() {
        double totalHarga = calculatePrice();
        double tax = calculateTax();

        System.out.println("Nama Barang " + getName()
                + " dengan tipe " + type + " berjumlah " + quantity
                + " dengan total harga Rp " + totalHarga + " dengan total pajak Rp " + tax);
    }

    
}
