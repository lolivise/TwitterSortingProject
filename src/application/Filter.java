package application;

import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Filter {
	
	private Database database;
	private List<String> keywords;
	
	
	public Filter() throws Exception{
		database = new Database();
		keywords = new ArrayList<>();
	}
	
	public void setFilter(String category) throws SQLException{
		database.readData("keywords", "keywords", category);
		keywords = database.getData();
	}
	
	public boolean filter(String newTweet) throws SQLException{
		
		int matchCount = 0;

		for(String keyword: keywords){
			
			if(newTweet.contains(" "+keyword+" ")){
				
				matchCount++;
			}
			if(matchCount>3){
				System.out.println("Found a match tweet:");
				System.out.println(newTweet);
				return true;
			}
		}
		
		System.out.println("Did not match!!!");
		System.out.println("Count: "+matchCount);
		
		
		return false;
	}
	
	public List<String> getKeywords(){
		return keywords;
	}
	
	public void addKeywords(List<String> addkeywords){
		this.keywords = addkeywords;
	}
}
