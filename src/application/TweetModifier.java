package application;

import javafx.scene.control.TextArea;

public class TweetModifier {

	private String modTweet;
	private TextArea view;
	
	public TweetModifier(){
		
	}
	
	public void cropTweet(String orgTweet){
		modTweet = orgTweet;
		//System.out.println(modTweet);
		modTweet = modTweet.replaceAll("^RT|\\shttps://[^\\s]+|\\s*@[^\\s]+|\n","");
		modTweet = modTweet.replaceAll("^\\s+", "");
		modTweet = modTweet.replaceAll("[^\\w\\s#]", "");
		modTweet = modTweet.replaceAll("\\s+", " ");
	}
	
	public String getModTweet(){
		return modTweet;
	}
	
}
