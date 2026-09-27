/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tubesriyal;

/**
 *
 * @author 7320
 */

public class Ikan implements Displayable {

    // =========================================================
    // ATRIBUT (private)
    // =========================================================

    /** Primary key di tabel ikan */
    private int id;

    /** Nama ilmiah / spesies, contoh: "Betta splendens" */
    private String namaSpesies;

    /** Habitat asli ikan, contoh: "Air tawar, tropis Asia Tenggara" */
    private String habitat;

    /** Jenis pakan yang cocok, contoh: "Cacing sutra, pelet" */
    private String pakan;

    /** Penyakit umum yang sering menyerang spesies ini */
    private String penyakitUmum;

    // =========================================================
    // CONSTRUCTOR
    // =========================================================

    /**
     * Constructor lengkap — digunakan EnsiklopediaService saat membaca dari DB
     */
    public Ikan(int id, String namaSpesies, String habitat,
                String pakan, String penyakitUmum) {
        this.id           = id;
        this.namaSpesies  = namaSpesies;
        this.habitat      = habitat;
        this.pakan        = pakan;
        this.penyakitUmum = penyakitUmum;
    }

    /**
     * Constructor tanpa id — untuk data baru sebelum di-INSERT ke DB
     */
    public Ikan(String namaSpesies, String habitat, String pakan, String penyakitUmum) {
        this(0, namaSpesies, habitat, pakan, penyakitUmum);
    }

    // =========================================================
    // GETTER & SETTER
    // =========================================================

    /** @return Primary key ikan di database */
    public int getId() {
        return id;
    }

    /** @return Nama spesies ikan */
    public String getNamaSpesies() {
        return namaSpesies;
    }

    /** @return Habitat asli ikan */
    public String getHabitat() {
        return habitat;
    }

    /**
     * Setter habitat — dipakai saat Admin update data ikan
     * @param habitat habitat baru
     */
    public void setHabitat(String habitat) {
        if (habitat != null && !habitat.trim().isEmpty()) {
            this.habitat = habitat.trim();
        }
    }

    /** @return Jenis pakan ikan */
    public String getPakan() {
        return pakan;
    }

    /**
     * Setter pakan
     * @param pakan pakan baru
     */
    public void setPakan(String pakan) {
        if (pakan != null && !pakan.trim().isEmpty()) {
            this.pakan = pakan.trim();
        }
    }

    /** @return Penyakit umum yang menyerang ikan ini */
    public String getPenyakitUmum() {
        return penyakitUmum;
    }

    /**
     * Setter penyakitUmum
     * @param penyakit penyakit umum baru
     */
    public void setPenyakitUmum(String penyakit) {
        if (penyakit != null && !penyakit.trim().isEmpty()) {
            this.penyakitUmum = penyakit.trim();
        }
    }

    // =========================================================
    // OVERRIDE METHOD
    // =========================================================

    /**
     * Menampilkan detail ikan ke console.
     * Dipanggil di EnsiklopediaFrame saat user pilih "Lihat Detail".
     */
    @Override
    public void display() {
        System.out.println("╔══════════════════════════════════╗");
        System.out.println("║       DETAIL IKAN HIAS           ║");
        System.out.println("╚══════════════════════════════════╝");
        System.out.println("  ID             : " + id);
        System.out.println("  Nama Spesies   : " + namaSpesies);
        System.out.println("  Habitat        : " + habitat);
        System.out.println("  Pakan          : " + pakan);
        System.out.println("  Penyakit Umum  : " + penyakitUmum);
        System.out.println("──────────────────────────────────");
    }

    /**
     * Ringkasan objek untuk debugging
     */
    @Override
    public String toString() {
        return "Ikan{id=" + id + ", namaSpesies='" + namaSpesies + "', habitat='" + habitat + "'}";
    }
}

