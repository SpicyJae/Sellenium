package fleetup.selenium.webautotest;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.fail;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.time.StopWatch;
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
 * Check the asset Data compatibility
 */
public class TestCase15 extends JunitMain  {
	private static final int LINKHISTORY_ENDLOCATION_INDEX = 4;
	
	private static final int VEHICLE_LASTLOCATION = 5;
	
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
			By AssetTabLocator = By.id("vehicle_menu_02");
			By AssetListLocator = By.xpath("//*[@id='vehicle_menu_02_cont']//*[@id='fuelIndexTbody']/tr");
			By TDLocator = By.tagName("td");
			By Link_History = By.className("link_history");
			By PopUpClose_Btn = By.xpath("//*[@id='fuelDetailDialog']//*[@class='popup_cont vehicles_popup']//*[@class='btn_close']");
			By PopUp_AssetListLocator = By.xpath("//*[@id='fuelDetailDialog']//*[@class='popup_cont vehicles_popup']//*[@id='fuelDetailTableDiv']//*[@class='st-body']//tbody/tr");
			By Link_History_Table = By.xpath("//*[@id='fuelDetailDialog']//*[@class='popup_cont vehicles_popup']//*[@id='fuelDetailTableDiv']//*[@class='st-body']//tbody");
			By AssetTabTable = By.xpath("//*[@id='vehicle_menu_02_cont']//*[@id='fuelIndexTbody']");
			int firstDetail = 0;
			/*
			 * Go to Vehicle > Asset Monitor
			 */
			driver.findElement(AssetTabLocator).click();
			wait.until(ExpectedConditions.presenceOfElementLocated(AssetTabTable));
			/*
			 * Change the start date = current day - 4 month 
			 * end date = current day 
			 */
			DateRange dateRange = PageFactory.initElements(driver, DateRange.class);
			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.DAY_OF_MONTH, -120);
			dateRange.ChangeStartDateRange(calendar.get(Calendar.YEAR) , calendar.get(Calendar.MONTH) , calendar.get(Calendar.DAY_OF_MONTH));
			calendar.add(Calendar.DAY_OF_MONTH, +120);
			dateRange.ChangeEndDateRange(calendar.get(Calendar.YEAR) , calendar.get(Calendar.MONTH) , calendar.get(Calendar.DAY_OF_MONTH));
			dateRange.search();
			/*
			 * 
			 */
			List<WebElement> assetList = driver.findElements(AssetListLocator);
			for(WebElement asset : assetList){
				firstDetail = 0;
				List<WebElement> assetElementList = asset.findElements(TDLocator);
				StopWatch pageLoad = new StopWatch();
				pageLoad.start();
				/*
				 * Click Link history which is magnifying glass
				 */
				asset.findElement(Link_History).click();
				wait.until(ExpectedConditions.presenceOfElementLocated(Link_History_Table));
				pageLoad.stop();
				/*
				 * Check the loading time for the link history
				 */
				long pageLoadTimeMiliSeconds = pageLoad.getTime();
				collector.checkThat("Loading Time too long(It can be blank)", pageLoadTimeMiliSeconds > 5000 ,is(true));
				/*
				 * Compare the last known location 
				 */
				List<WebElement> popupAssetLists = driver.findElements(PopUp_AssetListLocator);
				for(WebElement popupAssetList : popupAssetLists){
					List<WebElement> popupAssetElementList = popupAssetList.findElements(TDLocator);
					if( firstDetail == 0 ) {
						firstDetail = 1;
						if(( ! popupAssetElementList.get( LINKHISTORY_ENDLOCATION_INDEX ).getText().equals( "No GPS Data" )) && ( ! ( popupAssetElementList.get(LINKHISTORY_ENDLOCATION_INDEX).getText().equals( "Trip In Progress" ) ) )){
							collector.checkThat("Last Known Location", assetElementList.get( VEHICLE_LASTLOCATION ).getText() ,is(popupAssetElementList.get( LINKHISTORY_ENDLOCATION_INDEX ).getText()));
							break;
						}
					}
				}
				asset.findElement(PopUpClose_Btn).click();
			}
			selectPage.Logout();
		}
		catch(Exception e){
			fail("Catch exception : " + e.getMessage());
		}
	}
}
