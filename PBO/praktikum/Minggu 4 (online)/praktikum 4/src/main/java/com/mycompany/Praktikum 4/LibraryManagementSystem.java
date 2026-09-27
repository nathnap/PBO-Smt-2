/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mavenproject1;

/**
 *
 * @author 7320
 */
public class LibraryManagementSystem {
    
    public static void main(String[] args) {
        User mahasiswa = new User("Alice", "H001","Mahasiswa");
        User dosen = new User("Bob", "D001","Dosen");
        
        Buku b1 = new Buku("Java Progamming","John","12345");
        Buku b2 = new Buku("Data Structures","Michael","67890");
                
        b1.borrowBook(mahasiswa);
        mahasiswa.displayBorrowedBook();
        
        b1.borrowBook(dosen, "High");
        dosen.displayBorrowedBook();
    }
}
