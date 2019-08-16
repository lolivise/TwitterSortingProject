package application;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

public class MsgModifier {
	
	private WordNetAPI wordNetAPI;
	private List<String> containWords;
	
	public MsgModifier() throws MalformedURLException, IOException{
		wordNetAPI = new WordNetAPI();
		containWords = new ArrayList<>();
	}
	
	public String modifyTweet(String msg){
		msg = msg.replaceAll("\\shttps://[^\\s]+|\\s*@[^\\s]+|\n|\\d+","");
		msg = msg.replaceAll("\\s*#[^\\s]+", "");
		msg = msg.replaceAll("^\\s+", "");
		msg = msg.replaceAll("[^\\w\\s#]", "");
		msg = msg.replaceAll("\\s+", " ");
		//System.out.println(msg);
		return msg;
	}
	
	public List<String> getTweetContainWords(String msg){
		containWords.clear();
		msg = msg.toLowerCase();
		String[] words = msg.split(" ");
		for(String word: words){
			if(wordNetAPI.isExist(word)){
				containWords.add(word);
			}
		}
		return containWords;
	}
	
	public List<String> getNewsContainWords(String msg){
		containWords.clear();
		msg = msg.toLowerCase();
		msg = msg.replaceAll("[,;:.?!'_]"," ");
		String[] words = msg.split(" ");
		for(String word: words){
			containWords.add(word);
		}
		return containWords;
	}
}
