package fleetup.selenium.webautotest;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.fail;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
/*
 * Check the Daily trip for current month
 */
public class TestCase19 extends JunitMain{
	
	private int endDate = 0;
	private static final int VEHICLE_INDEX = 3;
	private static final int LICENSE_NO_INDEX = 4;
	private static final int HOURS_INDEX = 6;
	private static final int TRIPDETAIL_STARTTIME_INDEX = 1;
	private static final int TRIPDETAIL_ENDTIME_INDEX = 2;
	private static final int TRIPDETAIL_HOURS_INDEX = 3;
	private static final int TRIPDETAIL_STARTLOCATION_INDEX = 4;
	private static final int TRIPDETAIL_ENDLOCATION_INDEX = 5;
	private static final int TRIPDETAIL_MILEAGE_INDEX = 6;
	private static final int TRIPDETAIL_FUEL_INDEX = 7;
	private static final String disable_className_1 = "ui-datepicker-unselectable ui-state-disabled undefined";
	private static final String disable_className_2 = "ui-datepicker-week-end ui-datepicker-unselectable ui-state-disabled undefined";
	private static final String disable_className_3 = "ui-datepicker-unselectable ui-state-disabled undefined ui-datepicker-today";
	private static final String disable_className_4 = "ui-datepicker-days-cell-over ui-datepicker-unselectable ui-state-disabled undefined ui-datepicker-current-day ui-datepicker-today";
	private static final String able_className_1 = "undefined";
	private static final String able_className_2 = "ui-datepicker-week-end undefined";
	private static final String able_className_3 =  "ui-datepicker-days-cell-over undefined ui-datepicker-current-day ui-datepicker-today";
	
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
			By VehicleListLocator = By.xpath("//*[@id='fuelIndexTbody']/tr");
			By TDLocator = By.tagName("td");
			By TripDetail_Locator = By.className("link_history");
//			By TripDetail_PopUpLocator = By.xpath("//*[@class='popup_cont vehicles_popup']//*[@class='content']//*[@class='title']//label");
			By Trip_Detail_ListLocator = By.xpath("//*[@class='fdScrollTable st-container']//*[@class='st-body-table']/tbody/tr");
			By DT_vehicle_Name = By.id("label_vehicle");
			By DT_License = By.id("label_license");
			By PopUpCloseLocator = By.id("closeDialogBtn");
			By Daily_Trip_Locator = By.className("link_dailytrip");
			By datepicker = By.id("datepicker");
			By DT_each_trip = By.xpath("//*[@id='tbl_trip']/tbody/tr");
			//By DT_each_trip_button =  By.xpath("//*[@id='tbl_trip']//*[@class='btn_trip']");
			By dtEachTripButton =  By.className("btn_trip");
			//By PopUp_Close_Btn =  By.className("ui-button ui-widget ui-state-default ui-corner-all ui-button-icon-only ui-dialog-titlebar-close");
//			By Window_Button = By.xpath("//*[@id='infomsg_window']");
			By DT_Trip_Window_Close_Btn = By.xpath("//*[@class='ui-dialog-titlebar ui-widget-header ui-corner-all ui-helper-clearfix ui-draggable-handle']//button");
			By DT_Window_tr = By.xpath("//*[@id='tbl_infoWin3']//tr");
			Time tm = new Time();
			ArrayList<ArrayList<Object>> tripData ; 
			/*
			 * Change the date range 
			 * Start_date = current time - gap 
			 * End day = current time 
			 */
			DateRange dateRange = PageFactory.initElements(driver, DateRange.class);
			Calendar calendar = Calendar.getInstance();
			if( Calendar.getInstance().get(Calendar.DAY_OF_MONTH) < 2 ){
				dateRange.ChangeStartDateRange(calendar.get(Calendar.YEAR) , calendar.get(Calendar.MONTH) , 1 );
				dateRange.ChangeEndDateRange(calendar.get(Calendar.YEAR) , calendar.get(Calendar.MONTH) , 1 );
				endDate = 1;
			}
			else{
				dateRange.ChangeStartDateRange(calendar.get(Calendar.YEAR) , calendar.get(Calendar.MONTH) , 1 );
				/*
				 * Because trip can be IN_PROGRESS, so i except the current date
				 */
				dateRange.ChangeEndDateRange(calendar.get(Calendar.YEAR) , calendar.get(Calendar.MONTH) , calendar.get(Calendar.DAY_OF_MONTH) - 1 );
				endDate = calendar.get(Calendar.DAY_OF_MONTH) - 1;
			}
			dateRange.search();
			try{
				wait.until(ExpectedConditions.presenceOfElementLocated(By.id("fuelIndexTbody")));
			}
			catch (TimeoutException eTO) {
				System.out.println("System have no response");
			}
			List<WebElement> vehicleLists = driver.findElements(VehicleListLocator);
			/*
			 * Initialize
			 */
//			java.util.Date Start_Time;
//
//			java.util.Date End_Time;
			String startTime;
			String endTime;
			String hours = "";
			String startLocation = "";
			String endLocation = "";
			String mileage = "";
			String fuel = "";
			String append0 = "0";

