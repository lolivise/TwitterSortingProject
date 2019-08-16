package application;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddWebsiteController implements Initializable{

	private NewsTitle newsTitle;
	private List<String> titleList;
	private Database database; 
	private boolean stored;
	
	@FXML
	private TextField inputURL;
	
	@FXML
	private TextField className;
	
	@FXML
	private TextField classValue;
	
	@FXML
	private TextField element;
	
	@FXML
	private TextField defCategory;
	
	@FXML
	private Button search;
	
	@FXML
	private Button returnBack;
	
	@FXML
	private Button storeInDatabase;
	
	@FXML
	private TextArea showTitles;
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		stored = true;
		try {
			database = new Database();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
	}
	
	public void search (ActionEvent event) throws Exception {
		titleList = new ArrayList<>();
		newsTitle = new NewsTitle();
		if(!defCategory.getText().isEmpty()&&!inputURL.getText().isEmpty()&&!className.getText().isEmpty()&&!classValue.getText().isEmpty()&&!element.getText().isEmpty()){
			newsTitle.setCategory(defCategory.getText());
			newsTitle.setURL(inputURL.getText());
			newsTitle.setClassName(className.getText());
			newsTitle.setClassValue(classValue.getText());
			newsTitle.setElement(element.getText());
			titleList = newsTitle.collectTitle();
			showTitles.setText("");
			for(String title:titleList){
				showTitles.appendText(title+"\n");
			}
			stored = false;
		}else{
			showTitles.setText("Please complete the form!");
		}		
		
	}
	
	public void storeInDatabase (ActionEvent event) throws SQLException {
		String category = defCategory.getText();
		if(!category.isEmpty()&&!titleList.isEmpty()&&!stored){
			database.storeWebSiteInfo(category, inputURL.getText(), className.getText(), classValue.getText(), element.getText());
			database.storeNewsTitle(category, titleList);
			stored = true;
			reset();
		}
	}

	public void returnBack (ActionEvent event) throws IOException {
		Parent myScene = FXMLLoader.load(getClass().getResource("/application/FrontPage.fxml"));
		Scene rootScene = new Scene(myScene);
		Stage rootStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		rootStage.setScene(rootScene);
		rootStage.show();
	}
	
	private void reset(){
		inputURL.setText("");
		className.setText("");
		classValue.setText("");
		element.setText("");
		defCategory.setText("");
		showTitles.setText("");
	}

}
