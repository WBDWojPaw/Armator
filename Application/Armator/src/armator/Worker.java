/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package armator;

import java.sql.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

/**
 *
 * @author Nitrox
 */
public class Worker {
    
    // OREM in Sping does it automatically (mapping - DAO)
    private Integer workerId;
    private String workerName;
    private String workerSurname;
    private String workerHireDate;
    private String workerJobTitle;
    private Integer workerSalary;

    public Integer getWorkerId() {
        return workerId;
    }

    public void setWorkerId(Integer workerId) {
        this.workerId = workerId;
    }

    public String getWorkerName() {
        return workerName;
    }

    public void setWorkerName(String workerName) {
        this.workerName = workerName;
    }

    public String getWorkerSurname() {
        return workerSurname;
    }

    public void setWorkerSurname(String workerSurname) {
        this.workerSurname = workerSurname;
    }

    public String getWorkerHireDate() {
        return workerHireDate;
    }

    public void setWorkerHireDate(String workerHireDate) {
        this.workerHireDate = workerHireDate;
    }

    public String getWorkerJobTitle() {
        return workerJobTitle;
    }

    public void setWorkerJobTitle(String workerJobTitle) {
        this.workerJobTitle = workerJobTitle;
    }

    public Integer getWorkerSalary() {
        return workerSalary;
    }

    public void setWorkerSalary(Integer workerSalary) {
        this.workerSalary = workerSalary;
    }
    
    public ObservableList<Worker> getAll(Connection conn){
        
        ObservableList<Worker> workersList = FXCollections.observableArrayList();
        
        String sqlQuery = "SELECT NR_PRACOWNIKA, IMIE, NAZWISKO, DATA_ZATRUDNIENIA,"
                + " STANOWISKO, WYNAGRODZENIE FROM PRACOWNICY ORDER BY NR_PRACOWNIKA";
        
        Statement stmt;
        ResultSet rs;
        
        try{
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sqlQuery);
            
            while(rs.next()){
                Worker worker = new Worker();
                worker.setWorkerId(rs.getInt(1));
                worker.setWorkerName(rs.getString(2));
                worker.setWorkerSurname(rs.getString(3));
                worker.setWorkerHireDate(rs.getString(4));
                worker.setWorkerJobTitle(rs.getString(5));
                worker.setWorkerSalary(rs.getInt(6));
                System.out.println(worker.getWorkerSurname());
                workersList.add(worker);
            }
            
        }catch(SQLException exc){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Database data retrive error ...");
            alert.setContentText("Details: " + exc.getMessage());
            alert.show();
        }
        
        return workersList;
        
    }
    
}
