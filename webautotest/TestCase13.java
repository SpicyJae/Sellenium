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
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
/*
 * Check the admin & driver num
 */
public class TestCase13 extends JunitMain {
	
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
			By Vehicle_Monitor_Num = By.id("vehicle_menu_01");
			By Asset_Monitor_Num = By.id("vehicle_menu_02");
			By VehicleListLocator = By.xpath("//*[@id='clientDeviceIndexTbody']/tr");
			By VehicleAssetTabLocator= By.id("vehicle_menu_02");
			By AssetListLocator = By.xpath("//*[@id='assetSetting']//*[@id='clientDeviceIndexTbody']/tr");
			By DeviceTable = By.xpath("//*[@id='vehicleSetting']//*[@id='clientDeviceIndexTbody']");
			/*
			 * Go to Device Setting
			 */
			selectPage.gotoDeviceSetting();
			wait.until(ExpectedConditions.presenceOfElementLocated(DeviceTable));
			/*
			 * Total device # & Vehicle # & Asset #
			 */
			String totalNum = getNumFromText(driver.findElement(Total_Num).getText());
			String vehicleNum = getNumFromText(driver.findElement(Vehicle_Monitor_Num).getText());
			int vehicleNumI = Integer.parseInt(vehicleNum);
			String assetNum = getNumFromText(driver.findElement(Asset_Monitor_Num).getText());
			int assetNumI = Integer.parseInt(assetNum);
			/*
			 * Comparing Total = vehicle + asset 
			 */
			collector.checkThat("Total != Vehicle + Asset", Integer.parseInt(totalNum),is(vehicleNumI + assetNumI));
			/*
			 * Row # of vehicle
			 */
			List<WebElement> vehicleList = driver.findElements(VehicleListLocator);
			int vehicleActualNum = vehicleList.size();
			/*
			 * Row # of asset
			 */
			driver.findElement(VehicleAssetTabLocator).click();
			List<WebElement> assetList = driver.findElements(AssetListLocator);
			int assetActualNum = assetList.size();
			/*
			 * Vehicle # = Row #             &        Asset # = Row # 
			 */
			collector.checkThat("Vehicle num", vehicleNumI,is(vehicleActualNum));
			collector.checkThat("Asset num", assetNumI,is(assetActualNum));
			
			selectPage.Logout();
		}
		catch(Exception e){
			fail("Catch exception : " + e.getMessage());
		}
	}
	public String getNumFromText(String input){
		input = input.substring(input.indexOf("(")+1);
		input = input.substring(0, input.indexOf(")"));
		return input;
	}
}
