/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.praktikum7;

/**
 *
 * @author 7320
 */
class Driver extends Member {

    private String platNo;
    private String jenisKendaraan;

    public Driver(String id, String nama, String telepon, 
            String platNo, String jenisKendaraan, double saldo) {

        super(id, nama, telepon, saldo);
        this.platNo = platNo;
        this.jenisKendaraan = jenisKendaraan;
    }

    public String getPlatNo() {
        return platNo;
    }

    public String getJenisKendaraan() {
        return jenisKendaraan;
    }

    public String toString() {
        return super.toString() + "\n Driver "
                + "\n Nomor Plat: " + platNo
                + "\n Jenis Kendaraan: " + jenisKendaraan;
    }
}
