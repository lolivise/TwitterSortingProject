package application;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.script.ScriptException;

import javafx.scene.control.TextArea;
import twitter4j.FilterQuery;
import twitter4j.GeoLocation;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.conf.ConfigurationBuilder;

public class TwitterAPI {
	
	private ConfigurationBuilder cb;
	private Database database;
	private RuleScript ruleScript;
	private Filter filter;
	private int matchCount;
	private String tweet;
	private TweetModifier tweetModifier;
	private TextArea scene;
	private TwitterStream twitterStream;
	private long start;
	private double eclipse;
	private int totalSeconds;
	
	public TwitterAPI() throws Exception{
		totalSeconds = 0;
		cb = new ConfigurationBuilder();
		database = new Database();
		filter = new Filter();
		tweetModifier = new TweetModifier();
		ruleScript = new RuleScript();
	}
	
	/*Access token*/
	public void configurate(){
		cb.setDebugEnabled(true)
		  .setOAuthConsumerKey("gIgMHLe9CYsPeahuhKobdFA9E")
		  .setOAuthConsumerSecret("YLr2UZrcO01k6aODN5NJDIbvuxi3xRSVSIRi8hTCoZEXqtoGRS")
		  .setOAuthAccessToken("834242086350106624-LEFn3VQaGkYGIGv0WNIQB23ltSqr2LR")
		  .setOAuthAccessTokenSecret("5kG4mqvqfPkLiK7ExiHugdlxn0i7AJnQPjGtp04ueaIdK");
	}
	
	
	public void function(String func,String keyword) throws Exception{
		
		filter.setFilter(keyword);
		//ruleScript.setCategory(keyword);
		configurate();
		scene.setText("");
		twitterStream = new TwitterStreamFactory(cb.build()).getInstance();
        StatusListener listener = new StatusListener() {
            public void onStatus(Status status) {
            	if(status.isRetweet())
            	status = status.getRetweetedStatus();
            	System.out.println(status.getText());
            	if(func.equals("CostumFilter")){
            		tweet = status.getText();
            		try {
						if(ruleScript.runScript(tweet)){
							if(filter.filter(tweet)){
	            				scene.appendText(tweet+"\n");
	            			}
						}
					} catch ( Exception e) {
						System.out.println(e.getMessage());
					}
            		timeCheck();

            	}else{
            		database.setTweetInfo(status);
            		database.storeTweet(keyword);
            		scene.appendText(database.getTweet()+"\n");
            		timeCheck();
            	}
            }

            public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {}
            public void onTrackLimitationNotice(int numberOfLimitedStatuses) {}
            public void onScrubGeo(long userId, long upToStatusId) {}

            @Override
			public void onStallWarning(StallWarning warning) {}
            public void onException(Exception ex) {ex.printStackTrace();}		
        };
        
        twitterStream.addListener(listener);
        FilterQuery filterQuery = new FilterQuery();
        String[] keyWord = {keyword};
        filterQuery.track(keyWord);
        filterQuery.language(new String[]{"en"});
        twitterStream.filter(filterQuery);
        if(totalSeconds==0){stop();}
        start = System.nanoTime();

	}
	
	private void stop(){
		twitterStream.cleanUp();
        twitterStream.shutdown();
	}
	
	private void timeCheck(){
		eclipse = (double)(System.nanoTime()-start)/1000000000.0;
		if(eclipse>totalSeconds){
			stop();
			System.out.println("Time is up!!!");
		}
	}
	
	public void setArea(TextArea scene){
		this.scene = scene;
		scene.setText("");
	}
	
	public void setTotalSecond(int totalSeconds){
		this.totalSeconds = totalSeconds;
	}
	
	public void setFilterKey(List<String> keywords){
		filter.addKeywords(keywords);
	}
	
	public void setCostomFilter(List<String> rules){
		ruleScript.setRules(rules);
	}
}