			for ( WebElement vehicleList : vehicleLists ) {
			/*
			 * FleetUp Page	
			 */
				/*
				 * Vehicle & License # 
				 */
				List<WebElement> vehicleElementList = vehicleList.findElements(TDLocator);
				String vtVehicleName = vehicleElementList.get(VEHICLE_INDEX).getText().trim();
				String vtLicense = vehicleElementList.get(LICENSE_NO_INDEX).getText().trim();
				/*
				 * Get the trip information through trip detail -> Comparing with the Daily Trip. 
				 * Only when the Hours are 00:00:00 , 
				 */
				tripData = new ArrayList<ArrayList<Object>>();
				if ( ! vehicleElementList.get( HOURS_INDEX ).getText().equals( "00:00:00" ) )  {
					vehicleList.findElement(TripDetail_Locator).click();
					wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@class='fdScrollTable st-container']//*[@class='st-body-table']/tbody")));
					List<WebElement> tripDetailLists = driver.findElements(Trip_Detail_ListLocator);
					ArrayList<Object> listEndTime ;
					for ( WebElement tripDetailList : tripDetailLists ) {
						listEndTime = new ArrayList<Object>();
						List<WebElement> tripDetailElementList = tripDetailList.findElements(TDLocator);
						/*
						 * Get the Start & End Time using 2*n arraylist 
						 * 
						 */
//						Start_Time = tm.yyyymmddHHmmss_Format( (String) trip_detail_element_list.get(TRIPDETAIL_STARTTIME_INDEX).getText() );
//						End_Time = tm.yyyymmddHHmmss_Format( (String) trip_detail_element_list.get(TRIPDETAIL_ENDTIME_INDEX).getText() );
						startTime = tripDetailElementList.get(TRIPDETAIL_STARTTIME_INDEX).getText().toString();
						endTime = tripDetailElementList.get(TRIPDETAIL_ENDTIME_INDEX).getText().toString();
						hours = tripDetailElementList.get(TRIPDETAIL_HOURS_INDEX).getText();
						startLocation = tripDetailElementList.get(TRIPDETAIL_STARTLOCATION_INDEX).getText();
						endLocation = tripDetailElementList.get(TRIPDETAIL_ENDLOCATION_INDEX).getText();
						mileage = tripDetailElementList.get(TRIPDETAIL_MILEAGE_INDEX).getText();
						fuel = tripDetailElementList.get(TRIPDETAIL_FUEL_INDEX).getText();
						listEndTime.add( startTime );
						listEndTime.add( endTime );
						listEndTime.add( hours );
						listEndTime.add( startLocation );
						listEndTime.add( endLocation );
						listEndTime.add( mileage );
						listEndTime.add( fuel );
						tripData.add( listEndTime );
					}
					driver.findElement(PopUpCloseLocator).click();
				}
				/*
				 * Get the current window handle
				 */
				String pageVehicle = driver.getWindowHandle();
			/*
			 * Daily Trip Page
			 */
				/*
				 * Click daily trip icon -> New window
				 */
				vehicleList.findElement(Daily_Trip_Locator).click();
				wait.until(ExpectedConditions.jsReturnsValue("return document.readyState==\"complete\";"));
				/*
				 * switch focus of WebDriver to the next found window handle (that's your newly opened window)
				 */
				for (String pageDailyTrip : driver.getWindowHandles()) {
					driver.switchTo().window(pageDailyTrip); // switch focus of WebDriver to the next found window handle (that's your newly opened window)
				}
				/*
				 * Vehicle & License # 
				 */
				//collector.checkThat(vehicleElementList.get(VEHICLE_ALERT_COL).getText()+": Actual Table row number != Vehicle Tab number", Integer.parseInt(vehicleElementList.get(12).getText().trim()) ,is(alertList.size()));
				collector.checkThat("Vehicle Name : ",driver.findElement(DT_vehicle_Name).getText().trim(),is(vtVehicleName.trim()));
				collector.checkThat("License # : ",driver.findElement(DT_License).getText().trim(),is(vtLicense.trim()));
				/*
				 * Day
				 */
				ArrayList< ArrayList<Object> > specificTripData;
				ArrayList< ArrayList<Object> > dtSpecificTripData;
				for( int i = 1 ; i <= endDate ; i++ ){
					/*
					 * Trip data which day is i 
					 * Ex. 2017.3.1 
					 */
					specificTripData = new ArrayList< ArrayList<Object> >();
					dtSpecificTripData = new ArrayList< ArrayList<Object> >();
					specificTripData = getTripdataByDate(tripData , i);
					driver.findElement(datepicker).click();
					/*
					 * for each day, 
					 * enabled button -  className : "undefined" .. and so on
					 * disabled button - className : "ui-datepicker-unselectable ui-state-disabled undefined" .. and so on
					 */   
					if( specificTripData.size() == 0 ){
						By DatePickerDayLocator = By.xpath("//*[@class='ui-datepicker-calendar']//span[text()='"+ i +"']");
						WebElement datePick = driver.findElement(DatePickerDayLocator);
						String isEnableDisable = datePick.findElement(By.xpath("..")).getAttribute("class");
						collector.checkThat("Datepicker Enable / Disable : ",checkDisableEnableClassName(isEnableDisable.trim()),is(1));
					}
					if( specificTripData.size() != 0 ) {
						By DatePickerDayLocator = By.xpath("//*[@class='ui-datepicker-calendar']//a[text()='"+ i +"']");
						WebElement datePick = driver.findElement(DatePickerDayLocator);
						String isEnableDisable = datePick.findElement(By.xpath("..")).getAttribute("class");
						collector.checkThat("Datepicker Enable / Disable : ",checkDisableEnableClassName(isEnableDisable.trim()),is(2));
						if( ( checkDisableEnableClassName(isEnableDisable.trim()) == 2 ) ) {
							/*
							 * Click the day
							 */
							Actions actions = new Actions(driver);
						    actions.moveToElement(datePick).click().perform();
						    List<WebElement> dtTripLists = driver.findElements(DT_each_trip);		
						    ArrayList<Object> dtSpecificTripDataTmpElement ;
						    for ( WebElement dtTripList : dtTripLists ) {
								/*
								 * I will find another way to wait until the element is visible.
								 * I cannot find why this is happening because even though i was waited for the loading, it was crashed. 
								 * I think i used wrongly. I will fix this part later.
								 */
						    	dtSpecificTripDataTmpElement = new ArrayList<Object>();
						    	Thread.sleep(2000);
								dtTripList.findElement(dtEachTripButton).click();
								wait.until(ExpectedConditions.presenceOfElementLocated(By.id("infomsg_window")));
								/*
								 * Comparing the data on Alert Detail & Daily Trip window
								 */
								List<WebElement> eachRow = dtTripList.findElements(DT_Window_tr);
								for ( WebElement eachRowEach : eachRow ) {
									List<WebElement> eachRowElement = eachRowEach.findElements(TDLocator);
									dtSpecificTripDataTmpElement.add(eachRowElement.get(1).getText());
								}
								dtSpecificTripData.add(dtSpecificTripDataTmpElement);
								driver.findElement(DT_Trip_Window_Close_Btn).click();
						    }
						    /*
						     * Init
						     */
						    java.util.Date dtStartDate; 
						    java.util.Date vhStartDate; 
						    java.util.Date dtEndDate; 
						    java.util.Date vhEndDate; 
						    String dtHours =  "";
						    String vhHours =  "";
						    String dtSAddress =  "";
						    String vhSAddress =  "";
						    String dtEAddress =  "";
						    String vhEAddress =  "";
						    String dtMileage =  "";
						    String vhMileage =  "";
						    String dtGallon =  "";
						    String vhGallon =  "";
						    for ( int j = 0; j < dtSpecificTripData.size() ; j++ ){
						    	dtStartDate = tm.HHmmssmmddyyyyFormatToyyyyMMddHHmmssFormat((dtSpecificTripData.get(dtSpecificTripData.size() - j - 1).get(2).toString()));
						    	vhStartDate = tm.yyyyMMddHHmmssFormat(specificTripData.get(j).get(0).toString()); 
						    	dtEndDate = tm.HHmmssmmddyyyyFormatToyyyyMMddHHmmssFormat(dtSpecificTripData.get(dtSpecificTripData.size() - j - 1).get(3).toString());
						    	vhEndDate = tm.yyyyMMddHHmmssFormat(specificTripData.get(j).get(1).toString()); 
						    	dtHours =  dtSpecificTripData.get(dtSpecificTripData.size() - j - 1).get(4).toString();
						    	vhHours =  specificTripData.get(j).get(2).toString();
						    	dtSAddress =  dtSpecificTripData.get(dtSpecificTripData.size() - j - 1).get(0).toString();
						    	vhSAddress =  specificTripData.get(j).get(3).toString();
						    	dtEAddress =  dtSpecificTripData.get(dtSpecificTripData.size() - j - 1).get(1).toString();
						    	vhEAddress =  specificTripData.get(j).get(4).toString();
						    	dtMileage = dtSpecificTripData.get(dtSpecificTripData.size() - j - 1).get(5).toString();
						    	vhMileage = specificTripData.get(j).get(5).toString();
						    	dtGallon = dtSpecificTripData.get(dtSpecificTripData.size() - j - 1).get(6).toString();
						    	vhGallon = specificTripData.get(j).get(6).toString();
						    	collector.checkThat("Daily Trip - Start Date : ",dtStartDate,is(vhStartDate));
						    	collector.checkThat("Daily Trip - End Date : ",dtEndDate,is(vhEndDate));
						    	while( dtHours.length() != vhHours.length() ) {
						    		vhHours = append0 + vhHours;
						    	}
						    	collector.checkThat("Daily Trip - Hours : ",dtHours,is(vhHours));
						    	collector.checkThat("Daily Trip - Start Address : " + dtSAddress + " != " + vhSAddress , dtSAddress.equals( vhSAddress ) || ( dtSAddress.trim().contains("No Result") ) || ( vhSAddress.trim().equals("No GPS Data")),is(true));
						    	collector.checkThat("Daily Trip - Start Address : " + dtEAddress + " != " + vhEAddress , dtEAddress.equals( vhEAddress ) || ( dtEAddress.trim().contains("No Result") ) || ( dtEAddress.trim().equals("No GPS Data")),is(true));
						    	collector.checkThat("Daily Trip - Mileage : " + Double.parseDouble( vhMileage ) + " != " + Double.parseDouble( dtMileage.substring( 0, dtMileage.indexOf("miles") ).trim() ) ,  Math.abs( Double.parseDouble( dtMileage.substring( 0, dtMileage.indexOf("miles") ).trim() ) -    Double.parseDouble( vhMileage ) ) < 1 ,is(true));
						    	collector.checkThat("Daily Trip - Mileage : " + Double.parseDouble( vhGallon ) + " != " + Double.parseDouble( dtGallon.substring( 0, dtGallon.indexOf("gallons") ).trim() ) ,  Math.abs( Double.parseDouble( dtGallon.substring( 0, dtGallon.indexOf("gallons") ).trim() ) -    Double.parseDouble( vhGallon ) ) < 1 ,is(true));
						    }
						}	
					}
				}
				/*
				 * Close after comparing data
				 */
				driver.close();
				/*
				 * switch back to the original window -> Original Window is fleetup web.
				 */
				driver.switchTo().window(pageVehicle);
			}
			selectPage.Logout();
		}
		catch(Exception e){
			fail("Catch exception : " + e.getMessage());
		}
	}
	public static ArrayList<ArrayList<Object>> getTripdataByDate( ArrayList< ArrayList<Object> > whole , int date ) throws ParseException{
		ArrayList<ArrayList<Object>> specific = new ArrayList<ArrayList<Object>>();
		ArrayList<Object> tmp = new ArrayList<Object>();
		Time tm = new Time();
		for( int i = 0 ; i < whole.size() ; i++){
			int tripDataDate = tm.yyyyMMddHHmmssFormat(whole.get(i).get(0).toString()).getDate();
			if( tripDataDate == date ){
				tmp.add( whole.get(i).get(0) );
				tmp.add( whole.get(i).get(1) );
				tmp.add( whole.get(i).get(2) );
				tmp.add( whole.get(i).get(3) );
				tmp.add( whole.get(i).get(4) );
				tmp.add( whole.get(i).get(5) );
				tmp.add( whole.get(i).get(6) );
				specific.add( tmp );
				tmp = new ArrayList<Object>();
			}
		}
		return specific;
	}
	
	/*
	 * Checking the disable enable status
	 */
	public static int checkDisableEnableClassName(String inputClassName){
		int result = 0;
		if(inputClassName.equals(able_className_1)){
			result = 2;
		}
		if(inputClassName.equals(able_className_2)){
			result = 2;
		}
		if(inputClassName.equals(able_className_3)){
			result = 2;
		}
		if(inputClassName.equals(disable_className_1)){
			result = 1;
		}
		if(inputClassName.equals(disable_className_2)){
			result = 1;
		}
		if(inputClassName.equals(disable_className_3)){
			result = 1;
		}
		if(inputClassName.equals(disable_className_4)){
			result = 1;
		}
		return result;
	}
}
