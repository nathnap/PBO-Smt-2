/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.mavenproject1;

/**
 *
 * @author 7320
 */

public class Buku {
    private String title;
    private String author;
    private String isbn;
    private boolean available;
    private String[] reservationQueue;
    
    public Buku(String title, String author, String isbn) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.available = true;
        this.reservationQueue = new String[10];
    }
    
    public String getTitle(){
        return title;
    }
    
    public boolean borrowBook(User user) {
        if(available && user.canBorrow()) {
            available = false;
            user.addBorrowedBook(this);
            return true;
        } else {
            System.out.println("Book is not available or user cannot borrow more books");
            return false;
    }
    }
    
    public boolean borrowBook(User user, String priority) {
        if(!available) {
            addToReservationQueue(user.getUserId(), priority);
            return false;
        } else {
            return borrowBook(user);
        }
    }
    
    public void addToReservationQueue(String userId, String priority) {
        for(int i = 0; i < reservationQueue.length; i ++){
            if(reservationQueue[i] == null){
                reservationQueue[i] = userId + "(Priority: " + priority + ")";
                System.out.println("User " +userId + " added to the reservation queue with priority: " + priority);
                return;
            }
        } 
    }
    
    public void displayBookInfo(){
        System.out.println("Title : " + title);
        System.out.println("Author : " + author);
        System.out.println("ISBN : " + isbn);
        System.out.println("Available : " + (available ? "yes" : "No"));
    }
}
