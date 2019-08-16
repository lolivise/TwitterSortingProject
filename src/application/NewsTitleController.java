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
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class NewsTitleController implements Initializable{
	
	private Database database;
	private NewsTitle newsTitle;
	
	private List<String> category;
	private List<String> URL;
	private List<String> className;
	private List<String> classValue;
	private List<String> element;
	
	@FXML
	private Button search;
	
	@FXML
	private Button addWebsite;
	
	@FXML
	private Button returnBack;
	
	@FXML
	private TextArea showResult;
	
	@FXML
	private TextArea showData;
	
	@FXML
	private ProgressBar progress;
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		category = new ArrayList<>();
		URL = new ArrayList<>();
		className = new ArrayList<>();
		classValue = new ArrayList<>();
		element = new ArrayList<>();
		
		try {
			newsTitle = new NewsTitle();
			database = new Database();
			database.readData("website", "category", "*");
			category = database.getData();
			//System.out.println(category.toString());
			
			database.readData("website", "URL", "*");
			URL = database.getData();
			//System.out.println(URL.toString());
			
			database.readData("website", "className", "*");
			className = database.getData();
			//System.out.println(className.toString());
			
			database.readData("website", "classValue", "*");
			classValue = database.getData();
			//System.out.println(classValue.toString());
			
			database.readData("website", "element", "*");
			element = database.getData();
			//System.out.println(element.toString());
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
	}
	
	public void search (ActionEvent event) throws IOException, SQLException {
		showResult.setText("");
		for(int i = 0; i<URL.size(); i++){
			List<String> titleList = new ArrayList<>();
			newsTitle.setURL(URL.get(i).toString());
			//System.out.println(URL.get(i).toString());
			
			newsTitle.setClassName(className.get(i).toString());
			//System.out.println(className.get(i).toString());
			
			newsTitle.setClassValue(classValue.get(i));
			//System.out.println(classValue.get(i).toString());
			
			newsTitle.setElement(element.get(i));
			//System.out.println(element.get(i).toString());
			
			newsTitle.setCategory(category.get(i));
			//System.out.println(category.get(i).toString());
			
			titleList = newsTitle.collectTitle();
			System.out.println("Collect Done!");
			progress.setProgress((double)(i+1)/(double)(URL.size()));
			
			
			for(String title: titleList){
				showResult.appendText(category.get(i)+" - "+title+"\n");
			}
			
			
			
			database.storeNewsTitle(category.get(i), titleList);
		}
		showDataNum();
	}
	
	public void addWebsite (ActionEvent event) throws IOException {
		switchPage(event,"AddWebsite.fxml");
	}

	public void returnBack (ActionEvent event) throws IOException {
		switchPage(event,"FrontPage.fxml");
	}
	
	private void showDataNum() throws SQLException{
		List<String> cateList = new ArrayList<>();
		for(String c: category){
			if(!cateList.contains(c)){
				cateList.add(c);
			}
		}
		showData.setText("");
		for(String cate:cateList){
			database.readData("newstitle", "category", cate);
			showData.appendText(cate+": "+database.getData().size()+"\n");
		}
		
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
