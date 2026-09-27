/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.praktikum9;

/**
 *
 * @author 7320
 */
class PackageTravel extends Travel{
    private int minPeople;

    public PackageTravel(String travelCode, String cityName, String flight, 
                            int minPeople) {
        super(travelCode, cityName, flight, "Paket Travel");
        this.minPeople = minPeople; 
    }
    public void setReserved(int reserved) {
        if (reserved < minPeople) {
            System.out.println("ERROR! Minimal " + minPeople + " orang.");
        } else if (reserved <= 0) {
            System.out.println("ERROR! \t Harap pesan setidaknya 1 orang.");
        }   else {
            super.setReserved(reserved);
            System.out.println("Pesanan untuk " + reserved + " orang berhasil dibuat.");
        }
    }

    @Override
    public String toString() {
        return super.toString()
             + minPeople + " orang\t"
             + "N/A\t"
             + getReserved() + " orang";
    }
}
