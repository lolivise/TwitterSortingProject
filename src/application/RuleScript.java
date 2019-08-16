package application;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.script.*;

public class RuleScript {
	
	private List<String> rules;
	private List<String> listOfWord;
	private List<String> listOfText;
	private WordNetAPI wordNetAPI;
	private Database database;
	private String category;
	private int nWord;
	private List<String> errorMsg;
	private List<String> newsTitleList;
	private List<String> newstitleKeywords;
	private ScriptEngineManager manager;
	private ScriptEngine engine;
	
	public RuleScript() throws Exception{
		rules = new ArrayList<>();
		listOfWord = new ArrayList<>();
		listOfText = new ArrayList<>();
		newsTitleList = new ArrayList<>();
		newstitleKeywords = new ArrayList<>();
		wordNetAPI = new WordNetAPI();
		database = new Database();
		//database.readData("newstitle", "newstitle", category);
		
		manager = new ScriptEngineManager();
		engine = manager.getEngineByName("JavaScript");
		engine.put("nWords", 0);
		engine.put("tweet", "ABCDEFG CDFD GFDS FD");
		engine.put("wordNetAPI",wordNetAPI);
		engine.put("database", database);
		engine.put("listOfWord", listOfWord);
		engine.put("listOfText", listOfText);
		engine.put("newsTitleList", database.getData());
		//engine.put("newstitleKeywords", newstitleKeywords);
		BufferedReader br = new BufferedReader(new FileReader("ScriptCode.txt"));
		String s;
		while((s=br.readLine())!=null){
			engine.eval(s);
		}
		br.close();
	}
	
	public boolean runScript(String newTweet) throws Exception{
		/*database.readData("newstitle", "newstitle", category);
		newsTitleList = database.getData();
		engine.put("newsTitleList", newsTitleList);
		database.readData("wordsfromtitle", "wordsfromtitle", category);
		newstitleKeywords = database.getData();
		engine.put("newstitleKeywords", newstitleKeywords);*/
		
		int numOfSuccess = 0;
		nWord = newTweet.split(" ").length;
		engine.put("nWords", nWord);
		for(String rule: rules){
			if(engine.eval(rule).equals(Boolean.FALSE)){
				return false;
			}
			numOfSuccess++;
		}
		return true;
	}
	
	public boolean checkRules(){
		errorMsg = new ArrayList<>();
		boolean isCorrect = true;
		for(String rule:rules){
			try{
				engine.eval(rule);
			}catch(ScriptException e){
				errorMsg.add(e.getMessage());
				isCorrect = false;
			}
		}
		return isCorrect;
	}
	
	public void setRules(List<String> rules){
		this.rules = rules;
	}
	
	public List<String> getErrorMsg(){
		return errorMsg;
	}
	
	/*public void setCategory(String category){
		this.category = category;
	}*/

}
