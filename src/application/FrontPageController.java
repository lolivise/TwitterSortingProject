package application;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import twitter4j.TwitterException;

public class FrontPageController implements Initializable {
	
	@FXML
	private Button collectTweets;
	
	@FXML
	private Button searchTweets;
	
	@FXML
	private Button classification;
	
	@FXML
	private Button manageData;
	
	@Override
	public void initialize(URL url, ResourceBundle resourcebundle) {

	}

	public void collectTweets (ActionEvent event) throws IOException {
		String page = "CollectTweet.fxml";
		switchPage(event, page);
	}
	
	public void searchTweets (ActionEvent event) throws IOException {
		String page = "SearchTweet.fxml";
		switchPage(event, page);
	}
	
	public void classification (ActionEvent event) throws IOException {
		String page = "CostumFilter.fxml";
		switchPage(event, page);
	}
	
	public void manageData (ActionEvent event) throws IOException {
		String page = "ManageDatabase.fxml";
		switchPage(event, page);
	}
	
	private void switchPage(ActionEvent event,String page) throws IOException{
		   String location = "/application/"+page;
		   Parent newPage = FXMLLoader.load(getClass().getResource(location));
		   Scene newScene = new Scene(newPage);
		   Stage newStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		   newStage.setScene(newScene);
		   newStage.show();
	}
	
	
}
