package fleetup.selenium.webautotest;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.fail;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

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
 * Checking the vehicle # on Vehicle Tab
 */
public class TestCase07 extends JunitMain{
	
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
			By Total_Num = By.className("devsNum");
			By Vehicle_Monitor_Num = By.id("vehiclesNum");
			By Asset_Monitor_Num = By.id("vehicle_menu_02");
			By VehicleListLocator = By.xpath("//*[@id='fuelIndexTbody']/tr");
			By VehicleTabLocator= By.id("vehicle_menu_02");
			By AssetListLocator = By.xpath("//*[@id='vehicle_menu_02_cont']//*[@id='fuelIndexTbody']/tr");
			/*
			 * Total Number of vehicle on the tab
			 */
			String totalNum = driver.findElement(Total_Num).getText();
			totalNum = totalNum.substring(totalNum.indexOf("(")+1);
			totalNum = totalNum.substring(0, totalNum.indexOf(")"));
			/*
			 * Number of basic vehicle on the tab 
			 */
			int vehicleNum = Integer.parseInt(driver.findElement(Vehicle_Monitor_Num).getAttribute("value"));
			/*
			 * Number of asset on the tab
			 */
			String assetMonitorNum = driver.findElement(Asset_Monitor_Num).getText();
			assetMonitorNum = assetMonitorNum.substring(assetMonitorNum.indexOf("(")+1);
			assetMonitorNum = assetMonitorNum.substring(0, assetMonitorNum.indexOf(")"));
			int assetNum = Integer.parseInt(assetMonitorNum);
			/*
			 * Total = asset + vehicle
			 */
			collector.checkThat("Total != Vehicle + Asset", Integer.parseInt(totalNum),is(vehicleNum + assetNum));
			/*
			 * Counting the table row # : Vehicle Tab
			 */
			int vehicleActualNum = 0;
			List<WebElement> vehicleList = driver.findElements(VehicleListLocator);
			vehicleActualNum = vehicleList.size();
			/*
			 * Counting the table row # : Asset Tab
			 */
			driver.findElement(VehicleTabLocator).click();
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id='vehicle_menu_02_cont']//*[@id='fuelIndexTbody']")));
			int assetActualNum = 0;
			List<WebElement> assetList = driver.findElements(AssetListLocator);
			assetActualNum = assetList.size();
			/*
			 * Comparing the # on tab and # of row
			 */
			collector.checkThat("vehicle num",vehicleNum,is(vehicleActualNum));
			collector.checkThat("vehicle num",assetNum,is(assetActualNum));
			selectPage.Logout();
		}
		catch(Exception e){
			fail("Catch exception : " + e.getMessage());
		}
	}
}