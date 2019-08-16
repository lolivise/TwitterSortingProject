package application;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.script.ScriptException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;

public class CostumFilterController implements Initializable{
	
	private List<String> rules;
	private List<String> errorMsg;
	private RuleScript ruleScript;
	private TwitterAPI twitterAPI;
	private TimeSetting timeSetting;
	private boolean isCorrect;
	

	@FXML
	private Button addRule;
	
	@FXML
	private Button checkRule;
	
	@FXML
	private Button startSearch;
	
	@FXML
	private Button returnBack;
	
	@FXML
	private ChoiceBox optionList;
	
	@FXML
	private ChoiceBox hours;
   
   @FXML
	private ChoiceBox minute;
   
   @FXML
	private ChoiceBox second;
		
   @FXML
	private TextField keyword;
   
   @FXML
	private TextField weightOfTweet;
   
   @FXML
	private TextField weightOfTitle;
   
   @FXML
	private TextField weightOfRulse;
   
   @FXML
	private TextField threshold;
	
	@FXML
	private TextArea ruleList;
	
	@FXML
	private TextArea showResult;
	
	
	@Override
	public void initialize(URL url, ResourceBundle resourcebundle) {
		timeSetting = new TimeSetting(hours, minute, second);
		rules = new ArrayList<>();
		try {
			twitterAPI = new TwitterAPI();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			ruleScript = new RuleScript();
		} catch ( Exception e) {
			e.printStackTrace();
		}
		isCorrect = true;
		
		/*Set Choice Box*/
		optionList.getItems().removeAll(optionList.getItems());
		optionList.getItems().addAll("nWords: Number of words in a tweet (e.g: nWords>5)", 
									 "nTime('word'): the number of time that the word occurs",
									 "AntiWord('word'): Reject a tweets if it contain these words",
									 "hasSynonymOf(['word']):",
									 "hasAntonymOf(['word']):",
									 "sameHypernymOf(['word']):",
									 "hasSynonymOf(getnewstitle('word')):");
		optionList.getSelectionModel().select(0);
	}
	
	@FXML
	public void addRule (ActionEvent event){
		buildRuleList();
		String newRule = optionList.getSelectionModel().getSelectedItem().toString();
		newRule = newRule.substring(0, newRule.indexOf(':'));
		if(!rules.contains(newRule)){
			rules.add(newRule);
		}
		ruleList.setText("");
		for(String r:rules){
			ruleList.appendText(r+"\n");;
		}
		
	}
	
	@FXML
	public void checkRule (ActionEvent event) {
		buildRuleList();
		check();
		if(isCorrect){
			showResult.setText("The rules are correctly input!");
		}else{
			showErrorMeg();
		}
	}
	
	@FXML
	public void startSearch (ActionEvent event) throws Exception {
		twitterAPI = new TwitterAPI();
		buildRuleList();
		check();
		if(!keyword.getText().isEmpty()){
			if(isCorrect){
				twitterAPI.setTotalSecond(timeSetting.getTotalSecond(hours, minute, second));
				twitterAPI.setArea(showResult);
				twitterAPI.setCostomFilter(rules);
				twitterAPI.function("CostumFilter", keyword.getText());
			}else{
				showErrorMeg();
			}
		}else{
			showResult.setText("Please enter a keyword for searching");
		}
	}
	
	@FXML
	public void returnBack (ActionEvent event) throws IOException {
		Parent myScene = FXMLLoader.load(getClass().getResource("/application/FrontPage.fxml"));
		Scene rootScene = new Scene(myScene);
		Stage rootStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		rootStage.setScene(rootScene);
		rootStage.show();
	}
	
	private void check(){
		ruleScript.setRules(rules);
		isCorrect = ruleScript.checkRules();
	}
	
	private void showErrorMeg(){
		errorMsg = new ArrayList<>();
		errorMsg = ruleScript.getErrorMsg();
		showResult.setText("");
		for(String e:errorMsg){
			showResult.appendText(e+"\n");
		}
	}
	
	private void buildRuleList(){
		rules = new ArrayList<>();
		if(ruleList.getText().length()>0){
			for(String str: ruleList.getText().split("\\n")){
				rules.add(str);
			}
		}
		
	}
}
