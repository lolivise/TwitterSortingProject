package application;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class ManageDataController implements Initializable {
	
	private Database database;
	private List<String> data;
	private int pointer;
	private String selectTable;
	private String selectColumn;
	private String selectCategory;
	
	@FXML
	private Button back;
	
	@FXML
	private Button next;
	
	@FXML
	private Button select;
	
	@FXML
	private Button view;
	
	@FXML
	private Button returnBack;
	
	@FXML
	private Label totalNum;
	
	@FXML
	private Label range;
	
	@FXML
	private TextArea showData;
	
	@FXML
	private ChoiceBox tableOption;
	
	@FXML
	private ChoiceBox columOption;
	
	@FXML
	private ChoiceBox categoryOption;
	
	@Override
	public void initialize(URL url, ResourceBundle resourcebundle) {
		data = new ArrayList<>();
		tableOption.getItems().removeAll(tableOption.getItems());		
		try {
			database = new Database();
			tableOption.getItems().addAll(database.getTable());
		} catch (Exception e1) {
			System.out.println(e1.getMessage());
		}
		tableOption.getSelectionModel().select(0);
	}

	public void back (ActionEvent event) {
		if(pointer>0){
			pointer--;
			printResult();
		}
	}
	
	public void next (ActionEvent event) {
		pointer++;
		printResult();
	}
	
	public void view (ActionEvent event) throws SQLException {
		data.clear();
		selectTable = tableOption.getSelectionModel().getSelectedItem().toString();
		selectColumn = columOption.getSelectionModel().getSelectedItem().toString();
		selectCategory = categoryOption.getSelectionModel().getSelectedItem().toString();
		database.readData(selectTable, selectColumn, selectCategory);
		data = database.getData();
		String total = "Total number of "+selectTable+" : "+data.size();
		totalNum.setText(total);
		pointer = 0;
		printResult();
	}	
	
	public void returnBack (ActionEvent event) throws IOException {
		Parent myScene = FXMLLoader.load(getClass().getResource("/application/FrontPage.fxml"));
		Scene rootScene = new Scene(myScene);
		Stage rootStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		rootStage.setScene(rootScene);
		rootStage.show();
	}
	
	public void select (ActionEvent event) throws SQLException{
		String table = tableOption.getSelectionModel().getSelectedItem().toString();
		columOption.getItems().removeAll(columOption.getItems());
		categoryOption.getItems().removeAll(categoryOption.getItems());
		columOption.getItems().addAll(database.getColumns(table));
		categoryOption.getItems().addAll(database.getCategories(table));
		categoryOption.getItems().add("*");
		columOption.getSelectionModel().select(0);
		categoryOption.getSelectionModel().select(0);
	}
	
	private void printResult(){
		showData.clear();
		int start = 0;
		if(pointer!=0){
			start = pointer*50;
		}
		for(int i = start; i<start+50; i++){
			if(i<data.size()){
				showData.appendText((i+1)+" : "+data.get(i).toString()+"\n");
			}else{
				break;
			}
			
		}
	}
	
	
}
