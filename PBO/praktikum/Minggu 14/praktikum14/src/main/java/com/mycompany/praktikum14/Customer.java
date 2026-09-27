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
public class Customer {
    private String name;
    private int age;
    private HashMap<String, String> phone;
    
    public Customer(String name, int age, HashMap<String, String> phone) {
    this.name = name;
    this.age = age;
    this.phone = phone;
}
    
    public String getName(){
        return name;
    }
    
    public int getAge(){
        return age;
    }
    
    public void setAge(int age) {
        this.age = age;
    }
    
    public HashMap<String, String> getPhone(){
        return phone;
    }
    
    public void setPhone(HashMap<String, String> phone){
        this.phone = phone;
    }
}
