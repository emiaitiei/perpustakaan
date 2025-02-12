/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package library;

import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.JOptionPane;

/**
 *
 * @author user
 */
public class koneksi 
{
    // Connection koneksi=null;
 public static Connection koneksiDb()
 {
     try
     {
         String url = "jdbc:mysql://localhost:3306/perpustakaan";
         String user = "root";
         String password = "";
            
         Class.forName("com.mysql.jdbc.Driver");
         return DriverManager.getConnection(url, user, password);
         //Connection koneksi = DriverManager.getConnection("jdbc:mysql://localhost/perpustakaan","root","");
         //return koneksi;
     }
     catch(Exception e)
     {
        JOptionPane.showMessageDialog(null, e);
        return null;
     }
 }
}
