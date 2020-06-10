package fxmlControllers;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import classesForTable.AdresyTable;
import classesForTable.AtrakcjeTable;
import classesForTable.BiuraTable;
import classesForTable.CzarteryTable;
import classesForTable.LodzieTable;
import classesForTable.ModeleLodziTable;
import classesForTable.PlacowkiTable;
import connectionToDataBase.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class User_screen_controller {
	
	@FXML
	private AnchorPane rootPane;
	@FXML
	private Pane paneUsersScreen;
	@FXML
	private VBox vBoxMenu;
	@FXML
	private Label labelMenu;
	@FXML
	private Label labelTabelName;
	@FXML
	private Label labelModify;
	@FXML
	private Label labelSearch;
	@FXML
	private Button buttonLogOut;	
	@FXML
	private TableView tableViewData;
	@FXML
	private ScrollPane scrollPaneMenu;
	@FXML
	private AnchorPane anchorPaneMenu;
	@FXML
	private Label labelSearchName;
	@FXML
	private Label labelSearchValue;
	@FXML
	private Button buttonSearch;
	@FXML
	private TextField textFieldSearchName;
	@FXML
	private TextField textFieldSearchValue;
	@FXML
	private Button buttonModify;

	// Prepare normal variables ( not FXML ones )
	private int buttonHeight = 20;
	private String SQLTable;
	private int rights ;
	
	String[] avaibleTablesName;
	private int numberOfButtons;
	private Button[] menuButtons;
	
	private Connection conn;
	// the base of our query
	private String SQL_command = "SELECT * FROM ";
	
	public User_screen_controller(int rightsValue) {
		rights = rightsValue;
	}
	
	
	@FXML
	public void initialize()
	{
		// Set avaible tables
		avaibleTablesName = getRights(rights);

		numberOfButtons = avaibleTablesName.length;
		menuButtons = new Button[numberOfButtons];
		SQLTable = avaibleTablesName[0].toUpperCase();
		
		// Create a connection with SQL DATABASE
		conn = DBConnection.getConnetion();
		try 
		{
			// Create SQL statement
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = stmt.executeQuery(SQL_command + SQLTable);
		
			// Prepare columns and data
			setTableViewPublisher(SQLTable,rs);
		} 
		catch (SQLException e1)
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		// Initialize all the text on stuff
		initializeText();
		
		// Prepare buttons for the Vbox - our MENU
		for(int i = 0; i<numberOfButtons ; i++ )
		{
			Button btn1 = new Button();
			menuButtons[i] = btn1;
			btn1.setPrefHeight(buttonHeight); 
			btn1.setPrefWidth(vBoxMenu.getPrefWidth());
			vBoxMenu.getChildren().add(btn1);
			btn1.setText(avaibleTablesName[i]);
		}
		vBoxMenu.setPrefHeight(numberOfButtons * buttonHeight);
		
		//Set Background
		FileInputStream input;
		try 
		{
			input = new FileInputStream("C:\\Users\\user\\eclipse-workspace\\WBD_client\\photos\\Sea.jpg");
			Image image = new Image(input);
			BackgroundImage backgroundimage = new BackgroundImage(image,
												BackgroundRepeat.REPEAT,
												BackgroundRepeat.REPEAT,
												BackgroundPosition.DEFAULT,
													BackgroundSize.DEFAULT);
			Background background = new Background(backgroundimage);
			paneUsersScreen.setBackground(background);
		} 
		catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for(int i = 0; i < numberOfButtons ; i ++)
		{
			menuButtons[i].setOnAction(event -> {
			changeTable(event);
			});
		}
	}
	
	@FXML
	private void logOut(ActionEvent event) throws IOException
	{
		// Go to new screen
		FXMLLoader fxmlLoader = new FXMLLoader();
		Pane p = fxmlLoader.load(getClass().getResource("/fxmlFiles/Login_screen.fxml").openStream());
		Login_screen_controller login_screen_controller = (Login_screen_controller) fxmlLoader.getController();
		login_screen_controller.setAnchorPane(rootPane);
		rootPane.getChildren().setAll(p);
	}
		
	@FXML
	private void openWindowToModifyData(ActionEvent event)
	{
		Modify_data_screen_controller modify_data_screen_controller = new Modify_data_screen_controller(SQLTable,conn);
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxmlFiles/Modify_data_screen.fxml"));
		fxmlLoader.setController(modify_data_screen_controller);
		Parent root;
		try {
			root = fxmlLoader.load();
			Scene scene = new Scene(root);
			Stage stage = new Stage();
			stage.setTitle("Zmodyfikuj dane");
			stage.setScene(scene);
			stage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//User_screen_controller user_screen_controller = new User_screen_controller(2);	
		//FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxmlFiles/User_screen.fxml"));
		//fxmlLoader.setController(user_screen_controller);
		//Pane p = fxmlLoader.load();
		//user_screen_controller.setAnchorPane(rootPane);
		//rootPane.getChildren().setAll(p);
	}
	
	@FXML
	private void searchTheDatabase(ActionEvent event)
	{
		String userTextName = textFieldSearchName.getText().toUpperCase();
		String userTextValue = textFieldSearchValue.getText();
		// Check for potential SQL Injection
		boolean isNotOk = validateUsersArgument(userTextName);
		if(isNotOk)
		{
			return;
		}
		isNotOk = validateUsersArgument(userTextValue);
		if(isNotOk)
		{
			return;
		}
		
		// Prepare statement
		String searchString;
		if(userTextName.isEmpty() || userTextValue.isEmpty())
		{
			searchString = "SELECT * FROM "+ SQLTable;
		}
		else
		{
			searchString = "SELECT * FROM " + SQLTable + " WHERE " + userTextName + " = " + "\'" +userTextValue+ "\'";
		}
		System.out.println(searchString);
		// Send and execute query
		try {
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = stmt.executeQuery(searchString);
			
			// Check if the ResultSet is empty
			if(rs.next() == false)
			{
				return;
			}
			// move cursor before first element
			rs.beforeFirst();
			
			// Set TableView and data inside
			setTableViewPublisher(SQLTable,rs);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void setAnchorPane( AnchorPane anchorpane)
	{
		rootPane = anchorpane;
	}
	
	private void setTableViewPublisher(String nameOfTable, ResultSet rs) throws SQLException
	{
		// Fucntion will get the data from ResultSet and create columns and data based on this information. Also nameOfTable is required  because columns will get data based on the class name
		
		// Prepare data
		tableViewData.getColumns().clear();
		fillTableViewPublisher(nameOfTable,rs);
		
		// Reset cursor position, because later we need to use rs.getString, which returns value from pointed row
		rs.first();
		// Prepare TableView for data
		for(int i = 0; i<rs.getMetaData().getColumnCount();i++)
		{
			// Check if variable is convertable to integer
			Boolean isStringConvertableToInt = true;
			try 
			{
				Integer.parseInt(rs.getString(i+1));
			}
			catch(NumberFormatException ex)
			{
				isStringConvertableToInt = false;
			}
						
			if(isStringConvertableToInt)
			{
				addColumns(nameOfTable, isStringConvertableToInt , rs, i);
			}
			else
			{
				addColumns(nameOfTable, isStringConvertableToInt , rs, i);
			}	
		}
	}
	
	private void fillTableViewPublisher(String nameOfTable ,ResultSet rs) throws SQLException
	{
		// Funtion will create Observable list of data splited into classes and passes it to the TableView.
		// it is important to add new case if we want to add new table.
		switch(nameOfTable)
		{
		case "ADRESY":
			// File table with data
			ObservableList<AdresyTable> adresyDataList = FXCollections.observableArrayList();
			String[] adresyTempArray = new String[rs.getMetaData().getColumnCount()];
			while(rs.next())
			{
				for(int i = 0; i<rs.getMetaData().getColumnCount();i++)
				{
					adresyTempArray[i] = rs.getString(i+1);			
				}
				adresyDataList.add(new AdresyTable(adresyTempArray));
				// Add data to the table
				tableViewData.setItems(adresyDataList);
			}
			break;
		case "ATRAKCJE":
			// File table with data
			ObservableList<AtrakcjeTable> atrakcjeDataList = FXCollections.observableArrayList();
			String[] atrakcjeTempArray = new String[rs.getMetaData().getColumnCount()];
			while(rs.next())
			{
				for(int i = 0; i<rs.getMetaData().getColumnCount();i++)
				{
					atrakcjeTempArray[i] = rs.getString(i+1);			
				}
				atrakcjeDataList.add(new AtrakcjeTable(atrakcjeTempArray));
				// Add data to the table
				tableViewData.setItems(atrakcjeDataList);
			}
			break;
		case "BIURA":
			// File table with data
			ObservableList<BiuraTable> biuraDataList = FXCollections.observableArrayList();
			String[] biuraTempArray = new String[rs.getMetaData().getColumnCount()];
			while(rs.next())
			{
				for(int i = 0; i<rs.getMetaData().getColumnCount();i++)
				{
					biuraTempArray[i] = rs.getString(i+1);			
				}
				biuraDataList.add(new BiuraTable(biuraTempArray));
				// Add data to the table
				tableViewData.setItems(biuraDataList);
			}
			break;
		case "CZARTERY":
			// File table with data
			ObservableList<CzarteryTable> czarteryDataList = FXCollections.observableArrayList();
			String[] czarteryTempArray = new String[rs.getMetaData().getColumnCount()];
			while(rs.next())
			{
				for(int i = 0; i<rs.getMetaData().getColumnCount();i++)
				{
					czarteryTempArray[i] = rs.getString(i+1);			
				}
				czarteryDataList.add(new CzarteryTable(czarteryTempArray));
				// Add data to the table
				tableViewData.setItems(czarteryDataList);
			}
			break;
		case "LODZIE":
			// File table with data
			ObservableList<LodzieTable> lodzieDataList = FXCollections.observableArrayList();
			String[] lodzieTempArray = new String[rs.getMetaData().getColumnCount()];
			while(rs.next())
			{
				for(int i = 0; i<rs.getMetaData().getColumnCount();i++)
				{
					lodzieTempArray[i] = rs.getString(i+1);			
				}
				lodzieDataList.add(new LodzieTable(lodzieTempArray));
				// Add data to the table
				tableViewData.setItems(lodzieDataList);
			}
			break;
		case "MODELE_LODZI":
			// File table with data
			ObservableList<ModeleLodziTable> modeleLodziDataList = FXCollections.observableArrayList();
			String[] modeleLodziTempArray = new String[rs.getMetaData().getColumnCount()];
			while(rs.next())
			{
				for(int i = 0; i<rs.getMetaData().getColumnCount();i++)
				{
					modeleLodziTempArray[i] = rs.getString(i+1);			
				}
				modeleLodziDataList.add(new ModeleLodziTable(modeleLodziTempArray));
				// Add data to the table
				tableViewData.setItems(modeleLodziDataList);
			}
			break;
		case "PLACOWKI":
			// File table with data
			ObservableList<PlacowkiTable> placowkiDataList = FXCollections.observableArrayList();
			String[] placowkiTempArray = new String[rs.getMetaData().getColumnCount()];
			while(rs.next())
			{
				for(int i = 0; i<rs.getMetaData().getColumnCount();i++)
				{
					placowkiTempArray[i] = rs.getString(i+1);			
				}
				placowkiDataList.add(new PlacowkiTable(placowkiTempArray));
				// Add data to the table
				tableViewData.setItems(placowkiDataList);
			}
			break;
		}
	}
	
	private void initializeText()
	{
		// Prepare and set texts 
		labelMenu.setText("Dostêpne tablice");
		labelTabelName.setText("Tablica "+ SQLTable.toLowerCase());
		labelSearchName.setText("Wyszukaj w kolumnie");
		labelSearchValue.setText("Wartoœæ do wyszukania");
		buttonLogOut.setText("Wyloguj siê");
		buttonSearch.setText("Szukaj");
		buttonModify.setText("Zmodyfikuj dane");
	}
	
	private boolean validateUsersArgument(String userText)
	{
		// Function will check if the user tried to attack our database using SQLInjection
		Pattern p = Pattern.compile("[^a-z0-9 _/]",Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(userText);
		boolean b = m.find();
		return b;
	}
	
	@FXML 
	private void changeTable(ActionEvent event)
	{
		// Based on the menu , funtion will change the value of SQLTable (name of table we are using ) and load new data into TableView.
		// it is important to add new case if we want to add new table.
		Object node = event.getSource();
		Button b = (Button) node;
		switch(b.getText())
		{
		case "Adresy":
			SQLTable = "ADRESY";
			break;
		case "Atrakcje":
			SQLTable = "ATRAKCJE";
			break;
		case "Biura": 
			SQLTable = "BIURA";
			break;
		case "Czartery":
			SQLTable = "CZARTERY";
			break;
		case "Lodzie":
			SQLTable = "LODZIE";
			break;
		case "ModeleLodzi":
			SQLTable = "MODELE_LODZI";
			break;
		case "Placowki":
			SQLTable = "PLACOWKI";
			break;
		}
		// Change the name above table
		labelTabelName.setText("Tablica "+ SQLTable.toLowerCase());
		// Get Data from Database
		try 
		{
			// Create SQL statement
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = stmt.executeQuery(SQL_command + SQLTable);
		
			// Check if the ResultSet is empty
			if(rs.next() == false)
			{
				return;
			}
			// move cursor before first element
			rs.beforeFirst();
			
			// Prepare columns and data
			setTableViewPublisher(SQLTable,rs);
		} 
		catch (SQLException e1)
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	}
		
	private void addColumns(String nameOfTable, boolean isInteger , ResultSet rs, int i)
	{
		// Function will create columns for TableView to display data in them. it is important to add new case if we want to add new table.
		switch(nameOfTable)
		{
		case "ADRESY" :
			if(isInteger)
			{
				// Set name of column
				TableColumn<AdresyTable, Integer> col;
				try {
					col = new TableColumn<AdresyTable,Integer>(rs.getMetaData().getColumnName(i+1)); // here we set the name of the column
					// Set name of the variable, which will be used ( based on this name column will search for get function )
					col.setCellValueFactory(new PropertyValueFactory<>(rs.getMetaData().getColumnName(i+1)));
					tableViewData.getColumns().add(col);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else
			{
				// Set name of column
				TableColumn<AdresyTable, String> col;
				try {
					col = new TableColumn<AdresyTable,String>(rs.getMetaData().getColumnName(i+1));
					// Set name of the variable, which will be used ( based on this name column will search for get function )
					col.setCellValueFactory(new PropertyValueFactory<>(rs.getMetaData().getColumnName(i+1)));
					tableViewData.getColumns().add(col);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			break;
		case "ATRAKCJE":
			if(isInteger)
			{
				// Set name of column
				TableColumn<AtrakcjeTable, Integer> col;
				try {
					col = new TableColumn<AtrakcjeTable,Integer>(rs.getMetaData().getColumnName(i+1));
					// Set name of the variable, which will be used ( based on this name column will search for get function )
					col.setCellValueFactory(new PropertyValueFactory<>(rs.getMetaData().getColumnName(i+1)));
					tableViewData.getColumns().add(col);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else
			{
				// Set name of column
				TableColumn<AtrakcjeTable, String> col;
				try {
					col = new TableColumn<AtrakcjeTable,String>(rs.getMetaData().getColumnName(i+1));
					// Set name of the variable, which will be used ( based on this name column will search for get function )
					col.setCellValueFactory(new PropertyValueFactory<>(rs.getMetaData().getColumnName(i+1)));
					tableViewData.getColumns().add(col);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			break;
		case "BIURA":
			if(isInteger)
			{
				// Set name of column
				TableColumn<BiuraTable, Integer> col;
				try {
					col = new TableColumn<BiuraTable,Integer>(rs.getMetaData().getColumnName(i+1));
					// Set name of the variable, which will be used ( based on this name column will search for get function )
					col.setCellValueFactory(new PropertyValueFactory<>(rs.getMetaData().getColumnName(i+1)));
					tableViewData.getColumns().add(col);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else
			{
				// Set name of column
				TableColumn<BiuraTable, String> col;
				try {
					col = new TableColumn<BiuraTable,String>(rs.getMetaData().getColumnName(i+1));
					// Set name of the variable, which will be used ( based on this name column will search for get function )
					col.setCellValueFactory(new PropertyValueFactory<>(rs.getMetaData().getColumnName(i+1)));
					tableViewData.getColumns().add(col);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			break;
		case "CZARTERY":
			if(isInteger)
			{
				// Set name of column
				TableColumn<CzarteryTable, Integer> col;
				try {
					col = new TableColumn<CzarteryTable,Integer>(rs.getMetaData().getColumnName(i+1));
					// Set name of the variable, which will be used ( based on this name column will search for get function )
					col.setCellValueFactory(new PropertyValueFactory<>(rs.getMetaData().getColumnName(i+1)));
					tableViewData.getColumns().add(col);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else
			{
				// Set name of column
				TableColumn<CzarteryTable, String> col;
				try {
					col = new TableColumn<CzarteryTable,String>(rs.getMetaData().getColumnName(i+1));
					// Set name of the variable, which will be used ( based on this name column will search for get function )
					col.setCellValueFactory(new PropertyValueFactory<>(rs.getMetaData().getColumnName(i+1)));
					tableViewData.getColumns().add(col);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			break;
		case "LODZIE" :
			if(isInteger)
			{
				// Set name of column
				TableColumn<LodzieTable, Integer> col;
				try {
					col = new TableColumn<LodzieTable,Integer>(rs.getMetaData().getColumnName(i+1)); // here we set the name of the column
					// Set name of the variable, which will be used ( based on this name column will search for get function )
					col.setCellValueFactory(new PropertyValueFactory<>(rs.getMetaData().getColumnName(i+1)));
					tableViewData.getColumns().add(col);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else
			{
				// Set name of column
				TableColumn<LodzieTable, String> col;
				try {
					col = new TableColumn<LodzieTable,String>(rs.getMetaData().getColumnName(i+1));
					// Set name of the variable, which will be used ( based on this name column will search for get function )
					col.setCellValueFactory(new PropertyValueFactory<>(rs.getMetaData().getColumnName(i+1)));
					tableViewData.getColumns().add(col);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			break;
		case "PLACOWKI" :
			if(isInteger)
			{
				// Set name of column
				TableColumn<PlacowkiTable, Integer> col;
				try {
					col = new TableColumn<PlacowkiTable,Integer>(rs.getMetaData().getColumnName(i+1)); // here we set the name of the column
					// Set name of the variable, which will be used ( based on this name column will search for get function )
					col.setCellValueFactory(new PropertyValueFactory<>(rs.getMetaData().getColumnName(i+1)));
					tableViewData.getColumns().add(col);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else
			{
				// Set name of column
				TableColumn<PlacowkiTable, String> col;
				try {
					col = new TableColumn<PlacowkiTable,String>(rs.getMetaData().getColumnName(i+1));
					// Set name of the variable, which will be used ( based on this name column will search for get function )
					col.setCellValueFactory(new PropertyValueFactory<>(rs.getMetaData().getColumnName(i+1)));
					tableViewData.getColumns().add(col);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			break;
		case "MODELE_LODZI" :
			if(isInteger)
			{
				// Set name of column
				TableColumn<ModeleLodziTable, Integer> col;
				try {
					col = new TableColumn<ModeleLodziTable,Integer>(rs.getMetaData().getColumnName(i+1)); // here we set the name of the column
					// Set name of the variable, which will be used ( based on this name column will search for get function )
					col.setCellValueFactory(new PropertyValueFactory<>(rs.getMetaData().getColumnName(i+1)));
					tableViewData.getColumns().add(col);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else
			{
				// Set name of column
				TableColumn<ModeleLodziTable, String> col;
				try {
					col = new TableColumn<ModeleLodziTable,String>(rs.getMetaData().getColumnName(i+1));
					// Set name of the variable, which will be used ( based on this name column will search for get function )
					col.setCellValueFactory(new PropertyValueFactory<>(rs.getMetaData().getColumnName(i+1)));
					tableViewData.getColumns().add(col);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			break;
		}
	}	

	private String[] getRights(int i)
	{
		int size;
		if(i == 1)
		{
			size = 2;
		}
		else if(i == 2)
		{
			//size = 4;
			
			size = 6;
		}
		else
		{
			size = 0;
		}
		
		String[] avaibleTables = new String[size];
		
		if(i == 1)
		{
			avaibleTables[0] = "Lodzie";
			avaibleTables[1] = "ModeleLodzi";
			buttonModify.setDisable(true);
		}
		else if(i == 2)
		{
			avaibleTables[0] = "Adresy";
			avaibleTables[1] = "Atrakcje";
			avaibleTables[2] = "Biura";
			//avaibleTables[3] = "Czartery";
			avaibleTables[3] = "Placowki";
			avaibleTables[4] = "Lodzie";
			avaibleTables[5] = "ModeleLodzi";
		}
	return avaibleTables;
		
		
	}

	
}
