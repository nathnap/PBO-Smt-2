/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.praktikum9;

/**
 *
 * @author 7320
 */
public class TravelTest {

    public static void printMenu() {
        System.out.println("===============< Menu >===============");
        System.out.println("1. Periksa semua produk travel");
        System.out.println("2. Periksa produk Individual");
        System.out.println("3. Permintaan produk paket travel");
        System.out.println("4. Reservasi produk travel");
        System.out.println("9. End");
    }

    public static void printHeader() {
        System.out.println("-----------------------------------------------------------------------------------");
        System.out.println("Kode\tNama\tPenerbangan\tJenis Travel\tJumlah minimum orang\tMaksimal orang yang\tJumlah");
        System.out.print("Travel\tKota\t\t\t\t\tyang boleh berangkat\tbisa reservasi\t\tReservasi");
        System.out.println("\n-----------------------------------------------------------------------------------");
    }

    public static void main(String[] args) {
        Travel[] data = new Travel[5];

        data[0] = new IndividualTravel("TRV001", "Munich", "German Airlines", 10);
        data[1] = new IndividualTravel("TRV002", "Praha", "AirFrance", 20);
        data[2] = new PackageTravel("TRV003", "LA", "Delta Airlines", 8);
        data[3] = new IndividualTravel("TRV004", "Osaka", "Korean Air", 15);
        data[4] = new PackageTravel("TRV005", "Shanghai", "Southern Airlines", 12);

        int menu;

        do {
            printMenu();
            System.out.print("## Input Menu: ");
            menu = DataUtil.getInt();

            switch (menu) {
                case 1:
                    printHeader();
                    for (int i = 0; i < data.length; i++) {
                        System.out.println(data[i]);
                    }
                    break;

                case 2:
                    printHeader();
                    for (int i = 0; i < data.length; i++) {
                        if (data[i].getTravelType().equals("Individual Travel")) {
                            System.out.println(data[i]);
                        }
                    }
                    break;

                case 3:
                    printHeader();
                    for (int i = 0; i < data.length; i++) {
                        if (data[i].getTravelType().equals("Paket Travel")) {
                            System.out.println(data[i]);
                        }
                    }
                    break;

                case 4:
                    System.out.print(">> Input kode perjalanan yang akan dipesan: ");
                    String kode = DataUtil.getString();

                    boolean ada = false;

                    for (int i = 0; i < data.length; i++) {
                        if (data[i].getTravelCode().equalsIgnoreCase(kode)) {
                            ada = true;

                            System.out.print(">> Input jumlah orang yang akan dipesan: ");
                            int jumlah = DataUtil.getInt();

                            data[i].setReserved(jumlah); // polymorphism tetap jalan
                            break;
                        }
                    }

                    if (!ada) {
                        System.out.println("ERROR! Kode yang diinput salah.");
                    }
                    break;

                case 9:
                    System.out.println(">> Selesai!!");
                    break;

                default:
                    System.out.println(">> Silahkan Input kembali!!");
            }

        } while (menu != 9);
    }
}