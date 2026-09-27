/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.mycompany.praktikum7;

/**
 *
 * @author 7320
 */
public class Gojek {
    
    Member[] daftar = new Member[10];
    int jumlah = 0;
    
    public static void main(String[] args) {
        new Gojek().runThis();
    }

    
    void addDriver(String id, String nama, String telepon, 
                    String platNo, String jenisKendaraan, double saldo){
    
    Driver d = new Driver(id, nama, telepon, 
                    platNo, jenisKendaraan, saldo);
    
    daftar[jumlah++] = d;
    System.out.println("\n Penambahan Driver \n" + d);
    }
    
    void addCustomer(String id, String nama, String telepon, double saldo) {              
    
    Customer c = new Customer(id, nama, telepon, saldo);
    daftar[jumlah++] = c;
    
    System.out.println("\n Penambahan Customer \n" + c);
    }
    
    int cekId(String id) {
        for (int i = 0; i < jumlah; i++) {
            if (daftar[i].getId().equals(id)) {
                return i;
            }
        }
        return -1;
    }
    
    void topUpSaldo(String id, double saldo) {
        int idx = cekId(id);
        
        if(idx == -1) {
            System.out.println("ID: " + id + ". tidak ditemukan");
            return;
        }
        
        Member member = daftar[idx];
        
        if (member instanceof Driver) {
            System.out.println("\n Driver");
        } else {
            System.out.println("\n Customer");
        }
        System.out.println("Data Awal");
        System.out.println(member);
        
        member.setSaldo(member.getSaldo() + saldo);
        
        System.out.println("Data Akhir");
        System.out.println(member);
    }
    
    void viewDriver(String id) {
        int idx = cekId(id);
        
        if(idx == -1) {
            System.out.println("ID: " + id + ". tidak ditemukan");
            return;
        }
        
        if (daftar[idx] instanceof Driver) {
            System.out.println(daftar[idx]);
        } else {
            System.out.println("ID: " + id + ". Bukan Driver");
            return;
        }
    }
    
    void viewCustomer(String id) {
        int idx = cekId(id);
        
        if(idx == -1) {
            System.out.println("ID: " + id + ". tidak ditemukan");
            return;
        }
        
        if (daftar[idx] instanceof Customer) {
            System.out.println(daftar[idx]);
        } else {
            System.out.println("ID: " + id + ". Bukan Customer");
            return;
        }
    }
        
    void transaksi(String idDriver, String idCustomer, double tarif) {
        
        int d = cekId(idDriver);
        int c = cekId(idCustomer);
        
        if (d == -1 || c == -1) {
            System.out.println("Terdapat Kesalahan ID");
            return;
        }
        
        Member driver = daftar[d];
        Member customer = daftar[c];
        
        System.out.println("\n Besaran Transaksi: " + tarif);
        
        System.out.println("Data Lama");
        System.out.println(driver);
        System.out.println(customer);
        
        driver.setSaldo(driver.getSaldo() + tarif);
        customer.setSaldo(customer.getSaldo() - tarif);
        
        System.out.println("\n Data Baru");
        System.out.println(driver);
        System.out.println(customer);
        
        System.out.println("Transaksi Selesai.");
    }        
        
    void top5Driver() {
        Driver[] temp = new Driver[jumlah];
        int number = 0;
        
        for(int i = 0; i < jumlah; i++) {
            if(daftar[i] instanceof Driver) {
                temp[number++] = (Driver) daftar [i];
            }
        }
        
        for (int i = 0; i < number - 1; i++) {
            for (int j = i + 1; j < number; j++){
                if(temp[i].getSaldo() < temp[j].getSaldo()) {
                    Driver hehe = temp[i];
                    temp[i] = temp[j];
                    temp[j] = hehe;
                }
            }
        }
        
        System.out.println("\n TOP 5 Driver Saldo Terbanyak");
        
        for(int i = 0; i < number && i < 5; i++){
            System.out.println(temp[i]);
        }   
    }
    
        void top5Customer() {
        Customer[] temp = new Customer[jumlah];
        int number = 0;
        
        for(int i = 0; i < jumlah; i++) {
            if(daftar[i] instanceof Customer) {
                temp[number++] = (Customer) daftar [i];
            }
        }
        
        for (int i = 0; i < number - 1; i++) {
            for (int j = i + 1; j < number; j++){
                if(temp[i].getSaldo() < temp[j].getSaldo()) {
                    Customer hehe = temp[i];
                    temp[i] = temp[j];
                    temp[j] = hehe;
                }
            }
        }
        
        System.out.println("\n TOP 5 Driver Saldo Terbanyak");
        
        for(int i = 0; i < number && i < 5; i++){
            System.out.println(temp[i]);
        }   
    }
    
   
    void runThis() {
        addDriver("1", "Becky", "0811", "D 1 A", "Motor", 100000);
        addDriver("2", "Adrian", "0812", "B 15 A", "Motor", 50000);
        addDriver("3", "Begra", "0813", "J 45A LHO", "Motor", 0);

        addCustomer("4", "Eva", "0814", 100000);
        addCustomer("5", "Celia", "0815", 0);
        addCustomer("6", "Latjuba", "0816", 200000);
        addCustomer("7", "Lesmana", "0817", 0);

        topUpSaldo("1", 150000);
        topUpSaldo("3", 50000);
        topUpSaldo("4", 250000);
        topUpSaldo("5", 450000);
        topUpSaldo("6", 150000);
        topUpSaldo("9", 150000);

        viewDriver("1");
        viewDriver("3");
        viewDriver("4");

        viewCustomer("1");
        viewCustomer("4");
        viewCustomer("5");
        viewCustomer("8");

        transaksi("1", "4", 50000);
        transaksi("3", "5", 50000);
        transaksi("4", "5", 50000);
        transaksi("1", "2", 50000);
        
        top5Driver();
        top5Customer();
    }
    
}


