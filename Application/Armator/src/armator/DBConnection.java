/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package armator;

import java.sql.*;
import javafx.scene.control.Alert;

/**
 *
 * @author Nitrox
 */
public class DBConnection {
    
    private static Connection conn;
    
    public static Connection getConnection(){
        
        String DB_URL = "jdbc:oracle:thin:@localhost:1521:orcl";
        // Better solution with external, encrypted file
        String DB_USER = "wrokicki";
        String DB_PASS = "Zxcvbnmnbvcxz1";
        
        try{
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("You are connected to database ...");
            alert.show();
        }catch(SQLException exc){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Database connection error ...");
            alert.setContentText("Details: " + exc.getMessage());
            alert.show();
        }
        
        
        return conn;
    }
    
}
