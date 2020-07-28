package fleetup.selenium.webautotest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.openqa.selenium.WebDriver;
/*
 * This is using for distributing the alerts by alert type
 */
public class AlertSort {
	/*
	 * Total Alerts
	 */
	private ArrayList<NameValuePair> Total_array;
	/*
	 * Specific alert
	 */
	private ArrayList<NameValuePair> Speeding_array;
	private ArrayList<NameValuePair> RPM_array;
	private ArrayList<NameValuePair> Idling_array;
	private ArrayList<NameValuePair> Excessive_array;
	private ArrayList<NameValuePair> GeoFence_array;
	private ArrayList<NameValuePair> Driving_Event_array;
	private ArrayList<NameValuePair> DTC_array;
	private ArrayList<NameValuePair> Engine_Temp_array;
	private ArrayList<NameValuePair> Low_Voltage_array;
	private ArrayList<NameValuePair> Panic_array;
	private ArrayList<NameValuePair> Device_Plug_UnPlug_array;
	private ArrayList<NameValuePair> Vehicle_Alerts_array;
	/*
	 * initialize
	 */
	public AlertSort(ArrayList<NameValuePair> arrayList) {
		Total_array = new ArrayList<NameValuePair>();
		Speeding_array = new ArrayList<NameValuePair>();
		RPM_array = new ArrayList<NameValuePair>();
		Idling_array = new ArrayList<NameValuePair>();
		Excessive_array = new ArrayList<NameValuePair>();
		GeoFence_array = new ArrayList<NameValuePair>();
		Driving_Event_array = new ArrayList<NameValuePair>();
		DTC_array = new ArrayList<NameValuePair>();
		Engine_Temp_array = new ArrayList<NameValuePair>();
		Low_Voltage_array = new ArrayList<NameValuePair>();
		Panic_array = new ArrayList<NameValuePair>();
		Device_Plug_UnPlug_array = new ArrayList<NameValuePair>();
		Vehicle_Alerts_array = new ArrayList<NameValuePair>();
		this.Total_array.addAll(arrayList);
	}
	/*
	 * Distribue total array to specific array
	 */
	public void DivideArray(){
		for(NameValuePair pair : this.Total_array){
			if(pair.getName().equals("Speeding")){
				Speeding_array.add(new BasicNameValuePair(pair.getName(),pair.getValue()));
				Driving_Event_array.add(new BasicNameValuePair(pair.getName(),pair.getValue()));
			}
			else if(pair.getName().equals("High RPM")){
				RPM_array.add(new BasicNameValuePair(pair.getName(),pair.getValue()));
				Driving_Event_array.add(new BasicNameValuePair(pair.getName(),pair.getValue()));
			}
			
			else if(pair.getName().equals("Idling")){
				Idling_array.add(new BasicNameValuePair(pair.getName(),pair.getValue()));
				Driving_Event_array.add(new BasicNameValuePair(pair.getName(),pair.getValue()));
			}
			else if(pair.getName().equals("Excessive Driving Alert")){
				Excessive_array.add(new BasicNameValuePair(pair.getName(),pair.getValue()));
				Driving_Event_array.add(new BasicNameValuePair(pair.getName(),pair.getValue()));
			}
			else if(pair.getName().equals("Entering Zone")){
				GeoFence_array.add(new BasicNameValuePair(pair.getName(),pair.getValue()));
				Driving_Event_array.add(new BasicNameValuePair(pair.getName(),pair.getValue()));
			}
			else if(pair.getName().equals("Leaving Zone")){
				GeoFence_array.add(new BasicNameValuePair(pair.getName(),pair.getValue()));
				Driving_Event_array.add(new BasicNameValuePair(pair.getName(),pair.getValue()));
			}
			else if(pair.getName().equals("DTC")){
				DTC_array.add(new BasicNameValuePair(pair.getName(),pair.getValue()));
				Vehicle_Alerts_array.add(new BasicNameValuePair(pair.getName(),pair.getValue()));
			}
			else if(pair.getName().equals("Engine Temperature")){
				Engine_Temp_array.add(new BasicNameValuePair(pair.getName(),pair.getValue()));
				Vehicle_Alerts_array.add(new BasicNameValuePair(pair.getName(),pair.getValue()));
			}
			else if(pair.getName().equals("Battery: Low-Voltage")){
				Low_Voltage_array.add(new BasicNameValuePair(pair.getName(),pair.getValue()));
				Vehicle_Alerts_array.add(new BasicNameValuePair(pair.getName(),pair.getValue()));
			}
			else if(pair.getName().equals("Panic")){
				Panic_array.add(new BasicNameValuePair(pair.getName(),pair.getValue()));
				Vehicle_Alerts_array.add(new BasicNameValuePair(pair.getName(),pair.getValue()));
			}
			else if(pair.getName().equals("Device Unplugged")){
				Device_Plug_UnPlug_array.add(new BasicNameValuePair(pair.getName(),pair.getValue()));
				Vehicle_Alerts_array.add(new BasicNameValuePair(pair.getName(),pair.getValue()));
			}
			else if(pair.getName().equals("Device Plugged")){
				Device_Plug_UnPlug_array.add(new BasicNameValuePair(pair.getName(),pair.getValue()));
				Vehicle_Alerts_array.add(new BasicNameValuePair(pair.getName(),pair.getValue()));
			}
		}
	}
	
	/*
	 * To return array
	 */
	public ArrayList<NameValuePair> GetSpeedingArray(){
		return this.Speeding_array;
	}
	public ArrayList<NameValuePair> GetRPMArray(){
		return this.RPM_array;
	}
	public ArrayList<NameValuePair> GetIdlingArray(){
		return this.Idling_array;
	}
	public ArrayList<NameValuePair> GetExcessiveArray(){
		return this.Excessive_array;
	}
	public ArrayList<NameValuePair> GetGeoFenceArray(){
		return this.GeoFence_array;
	}
	public ArrayList<NameValuePair> GetDrivingEventArray(){
		return this.Driving_Event_array;
	}
	public ArrayList<NameValuePair> GetDTCArray(){
		return this.DTC_array;
	}
	public ArrayList<NameValuePair> GetEngineTempArray(){
		return this.Engine_Temp_array;
	}
	public ArrayList<NameValuePair> GetLowVoltageArray(){
		return this.Low_Voltage_array;
	}
	public ArrayList<NameValuePair> GetPanicArray(){
		return this.Panic_array;
	}
	
	public ArrayList<NameValuePair> GetDevicePlugUnPlugArray(){
		return this.Device_Plug_UnPlug_array;
	}
	public ArrayList<NameValuePair> GetVehicleAlertsArray(){
		return this.Vehicle_Alerts_array;
	}

}
