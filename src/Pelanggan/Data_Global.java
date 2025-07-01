/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Pelanggan;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;


/**
 *
 * @author DOSTAKIJO
 */
public class Data_Global {
    public static String namaPemesanAktif = "";
    public static HashSet<String> daftarSudahBayar = new HashSet<>();
    public static Map<String, ArrayList<String>> semuaPesananPerNama = new HashMap<>();
    public static Map<String, ArrayList<Integer>> semuaTotalPerNama = new HashMap<>();
    public static Map<String, String> metodePembayaranPerNama = new HashMap<>();
    
    public static class Transaksi {
    public String nama;
    public String metode;
    public ArrayList<String> daftarPesanan;
    public ArrayList<Integer> daftarHarga;
    public int total;
    public int bayar;
    public int kembalian;

    public Transaksi(String nama, String metode, ArrayList<String> daftarPesanan, ArrayList<Integer> daftarHarga, int total, int bayar, int kembalian) {
        this.nama = nama;
        this.metode = metode;
        this.daftarPesanan = daftarPesanan;
        this.daftarHarga = daftarHarga;
        this.total = total;
        this.bayar = bayar;
        this.kembalian = kembalian;
    }
}

    // ⬇️ Tambahkan list transaksi yang akan diakses dari dashboard admin
    public static ArrayList<Transaksi> daftarTransaksi = new ArrayList<>();

    
}
