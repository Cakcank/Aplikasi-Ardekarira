/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Pegawai;
import Main.*;
import Pelanggan.*;
import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author DOSTAKIJO
 */
public class DatabaseConnection {
        public static Connection connect(){
        Connection conn = null;
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/db_ardekarira", "root", "");
            System.out.println("Koneksi Berhasil");
        }catch(Exception e){
            System.out.println("Koneksi Gagal: "+e.getMessage());
        }
        
        return conn;
    }
    
}
