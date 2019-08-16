package application;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.control.TextArea;

import java.sql.*;

public class DataViewer {
	
	List<String> data = new ArrayList<>();
	private Connection conn;
	private Statement myStat;
	private ResultSet myRe;
	private TweetModifier tweetModifier;
	private List<String> colum;
	
	public void dataViewer(String table, String category){
		tweetModifier = new TweetModifier();
		try {
			connectDB();   
        	String sql = "select * from "+table+" where category LIKE '"+category+"'";
            myRe = myStat.executeQuery(sql);
            if(table.equals("tweetmesg")){
            	table = "message"; 
            }
            while (myRe.next()){
            	String newtweet = myRe.getString(table);
            	tweetModifier.cropTweet(newtweet);
            	data.add(tweetModifier.getModTweet());
            }
            
        } catch (Exception ex) {
        	
        }finally{
        	//close connection
        	try { if (myRe != null) myRe.close(); } catch (Exception e) {};
            try { if (myStat != null) myStat.close(); } catch (Exception e) {};
            try { if (conn != null) conn.close(); } catch (Exception e) {};
        }
	}
	
	public List<String> getColum() throws ClassNotFoundException, SQLException{
		connectDB();
		colum = new ArrayList<>();
		String sql1 = "select * from tweetmesg";
		myRe = myStat.executeQuery(sql1);
		while (myRe.next()){
			String categ = myRe.getString("category");
			if(!colum.contains(categ)){
				colum.add(categ);
			}
		}
		return colum;
	}
	
	private void connectDB() throws ClassNotFoundException, SQLException{
		Class.forName("com.mysql.jdbc.Driver");
    	conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/demo", "root", "963852");
    	myStat = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
	}
	
	public List<String> getData(){
		return data;
	}
	
}
