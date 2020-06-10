package fxmlControllers;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class Test_controller {
	@FXML
	private Button buttonLogOut;
	@FXML
	private Pane paneUserScreen;
	@FXML
	private AnchorPane rootPane;
	@FXML
	private VBox vBoxTest;
	@FXML
	private ScrollPane scrollPaneMenu;
	
	@FXML
	public void initialize()
	{
		// Prepare buttons for the Vbox - our MENU
		for(int i = 0; i<20 ; i++ )
		{
			Button btn1 = new Button(); 
			String btnName = String.format("Numer tablicy %d",i); 
			btn1.setPrefWidth(vBoxTest.getPrefWidth());
			vBoxTest.getChildren().add(btn1);
			btn1.setText(btnName);
		}
		
		
		
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
			paneUserScreen.setBackground(background);
		} 
		catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void setAnchorPane( AnchorPane anchorpane)
	{
		rootPane = anchorpane;
	}

}

