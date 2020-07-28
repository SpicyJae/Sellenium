package fleetup.selenium.webautotest;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.fail;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.commons.lang3.time.StopWatch;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/*
 * Verify Vehicle Tab data compatibility 
 * Start day : Current - 7 days
 * End day : Current
 */
public class TestCase05 extends JunitMain{
	
	private static final int THRES_HOLD = 1;
	private static final int VEHICLE_INDEX = 3;
	private static final int LICENSE_NO_INDEX = 4;
	private static final int LASTKNOWNLOCATION_INDEX = 5;
	private static final int HOURS_INDEX = 6;
	private static final int MILES_INDEX = 7;
	private static final int FUEL_INDEX = 8;
	private static final int MPG_INDEX = 9;
	private static final int ALERT_INDEX = 12;
	private static final int ALERTTAB_TIME_INDEX = 0;
	private static final int TRIPDETAIL_STARTTIME_INDEX = 1;
	private static final int TRIPDETAIL_ENDTIME_INDEX = 2;
	private static final int TRIPDETAIL_HOURS_INDEX = 3;
	private static final int TRIPDETAIL_ENDLOCATION_INDEX = 5;
	private static final int TRIPDETAIL_MILES_INDEX = 6;
	private static final int TRIPDETAIL_FUEL_INDEX = 7;
	private static final int TRIPDETAIL_ALERTS_INDEX = 9;

	@Rule
    public ErrorCollector collector = new ErrorCollector();
	
