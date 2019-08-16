package application;

import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class NewsTitle {
	
	private String category;
	private String URL;
	private String className;
	private String classValue;
	private String element;
	private List<String> newsTitles;
	
	private Database database;
	

	public NewsTitle() throws Exception{
		newsTitles = new ArrayList<>();
		database = new Database();
	}
	
	public List<String> collectTitle() throws IOException, SQLException{
		newsTitles.clear();
		Document doc = Jsoup.connect(URL).get();
		Elements items = doc.select(className+"."+classValue).select(element);
		for(int i=0; i<items.size();i++){
			String item = items.get(i).text();
			if(item.split(" ").length>5){
				newsTitles.add(item);
				//System.out.println(item);
			}
		}
		
		//database.storeNewsTitle(category, newsTitles);
		
		return newsTitles;
	}

	public void setCategory(String category){
		this.category = category;
	}
	
	public void setURL(String URL){
		this.URL = URL;
	}
	
	public void setClassName(String className){
		this.className = className;
	}
	
	public void setClassValue(String classValue){
		this.classValue = classValue;
	}
	
	public void setElement(String element){
		this.element = element;
	}
}
