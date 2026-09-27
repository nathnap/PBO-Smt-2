/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mycompany.tubesriyal;

/**
 *
 * @author 7320
 */

public interface Manageable {
    /**
     * Menyimpan objek ke database
     * @param obj objek yang akan disimpan
     * @return true jika berhasil
     */
    boolean save(Object obj);

    /**
     * Menghapus data dari database berdasarkan id
     * @param id primary key data yang akan dihapus
     * @return true jika berhasil
     */
    boolean delete(int id);

    /**
     * Mencari satu data spesifik dari database
     * @param id primary key data yang dicari
     * @return objek yang ditemukan, atau null jika tidak ada
     */
    Object findById(int id);
}

