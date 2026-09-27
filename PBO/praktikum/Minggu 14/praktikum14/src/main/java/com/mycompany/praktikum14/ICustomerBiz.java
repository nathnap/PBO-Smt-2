/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.praktikum14;
import java.util.HashMap;
/**
 *
 * @author 7320
 */

public interface ICustomerBiz {
    void initializeCustomer();
    void printAllCustomer();
    void insertCustomer(String name, int age, HashMap<String, String> phone);
    void changeAge(int number, int age);
    void changePhone(int number, HashMap<String, String> phone);
    void deleteCustomer(int number);
    int getCustomerNumber();
}

