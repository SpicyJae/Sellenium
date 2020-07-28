package fleetup.selenium.webautotest;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.fail;

import java.util.Calendar;

import org.apache.commons.lang3.time.StopWatch;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
/*
 * Alert Tab Loading Performance 
 * Count the Loading time for each time gap
 */
public class TestCase16 extends JunitMain {
	
	@Rule
    public ErrorCollector collector = new ErrorCollector();
	
	@Test
	public void run(){
		/*
		 * i -> Interval 
		 */
	    for( int i = 0 ; i <= 155 ; i += 30 ) {
	    	drivingEventTab(productionServer, i);
	    	vehicleAlertTab(productionServer, i );
	    }
	}
	/*
	 * Vehicle Tab
	 */
	public void drivingEventTab(String server,int minusFromCurrent) {
		boolean timeOut = false ; 
		By PopUp_OK_Btn = By.xpath("//*[@class='messager-button']//span[text()='Ok']");
		By AlertTable = By.id("alertIndexTbody");
		try{
			LoginPage loginPage = PageFactory.initElements(driver, LoginPage.class);
			SelectTab selectPage = loginPage.loginAs(acct1_id,acct1_pw);
			WebDriverWait wait = new WebDriverWait(driver, 100); 
			wait.until(ExpectedConditions.jsReturnsValue("return document.readyState==\"complete\";"));
			/*
			 * Go to Alert Tab			
			 */
			selectPage.gotoAlert();
			wait.until(ExpectedConditions.presenceOfElementLocated(AlertTable));
			/*
			 * Change the date range 
			 * Start_date = current time - gap 
			 * End day = current time 
			 */
			DateRange dateRange = PageFactory.initElements(driver, DateRange.class);
			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.DAY_OF_MONTH, -minusFromCurrent);
			dateRange.ChangeStartDateRange(calendar.get(Calendar.YEAR) , calendar.get(Calendar.MONTH) , calendar.get(Calendar.DAY_OF_MONTH));
			calendar.add(Calendar.DAY_OF_MONTH, +minusFromCurrent);
			dateRange.ChangeEndDateRange(calendar.get(Calendar.YEAR) , calendar.get(Calendar.MONTH) , calendar.get(Calendar.DAY_OF_MONTH));
			/*
			 * Count the Loading time
			 */
			StopWatch pageLoad = new StopWatch();
			dateRange.search();
			pageLoad.start();
			try{
				wait.until(ExpectedConditions.presenceOfElementLocated(AlertTable));
			}
			catch (TimeoutException eTO) {
				timeOut = true;
				driver.findElement(PopUp_OK_Btn).click();
			}
			pageLoad.stop();
			collector.checkThat("System has no response", timeOut ,is(false));
			collector.checkThat("Driving Event Loading Time For "+ minusFromCurrent +" Days : " + pageLoad.getTime(), timeOut ,is(true));
			selectPage.Logout();
		}
		catch(Exception e){
			fail("Catch exception : " + e.getMessage());
		}
	}
	/*
	 * Alert Tab
	 */
	public void vehicleAlertTab(String server,int minusFromCurrent) {
		boolean timeOut = false ; 
		By PopUp_OK_Btn = By.xpath("//*[@class='messager-button']//span[text()='Ok']");
		By AlertTable = By.id("alertIndexTbody");
		By VehicleAlertTable = By.xpath("//*[@id='alert_menu_02_cont']//*[@id='vehiclealertIndexTbody']");
		try{
			LoginPage loginPage = PageFactory.initElements(driver, LoginPage.class);
			SelectTab selectPage = loginPage.loginAs(acct1_id,acct1_pw);
			/*
			 * Go to Alert Tab
			 */
			selectPage.gotoAlert();
			WebDriverWait wait = new WebDriverWait(driver, 100); 
			wait.until(ExpectedConditions.presenceOfElementLocated(AlertTable));
			/*
			 * Vehicle Alert Tab
			 */
			driver.findElement(By.id("alert_menu_02")).click();
			wait.until(ExpectedConditions.presenceOfElementLocated(VehicleAlertTable));
			/*
			 * Change the date
			 */
			DateRange dateRange = PageFactory.initElements(driver, DateRange.class);
			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.DAY_OF_MONTH, -minusFromCurrent);
			dateRange.ChangeStartDateRange(calendar.get(Calendar.YEAR) , calendar.get(Calendar.MONTH) , calendar.get(Calendar.DAY_OF_MONTH));
			calendar.add(Calendar.DAY_OF_MONTH, +minusFromCurrent);
			dateRange.ChangeEndDateRange(calendar.get(Calendar.YEAR) , calendar.get(Calendar.MONTH) , calendar.get(Calendar.DAY_OF_MONTH));
			/*
			 * Count the Loading time
			 */
			StopWatch pageLoad = new StopWatch();
			dateRange.search();
			pageLoad.start();
			try{
				wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id='alert_menu_02_cont']//*[@id='vehiclealertIndexTbody']")));
			}
			catch (TimeoutException eTO) {
				timeOut = true;
				driver.findElement(PopUp_OK_Btn).click();
			}
			pageLoad.stop();
			collector.checkThat("System has no response", timeOut ,is(false));
			collector.checkThat("Vehicle Alert Loading Time For "+ minusFromCurrent +" Days : " + pageLoad.getTime(), timeOut ,is(true));
			selectPage.Logout();
		}
		catch(Exception e){
			fail("Catch exception : " + e.getMessage());
		}
	}
}
