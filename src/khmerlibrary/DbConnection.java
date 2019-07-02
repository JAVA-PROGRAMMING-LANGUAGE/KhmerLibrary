/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package khmerlibrary;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.sqlite.SQLiteConfig;

/**
 *
 * @author SRUN VANNARA
 */
public class DbConnection {
//    private static String url = "jdbc:postgresql://localhost/postgres";
//    private static String user = "postgres";
//    private static String pwd = "8899";
//
//    public static Connection connect() {
//        Connection conn = null;
//            try {
//                conn = DriverManager.getConnection(url, user, pwd);
//                //System.out.println("Connected");
//            } catch (SQLException ex) {
//                new InfoDialog().show("មានបញ្ហា!", ex.getMessage());
//        }
//            return conn;
//        }

    //private static String user = "postgres";
    //private static String pwd = "8899";
    public static Connection connect() {
        Connection conn = null;
        try {
            String url = "jdbc:sqlite:D:/LibraryData/data.db";
            SQLiteConfig config = new SQLiteConfig();
            config.enforceForeignKeys(true);
            conn = DriverManager.getConnection(url, config.toProperties());
            //System.out.println("Connected");
        } catch (SQLException ex) {
            new InfoDialog().show("មានបញ្ហា!", ex.getMessage());
        }
        return conn;
    }
}
