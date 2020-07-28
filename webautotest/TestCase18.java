package fleetup.selenium.webautotest;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.fail;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
/*
 * Comparing the alert data between Vehicle Tab and Alert Tab 
 * Comparing the odometer value among ( Alert > Vehicle Alerts ) & ( e-Maintenance ) & ( Device Setting )  
 */
public class TestCase18 extends JunitMain {
	
	private static final int VEHICLE_VEHICLE_COL = 3;
	private static final int VEHICLE_ALERT_COL = 12;
	private static final int ALERT_TYPE_INDEX = 2;
	private static final int ALERT_TYPE_INDEX_2 = 3;
	private static final int ALERT_TIME_INDEX = 0;
	private static final int DRIVING_EVENT_LICENSE_NO = 1;
	private static final int Vehicle_Alert_LICENSE_NO = 1;
	private static final int Vehicle_Alert_odometer = 2;
	private static final int DRIVING_EVENT_SPEEDING = 2;
	private static final int DRIVING_EVENT_RPM = 3;
	private static final int ALERT_DETAIL_TIME = 0;
	private static final int ALERT_DETAIL_ALERT = 2;
	private static final int ALERT_DETAIL_ALERT_2 = 3;
	private static final int DRIVING_EVENT_Idling = 4;
	/*
	 * Bug 2704 : Remove Excessive Driving
	 */
//	private static final int DRIVING_EVENT_Excessive = 5;
//	private static final int DRIVING_EVENT_GeoFence = 6;
//	private static final int DRIVING_EVENT_Driving_Event = 7;
	private static final int DRIVING_EVENT_GeoFence = 5;
	private static final int DRIVING_EVENT_Driving_Event = 6;
	private static final int VEHICLE_ALERT_License_No = 1;
	private static final int VEHICLE_ALERT_DTC = 4;
	private static final int VEHICLE_ALERT_Engine_Temp = 5;
	private static final int VEHICLE_ALERT_Low_Voltage = 6;
	private static final int VEHICLE_ALERT_Panic = 7;
	private static final int VEHICLE_ALERT_Device_Plug_UnPlug = 8;
	private static final int VEHICLE_ALERT_Alerts = 9;
	private static final int Device_Setting_License_No = 1;
	private static final int Maintenance_License_No = 1; 
	private static final int Maintenance_Odometer_value = 4;
	private static final int ALERT_TRIG_INDEX = 0;
	
	@Rule
    public ErrorCollector collector = new ErrorCollector();
	
