package application;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.control.ChoiceBox;

public class TimeSetting {
	
	public TimeSetting(ChoiceBox hours, ChoiceBox minute, ChoiceBox second){
		hours.getItems().removeAll(hours.getItems());
		   minute.getItems().removeAll(minute.getItems());
		   second.getItems().removeAll(second.getItems());
		   for(int i=0;i<24;i++){
			   hours.getItems().add(String.valueOf(i));
		   }
		   for(int j=0;j<60;j++){
			   minute.getItems().add(String.valueOf(j));
			   second.getItems().add(String.valueOf(j));
		   }
		   hours.getSelectionModel().select(0);
		   minute.getSelectionModel().select(0);
		   second.getSelectionModel().select(0);
	}
	
	public int getTotalSecond(ChoiceBox hours, ChoiceBox minute, ChoiceBox second){
		String h;
		   String m;
		   String s;
		   h = hours.getSelectionModel().getSelectedItem().toString();
		   m = minute.getSelectionModel().getSelectedItem().toString();
		   s = second.getSelectionModel().getSelectedItem().toString();
		   
		   return Integer.parseInt(h)*60*60 + Integer.parseInt(m)*60 + Integer.parseInt(s);
	}
	
}
