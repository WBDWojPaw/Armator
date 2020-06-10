package connectionToDataBase;
import java.sql.*;

import javafx.scene.control.Alert;

public class DBConnection {

	private static Connection conn;
	
	public static Connection getConnetion()
	{
		String DB_URL = "jdbc:oracle:thin:@ora3.elka.pw.edu.pl:1521:ora3inf";
		String DB_USER = "wrokicki";
		String DB_PASS = "wrokicki";
		
		try {
			conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
			//Alert alert = new Alert(Alert.AlertType.INFORMATION);
			//alert.setTitle("You are connected to the Database");
			//alert.show();
		} catch (SQLException e) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Error to database connection...");
			alert.setContentText("Details: " + e.getMessage());
			alert.show();
		}
		
		return conn;
	}
}
