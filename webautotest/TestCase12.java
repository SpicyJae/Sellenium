package fleetup.selenium.webautotest;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

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
 * Group test 
 * Map Tab & Device Setting & Account Setting
 * Create New Group > Check on Map Tab & Account Setting Tab > Delete
 */
public class TestCase12 extends JunitMain {
	
	private static final int DEVICEID_INDEX = 2;
	private static final int GROUPNAME_INDEX = 0;
	private static final int ASSIGNED_VEHICLE_NUM_INDEX = 1;
	private static final String set_group_name = "seltestgroup";
	
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
			By Admin_Total_Num = By.id("user_menu_02");
			By group_add_button = By.xpath("//*[@class='user group']//*[@class='btn_add']");
			By group_name_input = By.id("groupNameNew");
			By submit_button = By.xpath("//*[@class='popup_cont newGroup']//input[@value='Submit']");
			By DS_Vehicle_Device = By.xpath("//*[@id='vehicleSetting']//*[@id='clientDeviceIndexTbody']/tr");
			By Vehicle_Info_SubTab = By.id("tab_menu_02");
			By Link_Edit_Btn = By.className("link_edit");
			By group_dropbox = By.xpath("//*[@id='tab_menu_02_cont']//*[@id='GroupId']");
			By TDLocator = By.tagName("td");
			By device_setting_update = By.xpath("//*[@id='tab_menu_02_cont']//*[@class='btn_update']");
			By PopUpOKButton = By.xpath("//*[@class='messager-button']//span[text()='Ok']");
			By DS_close_button = By.xpath("//*[@id='device_setting']//*[@class='popup_cont deviceSetting_popup']//*[@class='btn_close']");
			By Group_List = By.xpath("//*[@class='user group']//*[@class='table_list']//*[@class='st-body-table']/tbody/tr");
			By DeleteDriverButton = By.className("link_ban");
			By DeletePopUpText = By.xpath("//*[@class='panel window messager-window']//*[@class='messager-body panel-body panel-body-noborder window-body']/div[2]");
			By Car_List_Locator = By.xpath("//ul[@id='tt_ul']/li/ul/li");
			By group_icon_name = By.className("tree-title");
			By icon_indicator = By.xpath("//div[@class='tree-node']/span[3]");
			By vehicles_inside_group = By.tagName("li");
			By vehicle_inside_group = By.className("tree-node");
			By AccountTable = By.xpath("//*[@id='user_menu_01_cont']//*[@id='driverTbody']");
			By DeviceTable = By.xpath("//*[@id='vehicleSetting']//*[@id='clientDeviceIndexTbody']");
			/*
			 * Go to Account Setting
			 */
			selectPage.gotoAccountSetting();
			wait.until(ExpectedConditions.presenceOfElementLocated(AccountTable));
			/*
			 * Go to Admin Subtab
			 */
			driver.findElement(Admin_Total_Num).click();
			/*
			 * Add group
			 */
			driver.findElement(group_add_button).click();
			driver.findElement(group_name_input).sendKeys(set_group_name);
			driver.findElement(submit_button).click();
			/*
			 * Go to Device setting
			 */
			selectPage.gotoDeviceSetting();
			wait.until(ExpectedConditions.presenceOfElementLocated(DeviceTable));
			/*
			 *  To save vehicle # which is changed group
			 */
			String changedGroupVehicle = "";
			/*
			 * Device setting table
			 */
			List<WebElement> dsVehicleDevices = driver.findElements(DS_Vehicle_Device);
			for(WebElement dsVehicleDevice : dsVehicleDevices){
				/*
				 * Go to Vehicle info tab for checking group. 
				 * If the group is default, try to assign created group
				 */
				dsVehicleDevice.findElement(Link_Edit_Btn).click();
				driver.findElement(Vehicle_Info_SubTab).click();
				WebElement selectedOption = new Select(driver.findElement(group_dropbox)).getFirstSelectedOption();
				List<WebElement> dsElementList = dsVehicleDevice.findElements(TDLocator);
				/*
				 * For DEFAULT group, assign new group for that vehicle
				 */
				if(selectedOption.getText().equals("DEFAULT")){
					changedGroupVehicle = dsElementList.get(DEVICEID_INDEX).getText();
					Select select = new Select(driver.findElement(group_dropbox));
					select.selectByVisibleText(set_group_name);
					driver.findElement(device_setting_update).click();
					driver.findElement(PopUpOKButton).click();
					break;
				}
				else{
					driver.findElement(DS_close_button).click();
				}
			}
			selectPage.gotoAccountSetting();
			wait.until(ExpectedConditions.presenceOfElementLocated(AccountTable));
			driver.findElement(Admin_Total_Num).click();
			/*
			 * Check that we cannot delete group if that group have assigned vehicle
			 */
			List<WebElement> groupList = driver.findElements(Group_List);
			for(WebElement groupUser : groupList){
				List<WebElement> groupElementList = groupUser.findElements(TDLocator);
				if((groupElementList.get(GROUPNAME_INDEX).getText().equals(set_group_name))){
					collector.checkThat("one vehicle for group", groupElementList.get(ASSIGNED_VEHICLE_NUM_INDEX).getText(),is("1"));
					/*
					 * Click delete button
					 */
					groupUser.findElement(DeleteDriverButton).click();
					/*
					 * If i tried to delete group which have assigned vehicle, it should throw error.
					 */
					//Comment: Change that check the alert pop-up present with collector.alertthat
					collector.checkThat("PopUp Text", driver.findElement(DeletePopUpText).getText().trim(),is("Fence or device is used in the group, can not be deleted."));
					driver.findElement(PopUpOKButton).click();
					break;
				}
			}
			/*
			 * Go to Map Tab, and check that vehicle is inside the group icon
			 */
			selectPage.gotoMap();
			List<WebElement> carNumList = driver.findElements(Car_List_Locator);
			for(WebElement carNumListEl : carNumList){
				String carGroup = carNumListEl.findElement(group_icon_name).getText();
				String carGroupClassCheck = carNumListEl.findElement(icon_indicator).getAttribute("class");
				if( (carGroupClassCheck.equals("tree-icon tree-folder icon-house")) && (carGroup.contains(set_group_name)) ){
					List<WebElement> each_car_num_list = carNumListEl.findElements(vehicles_inside_group);
					for(WebElement each_car : each_car_num_list){
						String vehicle_name = each_car.findElement(vehicle_inside_group).getAttribute("node-id");
						collector.checkThat("Wrong vehicle inside the group", vehicle_name,is(changedGroupVehicle));
					}
				}
			}
			/*
			 * Go back to device setting and set the vehicle's group to default
			 */
			selectPage.gotoDeviceSetting();
			wait.until(ExpectedConditions.presenceOfElementLocated(DeviceTable));
			dsVehicleDevices = driver.findElements(DS_Vehicle_Device);
			for(WebElement dsVehicleDevice : dsVehicleDevices){
				List<WebElement> dsElementList = dsVehicleDevice.findElements(TDLocator);
				if( dsElementList.get(DEVICEID_INDEX).getText().equals(changedGroupVehicle) ){
					driver.findElement(Link_Edit_Btn).click();
					driver.findElement(Vehicle_Info_SubTab).click();
					Select select = new Select(driver.findElement(group_dropbox));
					select.selectByVisibleText("DEFAULT");
					driver.findElement(device_setting_update).click();
					driver.findElement(PopUpOKButton).click();
					break;
				}
			}
			/*
			 * After saving, page is loading the device list again
			 */
			wait.until(ExpectedConditions.presenceOfElementLocated(DeviceTable));
			/*
			 * Delete group and check that vehicle should have default group
			 */
			selectPage.gotoAccountSetting();
			wait.until(ExpectedConditions.presenceOfElementLocated(AccountTable));
			driver.findElement(Admin_Total_Num).click();
			groupList = driver.findElements(Group_List);
			for(WebElement groupUser : groupList){
				List<WebElement> groupElementList = groupUser.findElements(TDLocator);
				if((groupElementList.get(GROUPNAME_INDEX).getText().equals(set_group_name))){
					groupUser.findElement(DeleteDriverButton).click();
					driver.findElement(PopUpOKButton).click();
					break;
				}
			}
			selectPage.Logout();
		}
		catch(Exception e){
			fail("Catch exception : " + e.getMessage());
		}
	}
}
