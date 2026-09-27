/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tubesriyal;

/**
 *
 * @author 7320
 */

public class Penghobi extends User {

    // =========================================================
    // ATRIBUT KHUSUS PENGHOBI (private)
    // =========================================================

    /** Hobi ikan hias, contoh: "Cupang Hias", "Koi", "Arwana" */
    private String hobiIkan;

    /** Level keanggotaan: "PEMULA", "MENENGAH", "AHLI" */
    private String tingkatKeanggotaan;

    /** Tanggal pertama kali mendaftar, format: "yyyy-MM-dd" */
    private String tanggalDaftar;

    // =========================================================
    // CONSTRUCTOR
    // =========================================================

    /**
     * Constructor lengkap — digunakan LoginService saat login berhasil
     * dan UserService saat load profil dari DB.
     */
    public Penghobi(int id, String nama, String email, String password,
                    String hobiIkan, String tingkatKeanggotaan, String tanggalDaftar) {
        super(id, nama, email, password, "PENGHOBI");
        this.hobiIkan           = hobiIkan;
        this.tingkatKeanggotaan = tingkatKeanggotaan;
        this.tanggalDaftar      = tanggalDaftar;
    }

    /**
     * Constructor untuk registrasi baru — tingkat default PEMULA, tanggal hari ini
     */
    public Penghobi(String nama, String email, String password, String hobiIkan) {
        super(0, nama, email, password, "PENGHOBI");
        this.hobiIkan           = hobiIkan;
        this.tingkatKeanggotaan = "PEMULA";
        this.tanggalDaftar      = java.time.LocalDate.now().toString();
    }

    // =========================================================
    // GETTER & SETTER
    // =========================================================

    /** @return Hobi ikan penghobi */
    public String getHobiIkan() {
        return hobiIkan;
    }

    /**
     * Setter hobi ikan — dipanggil saat user edit hobi di profil
     * @param hobiIkan hobi baru
     */
    public void setHobiIkan(String hobiIkan) {
        if (hobiIkan != null && !hobiIkan.trim().isEmpty()) {
            this.hobiIkan = hobiIkan.trim();
        }
    }

    /** @return Tingkat keanggotaan: PEMULA / MENENGAH / AHLI */
    public String getTingkatKeanggotaan() {
        return tingkatKeanggotaan;
    }

    /**
     * Setter tingkat keanggotaan — bisa diubah oleh Admin
     * @param tingkat tingkat baru
     */
    public void setTingkatKeanggotaan(String tingkat) {
        if (tingkat != null && !tingkat.trim().isEmpty()) {
            this.tingkatKeanggotaan = tingkat.trim().toUpperCase();
        }
    }

    /** @return Tanggal daftar format yyyy-MM-dd */
    public String getTanggalDaftar() {
        return tanggalDaftar;
    }

    // =========================================================
    // OVERRIDE METHOD
    // =========================================================

    /**
     * Menampilkan profil lengkap versi Penghobi ke console.
     * Dipanggil oleh ProfilFrame untuk menampilkan data.
     */
    @Override
    public void display() {
        System.out.println("╔══════════════════════════════════╗");
        System.out.println("║         PROFIL PENGHOBI          ║");
        System.out.println("╚══════════════════════════════════╝");
        System.out.println("  ID              : " + id);
        System.out.println("  Nama            : " + nama);
        System.out.println("  Email           : " + email);
        System.out.println("  Role            : " + role);
        System.out.println("  Hobi Ikan       : " + hobiIkan);
        System.out.println("  Tingkat         : " + tingkatKeanggotaan);
        System.out.println("  Tanggal Daftar  : " + tanggalDaftar);
        System.out.println("──────────────────────────────────");
    }

    /**
     * Ringkasan objek untuk debugging
     */
    @Override
    public String toString() {
        return "Penghobi{id=" + id + ", nama='" + nama + "', email='" + email +
               "', hobi='" + hobiIkan + "', tingkat='" + tingkatKeanggotaan + "'}";
    }
}

