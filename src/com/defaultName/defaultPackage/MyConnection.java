package com.defaultName.defaultPackage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MyConnection {

    public static Connection getConnection(){

        Connection conn = null;
        try{
            conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306/guestDatabase?user=USERNAME&password=PASSWORD");
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return conn;
    }
}