	@Test
	public void run(){
		try{
			
			LoginPage loginPage = PageFactory.initElements(driver, LoginPage.class);
			SelectTab selectPage = loginPage.loginAs(acct1_id,acct1_pw);
			WebDriverWait wait = new WebDriverWait(driver, 100); 
			wait.until(ExpectedConditions.jsReturnsValue("return document.readyState==\"complete\";"));
			/*
			 * Declare
			 */
			HashMap<String,ArrayList<NameValuePair>> vehicleTimeAlertHm = new HashMap<String,ArrayList<NameValuePair>>();
			HashMap<String,String> alertTabOdometer = new HashMap<String,String>();
			HashMap<String,String> deviceSettingOdometer = new HashMap<String,String>();
			HashMap<String,String> maintenanceOdometer = new HashMap<String,String>();
			By VehicleListLocator = By.xpath("//*[@id='fuelIndexTbody']/tr");
			By TDLocator = By.tagName("td");
			By AlertListLocator = By.xpath("//*[@class='adScrollTable st-container']//*[@class='st-body-table']/tbody/tr");
			By AlertListWait = By.xpath("//*[@class='adScrollTable st-container']//*[@class='st-body-table']/tbody");
			By TitleLocator = By.xpath("//*[@class='popup_cont alert_details']//*[@class='content']//*[@class='title']//label");
			By PopUpCloseLocator = By.xpath("//*[@id='alertDetailDialog']//*[@class='popup_cont alert_details']/div/h1//a");
			By DrivingEventsTable = By.xpath("//*[@id='Alert_content']//*[@id='alert_menu_01_cont']//*[@id='alertIndexTbody']//tr");
			By details_Table_Locator = By.xpath("//*[@id='alertDetailTableDiv']//tbody//tr//tbody//tr");
			By PopUpCloseBtn = By.xpath("//*[@id='alertDetailDialog']//*[@class='popup_cont alert_details']//*[@class='btn_close']");
			By Vehicle_Alert_Tab = By.id("alert_menu_02");
			By VehicleAlertTable = By.xpath("//*[@id='Alert_content']//*[@id='alert_menu_02_cont']//*[@id='vehiclealertIndexTbody']//tr");
			By Device_Tab_VehicleListLocator = By.xpath("//*[@id='clientDeviceIndexTbody']/tr");
			By Device_Setting_odomter_subtab = By.xpath("//*[@class='popup_cont deviceSetting_popup']//*[@id='tab_menu_04']");
			//By odometerLocator = By.xpath("//*[@id='device_setting']//*[@id='tab_menu_04_cont']//*[@id=lbl_current]");
			By odometerLocator = By.id("Mil_odo");
			By DS_close_button = By.xpath("//*[@id='device_setting']//*[@class='popup_cont deviceSetting_popup']//*[@class='btn_close']");
			By Maintenance_VehicleListLocator = By.xpath("//*[@id='fuelIndexTbody']/tr");
			Time tm = new Time();
			java.util.Date alertTrigTime;
			/*
			 * Change the date 
			 * start day = current day - 10 days 
			 * end day = current day 
			 */
			DateRange dateRange = PageFactory.initElements(driver, DateRange.class);
			Calendar currentStartTimeCal = Calendar.getInstance();
			Calendar currentEndTimeCal = Calendar.getInstance();
			if( Calendar.getInstance().get(Calendar.DAY_OF_MONTH) >= 16 ){
				dateRange.ChangeStartDateRange(Calendar.getInstance().get(Calendar.YEAR),Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH) - 15);
				dateRange.ChangeEndDateRange(Calendar.getInstance().get(Calendar.YEAR),Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
				currentStartTimeCal.set(Calendar.getInstance().get(Calendar.YEAR),Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH) - 15 ,0, 0, 0);
				currentEndTimeCal.set(Calendar.getInstance().get(Calendar.YEAR),Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH), 23,	59, 59);
			}
			else{
				dateRange.ChangeStartDateRange(Calendar.getInstance().get(Calendar.YEAR),Calendar.getInstance().get(Calendar.MONTH)-1, 15);
				dateRange.ChangeEndDateRange(Calendar.getInstance().get(Calendar.YEAR),Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
				currentStartTimeCal.set(Calendar.getInstance().get(Calendar.YEAR),Calendar.getInstance().get(Calendar.MONTH) - 1 , 15,0, 0, 0);
				currentEndTimeCal.set(Calendar.getInstance().get(Calendar.YEAR),Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH), 23,	59, 59);
			}
			/*
			 * Search 
			 */
			dateRange.search();
			wait.until(ExpectedConditions.presenceOfElementLocated(By.id("fuelIndexTbody")));
			List<WebElement> vehicleLists = driver.findElements(VehicleListLocator);
			for(WebElement vehicleList : vehicleLists){
				List<WebElement> vehicleElementList = vehicleList.findElements(TDLocator);
				/*
				 * If the alerts count = '0', we don't need to open that page
				 */
				if(!vehicleElementList.get(VEHICLE_ALERT_COL).getText().equals("0")){
					vehicleElementList.get(VEHICLE_ALERT_COL).click();
					wait.until(ExpectedConditions.presenceOfElementLocated(AlertListWait));
					List<WebElement> alertList = driver.findElements(AlertListLocator);
					String licenseNumber = driver.findElement(TitleLocator).getText();
					licenseNumber = licenseNumber.substring( licenseNumber.indexOf(",") + 2 , licenseNumber.length() );
					collector.checkThat(vehicleElementList.get(VEHICLE_ALERT_COL).getText()+": Actual Table row number != Vehicle Tab number", Integer.parseInt(vehicleElementList.get(12).getText().trim()) ,is(alertList.size()));
					/*
					 * Alert_Time_hm -> All the Alert name & Time  
					 */
					ArrayList<NameValuePair> alertTimeHm = new ArrayList<NameValuePair>();
					for(WebElement alert : alertList){
						List<WebElement> alertElementsList = alert.findElements(TDLocator);
						alertTrigTime = tm.yyyymmddHHmmssFormat((String) alertElementsList.get(ALERT_TRIG_INDEX).getText());
						collector.checkThat(vehicleElementList.get(VEHICLE_VEHICLE_COL).getText()+","+currentStartTimeCal.getTime().getTime()+","+alertTrigTime.getTime()+","+currentEndTimeCal.getTime().getTime()+ ": have Wrong Range of the data", (currentStartTimeCal.getTime().getTime() < alertTrigTime.getTime()) && (currentEndTimeCal.getTime().getTime()> alertTrigTime.getTime()) ,is(true));
						/*
						 * Distribute the alert type
						 */
						if(alertElementsList.get(ALERT_TYPE_INDEX).getText().equals("Speeding")){
							alertTimeHm.add(new BasicNameValuePair(alertElementsList.get(ALERT_TYPE_INDEX).getText(), alertElementsList.get(ALERT_TIME_INDEX).getText()));
						}
						else if(alertElementsList.get(ALERT_TYPE_INDEX).getText().equals("High RPM")){
							alertTimeHm.add(new BasicNameValuePair(alertElementsList.get(ALERT_TYPE_INDEX).getText(), alertElementsList.get(ALERT_TIME_INDEX).getText()));
						}
						else if(alertElementsList.get(ALERT_TYPE_INDEX).getText().equals("Idling")){
							alertTimeHm.add(new BasicNameValuePair(alertElementsList.get(ALERT_TYPE_INDEX).getText(), alertElementsList.get(ALERT_TIME_INDEX).getText()));
						}
						else if(alertElementsList.get(ALERT_TYPE_INDEX).getText().equals("Excessive Driving Alert")){
							alertTimeHm.add(new BasicNameValuePair(alertElementsList.get(ALERT_TYPE_INDEX).getText(), alertElementsList.get(ALERT_TIME_INDEX).getText()));
						}
						else if(alertElementsList.get(ALERT_TYPE_INDEX_2).getText().equals("Entering Zone")){
							alertTimeHm.add(new BasicNameValuePair(alertElementsList.get(ALERT_TYPE_INDEX_2).getText(), alertElementsList.get(ALERT_TIME_INDEX).getText()));
						}
						else if(alertElementsList.get(ALERT_TYPE_INDEX_2).getText().equals("Leaving Zone")){
							alertTimeHm.add(new BasicNameValuePair(alertElementsList.get(ALERT_TYPE_INDEX_2).getText(), alertElementsList.get(ALERT_TIME_INDEX).getText()));
						}
						else if(alertElementsList.get(ALERT_TYPE_INDEX).getText().contains("DTC")){
							alertTimeHm.add(new BasicNameValuePair("DTC", alertElementsList.get(ALERT_TIME_INDEX).getText()));
						}
						else if(alertElementsList.get(ALERT_TYPE_INDEX).getText().equals("Engine Temperature")){
							alertTimeHm.add(new BasicNameValuePair(alertElementsList.get(ALERT_TYPE_INDEX).getText(), alertElementsList.get(ALERT_TIME_INDEX).getText()));
						}
						else if(alertElementsList.get(ALERT_TYPE_INDEX).getText().equals("Battery: Low-Voltage")){
							alertTimeHm.add(new BasicNameValuePair(alertElementsList.get(ALERT_TYPE_INDEX).getText(), alertElementsList.get(ALERT_TIME_INDEX).getText()));
						}
						else if(alertElementsList.get(ALERT_TYPE_INDEX).getText().equals("Panic")){
							alertTimeHm.add(new BasicNameValuePair(alertElementsList.get(ALERT_TYPE_INDEX).getText(), alertElementsList.get(ALERT_TIME_INDEX).getText()));
						}
						else if(alertElementsList.get(ALERT_TYPE_INDEX).getText().equals("Device Unplugged")){
							alertTimeHm.add(new BasicNameValuePair("Device Unplugged", alertElementsList.get(ALERT_TIME_INDEX).getText()));
						}
						else if(alertElementsList.get(ALERT_TYPE_INDEX).getText().equals("Device Plugged")){
							alertTimeHm.add(new BasicNameValuePair("Device Plugged", alertElementsList.get(ALERT_TIME_INDEX).getText()));
						}
					}
					/*
					 * After gathering the alert for one vehicle, add to Vehicle_Time_Alert_hm 
					 * 
					 * Vehicle_Time_Alert_hm -> Vehicle & ( Alert & Time ) -> Vehicle & Alert_Time_hm
					 */
					vehicleTimeAlertHm.put(licenseNumber, alertTimeHm);
					driver.findElement(PopUpCloseLocator).click();
				}
			}
			/*
			 * Go to Alert Tab for comparing the alerts data
			 */
			selectPage.gotoAlert();
			wait.until(ExpectedConditions.presenceOfElementLocated(By.id("alertIndexTbody")));
			/*
			 * Driving event list 
			 */
			List<WebElement> drivingEventList = driver.findElements(DrivingEventsTable);
			for(WebElement drivingEvent : drivingEventList){
				List<WebElement> drivingEventElementList = drivingEvent.findElements(TDLocator);
				/*
				 * Check Driving event list's license # is on the Vehicle Tab alert hashmap
				 */
				String drivingEventLicense = drivingEventElementList.get(DRIVING_EVENT_LICENSE_NO).getText();
				if(vehicleTimeAlertHm.containsKey(drivingEventLicense)){
					/*
					 * Fetch the alerts which have this license #, and divide them by the alert type
					 */
					AlertSort as = new AlertSort(vehicleTimeAlertHm.get(drivingEventLicense));
					as.DivideArray();
					/*
					 * Check the size of speeding array & Driving event count
					 */
					collector.checkThat(drivingEventLicense+ ": Speeding # is different between Vehicle Tab & Alert Tab ", drivingEventElementList.get(DRIVING_EVENT_SPEEDING).getText().trim(),is(Integer.toString( as.GetSpeedingArray().size())));
					if(drivingEventElementList.get(DRIVING_EVENT_SPEEDING).getText().equals( Integer.toString( as.GetSpeedingArray().size() ) ) ){
						as.GetSpeedingArray();
						/*
						 * If the size is not 0, check the inside table information	  
						 */
						if( as.GetSpeedingArray().size() != 0 ) {
							/*
							 * Click Speeding column
							 */
							drivingEventElementList.get(DRIVING_EVENT_SPEEDING).click();
							wait.until(ExpectedConditions.presenceOfElementLocated(AlertListWait));
							List<WebElement> detailList = driver.findElements(details_Table_Locator);
							int row = 0; 
							for(WebElement detail : detailList){
								List<WebElement> detailElementList = detail.findElements(TDLocator);
								collector.checkThat(drivingEventLicense+": Speeding detail", detailElementList.get(ALERT_DETAIL_TIME).getText().trim(),is(as.GetSpeedingArray().get(row).getValue()));
								collector.checkThat(drivingEventLicense+": Speeding detail :", detailElementList.get(ALERT_DETAIL_ALERT).getText().trim(),is(as.GetSpeedingArray().get(row).getName()));
								row++;
							}
							drivingEvent.findElement(PopUpCloseBtn).click();
						}
					}
					/*
					 * Check the size of RPM array & Driving event count
					 */
					collector.checkThat(drivingEventLicense+ ": RPM # is different between Vehicle Tab & Alert Tab ", drivingEventElementList.get(DRIVING_EVENT_RPM).getText().trim(),is(Integer.toString( as.GetRPMArray().size())));
					if ( drivingEventElementList.get(DRIVING_EVENT_RPM).getText().equals(Integer.toString( as.GetRPMArray().size() ) ) ) {
						as.GetRPMArray();
						if(as.GetRPMArray().size()!=0){
							drivingEventElementList.get(DRIVING_EVENT_RPM).click();
							wait.until(ExpectedConditions.presenceOfElementLocated(AlertListWait));
							List<WebElement> detailList = driver.findElements(details_Table_Locator);
							int row = 0; 
							for(WebElement detail : detailList){
								List<WebElement> detailElementList = detail.findElements(TDLocator);
								collector.checkThat(drivingEventLicense+": RPM detail", detailElementList.get(ALERT_DETAIL_TIME).getText().trim(),is(as.GetRPMArray().get(row).getValue()));
								collector.checkThat(drivingEventLicense+": RPM detail :", detailElementList.get(ALERT_DETAIL_ALERT).getText().trim(),is(as.GetRPMArray().get(row).getName()));
								row++;
							}
							drivingEvent.findElement(PopUpCloseBtn).click();
						}
					}
					/*
					 * Check the size of Idling array & Driving event count
					 */ 
					collector.checkThat(drivingEventLicense+ ": Idling # is different between Vehicle Tab & Alert Tab ", drivingEventElementList.get(DRIVING_EVENT_Idling).getText().trim(),is(Integer.toString( as.GetIdlingArray().size())));
					if( drivingEventElementList.get(DRIVING_EVENT_Idling).getText().equals( Integer.toString( as.GetIdlingArray().size() ) ) ) {	  
						as.GetIdlingArray();
						if( as.GetIdlingArray().size() != 0 ) {
							drivingEventElementList.get(DRIVING_EVENT_Idling).click();
							wait.until(ExpectedConditions.presenceOfElementLocated(AlertListWait));
							List<WebElement> detailList = driver.findElements(details_Table_Locator);
							int row = 0; 
							for(WebElement detail : detailList){
								List<WebElement> detailElementList = detail.findElements(TDLocator);
								collector.checkThat(drivingEventLicense+": Idling detail", detailElementList.get(ALERT_DETAIL_TIME).getText().trim(),is(as.GetIdlingArray().get(row).getValue()));
								collector.checkThat(drivingEventLicense+": Idling detail :", detailElementList.get(ALERT_DETAIL_ALERT).getText().trim(),is(as.GetIdlingArray().get(row).getName()));
								row++;
							}
							drivingEvent.findElement(PopUpCloseBtn).click();
						}
					}
					/*
					 * Check the size of Excessive array & Driving event count
					 */ 
					/*collector.checkThat(drivingEventLicense+ ": Excessive Driving # is different between Vehicle Tab & Alert Tab ", drivingEventElementList.get(DRIVING_EVENT_Excessive).getText().trim(),is(Integer.toString( as.GetExcessiveArray().size())));
					if( drivingEventElementList.get(DRIVING_EVENT_Excessive).getText().equals( Integer.toString( as.GetExcessiveArray().size() ) ) ) {
						as.GetExcessiveArray();
						if( as.GetExcessiveArray().size() != 0 ) {
							drivingEventElementList.get(DRIVING_EVENT_Excessive).click();
							wait.until(ExpectedConditions.presenceOfElementLocated(AlertListWait));
							List<WebElement> detailList = driver.findElements(details_Table_Locator);
							int row = 0; 
							for(WebElement detail : detailList){
								List<WebElement> detailElementList = detail.findElements(TDLocator);
								collector.checkThat(drivingEventLicense+": Excessive Driving detail", detailElementList.get(ALERT_DETAIL_TIME).getText().trim(),is(as.GetIdlingArray().get(row).getValue()));
								collector.checkThat(drivingEventLicense+": Excessive Driving detail :", detailElementList.get(ALERT_DETAIL_ALERT).getText().trim(),is(as.GetIdlingArray().get(row).getName()));
								row++;
							}
							drivingEvent.findElement(PopUpCloseBtn).click();
						}
					}*/
					/*
					 * Check the size of GeoFence array & Driving event count
					 */  
					collector.checkThat(drivingEventLicense+ ": GeoFence # is different between Vehicle Tab & Alert Tab ", drivingEventElementList.get(DRIVING_EVENT_GeoFence).getText().trim(),is(Integer.toString( as.GetGeoFenceArray().size())));
					if( drivingEventElementList.get(DRIVING_EVENT_GeoFence).getText().equals( Integer.toString(as.GetGeoFenceArray().size() ) ) ) {
						as.GetGeoFenceArray();
						if(as.GetGeoFenceArray().size()!=0){
							drivingEventElementList.get(DRIVING_EVENT_GeoFence).click();
							wait.until(ExpectedConditions.presenceOfElementLocated(AlertListWait));
							List<WebElement> detailList = driver.findElements(details_Table_Locator);
							int row = 0; 
							for(WebElement detail : detailList){
								List<WebElement> detailElementList = detail.findElements(TDLocator);
								collector.checkThat(drivingEventLicense+": GeoFence detail", detailElementList.get(ALERT_DETAIL_TIME).getText().trim(),is(as.GetGeoFenceArray().get(row).getValue()));
								collector.checkThat(drivingEventLicense+": GeoFence detail :", detailElementList.get(ALERT_DETAIL_ALERT).getText().trim(),is(as.GetGeoFenceArray().get(row).getName()));
								row++;
							}
							drivingEvent.findElement(PopUpCloseBtn).click();
						}
					}
					/*
					 * Check the size of Driving_Event
					 */ 
					collector.checkThat(drivingEventLicense+ ": Total Driving Event # is different between Vehicle Tab & Alert Tab ", drivingEventElementList.get(DRIVING_EVENT_Driving_Event).getText().trim(),is(Integer.toString( as.GetDrivingEventArray().size())));
					if( drivingEventElementList.get(DRIVING_EVENT_Driving_Event).getText().equals( Integer.toString( as.GetDrivingEventArray().size() ) ) ) {
						as.GetDrivingEventArray();
						if(as.GetDrivingEventArray().size()!=0){
							drivingEventElementList.get(DRIVING_EVENT_Driving_Event).click();
							wait.until(ExpectedConditions.presenceOfElementLocated(AlertListWait));
							List<WebElement> detailList = driver.findElements(details_Table_Locator);
							int row = 0; 
							for(WebElement detail : detailList){
								List<WebElement> detailElementList = detail.findElements(TDLocator);
								if( as.GetDrivingEventArray().get(row).getName().equals("Leaving Zone") || as.GetDrivingEventArray().get(row).getName().equals("Entering Zone") ){
									collector.checkThat(drivingEventLicense+": Driving Event detail", detailElementList.get(ALERT_DETAIL_TIME).getText().trim(),is(as.GetDrivingEventArray().get(row).getValue()));
									collector.checkThat(drivingEventLicense+": Driving Event detail :", detailElementList.get(ALERT_DETAIL_ALERT_2).getText().trim(),is(as.GetDrivingEventArray().get(row).getName()));
								}
								else{
									collector.checkThat(drivingEventLicense+": Driving Event detail", detailElementList.get(ALERT_DETAIL_TIME).getText().trim(),is(as.GetDrivingEventArray().get(row).getValue()));
									collector.checkThat(drivingEventLicense+": Driving Event detail :", detailElementList.get(ALERT_DETAIL_ALERT).getText().trim(),is(as.GetDrivingEventArray().get(row).getName()));
								}
								row++;
							}
							drivingEvent.findElement(PopUpCloseBtn).click();
						}
					}
				}
				collector.checkThat(drivingEventLicense + ": Total Number is different with sum of others",(Integer.parseInt( drivingEventElementList.get(DRIVING_EVENT_GeoFence).getText() ) + Integer.parseInt( drivingEventElementList.get(DRIVING_EVENT_Idling).getText() ) + Integer.parseInt( drivingEventElementList.get(DRIVING_EVENT_RPM).getText() ) + Integer.parseInt( drivingEventElementList.get(DRIVING_EVENT_SPEEDING).getText() )), is(Integer.parseInt( drivingEventElementList.get(DRIVING_EVENT_Driving_Event).getText())));
			}
			/*
			 * Go to Vehicle Alert Tab
			 */
			driver.findElement(Vehicle_Alert_Tab).click();
			wait.until(ExpectedConditions.presenceOfElementLocated(By.id("vehiclealertIndexTbody")));
			List<WebElement> vehicleAlertList = driver.findElements(VehicleAlertTable);
			for(WebElement vehicleAlert : vehicleAlertList){
				List<WebElement> vehicleAlertElementList = vehicleAlert.findElements(TDLocator);
				/*
				 * While reading the Vehicle Alert table, save the odometer with Vehicle & License number -> This will use later for comparing on device setting > Odometer
				 */
				alertTabOdometer.put(vehicleAlertElementList.get(Vehicle_Alert_LICENSE_NO).getText(), vehicleAlertElementList.get(Vehicle_Alert_odometer).getText());
				if( vehicleTimeAlertHm.containsKey( vehicleAlertElementList.get(Vehicle_Alert_LICENSE_NO).getText() ) ) {
					AlertSort as = new AlertSort( vehicleTimeAlertHm.get( vehicleAlertElementList.get(Vehicle_Alert_LICENSE_NO).getText() ) );
					as.DivideArray();
					/*
					 * Get DTC array 
					 */
					collector.checkThat(vehicleAlertElementList.get(Vehicle_Alert_LICENSE_NO).getText() + ": DTC # is different between Vehicle Tab & Alert Tab ", vehicleAlertElementList.get(VEHICLE_ALERT_DTC).getText().trim(),is(Integer.toString( as.GetDTCArray().size())));
					if( vehicleAlertElementList.get(VEHICLE_ALERT_DTC).getText().equals( Integer.toString( as.GetDTCArray().size() ) ) ) {
						as.GetDTCArray();
						if( as.GetDTCArray().size() != 0){
							/*
							 * Click the DTC for detail
							 */
							vehicleAlertElementList.get(VEHICLE_ALERT_DTC).click();
							/*
							 * DTC detail table reading
							 */
							List<WebElement> detailList = driver.findElements(details_Table_Locator);
							int row = 0; 
							for(WebElement detail : detailList){
								List<WebElement> detailElementList = detail.findElements(TDLocator);
								collector.checkThat(vehicleAlertElementList.get(Vehicle_Alert_LICENSE_NO).getText()+": DTC detail", detailElementList.get(ALERT_DETAIL_TIME).getText().trim(),is(as.GetDTCArray().get(row).getValue()));
								collector.checkThat(vehicleAlertElementList.get(Vehicle_Alert_LICENSE_NO).getText()+": DTC detail :", detailElementList.get(ALERT_DETAIL_ALERT).getText().trim(),is(as.GetDTCArray().get(row).getName()));
								row++;
							}
							vehicleAlert.findElement(PopUpCloseBtn).click();
						}
					}
					/*
					 * Engine Temp comparing
					 */
					collector.checkThat(vehicleAlertElementList.get(Vehicle_Alert_LICENSE_NO).getText() + ": Engine Temp # is different between Vehicle Tab & Alert Tab ", vehicleAlertElementList.get(VEHICLE_ALERT_Engine_Temp).getText().trim(),is(Integer.toString( as.GetEngineTempArray().size())));
					if(vehicleAlertElementList.get(VEHICLE_ALERT_Engine_Temp).getText().equals( Integer.toString( as.GetEngineTempArray().size() ) ) ) {
						as.GetEngineTempArray();
						if(as.GetEngineTempArray().size()!=0){
							/*
							 * Click the engine temperature 
							 */
							vehicleAlertElementList.get(VEHICLE_ALERT_Engine_Temp).click();
							List<WebElement> detailList = driver.findElements(details_Table_Locator);
							int row = 0; 
							for(WebElement detail : detailList){
								List<WebElement> detailElementList = detail.findElements(TDLocator);
								collector.checkThat(vehicleAlertElementList.get(Vehicle_Alert_LICENSE_NO).getText()+": Engine Temp detail", detailElementList.get(ALERT_DETAIL_TIME).getText().trim(),is(as.GetEngineTempArray().get(row).getValue()));
								collector.checkThat(vehicleAlertElementList.get(Vehicle_Alert_LICENSE_NO).getText()+": Engine Temp detail :", detailElementList.get(ALERT_DETAIL_ALERT).getText().trim(),is(as.GetEngineTempArray().get(row).getName()));
								row++;
							}
							vehicleAlert.findElement(PopUpCloseBtn).click();
						}
					}
					collector.checkThat(vehicleAlertElementList.get(Vehicle_Alert_LICENSE_NO).getText() + ": Low Voltage # is different between Vehicle Tab & Alert Tab ", vehicleAlertElementList.get(VEHICLE_ALERT_Low_Voltage).getText().trim(),is(Integer.toString( as.GetLowVoltageArray().size())));
					if( vehicleAlertElementList.get(VEHICLE_ALERT_Low_Voltage).getText().equals( Integer.toString(as.GetLowVoltageArray().size() ) ) ) {
						as.GetLowVoltageArray();
						if(as.GetLowVoltageArray().size()!=0){
							/*
							 * Click the Low Voltage
							 */
							vehicleAlertElementList.get(VEHICLE_ALERT_Low_Voltage).click();
							List<WebElement> detailList = driver.findElements(details_Table_Locator);
							int row = 0; 
							for(WebElement detail : detailList){
								List<WebElement> detailElementList = detail.findElements(TDLocator);
								collector.checkThat(vehicleAlertElementList.get(Vehicle_Alert_LICENSE_NO).getText()+": Low Voltage detail", detailElementList.get(ALERT_DETAIL_TIME).getText().trim(),is(as.GetLowVoltageArray().get(row).getValue()));
								collector.checkThat(vehicleAlertElementList.get(Vehicle_Alert_LICENSE_NO).getText()+": Low Voltage detail :", detailElementList.get(ALERT_DETAIL_ALERT).getText().trim(),is(as.GetLowVoltageArray().get(row).getName()));
								row++;
							}
							vehicleAlert.findElement(PopUpCloseBtn).click();
						}
					}
					collector.checkThat(vehicleAlertElementList.get(Vehicle_Alert_LICENSE_NO).getText() + ": Panic # is different between Vehicle Tab & Alert Tab ", vehicleAlertElementList.get(VEHICLE_ALERT_Panic).getText().trim(),is(Integer.toString( as.GetPanicArray().size())));
					if(vehicleAlertElementList.get(VEHICLE_ALERT_Panic).getText().equals( Integer.toString(as.GetPanicArray().size() ) ) ){
						as.GetPanicArray();
						if(as.GetPanicArray().size()!=0){
							vehicleAlertElementList.get(VEHICLE_ALERT_Panic).click();
							List<WebElement> detailList = driver.findElements(details_Table_Locator);
							int row = 0; 
							for(WebElement detail : detailList){
								List<WebElement> detailElementList = detail.findElements(TDLocator);
								collector.checkThat(vehicleAlertElementList.get(Vehicle_Alert_LICENSE_NO).getText()+": Panic detail", detailElementList.get(ALERT_DETAIL_TIME).getText().trim(),is(as.GetPanicArray().get(row).getValue()));
								collector.checkThat(vehicleAlertElementList.get(Vehicle_Alert_LICENSE_NO).getText()+": Panic detail :", detailElementList.get(ALERT_DETAIL_ALERT).getText().trim(),is(as.GetPanicArray().get(row).getName()));
								row++;
							}
							vehicleAlert.findElement(PopUpCloseBtn).click();
						}
					}
					collector.checkThat(vehicleAlertElementList.get(Vehicle_Alert_LICENSE_NO).getText() + ": Device Plug # is different between Vehicle Tab & Alert Tab ", vehicleAlertElementList.get(VEHICLE_ALERT_Device_Plug_UnPlug).getText().trim(),is(Integer.toString( as.GetDevicePlugUnPlugArray().size())));
					if( vehicleAlertElementList.get(VEHICLE_ALERT_Device_Plug_UnPlug).getText().equals( Integer.toString(as.GetDevicePlugUnPlugArray().size() ) ) ) {
						as.GetDevicePlugUnPlugArray();
						if( as.GetDevicePlugUnPlugArray().size() != 0 ) {
							vehicleAlertElementList.get(VEHICLE_ALERT_Device_Plug_UnPlug).click();
							List<WebElement> detailList = driver.findElements(details_Table_Locator);
							int row = 0; 
							for(WebElement detail : detailList){
								List<WebElement> detailElementList = detail.findElements(TDLocator);
								collector.checkThat(vehicleAlertElementList.get(Vehicle_Alert_LICENSE_NO).getText()+": Device Plug detail", detailElementList.get(ALERT_DETAIL_TIME).getText().trim(),is(as.GetDevicePlugUnPlugArray().get(row).getValue()));
								collector.checkThat(vehicleAlertElementList.get(Vehicle_Alert_LICENSE_NO).getText()+": Device Plug detail :", detailElementList.get(ALERT_DETAIL_ALERT).getText().trim(),is(as.GetDevicePlugUnPlugArray().get(row).getName()));
								row++;
							}
							vehicleAlert.findElement(PopUpCloseBtn).click();
						}
					}
					collector.checkThat(vehicleAlertElementList.get(Vehicle_Alert_LICENSE_NO).getText() + ": Vehicle Alert # is different between Vehicle Tab & Alert Tab ", vehicleAlertElementList.get(VEHICLE_ALERT_Alerts).getText().trim(),is(Integer.toString( as.GetVehicleAlertsArray().size())));
					if( vehicleAlertElementList.get(VEHICLE_ALERT_Alerts).getText().equals( Integer.toString(as.GetVehicleAlertsArray().size() ) ) ) { 
						as.GetVehicleAlertsArray();
						if( as.GetVehicleAlertsArray().size() != 0 ) {
							vehicleAlertElementList.get(VEHICLE_ALERT_Alerts).click();
							List<WebElement> detailList = driver.findElements(details_Table_Locator);
							int row = 0; 
							for(WebElement detail : detailList){
								List<WebElement> detailElementList = detail.findElements(TDLocator);
								collector.checkThat(vehicleAlertElementList.get(Vehicle_Alert_LICENSE_NO).getText()+": Device Plug detail", detailElementList.get(ALERT_DETAIL_TIME).getText().trim(),is(as.GetVehicleAlertsArray().get(row).getValue()));
								collector.checkThat(vehicleAlertElementList.get(Vehicle_Alert_LICENSE_NO).getText()+": Device Plug detail :", detailElementList.get(ALERT_DETAIL_ALERT).getText().trim(),is(as.GetVehicleAlertsArray().get(row).getName()));
								row++;
							}
							vehicleAlert.findElement(PopUpCloseBtn).click();
						}
					}
				}
				collector.checkThat(vehicleAlertElementList.get(Vehicle_Alert_LICENSE_NO).getText()+": Total Number is different with sum of others",(Integer.parseInt( vehicleAlertElementList.get(VEHICLE_ALERT_DTC).getText() ) + Integer.parseInt( vehicleAlertElementList.get(VEHICLE_ALERT_Engine_Temp).getText() ) + Integer.parseInt( vehicleAlertElementList.get(VEHICLE_ALERT_Low_Voltage).getText() ) + Integer.parseInt( vehicleAlertElementList.get(VEHICLE_ALERT_Panic).getText() ) ) + Integer.parseInt( vehicleAlertElementList.get(VEHICLE_ALERT_Device_Plug_UnPlug).getText() ), is(Integer.parseInt( vehicleAlertElementList.get(VEHICLE_ALERT_Alerts).getText())));
			}
			
