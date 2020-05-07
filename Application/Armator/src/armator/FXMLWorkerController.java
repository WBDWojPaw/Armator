/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package armator;

import java.net.URL;
import java.sql.Connection;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

/**
 *
 * @author Nitrox
 */
public class FXMLWorkerController implements Initializable {
    
    private Connection conn;
   
    private ObservableList<Worker> workersList = FXCollections.observableArrayList(); 
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        conn = DBConnection.getConnection();
        workersList = new Worker().getAll(conn);
    }
    
}
