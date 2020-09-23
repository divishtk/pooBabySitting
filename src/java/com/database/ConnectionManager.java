/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.database;

import java.sql.*;
/**
 *
 * @author Nevets
 */
public class ConnectionManager{
//    private static String url = "jdbc:mysql://localhost:3306/mydb?useSSL=false&allowPublicKeyRetrieval=true";
//    private static String username = "root";
//    private static String password = "Steven@1996";
    private static String url = "jdbc:mysql://35.213.166.86:3306/dbtaz6hfa5t33b?useSSL=false&allowPublicKeyRetrieval=true&autoReconnect=true";
    private static String driverName = "com.mysql.cj.jdbc.Driver";
    private static String username = "uy6sf3dgdchrp";
    private static String password = "qwerty@123";
    private static Connection con;
    
    public static Connection getConnection() {
        try {
            Class.forName(driverName);
            try {
                con = DriverManager.getConnection(url, username, password);
            } catch (SQLException ex) {
                System.out.println("Failed to create the database connection.");
            }
        } catch (ClassNotFoundException ex) {
            System.out.println("Driver not found.");
        }
        return con;
    }
}