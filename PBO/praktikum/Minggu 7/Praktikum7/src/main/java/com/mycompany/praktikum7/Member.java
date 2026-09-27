/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.praktikum7;

/**
 *
 * @author 7320
 */
public class Member {

    private double saldo;
    private String id;
    private String nama;
    private String telepon;

    public Member(String id, String nama, String telepon, double saldo) {
        this.id = id;
        this.nama = nama;
        this.telepon = telepon;
        this.saldo = saldo;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getTelepon() {
        return telepon;
    }

    public void setTelepon(String telepon) {
        this.telepon = telepon;
    }

    public String getId() {
        return id;
    }

    public Double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public String toString() {
        return "Member \n id: " + id
                + "\n nama: " + nama
                + "\n telepon: " + telepon
                + "\n saldo: " + saldo;
    }
}