			selectPage.gotoDeviceSetting();
			List<WebElement> dsVehicleDevices = driver.findElements(Device_Tab_VehicleListLocator);
			for(WebElement dsVehicleDevice : dsVehicleDevices){
				/*
				 * Go to Odometer subtab for getting odometer
				 */
				dsVehicleDevice.findElement(By.className("link_edit")).click();
				dsVehicleDevice.findElement(Device_Setting_odomter_subtab).click();
				List<WebElement> dsElementList = dsVehicleDevice.findElements(TDLocator);
				deviceSettingOdometer.put( dsElementList.get(Device_Setting_License_No).getText() , driver.findElement(odometerLocator).getAttribute("value") );
				/*
				 * Close
				 */
				dsVehicleDevice.findElement(DS_close_button).click();
			}
			selectPage.gotoeMaintenance();
			List<WebElement> mVehicleList = driver.findElements(Maintenance_VehicleListLocator);
			for(WebElement mVehicle : mVehicleList){
				List<WebElement> drivingEventElementList = mVehicle.findElements(TDLocator);
				maintenanceOdometer.put( drivingEventElementList.get(Maintenance_License_No).getText() , drivingEventElementList.get(Maintenance_Odometer_value).getText() );
			}
			//Comment
			//I have to printout vehicle # for each wrong one.
			collector.checkThat("Odometer value is different among ( Alert > Vehicle Alerts ) & ( e-Maintenance ) & ( Device Setting )",comparingThreeArray( alertTabOdometer , deviceSettingOdometer , maintenanceOdometer ),is(true));
		}
		catch(Exception e){
			fail("Catch exception : " + e.getMessage());
		}
	}
	public static boolean comparingThreeArray( HashMap<String,String> first_array , HashMap<String,String> second_array , HashMap<String,String> third_array ){
		boolean same = true;
		for(String key : first_array.keySet() ){
			if( ( ! first_array.get(key).equals( second_array.get(key) ) ) && ( ! first_array.get(key).equals( third_array.get(key) ) ) ){
				same = false;
			}
		}
		return same;
	}
}
