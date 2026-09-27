/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tubesriyal;

/**
 *
 * @author 7320
 */
public abstract class User implements Displayable {

    // =========================================================
    // ATRIBUT (protected — diakses oleh Penghobi & Mentor)
    // =========================================================

    /** ID unik dari database, dipakai semua query WHERE id=? */
    protected int id;

    /** Nama ditampilkan di menu utama "Selamat datang, [nama]" */
    protected String nama;

    /** Dipakai sebagai username login */
    protected String email;

    /** Dicocokkan saat login (idealnya di-hash di produksi nyata) */
    protected String password;

    /** Menentukan menu apa yang muncul: "PENGHOBI" atau "MENTOR" */
    protected String role;

    // =========================================================
    // CONSTRUCTOR
    // =========================================================

    /**
     * Constructor User — dipanggil via super() dari Penghobi / Mentor
     */
    public User(int id, String nama, String email, String password, String role) {
        this.id       = id;
        this.nama     = nama;
        this.email    = email;
        this.password = password;
        this.role     = role;
    }

    // =========================================================
    // GETTER & SETTER
    // =========================================================

    /** @return ID unik user */
    public int getId() {
        return id;
    }

    /** @return Nama user */
    public String getNama() {
        return nama;
    }

    /**
     * Setter nama — dipakai saat edit profil
     * @param nama nama baru
     */
    public void setNama(String nama) {
        if (nama != null && !nama.trim().isEmpty()) {
            this.nama = nama.trim();
        }
    }

    /** @return Email user */
    public String getEmail() {
        return email;
    }

    /**
     * Setter email — dipakai saat edit profil
     * @param email email baru
     */
    public void setEmail(String email) {
        if (email != null && email.contains("@")) {
            this.email = email.trim();
        }
    }

    /** @return Password (raw) — dipakai LoginService saat validasi */
    public String getPassword() {
        return password;
    }

    /** @return Role user: "PENGHOBI" atau "MENTOR" */
    public String getRole() {
        return role;
    }

    // =========================================================
    // METHOD ABSTRACT — wajib di-override oleh child class
    // =========================================================

    /**
     * Menampilkan profil lengkap user ke console / GUI.
     * Tiap role menampilkan data yang berbeda, sehingga dibuat abstract.
     */
    @Override
    public abstract void display();
}