	@Test
	public void run(){
		try {
			LoginPage loginPage = PageFactory.initElements(driver, LoginPage.class);
			SelectTab selectPage = loginPage.loginAs(acct1_id, acct1_pw);
			WebDriverWait wait = new WebDriverWait(driver, 100);
			wait.until(ExpectedConditions.jsReturnsValue("return document.readyState==\"complete\";"));
			/*
			 * Detail Column variables
			 */
			int firstDetail = 0;
			double detMiles;
			double detFuel;
			double detAlert;
			By TDLocator = By.tagName("td");
			By VehicleListLocator = By.xpath("//*[@id='fuelIndexTbody']/tr");
			By Alert_PopUpNameLocator = By.xpath("//*[@class='popup_cont alert_details']//*[@class='content']//*[@class='title']//label");
			By Alert_ListLocator = By.xpath("//*[@class='adScrollTable st-container']//*[@class='st-body-table']/tbody/tr");
			By Alert_PopUpCloseLocator = By.xpath("//*[@id='alertDetailDialog']//*[@class='popup_cont alert_details']/div/h1//a");
			By TripDetail_Locator = By.className("link_history");
			By TripDetail_PopUpLocator = By.xpath("//*[@class='popup_cont vehicles_popup']//*[@class='content']//*[@class='title']//label");
			By Trip_Detail_ListLocator = By.xpath("//*[@class='fdScrollTable st-container']//*[@class='st-body-table']/tbody/tr");
			By PopUpCloseLocator = By.id("closeDialogBtn");
			Time tm = new Time();
			/*
			 * Change the start date = current date - 7 end date = current date
			 */
			DateRange dateRange = PageFactory.initElements(driver, DateRange.class);
			Calendar currentStartTimeCal = Calendar.getInstance();
			Calendar currentEndTimeCal = Calendar.getInstance();
			if( Calendar.getInstance().get(Calendar.DAY_OF_MONTH) >= 8 ){
				dateRange.ChangeStartDateRange(Calendar.getInstance().get(Calendar.YEAR),Calendar.getInstance().get(Calendar.MONTH) , Calendar.getInstance().get(Calendar.DAY_OF_MONTH) - 7 );
				dateRange.ChangeEndDateRange(Calendar.getInstance().get(Calendar.YEAR),Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
				currentStartTimeCal.set(Calendar.getInstance().get(Calendar.YEAR),Calendar.getInstance().get(Calendar.MONTH) , Calendar.getInstance().get(Calendar.DAY_OF_MONTH) - 7 ,0, 0, 0);
				currentEndTimeCal.set(Calendar.getInstance().get(Calendar.YEAR),Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH), 23,	59, 59);
			}
			else{
				dateRange.ChangeStartDateRange(Calendar.getInstance().get(Calendar.YEAR),Calendar.getInstance().get(Calendar.MONTH) - 1 , 24);
				dateRange.ChangeEndDateRange(Calendar.getInstance().get(Calendar.YEAR),Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
				currentStartTimeCal.set(Calendar.getInstance().get(Calendar.YEAR),Calendar.getInstance().get(Calendar.MONTH) - 1 , 24 ,0, 0, 0);
				currentEndTimeCal.set(Calendar.getInstance().get(Calendar.YEAR),Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH), 23,	59, 59);
			}
			/*
			 * Check page load time for 7 days
			 */
			StopWatch pageLoad = new StopWatch();
			pageLoad.start();
			dateRange.search();
			wait.until(ExpectedConditions.presenceOfElementLocated(By.id("fuelIndexTbody")));
			pageLoad.stop();
			collector.checkThat( "Page Loading Time is too long" , pageLoad.getTime() < 3000, is(true));
			/*
			 * vehicle Tab List
			 */
			List<WebElement> vehicleLists = driver.findElements(VehicleListLocator);
			for (WebElement vehicleList : vehicleLists) {
				firstDetail = 0;
				detMiles = 0;
				detFuel = 0;
				detAlert = 0;
				List<WebElement> vehicleElementList = vehicleList.findElements(TDLocator);
				/*
				 * If the alert column have '0' value, we don't need to check.
				 */
				if (!vehicleElementList.get(ALERT_INDEX).getText().equals("0")) {
					vehicleElementList.get(ALERT_INDEX).click();
					wait.until(ExpectedConditions.presenceOfElementLocated(Alert_PopUpNameLocator));
					/*
					 * Name check between Alert pop-up and vehicle tab
					 */
					//Comment
					//collector.checkThat( vehicleElementList.get(VEHICLE_INDEX).getText() + ": License number" , vehicleElementList.get(LICENSE_NO_INDEX).getText().trim() , is(driver.findElement(Alert_PopUpNameLocator).getText().trim()));
					/*
					 * Alert list # check
					 */
					List<WebElement> alertLists = driver.findElements(Alert_ListLocator);
					java.util.Date alertTime;
					for (WebElement alertList : alertLists) {
						List<WebElement> alertElementsList = alertList.findElements(TDLocator);
						alertTime = tm.yyyymmddHHmmssFormat(alertElementsList.get(ALERTTAB_TIME_INDEX).getText());
						/*
						 * Alert list Time check
						 */
						collector.checkThat( vehicleElementList.get(VEHICLE_INDEX).getText() + ": Alert's Time out of the range", (currentStartTimeCal.getTime().getTime()  < alertTime.getTime()) && (currentEndTimeCal.getTime().getTime() > alertTime.getTime()) , is(true));
					}
					collector.checkThat(vehicleElementList.get(VEHICLE_INDEX).getText() +": Alert num",alertLists.size(),is(Integer.parseInt(vehicleElementList.get(ALERT_INDEX).getText())));
					driver.findElement(Alert_PopUpCloseLocator).click();
				}
				/*
				 * Trip Detail Check
				 */
				wait.until(ExpectedConditions.elementToBeClickable(vehicleList.findElement(TripDetail_Locator)));
				vehicleList.findElement(TripDetail_Locator).click();
				wait.until(ExpectedConditions.presenceOfElementLocated(TripDetail_PopUpLocator));
				/*
				 * License # Check
				 */
				//Comment
				//collector.checkThat( vehicleElementList.get(VEHICLE_INDEX).getText() + ": License number",vehicleElementList.get(LICENSE_NO_INDEX).getText().trim(),is(driver.findElement(TripDetail_PopUpLocator).getText().trim()));
				/*
				 * If the HOURS column have "00:00:00" value, we don't need to check trip detail
				 */
				if (!vehicleElementList.get(HOURS_INDEX).getText().equals("00:00:00")) {
					List<WebElement> tripDetailLists = driver.findElements(Trip_Detail_ListLocator);
					java.util.Date startTime;
					java.util.Date endTime;
					for (WebElement tripDetailList : tripDetailLists) {
						List<WebElement> tripDetailElementList = tripDetailList.findElements(TDLocator);
						startTime = tm.yyyymmddHHmmssFormat((String) tripDetailElementList.get(TRIPDETAIL_STARTTIME_INDEX).getText());
						endTime = tm.yyyymmddHHmmssFormat((String) tripDetailElementList.get(TRIPDETAIL_ENDTIME_INDEX).getText());
						/*
						 * Data which is on wrong date range
						 */
						collector.checkThat( vehicleElementList.get(VEHICLE_INDEX).getText() + ": Trip's Time out of the range" ,(currentStartTimeCal.getTime().getTime() - startTime.getTime() < 0) && (currentEndTimeCal.getTime().getTime() - endTime.getTime() > 0),is(true));
						/*
						 * time gap have minus value
						 */
						String stringHours = tripDetailElementList.get(TRIPDETAIL_HOURS_INDEX).getText();
						collector.checkThat( vehicleElementList.get(VEHICLE_INDEX).getText() + ": Trip's Minus Hour" , stringHours.contains("-") , is(false));
						/*
						 * Hour is wrong
						 */
						long diff = endTime.getTime() - startTime.getTime();
						long longHours = tm.HoursChangeToMiliseconds(stringHours);
						collector.checkThat( vehicleElementList.get(VEHICLE_INDEX).getText() + ": Trip's Hour" , Math.abs(longHours-diff)<300 , is(true));
						/*
						 * For the first Trip, check the last known location
						 */
						if( firstDetail == 0 ) {
							firstDetail = 1;
							if (! ( tripDetailElementList.get(TRIPDETAIL_ENDLOCATION_INDEX).getText().equals("Trip In Progress") || tripDetailElementList.get(TRIPDETAIL_ENDLOCATION_INDEX).getText().equals("No GPS Data") ) ) {
								
								/*
								* Last Known Location is wrong
								*/
								collector.checkThat( vehicleElementList.get(VEHICLE_INDEX).getText() + ": Trip's Last Known Location" ,vehicleElementList.get(LASTKNOWNLOCATION_INDEX).getText().trim(),is(tripDetailElementList.get(TRIPDETAIL_ENDLOCATION_INDEX).getText().trim()));
							}
						}
						detMiles += Double.parseDouble(tripDetailElementList.get(TRIPDETAIL_MILES_INDEX).getText());
						detFuel += Double.parseDouble(tripDetailElementList.get(TRIPDETAIL_FUEL_INDEX).getText());
						detAlert += Double.parseDouble(tripDetailElementList.get(TRIPDETAIL_ALERTS_INDEX).getText());
					}
				}
				/*
				 * Comparing Trip detail value & Vehicle Tab value
				 * 1) Miles
				 * 2) Fuel
				 * 3) MPG
				 * 4) ALERT
				 */
				collector.checkThat( vehicleElementList.get(VEHICLE_INDEX).getText() + ": Trip's Miles", (Math.abs(detMiles - Double.parseDouble(vehicleElementList.get(MILES_INDEX).getText())) <= THRES_HOLD) , is(true));
				collector.checkThat( vehicleElementList.get(VEHICLE_INDEX).getText() + ": Trip's Fuel" +","+ (Math.abs(detFuel - Double.parseDouble(vehicleElementList.get(FUEL_INDEX).getText()))) , (Math.abs(detFuel - Double.parseDouble(vehicleElementList.get(FUEL_INDEX).getText())) <= THRES_HOLD) , is(true));
				if(detFuel!=0){
					collector.checkThat( vehicleElementList.get(VEHICLE_INDEX).getText() + ": Trip's MPG" , (Math.abs(detMiles/detFuel - Double.parseDouble(vehicleElementList.get(MPG_INDEX).getText())) <= THRES_HOLD) , is(true));
				}
				collector.checkThat( vehicleElementList.get(VEHICLE_INDEX).getText() + ": Trip's ALERT", (Math.abs(detAlert - Double.parseDouble(vehicleElementList.get(ALERT_INDEX).getText())) <= THRES_HOLD) , is(true));
				driver.findElement(PopUpCloseLocator).click();
			}
			selectPage.Logout();
		}
		catch (Exception e) {
			fail("Catch exception : " + e.getMessage());
		}
	}
}
