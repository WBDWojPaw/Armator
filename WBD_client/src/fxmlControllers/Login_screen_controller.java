package fxmlControllers;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import connectionToDataBase.DBConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;

public class Login_screen_controller {
	@FXML
	private Label labelTitle;
	@FXML
	private Label labelLogin;
	@FXML
	private Label labelPassword;
	@FXML
	private Label labelWrongPass;
	@FXML
	private Button buttonLogin;
	@FXML
	private Pane paneLoginScreen;
	@FXML
	private AnchorPane rootPane;
	@FXML
	private TextField textFieldLogin;
	@FXML
	private PasswordField passwordFieldPassword;
	
	@FXML
	public void initialize()
	{
		labelTitle.setText("Szczêœliwe wycieczki wodne - Chlapek");
		labelTitle.setFont(new Font("Arial",20));
		labelLogin.setText("Login");
		labelPassword.setText("Has³o");
		labelWrongPass.setText("");
		buttonLogin.setText("Zaloguj siê");
		
		//Set Background
		FileInputStream input;
		try 
		{
			input = new FileInputStream("C:\\Users\\user\\eclipse-workspace\\WBD_client\\photos\\tlo_login.png");
			Image image = new Image(input);
			BackgroundImage backgroundimage = new BackgroundImage(image,
												BackgroundRepeat.NO_REPEAT,
												BackgroundRepeat.NO_REPEAT,
												BackgroundPosition.DEFAULT,
													BackgroundSize.DEFAULT);
			Background background = new Background(backgroundimage);
			paneLoginScreen.setBackground(background);
		} 
		catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@FXML
	private void loginAttempt(ActionEvent event) throws IOException
	{
		
		int rights = 0;
		rights = compareLoginPassword();
		if(rights == 0)
		{
			return;
		}
		else
		{
			// Go to new screen
			User_screen_controller user_screen_controller = new User_screen_controller(rights);	
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxmlFiles/User_screen.fxml"));
			fxmlLoader.setController(user_screen_controller);
			Pane p = fxmlLoader.load();
			user_screen_controller.setAnchorPane(rootPane);
			rootPane.getChildren().setAll(p);
		}
	}
	
	public void setAnchorPane( AnchorPane anchorpane)
	{
		rootPane = anchorpane;
	}

	private int compareLoginPassword()
	{
		// Create a connection with SQL DATABASE
		Connection conn = DBConnection.getConnetion();
		try 
		{
			// Create SQL statement
			PreparedStatement stmt = conn.prepareStatement("SELECT LICZBA_DOSTEPU FROM DOSTEP WHERE LOGIN = ? AND HASLO = ?");
			stmt.setString(1, textFieldLogin.getText());
			stmt.setString(2, passwordFieldPassword.getText());
			ResultSet rs = stmt.executeQuery();
			if(rs.next())
			{
				System.out.println(rs.getInt(1));
				return rs.getInt(1);
			}
			else
			{
				Alert error = new Alert(AlertType.ERROR);
				error.setContentText("B³êdne has³o lub login");
				error.show();
			}
			conn.close();
		} 
		catch (SQLException e1)
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return 0;
	}
}
