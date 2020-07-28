package fleetup.selenium.webautotest;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
/*
 * For checking Vehicle group between Device Setting & Map Tab
 */
//Comment 
//Should check after loading the Asset Device Window - So, Sometimes, it fetch wrong data
public class TestCase14 extends JunitMain {
	
	private static final int DEVICEID_INDEX = 2;
	private static final int LICENSE_INDEX = 1;
	private static final int MAKE_INDEX = 5;
	private static final int MODEL_INDEX = 6;
	private static final int YEAR_INDEX = 7;
	private static final int ASSET_NO_INDEX = 0;
	private static final int CONTENTS_INDEX = 4;
	private static final int ASSET_DEVICEID_INDEX = 1;
	
	@Rule
    public ErrorCollector collector = new ErrorCollector();
	
	@Test
	public void run(){
		try{
			LoginPage loginPage = PageFactory.initElements(driver, LoginPage.class);
			SelectTab selectPage = loginPage.loginAs("fleetupdemo","fleetupdemous");
			WebDriverWait wait = new WebDriverWait(driver, 100); 
			wait.until(ExpectedConditions.jsReturnsValue("return document.readyState==\"complete\";"));
			/*
			 * Declare
			 */
			By VehicleListLocator = By.xpath("//*[@id='clientDeviceIndexTbody']/tr");
			By TDLocator = By.tagName("td");
			By Link_Edit_Btn = By.className("link_edit");
			By DeviceSubTab = By.id("tab_menu_01");
			By DeviceSubTab_DeviceID = By.id("DevIdfors_lbl");
			By Vehicle_Info_SubTab = By.id("tab_menu_02");
			By LICENSE_LOCATOR = By.id("License");
			By MAKE_LOCATOR = By.id("Maker");
			By MODEL_LOCATOR = By.id("Model");
			By YEAR_LOCATOR = By.id("Year");
			By group_dropbox = By.xpath("//*[@id='tab_menu_02_cont']//*[@id='GroupId']");
			By asset_group_dropbox = By.xpath("//*[@id='asset_menu_01_cont']//*[@id='assetGroupId']");
			By VehicleAssetTabLocator= By.id("vehicle_menu_02");
			By AssetListLocator = By.xpath("//*[@id='assetSetting']//*[@id='clientDeviceIndexTbody']/tr");
			By DS_close_button = By.xpath("//*[@id='device_setting']//*[@class='popup_cont deviceSetting_popup']//*[@class='btn_close']");
			By AS_close_button = By.xpath("//*[@id='asset_setting']//*[@class='popup_cont assetSetting_popup']//*[@class='btn_close']");
			By Asset_Info_SubTab = By.xpath("//*[@id='asset_setting']//*[@class='popup_cont assetSetting_popup']//*[@id='asset_menu_01']");
			//By Asset_No = By.id("assetNo");
			By Asset_No = By.xpath("//*[@class='popup_cont assetSetting_popup']//*[@id='asset_menu_01_cont']//*[@id='assetNo']");
			By Asset_Content = By.xpath("//*[@class='popup_cont assetSetting_popup']//*[@id='asset_menu_01_cont']//*[@id='assetCont']");
			By AssetDeviceSetting_SubTab = By.id("asset_menu_02");
			By AssetDeviceID_Locator = By.xpath("//*[@id='asset_menu_02_cont']//*[@id='assetDevId']");
			By Car_List_Locator = By.xpath("//ul[@id='tt_ul']/li/ul/li");
			By VehicleDeviceTable = By.xpath("//*[@id='vehicleSetting']//*[@id='clientDeviceIndexTbody']");
			By AssetDeviceTable = By.xpath("//*[@id='assetSetting']//*[@id='clientDeviceIndexTbody']");
			/*
			 * Go to Device Setting
			 */
			selectPage.gotoDeviceSetting();
			wait.until(ExpectedConditions.presenceOfElementLocated(VehicleDeviceTable));
			/*
			 * This is for device - group checking for all the vehicle ( asset + vehicle)
			 */
			Map<String,String> vehicleDeviceMap = new HashMap<String,String>();
			Map<String,String> assetDeviceMap = new HashMap<String,String>();
			/*
			 * Device table read
			 */
			List<WebElement> dsVehicleDevices = driver.findElements(VehicleListLocator);
			for( WebElement dsVehicleDevice : dsVehicleDevices ){
				List<WebElement> dsElementList = dsVehicleDevice.findElements(TDLocator);
				dsVehicleDevice.findElement(Link_Edit_Btn).click();
				/*
				 * Sometimes, pop-up page is not on the device tab 
				 */
				driver.findElement(DeviceSubTab).click();
				String deviceID = driver.findElement(DeviceSubTab_DeviceID).getText();
				collector.checkThat(dsElementList.get(DEVICEID_INDEX).getText()+": "+"Vehicle - Device ID on details", deviceID,is(dsElementList.get(DEVICEID_INDEX).getText().trim()));
				/*
				 * Comparing the data inside the detail and table
				 */
				driver.findElement(Vehicle_Info_SubTab).click();
				collector.checkThat(dsElementList.get(DEVICEID_INDEX).getText()+": "+"Vehicle - License # on details", driver.findElement(LICENSE_LOCATOR).getAttribute("value").trim() ,is(dsElementList.get(LICENSE_INDEX).getText().trim()));
				collector.checkThat(dsElementList.get(DEVICEID_INDEX).getText()+": "+"Vehicle - Maker on details", driver.findElement(MAKE_LOCATOR).getAttribute("value").trim() ,is(dsElementList.get(MAKE_INDEX).getText().trim()));
				collector.checkThat(dsElementList.get(DEVICEID_INDEX).getText()+": "+"Vehicle - Model on details", driver.findElement(MODEL_LOCATOR).getAttribute("value").trim() ,is(dsElementList.get(MODEL_INDEX).getText().trim()));
				collector.checkThat(dsElementList.get(DEVICEID_INDEX).getText()+": "+"Vehicle - Year on details", driver.findElement(YEAR_LOCATOR).getAttribute("value").trim() ,is(dsElementList.get(YEAR_INDEX).getText().trim()));
				/*
				 * Save the vehicle name & Group for checking on map tab
				 */
				WebElement selectedOption = new Select(driver.findElement(group_dropbox)).getFirstSelectedOption();
				vehicleDeviceMap.put(deviceID,selectedOption.getText());
				driver.findElement(DS_close_button).click();
			}
			/*
			 * Go to Asset SubTab 
			 */
			driver.findElement(VehicleAssetTabLocator).click();
			wait.until(ExpectedConditions.presenceOfElementLocated(AssetDeviceTable));
			/*
			 * Asset list
			 */
			List<WebElement> assetList = driver.findElements(AssetListLocator);
			for(WebElement dsAssetDevice : assetList){
				List<WebElement> dsElementList = dsAssetDevice.findElements(TDLocator);
				dsAssetDevice.findElement(Link_Edit_Btn).click();
				/*
				 * Asset data comparison
				 */
				collector.checkThat(dsElementList.get(ASSET_NO_INDEX).getText()+": "+"Asset - Asset No", dsElementList.get(ASSET_NO_INDEX).getText().trim() ,is(dsAssetDevice.findElement(Asset_No).getAttribute("value").trim()));
				collector.checkThat(dsElementList.get(ASSET_NO_INDEX).getText()+": "+"Asset - Content", dsElementList.get(CONTENTS_INDEX).getText().trim() ,is(dsAssetDevice.findElement(Asset_Content).getAttribute("value").trim()));
				driver.findElement(Asset_Info_SubTab).click();
				/*
				 * Save vehicle + Group data for checking on Map Tab
				 */
				WebElement selectedOption = new Select( dsAssetDevice.findElement(asset_group_dropbox) ).getFirstSelectedOption();
				assetDeviceMap.put( dsElementList.get( ASSET_DEVICEID_INDEX ).getText() , selectedOption.getText() );
				driver.findElement(AssetDeviceSetting_SubTab).click();
				collector.checkThat(dsElementList.get(ASSET_NO_INDEX).getText()+": "+"Asset - Content", dsElementList.get( ASSET_DEVICEID_INDEX ).getText().trim() ,is(dsAssetDevice.findElement( AssetDeviceID_Locator ).getText()));
				/*
				 * Close the pop-up window
				 */
				dsAssetDevice.findElement(AS_close_button).click();
			}
			/* 
			 * Map Tab - Data comparison
			 */
			selectPage.gotoMap();
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id='tt_ul']")));
			List<WebElement> carNumList = driver.findElements(Car_List_Locator);
			/*
			 * num is indicator for vehicle which is default or group icon.
			 */
			int num = 0;
			for(WebElement carNumListEl : carNumList){
				String carGroupClassCheck = carNumListEl.findElement(By.xpath("//ul[@id='tt_ul']/li/ul/li["+(num+1)+"]/div/span[3]")).getAttribute("class");
				/*
				 * NOT INSIDE -> If the icon is group icon, 
				 */
				if( ( carGroupClassCheck.equals("tree-icon tree-folder icon-house") ) || ( carGroupClassCheck.equals( "tree-icon tree-folder icon-onLineHouse" ) ) ){
					List<WebElement> eachCarNumList = carNumListEl.findElements(By.xpath("//ul[@id='tt_ul']/li/ul/li["+(num+1)+"]/ul/li"));
					/*
					 * num_2 is indicator for vehicle which is inside the group 
					 */
					int num_2 = 0;
					for(WebElement eachCar : eachCarNumList){
						String vehicleName = eachCar.findElement(By.className("tree-node")).getAttribute("node-id");
						String carGroupName = carNumListEl.findElement(By.className("tree-title")).getText();
						carGroupName = carGroupName.substring(0,carGroupName.indexOf("("));
						/*
						 * INSIDE -> Inside group, check the vehicle type = Asset or vehicle
						 */
						String car_group_class_check_2 = carNumListEl.findElement(By.xpath("//ul[@id='tt_ul']/li/ul/li["+(num+1)+"]/ul/li["+(num_2+1)+"]/div/span[4]")).getAttribute("class");
						/*
						 * INSIDE -> If asset, check the asset HashMap, the group is right
						 */
						if((car_group_class_check_2.equals("tree-icon tree-file icon-asset-car-stop"))){
							collector.checkThat(vehicleName+","+"Group is different between Map & Device", assetDeviceMap.get(vehicleName).trim(),is(carGroupName.trim()));
						}
						/*
						 * INSIDE -> If vehicle, check the asset HashMap, the group is right
						 */
						else if((car_group_class_check_2.equals("tree-icon tree-file icon-car"))){
							collector.checkThat(vehicleName+","+"Group is different between Map & Device", vehicleDeviceMap.get(vehicleName).trim(),is(carGroupName.trim()));
						}
						/*
						 * INSIDE -> If online vehicle, check the asset HashMap, the group is right
						 */
						else if((carGroupClassCheck.equals("tree-icon tree-file icon-onLineCar"))){
							collector.checkThat(vehicleName+","+"Group is different between Map & Device", vehicleDeviceMap.get(vehicleName).trim(),is(carGroupName.trim()));
						}
						/*
						 * INSIDE -> If online asset, check the asset HashMap, the group is right
						 */
						else if((carGroupClassCheck.equals("tree-icon tree-file icon-asset-car"))){
							collector.checkThat(vehicleName+","+"Group is different between Map & Device", vehicleDeviceMap.get(vehicleName).trim(),is(carGroupName.trim()));
						}
						num_2++;
					}
				}
				/*
				 * NOT INSIDE -> vehicle
				 */
				else if((carGroupClassCheck.equals("tree-icon tree-file icon-car"))){
					String vehicleName = carNumListEl.findElement(By.className("tree-node")).getAttribute("node-id");
					collector.checkThat(vehicleName+","+"Group is different between Map & Device", vehicleDeviceMap.get(vehicleName).trim(),is("DEFAULT"));
				}
				/*
				 * NOT INSIDE -> online vehicle
				 */
				else if((carGroupClassCheck.equals("tree-icon tree-file icon-onLineCar"))){
					String vehicleName = carNumListEl.findElement(By.className("tree-node")).getAttribute("node-id");
					collector.checkThat(vehicleName+","+"Group is different between Map & Device", vehicleDeviceMap.get(vehicleName).trim(),is("DEFAULT"));
				}
				/*
				 * NOT INSIDE -> asset vehicle
				 */
				else if((carGroupClassCheck.equals("tree-icon tree-file icon-asset-car-stop"))){
					String vehicleName = carNumListEl.findElement(By.className("tree-node")).getAttribute("node-id");
					collector.checkThat(vehicleName+","+"Group is different between Map & Device", assetDeviceMap.get(vehicleName).trim(),is("DEFAULT"));
				}
				/*
				 * NOT INSIDE -> online asset vehicle
				 */
				else if((carGroupClassCheck.equals("tree-icon tree-file icon-asset-car"))){
					String vehicleName = carNumListEl.findElement(By.className("tree-node")).getAttribute("node-id");
					collector.checkThat(vehicleName+","+"Group is different between Map & Device", assetDeviceMap.get(vehicleName).trim(),is("DEFAULT"));
				}
				num++;
			}
			selectPage.Logout();
		}
		catch(Exception e){
			fail("Catch exception : " + e.getMessage());
		}
	}
}
