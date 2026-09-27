/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mavenproject1;

/**
 *
 * @author 7320
 */
public class User {
    private String name;
    private String userId;
    private String userType;
    private Buku[] borrowedBook;
    private int borrowLimit;
    private int borrowDuration;
    
    public User(String name, String userId) {
        this.name = name;
        this.userId = userId;
        this.userType = "General";
        this.borrowedBook = new Buku[3];
        this.borrowLimit = 3;
        this.borrowDuration = 7;
    }
    
    public User(String name, String userId, String userType) {
        this.name = name;
        this.userId = userId;
        this.userType = userType;
        setBorrowedRules(userType);
        this.borrowedBook = new Buku[borrowLimit];
    }
    
    public String getUserId(){
        return userId;
    }
    
    public void setBorrowedRules (String userType) {
        if(userType.equalsIgnoreCase("Mahasiswa")) {
            this.borrowLimit = 5;
            this.borrowDuration = 14;
        } else if(userType.equalsIgnoreCase("Dosen")) {
            this.borrowLimit = 10;
            this.borrowDuration = 30;
        } else {
            this.borrowLimit = 3;
            this.borrowDuration = 7;
        }
    }
    
    public boolean canBorrow(){
        for(int i = 0; i < borrowedBook.length; i++){
            if(borrowedBook[i] == null) {
                return true;
            }   
        }
            return false;
    }
    
    public void addBorrowedBook (Buku buku) {
        for(int i = 0; i < borrowedBook.length; i++) {
            if(borrowedBook[i] == null) {
                borrowedBook[i] = buku;
                System.out.println(name + " has borrowed the book: " + buku.getTitle());
                return;
            }
        }
    }
    public void displayBorrowedBook(){
        System.out.println("Borrowed books by " + name + " : ");
        for(Buku buku: borrowedBook) {
            if(buku != null) {
                System.out.println(buku.getTitle());
            }
        }
    }
    
}
