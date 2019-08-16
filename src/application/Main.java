package application;
	
import java.io.IOException;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
	private Stage primaryStage;
	private AnchorPane rootScene;
	
    @Override
    public void start(Stage primaryStage) {
    	this.primaryStage = primaryStage;
    	this.primaryStage.setTitle("My Application");
    	
    	myScene();
    	//showClassify();
    }
    
    public void myScene(){
    	
    	try {
    		FXMLLoader loader = new FXMLLoader();
        	loader.setLocation(Main.class.getResource("/application/FrontPage.fxml"));
        	rootScene = (AnchorPane) loader.load();
			
			Scene scene = new Scene(rootScene);
			primaryStage.setScene(scene);
			primaryStage.show();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    /*
    public void showClassify(){
    	try{
    		FXMLLoader loader = new FXMLLoader();
    		loader.setLocation(Main.class.getResource("/application/MyScene.fxml"));
    		AnchorPane classification = (AnchorPane) loader.load();
    		
    		rootScene.setCenter(classification);
    	} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }*/
    
    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
