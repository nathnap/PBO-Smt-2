/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.praktikum14;
import java.util.HashMap;
import java.util.ArrayList;

/**
 *
 * @author 7320
 */
public class CustomerBiz implements ICustomerBiz {
    
    private ArrayList<Customer> customers;
    
    public void initializeCustomer(){
        customers = new ArrayList<Customer>();
        
        HashMap<String, String> phone1 = new HashMap<String, String>();
        phone1.put("HP", "010-5678-1234");
        phone1.put("OFFICE", "010-2132-9876");
        phone1.put("Perwakilan", "HP");
        customers.add(new Customer("Lee", 28, phone1));
        
        HashMap<String, String> phone2 = new HashMap<String, String>();
        phone2.put("HP", "010-4567-9876");
        phone2.put("OFFICE", "010-1234-5678");
        phone2.put("Perwakilan", "HP");
        customers.add(new Customer("Park", 31, phone2));
        
        HashMap<String, String> phone3 = new HashMap<String, String>();
        phone3.put("HP", "010-8888-9999");
        phone3.put("OFFICE", "010-1111-2222");
        phone3.put("Perwakilan", "OFFICE");
        customers.add(new Customer("Choi", 25, phone3));
    }
    
    public void printAllCustomer(){
        for(int i = 0; i < customers.size(); i++){
            Customer c = customers.get(i);
            String wakil = c.getPhone().get("Perwakilan");
            String wakilNo = c.getPhone().get(wakil);
            System.out.println((i + 1) + ". Nama : " + c.getName()
                                       + "\t Usia : " + c.getAge()
                                       + "\t Np Telepon : [" + wakil + "]" + wakilNo);
        }
    }
    
    public void insertCustomer(String name, int age, HashMap<String, String> phone){
        Customer c = new Customer(name, age, phone);
        customers.add(c);
    }
    
    public void changeAge(int number, int age){
        customers.get(number - 1).setAge(age);
    }
    
    public void changePhone(int number, HashMap<String, String> phone) {
        customers.get(number - 1).setPhone(phone);
    }
    
    public void deleteCustomer(int number) {
        customers.remove(number - 1);
    }
    
    public int getCustomweNumber(){
        return customers.size();
    }

    @Override
    public int getCustomerNumber() {
        return customers.size();
    }
}
