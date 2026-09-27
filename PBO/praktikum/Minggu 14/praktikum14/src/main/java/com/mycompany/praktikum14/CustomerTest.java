/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.praktikum14;
import java.util.HashMap;
/**
 *
 * @author 7320
 */
public class CustomerTest {

    public static void main(String[] args) {
        ICustomerBiz biz = new CustomerBiz();
        biz.initializeCustomer();
        
        while (true) {
            printMenu();
            System.out.print("## Input Menu : ");
            int menu = Integer.parseInt(MissionUtil.getUserInput());
            
            if (menu == 1) {
                System.out.println("--------------------------------------- Informasi Pelanggan ---------------------------------------");
                biz.printAllCustomer();
                System.out.println("---------------------------------------------------------------------------------------------------");
                
            } else if(menu == 2) {
                System.out.println("---------------------------------------");
                System.out.println("Tambahkan Pelanggan Baru");
                System.out.println("Masukkan Informasi Pelanggan Baru");
                System.out.println("---------------------------------------");
                
                System.out.print("> Nama : ");
                String name = MissionUtil.getUserInput();
                System.out.print("> Usia : ");
                int age = Integer.parseInt(MissionUtil.getUserInput());
                
                if(age < 0) {
                    System.out.println("[ERROR] sia Tidak Bisa di Input.");
                    continue;
                }
                
                HashMap<String, String> phone = new HashMap<String, String>();
                
                System.out.print("> No Telepon [HP] : ");
                String hp = MissionUtil.getUserInput();
                phone.put("HP", hp);
                
                System.out.print("> No Telepon [OFFICE] : ");
                String office = MissionUtil.getUserInput();
                phone.put("OFFICE", office);
                
                System.out.print("> No Perwakilan (HP/OFFICE) : ");
                String wakil = MissionUtil.getUserInput();
                phone.put("Perwakilan", wakil);
                
                biz.insertCustomer(name, age, phone);
                System.out.println("@ Informasi Pelanggan Sudah Ditambahkan.");
                System.out.println("---------------------------------------");
                
            } else if (menu == 3) {
                System.out.println("---------------------------------------");
                System.out.println("Edit Usia Pelanggan.");
                System.out.println("---------------------------------------");
                
                System.out.print("> Nomor Pelanggan yang Akan di Edit : ");
                int number = Integer.parseInt(MissionUtil.getUserInput());
                
                if(number < 1 || number > biz.getCustomerNumber()) {
                    System.out.println("[ERROR] Tidak Bisa Menemukan Nomor Pelanggan yang Sesuai.");
                    continue;
                }
                
                System.out.print("> Informasi Usia yang Akan di Edit : ");
                int age = Integer.parseInt(MissionUtil.getUserInput());
                
                if(age < 0) {
                    System.out.println("[ERROR] sia Tidak Bisa di Input.");
                    continue;
                }
                
                biz.changeAge(number, age);
                System.out.println("@ Informasil Pelanggan Berhasil Diubah.");
                System.out.println("---------------------------------------");
                
            } else if(menu == 4){
                System.out.println("---------------------------------------");
                System.out.println("Ubah No Telepon Pelanggan.");
                System.out.println("---------------------------------------");
                
                System.out.print("> No Informasi Pelanggan yang Akan di Ubah : ");
                int number = Integer.parseInt(MissionUtil.getUserInput());
                
                if(number < 1 || number > biz.getCustomerNumber()) {
                    System.out.println("[ERROR] Tidak Bisa Menemukan Nomor Pelanggan yang Sesuai.");
                    continue;
                }
                
                HashMap<String, String> phone = new HashMap<String, String>();
                
                System.out.print("> No Telepon yang Akan di Edit (HP) : ");
                String hp = MissionUtil.getUserInput();
                phone.put("HP", hp);
                
                System.out.print("> No Telepon yang Akan di Edit (OFFICE) : ");
                String office = MissionUtil.getUserInput();
                phone.put("OFFICE", office);
                
                System.out.print("> No Perwakilan (HP/OFFICE) : ");
                String wakil = MissionUtil.getUserInput();
                phone.put("Perwakilan", wakil);
                
                biz.changePhone(number, phone);
                System.out.println("@ Informasi Pelanggan Telah Diubah.");
                System.out.println("---------------------------------------");
                
            }else if(menu == 5) {
                System.out.println("---------------------------------------");
                System.out.println("Menghapus Informasi Pelanggan.");
                System.out.println("---------------------------------------");
                
                System.out.print("> Nomor Pelanggan yang Akan Dihapus : ");
                int number = Integer.parseInt(MissionUtil.getUserInput());
                
                if (number < 1 || number > biz.getCustomerNumber()) {
                    System.out.println("[ERROR] Tidak Bisa Menemukan Nomor Pelanggan yang Sesuai");
                    continue;
                }
                
                biz.deleteCustomer(number);
                System.out.println("@ Informasi Pelanggan Telah Dihapus");
                
            }else if (menu == 9) {
                System.out.println("---------------------------------------");
                System.out.println("End. Bye~ Bye~");
                System.out.println("---------------------------------------");
                break;
                
            }else{
                System.out.println("[ERROR] Menu Tidak Valid. Masukkan Menu 1-5 atau 9.");
            }
        }
    }
        
        public static void printMenu() {
            System.out.println("======== << Program manajemen pelanggan >> ========");
            System.out.println(" 1. Mendapat informasi semua pelanggan");
            System.out.println(" 2. Tambahkan informasi pelanggan");
            System.out.println(" 3. Edit usia pelanggan");
            System.out.println(" 4. Edit nomor telepon pelanggan");
            System.out.println(" 5. Hapus informasi pelanggan");
            System.out.println(" 9. Keluar sistem");
            System.out.println("============================================");
    }
}
