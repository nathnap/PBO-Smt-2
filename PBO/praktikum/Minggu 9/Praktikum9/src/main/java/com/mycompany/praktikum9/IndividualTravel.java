/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.praktikum9;

/**
 *
 * @author 7320
 */
class IndividualTravel extends Travel {

    private int maxPeople;

    public IndividualTravel(String travelCode, String cityName, String flight,
            int maxPeople) {

        super(travelCode, cityName, flight, "Individual Travel");
        this.maxPeople = maxPeople;
    }

    public void setReserved(int reserved) {
        if (reserved > maxPeople) {
            System.out.println("ERROR! \t Tidak dapat memesan lebih dari jumlah maksimum orang.");
        } else if (reserved <= 0) {
            System.out.println("ERROR! \t Harap pesan setidaknya 1 orang.");
        } else {
            super.setReserved(reserved);
            System.out.println("Pesanan untuk " + reserved + " orang berhasil dibuat.");
        }
    }

    public String toString() {
        return super.toString()
                + "N/A\t"
                + maxPeople + " orang\t"
                + getReserved() + " orang";
    }

}
