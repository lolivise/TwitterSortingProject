package application;

import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import twitter4j.HashtagEntity;
import twitter4j.Status;

public class Database {
	
	private Connection conn;
	private Statement state;
	private ResultSet result;
	private MsgModifier msgModifier;
	
	private String text;
	private int rtCount;
	private double latitude;
	private double longitude;
	private List<String> tagList;
	private List<String> words;
	
	private List<String> tables;
	private List<String> columns;
	private List<String> categories;
	
	private List<String> data;
	
	public Database() throws Exception{
		tagList = new ArrayList<>();
		words = new ArrayList<>();
		data = new ArrayList<>();
		msgModifier = new MsgModifier();
		login();
	}
	
	public void login() throws Exception{
		Class.forName("com.mysql.jdbc.Driver");
        conn = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/demo", "root", "963852");
        state = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
	}
	
	public void storeTweet(String category){
		String sql = "insert into tweetmesg values('"+category+"','"+text+"',"+latitude+","+longitude+","+rtCount+")";
		try {
			state.executeUpdate(sql);
			storeContainWords("keywords",category);
			storeHashtag(category);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void storeNewsTitle(String category, List<String> newsTitles){
		//System.out.println(category);
		for(String title:newsTitles){
			title = title.replaceAll("'", "");
			String sql = "insert into newstitle values('"+category+"','"+title+"')";
			try {
				state.executeUpdate(sql);
				setNewsContainsWords(title);
				storeContainWords("wordsfromtitle",category);
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		}
	}
	
	public void storeWebSiteInfo(String category, String URL, String className, String classValue, String element) throws SQLException{
		String sql = "insert into website values('"+category+"','"+URL+"','"+className+"','"+classValue+"','"+element+"')";
		state.executeUpdate(sql);
	}
	
	public void readData(String table, String colum, String category) throws SQLException{
		data = new ArrayList<>();
		String sql = "";
		//System.out.println(colum);
		if(category.equals("*")){
			sql = "select "+colum+" from "+table;
		}else{
			sql = "select "+colum+" from "+table+" where category Like '"+category+"'";
		}
		result = state.executeQuery(sql);
		while(result.next()){
			String text = result.getString(colum);
			data.add(text);
		}
	}
	
	public void closeDB(){
		try { if (result != null) result.close(); } catch (Exception e) {};
        try { if (state != null) state.close(); } catch (Exception e) {};
        try { if (conn != null) conn.close(); } catch (Exception e) {};
	}
	
	public void setTweetInfo(Status status){
		latitude  = 0.0;
		longitude = 0.0;
		words.clear();
		tagList.clear();
		
		text = msgModifier.modifyTweet(status.getText());
		rtCount = status.getRetweetCount();
		setTweetContainsWords(text);
		
		for(HashtagEntity tag:status.getHashtagEntities()){
			tagList.add(tag.getText());
		}
		
		if(status.getGeoLocation()!=null){
			latitude = status.getGeoLocation().getLatitude();
			longitude = status.getGeoLocation().getLongitude();
		}
	}
	
	public void setTweetContainsWords(String text){
		words = new ArrayList<>();
		words = msgModifier.getTweetContainWords(text);
	}
	
	public void setNewsContainsWords(String text){
		words = new ArrayList<>();
		words = msgModifier.getNewsContainWords(text);
	}
	
	private void storeContainWords(String table,String category) throws SQLException{
		readData(table,table,category);
		for(String w: words){
			if(data.contains(w)){
				addFreq(table, category, w);
			}else{
				String sql = "insert into "+table+" values('"+category+"','"+w+"',1)";
	    		state.executeUpdate(sql);
	    		data.add(w);
			}
		}
	}
	
	private void storeHashtag(String category) throws SQLException{
		readData("hashtag","hashtag",category);
		for(String tag: tagList){
			if(data.contains(tag)){
				addFreq("hashtag", category, tag);
			}else{
				String sql = "insert into hashtag values('"+category+"','"+tag+"',1)";
	    		state.executeUpdate(sql);
	    		data.add(tag);
			}
		}
	}
	
	private void addFreq(String table, String category, String target) throws SQLException{
		//System.out.println(target+" is duplicated!");
		state = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
		String sql = "select * from "+table+" where category LIKE '"+category+"' AND "+table+" LIKE '"+target+"'";
		result = state.executeQuery(sql);
		//System.out.println(table+" is in category of "+category);
		
		
		while(result.next()){
			int newCount = result.getInt("count")+1;
			try{
				result.updateInt(3, newCount);
				result.updateRow();
				//System.out.println(target+" is update");
			}catch(Exception e){
				//System.out.println(target+" is not update");
			}
		}
	}
	
	/*get method*/
	public List<String> getData(){
		return data;
	}
	
	public List<String> getTable() throws SQLException{
		tables = new ArrayList<>();
		DatabaseMetaData md = conn.getMetaData();
		result = md.getTables(null, null, null, null);
		while(result.next()){
			String tableName = result.getString(3);
			if(!tables.contains(tableName)){
				tables.add(tableName);
			}
		}
		return tables;
	}
	
	public List<String> getColumns(String table) throws SQLException{
		columns = new ArrayList<>();
		DatabaseMetaData md = conn.getMetaData();
		result = md.getColumns(null, null, table, null);
		while(result.next()){
			String columnName = result.getString(4);
			if(!columns.contains(columnName)){
				columns.add(result.getString(4));
			}
		}
		return columns;
	}
	
	public List<String> getCategories(String table) throws SQLException{
		categories = new ArrayList<>();
		readData(table,"category","*");
		for(String c:data){
			if(!categories.contains(c)){
				categories.add(c);
			}
		}
		return categories;
	}
	
	public String getTweet(){
		return text;
	}
}
