package fxmlControllers;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import connectionToDataBase.DBConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Modify_data_screen_controller {

	@FXML
	private VBox vBoxUserMenu;
	
	@FXML
	private Pane paneModifyData;
	
	@FXML
	private Button buttonClose;
	
	@FXML
	private Button buttonConfirm;
	
	@FXML
	private Label labelTitle;
	
	@FXML
	private Button buttonAddData;
	
	@FXML
	private Button buttonDeleteData;
	
	@FXML
	private Button buttonModifyData;
	
	
	// Declare rest of the variables
	// 1 - add data
	// 2 - modify row
	// 3 - delete row
	private String action = "add";
	private String SQLTable;
	private String SQL_command = "SELECT * FROM ";
	private Connection conn;
	private TextField[] insertTextFields;
	private Label[] insertLabels;
	private int labelHeight = 30;
	private int textFieldHeight = 30;
	private double totalHeight = 0;
	private int numberOfColumns;
	private String[] columnsNames;
	private String[] columnsTypes;
	
		
	
	
	public Modify_data_screen_controller(String SQLTable, Connection conn)
	{
		this.SQLTable = SQLTable;
		this.conn = conn;
	}

	@FXML
	public void initialize()
	{
		initializeText();
		
		// Create a connection with SQL DATABASE
		// conn = DBConnection.getConnetion();

		// Get data from SQL
		Statement stmt;
		try 
		{
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = stmt.executeQuery(SQL_command + SQLTable);
			
			numberOfColumns = rs.getMetaData().getColumnCount();
			columnsNames = new String[numberOfColumns];
			columnsTypes = new String[numberOfColumns];
			
			for(int i = 0; i<numberOfColumns ;i++)
			{
				columnsNames[i] = rs.getMetaData().getColumnName(i+1);
				columnsTypes[i] = getColumnTypes(rs.getMetaData().getColumnType(i+1));
				
				System.out.println(columnsTypes[i]);
			}	
		}
		catch(SQLException e)
		{
			
		}
		
		// Set VBox
		fillVBox();
		
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
			paneModifyData.setBackground(background);
		} 
		catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private void initializeText()
	{
		labelTitle.setText("Dodaj dane do tabeli " + SQLTable);
		labelTitle.setFont(new Font("Arial",20));
		buttonClose.setText("Zamknij okno");
		buttonClose.setFont(new Font("Arial",12));
		buttonConfirm.setText("Potwierdz czynnoœæ");
		buttonConfirm.setFont(new Font("Arial",12));
		buttonAddData.setText("Dodaj dane");
		buttonAddData.setFont(new Font("Arial",12));
		buttonDeleteData.setText("Usuñ dane");
		buttonDeleteData.setFont(new Font("Arial",12));
		buttonModifyData.setText("Zmodyfikuj dane");
		buttonModifyData.setFont(new Font("Arial",12));
	}
	
	private void fillVBox()
	{
		// Clear Vbox
		vBoxUserMenu.getChildren().clear();
		totalHeight = 0;
		if(action == "add")
		{
			labelTitle.setText("DODAJ DANE W TABLICY " + SQLTable);

			
			// Prepare arrays
			insertLabels = new Label[numberOfColumns - 1];
			insertTextFields = new TextField[numberOfColumns - 1];
			
			for(int i = 1; i<numberOfColumns ;i++)
			{
				Label label = new Label();
				insertLabels[i-1] = label;
				label.setPrefHeight(labelHeight); 
				label.setPrefWidth(vBoxUserMenu.getPrefWidth());
				label.setAlignment(Pos.BOTTOM_CENTER);
				vBoxUserMenu.getChildren().add(label);
				label.setText("Proszê podaj wartoœæ dla parametru " + columnsNames[i]);
				
				TextField textField = new TextField();
				insertTextFields[i-1] = textField;
				textField.setPrefHeight(textFieldHeight); 
				textField.setPrefWidth(vBoxUserMenu.getPrefWidth());
				vBoxUserMenu.getChildren().add(textField);
				vBoxUserMenu.setAlignment(Pos.TOP_LEFT);
				
				totalHeight = totalHeight + labelHeight + textFieldHeight;
			}
			vBoxUserMenu.setPrefHeight(totalHeight);
			
		}
		else if(action == "modify")
		{
			labelTitle.setText("ZMODYFIKUJ DANE W TABLICY " + SQLTable);
			
			Label labelDesc = new Label();
			labelDesc.setPrefHeight(labelHeight*3); 
			labelDesc.setPrefWidth(vBoxUserMenu.getPrefWidth());
			labelDesc.setAlignment(Pos.TOP_CENTER);
			vBoxUserMenu.getChildren().add(labelDesc);
			labelDesc.setWrapText(true);
			labelDesc.setText("Zmodyfikowanie wartoœci polega na wybraniu wiersza na podstawie pierwszego parametru. Nastêpnie wpisane wartoœci podmienia ju¿ istniejace wartoœci w wybranym wierszu. Je¿eli wartoœæ ma pozostaæ bez zmian to zostawiamy pole puste. ");
			totalHeight = totalHeight + labelDesc.getPrefHeight();
			
			// Prepare arrays
			insertLabels = new Label[numberOfColumns];
			insertTextFields = new TextField[numberOfColumns];
			
			Label labelMod = new Label();
			insertLabels[0] = labelMod;
			labelMod.setPrefHeight(labelHeight); 
			labelMod.setPrefWidth(vBoxUserMenu.getPrefWidth());
			labelMod.setAlignment(Pos.BOTTOM_CENTER);
			vBoxUserMenu.getChildren().add(labelMod);
			labelMod.setText("Proszê wybraæ wiersz na podstawie kolumny " + columnsNames[0]);
			
			TextField textFieldMod = new TextField();
			insertTextFields[0] = textFieldMod;
			textFieldMod.setPrefHeight(textFieldHeight); 
			textFieldMod.setPrefWidth(vBoxUserMenu.getPrefWidth());
			vBoxUserMenu.getChildren().add(textFieldMod);
			vBoxUserMenu.setAlignment(Pos.TOP_LEFT);
			
			totalHeight = totalHeight + labelHeight + textFieldHeight;
			
			for(int i = 1; i<numberOfColumns ;i++)
			{
				Label label = new Label();
				insertLabels[i] = label;
				label.setPrefHeight(labelHeight); 
				label.setPrefWidth(vBoxUserMenu.getPrefWidth());
				label.setAlignment(Pos.BOTTOM_CENTER);
				vBoxUserMenu.getChildren().add(label);
				label.setText("Proszê podaj wartoœæ dla parametru " + columnsNames[i]);
				
				TextField textField = new TextField();
				insertTextFields[i] = textField;
				textField.setPrefHeight(textFieldHeight); 
				textField.setPrefWidth(vBoxUserMenu.getPrefWidth());
				vBoxUserMenu.getChildren().add(textField);
				vBoxUserMenu.setAlignment(Pos.TOP_LEFT);
				
				totalHeight = totalHeight + labelHeight + textFieldHeight;
			}
			vBoxUserMenu.setPrefHeight(totalHeight);
			
		
		}
		else if(action == "delete")
		{
			labelTitle.setText("USUÑ DANE W TABLICY " + SQLTable);
			
			// Prepare arrays
			insertLabels = new Label[2];
			insertTextFields = new TextField[1];
			
			
			Label label = new Label();
			insertLabels[0] = label;
			label.setPrefHeight(labelHeight); 
			label.setPrefWidth(vBoxUserMenu.getPrefWidth());
			label.setAlignment(Pos.BOTTOM_CENTER);
			vBoxUserMenu.getChildren().add(label);
			label.setText("Proszê wybraæ wiersz na podstawie wartoœci z parametru " + columnsNames[0]);
			
			TextField textField = new TextField();
			insertTextFields[0] = textField;
			textField.setPrefHeight(textFieldHeight); 
			textField.setPrefWidth(vBoxUserMenu.getPrefWidth());
			vBoxUserMenu.getChildren().add(textField);
			vBoxUserMenu.setAlignment(Pos.TOP_LEFT);
			
			Label labelDes = new Label();
			insertLabels[1] = labelDes;
			labelDes.setPrefHeight(2*labelHeight); 
			labelDes.setPrefWidth(vBoxUserMenu.getPrefWidth());
			labelDes.setAlignment(Pos.TOP_RIGHT);
			labelDes.setWrapText(true);
			vBoxUserMenu.getChildren().add(labelDes);
			labelDes.setText("Po klikniêciu przycisku zatwierdz zostanie usuniêty rekord z podana wartoœcia. Po zatiwerdzeniu nie mo¿na cofnaæ operacji.");

			totalHeight = labelHeight + textFieldHeight + labelDes.getPrefHeight();
			
			vBoxUserMenu.setPrefHeight(totalHeight);
			
		}
	}
	
	@FXML
	void addData(ActionEvent event)
	{
		action = "add";
		fillVBox();
	}
	
	@FXML
	void deleteData(ActionEvent event) 
	{
		action = "delete";
		fillVBox();
	}
	
	@FXML
	void modifyData(ActionEvent event) 
	{
		action = "modify";
		fillVBox();
	}
	
	@FXML
	void closeWindow(ActionEvent event) 
	{
		Stage stage = (Stage) buttonClose.getScene().getWindow();
		stage.close();
	}
	
	@FXML
	void confirmAction(ActionEvent event) 
	{
		// Check for the suspicious signs
		String validationResults = validateUserInput();
		if(validationResults != "OK")
		{
			return;
		}
		// Check if mandatory values are provided
		if(checkForMandatoryValues() == false)
		{
			return;
		}
		// Check types of provided values 
		if(checkTypesOfInput() == false)
		{
			return;
		}
		// Create SQL command
		String SQLCommand = createCommandForSQL();
		// Get data from SQL
		System.out.println("Action in progress");
		System.out.println(SQLCommand);
		Statement stmt;
		try 
		{
			stmt = conn.createStatement();
			stmt.executeUpdate(SQLCommand);
			System.out.println("Action completed");
			stmt.close();
			
		}
		catch(SQLException e)
		{
			System.out.println(e.getMessage());
			System.out.println(e.getErrorCode());
			
			Alert error = new Alert(AlertType.ERROR);
			switch(e.getErrorCode())
			{
			case 2291:
				error.setContentText("Jeden lub wiêcej z podanych kluczy obcych ( po³aczeñ z inna tablica ) nie ma odpowiednika.");
			break;
			case 12899:
				error.setContentText(e.getMessage());
			break;
			case 2:
				error.setContentText(e.getMessage());
			break;
			case 3:
				error.setContentText(e.getMessage());
			break;
			case 4:
				error.setContentText(e.getMessage());
			break;
			}
			error.show();
		}
		
	}
	
	private boolean validateText(String text)
	{
		// Function will check if the user tried to attack our database using SQLInjection
		Pattern p = Pattern.compile("[^a-z0-9 _/]",Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(text);
		boolean b = m.find();
		return b;
	}
	
	private String validateUserInput()
	{
		String validationRes = "";
		for(int i = 0; i< insertTextFields.length;i++)
		{
			boolean decision;
			decision = validateText(insertTextFields[i].getText());
			if(decision)
			{
				if(action == "add")
				{
					validationRes = validationRes + "B³êdnie wprowadzone dane w polu dotyczacego parametru " + columnsNames[i+1] + "\n";
				}
				else
				{
					validationRes = validationRes + "B³êdnie wprowadzone dane w polu dotyczacego parametru " + columnsNames[i] + "\n";
				}
			}
		}
		if(validationRes == "")
		{
			validationRes = "OK";
		}
		else
		{
			Alert error = new Alert(AlertType.ERROR);
			error.setContentText(validationRes);
			error.show();
		}
		return validationRes;
	}

	private String createCommandForSQL()
	{
		String SQLCommand = "";
		if(action == "add")
		{
			SQLCommand = "INSERT INTO "+SQLTable+" ( ";
			//INSERT INTO table_name (column1, column2, column3, ...) VALUES (value1, value2, value3, ...);
			for(int i = 1; i < numberOfColumns;i++)
			{
				SQLCommand = SQLCommand + columnsNames[i];
				if(i< numberOfColumns - 1)
				{
					SQLCommand = SQLCommand + ", ";
				}
			}
			SQLCommand = SQLCommand + ") VALUES ( ";
			for(int i = 0; i < insertTextFields.length;i++)
			{
				if(columnsTypes[i+1] == "Date")
				{
					SQLCommand = SQLCommand + "TO_DATE('" +insertTextFields[i].getText()+"','dd/mm/yyyy') ";
				}
				else
				{
					SQLCommand = SQLCommand + "'" +insertTextFields[i].getText()+"'";
				}
				if(i<numberOfColumns - 2)
				{
				SQLCommand = SQLCommand + ", ";	
				}
			}
			SQLCommand = SQLCommand + ")";
		}
		else if(action == "modify")
		{
			SQLCommand = "UPDATE "+SQLTable+" SET ";
			boolean isFirst = true;
			//UPDATE table_name SET column1 = value1, column2 = value2, ... WHERE condition;
			for(int i = 1; i < numberOfColumns;i++)
			{
				if(insertTextFields[i].getText().isEmpty() == false)
				{
					if(isFirst == false)
					{
					SQLCommand = SQLCommand + ", ";	
					}
					isFirst = false;
					
					if(columnsTypes[i] == "Date")
					{
						SQLCommand = SQLCommand + columnsNames[i] + " = TO_DATE('" +insertTextFields[i].getText()+"','dd/mm/yyyy') ";
					}
					else
					{
						SQLCommand = SQLCommand + columnsNames[i] + " = '" + insertTextFields[i].getText()+"'";
					}
				}
			}
			SQLCommand = SQLCommand + " WHERE " + columnsNames[0] + " = '"+ insertTextFields[0].getText() + "'";
		}
		else if(action == "delete")
		{
			SQLCommand = "DELETE FROM "+SQLTable+" WHERE ";
			//DELETE FROM table_name WHERE condition;
			SQLCommand = SQLCommand +  columnsNames[0] + " = '"+ insertTextFields[0].getText() + "'";
		}
		System.out.println(SQLCommand);
		return SQLCommand;
	}

	private boolean checkForMandatoryValues()
	{
		boolean isOk = true;
		if(action == "add")
		{
			Alert error = new Alert(AlertType.ERROR);
			String errorMessage = "Wszystkie pola musza byæ wype³nione, je¿eli ma byæ puste to wprowadz wartoœæ NULL. \n";
			for(int i = 0; i<insertTextFields.length;i++)
			{
				if(insertTextFields[i].getText().isEmpty())
				{
					isOk = false;
					errorMessage = errorMessage + "Brakujaca wartoœæ w polu : " + columnsNames[i+1] + ".\n";
				}
			}
			if(isOk == false)
			{
				error.setContentText(errorMessage);
				error.show();
			}
		}
		else if(action == "modify")
		{
			Alert error = new Alert(AlertType.ERROR);
			String errorMessage = "";
			
			int numberOfFilledFields = 0;
			if(insertTextFields[0].getText().isEmpty())
			{
				isOk = false;
				errorMessage = errorMessage + "Pierwsze pole jest wymagane, ¿eby móc wykonaæ operacjê zmodyfikowania rekordu.\n";
			}
			for(int i = 1; i < insertLabels.length;i++)
			{
				if(insertTextFields[i].getText().isEmpty() == false)
				{
					numberOfFilledFields = numberOfFilledFields + 1;
				}
			}
			if(numberOfFilledFields == 0)
			{
				isOk = false;
				errorMessage = errorMessage + "Przynajmniej jedno pole poza pierwszym musi byc wypelnione.\n";
			}
			if(isOk == false)
			{
				error.setContentText(errorMessage);
				error.show();
			}
		}
		else if(action == "delete")
		{
			if(insertTextFields[0].getText().isEmpty())
			{
				isOk = false;
				Alert error = new Alert(AlertType.ERROR);
				error.setContentText("Pole musi mieæ wartoœæ, ¿eby móc wykonaæ rozkaz usuñ.");
				error.show();
			}
		}
		return isOk;
	}

	private String getColumnTypes(int numberType)
	{
		String resultType = "";
		switch(numberType)
		{
		case 12:
			resultType = "String";
		break;
		case 91:
			resultType = "Date";
		break;
		case 2:
			resultType = "int";
		break;
		case 8:
			resultType = "double";
		break;
		case 93:
			resultType = "Date";
		break;
		}
		
		return resultType;
	}

	private boolean checkTypesOfInput()
	{
		boolean isOk = true;
		if(action == "add")
		{
			for(int i = 0; i < insertTextFields.length ;i++)
			{
				if(isStringCastableonType(insertTextFields[i].getText(), columnsTypes[i+1]) == false)
				{
					isOk = false;
				}
			}
		}
		else if(action == "modify")
		{
			for(int i = 0; i < insertTextFields.length ;i++)
			{
				if(insertTextFields[i].getText().isEmpty() == false)
				{
					if(isStringCastableonType(insertTextFields[i].getText(), columnsTypes[i]) == false)
					{
						isOk = false;
					}
				}
			}
		}
		else if(action == "delete")
		{
			if(isStringCastableonType(insertTextFields[0].getText(), columnsTypes[0]) == false)
			{
				isOk = false;
			}
		}
		return isOk;
	}
	
	private boolean isStringCastableonType(String text, String type)
	{
		boolean isCastable = true;
		
		switch(type)
		{
		case "double":
			try
			{
				Double.parseDouble(text);
			}
			catch(NumberFormatException e)
			{
			    isCastable = false;
				Alert error = new Alert(AlertType.ERROR);
				error.setContentText("Wartoœæ "+text+" powinna byæ liczba.");
				error.show();
			}
			break;
		case "int":
			
			try
			{
				Integer.parseInt(text);
			}
			catch(NumberFormatException e)
			{
			    isCastable = false;
				Alert error = new Alert(AlertType.ERROR);
				error.setContentText("Wartoœæ "+text+" powinna byæ liczba bez wartoœci u³amkowych.");
				error.show();
			}
			
			break;
		case "Date":
			
			Pattern p = Pattern.compile("\\d{2}\\/\\d{2}\\/\\d{4}");
			Matcher m = p.matcher(text);
			if(m.find() == false || text.length() != 10 )
	        {
	            isCastable =  false;
				Alert error = new Alert(AlertType.ERROR);
				error.setContentText("Wartoœæ "+text+" powinna byæ data formatu DD/MM/YYYY");
				error.show();
	        }
			
		}
		
		return isCastable;
	}
}

