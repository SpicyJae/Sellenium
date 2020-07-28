package fleetup.selenium.webautotest;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.WebElement;

public class Time {
	HashMap<Integer,Integer> time_hm;
	
	public Time(){
		
	}
	
	public String MMDDYYYYFormat(Date date){
		
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		
		return dateFormat.format(date);
		
	}
	
	
	public String MMDDYYYYFormat(String date){
		
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		
		return dateFormat.format(date);
		
	}

	public java.util.Date yyyymmddHHmmssFormat(String date) throws ParseException{
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		java.util.Date result = dateFormat.parse(date);
		
		return result;
		
	}
	
	public java.util.Date yyyymmddHHmmssFormat(java.util.Date date) throws ParseException{
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		java.util.Date result = dateFormat.parse(date.toString());
		
		return result;
		
	}

	
	public java.util.Date HHmmssmmddyyyyFormat(String date) throws ParseException{
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss MM/dd/yyyy");
		
		//String str = dateFormat.format(date.toString());
		
		java.util.Date result = dateFormat.parse(date);
		
		return result;
		
	}
	
	public java.util.Date HHmmssmmddyyyyFormatToyyyyMMddHHmmssFormat(String date) throws ParseException{
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss MM/dd/yyyy");
		
		SimpleDateFormat dateFormat_new = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		java.util.Date result = dateFormat.parse(date);
		
		String new_DateFormat_String = dateFormat_new.format(result);
		
		java.util.Date new_DateFormat_Date = dateFormat_new.parse(new_DateFormat_String);
		
		return new_DateFormat_Date;
		
	}

	public java.util.Date yyyyMMddHHmmssFormat(Date date) throws ParseException{
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		String str = dateFormat.format(date);
		
		java.util.Date result = dateFormat.parse(str);
		
		return result;
	}
	
	public java.util.Date yyyyMMddHHmmssFormat(String click_car_time) throws ParseException {
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		java.util.Date result = dateFormat.parse(click_car_time);
		
		return result;
	
	}

	public long HoursChangeToMiliseconds(String hours){
		
		time_hm = new HashMap<Integer,Integer>();
		
		time_hm.put(0, 3600);
		
		time_hm.put(1, 60);
		
		time_hm.put(2, 1);
				
		List<String> splited_hours = Arrays.asList(hours.split(":"));
		
		int sum = 0;
		
		for( int i = 0 ; i < splited_hours.size() ; i++ ) {
			
			int unit = 0;
			
			for( int j = 0 ; j < splited_hours.get(i).length() ; j++ ){
				
				unit += Character.getNumericValue( splited_hours.get(i).charAt(j) ) * (Math.pow(10, splited_hours.get(i).length() - j - 1 ) );
				
			}
			
			unit = unit * time_hm.get(i);
			
			sum += unit;
			
		}
		
		return sum * 1000;
	
	}


	
}
