/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.praktikum9;

/**
 *
 * @author 7320
 */
public class Travel {
    private String travelCode;
    private String cityName;
    private String flight;
    private String travelType;
    private int reserved;

    public Travel(String travelCode, String cityName, String flight, String travelType) {
        this.travelCode = travelCode;
        this.cityName = cityName;
        this.flight = flight;
        this.travelType = travelType;
        this.reserved = 0;
    }

    public String getTravelCode() {
        return travelCode;
    }
    
    public String getTravelType() {
        return travelType;
    }

    public int getReserved() {
        return reserved;
    }

    public void setReserved(int reserved) {
        this.reserved += reserved;
    }

    public String toString() {
        return travelCode + "\t"
             + cityName + "\t"
             + flight + "\t"
             + travelType + "\t";
    }
}