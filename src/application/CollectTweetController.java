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

import javax.script.ScriptException;

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

public class CollectTweetController implements Initializable {
	
	private TwitterAPI twitterAPI;
	private String keyword;
	private TimeSetting timeSetting;
	
	private TweetModifier tweetModifier;

   @FXML
   private Button collectTweet;
   
   @FXML
   private TextField myTextField;
      
   @FXML
   private Button SearchTweet;
   
   @FXML
   private Button returnBack;
   
   @FXML
   private Button newsTitle;
   
   @FXML
   private Button wikiInfo;
   
   @FXML
   public TextArea showResult;
   
   @FXML
	private ChoiceBox hours;
   
   @FXML
	private ChoiceBox minute;
   
   @FXML
	private ChoiceBox second;


   @Override
   public void initialize(URL location, ResourceBundle resources) {
	   timeSetting = new TimeSetting(hours, minute, second);
   }


   public void collectTweet(ActionEvent event) throws Exception {
	   keyword = myTextField.getText();
	   twitterAPI = new TwitterAPI();
	   twitterAPI.setTotalSecond(timeSetting.getTotalSecond(hours, minute, second));
	   twitterAPI.setArea(showResult);
       twitterAPI.function("collect",keyword);
       

   }
   
   public void searchTweet(ActionEvent event) throws Exception {
	   keyword = myTextField.getText()+"_keywords";
	   twitterAPI = new TwitterAPI();
	   myTextField.setText("Searching");
	 //  twitterAPI.setTable(keyword);
	   twitterAPI.setArea(showResult);
	   twitterAPI.function("search",keyword); 
	   

   }   
   
   public void newsTitle (ActionEvent event) throws IOException, ScriptException {
	   Parent myScene = FXMLLoader.load(getClass().getResource("/application/NewsTitle.fxml"));
		Scene rootScene = new Scene(myScene);
		Stage rootStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		rootStage.setScene(rootScene);
		rootStage.show();
   }
   
   public void wikiInfo (ActionEvent event) throws IOException, ScriptException {
	   
   }
   
   public void returnBack (ActionEvent event) throws IOException {
	   Parent myScene = FXMLLoader.load(getClass().getResource("/application/FrontPage.fxml"));
		Scene rootScene = new Scene(myScene);
		Stage rootStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		rootStage.setScene(rootScene);
		rootStage.show();
	   
   }   

}