package com.printer.apiprinter.conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class conexion {
    private static Connection cn = null;

    public static Connection getConnection(String db, String user, String pass) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            cn = DriverManager.getConnection(db, user, pass);
            return cn;
        } catch (Exception ex) {
            System.out.println("Database.getConnection() Error -->" + ex.getMessage());
            return null;
        }
    }

    public static void Close(Connection con) {
        try {
            con.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

}