package fleetup.selenium.webautotest;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
/*
 * After Login, SelectTab is using for selecting tab.
 * Ex) Vehicle Tab. 
 */
public class SelectTab {
	
	private final WebDriver driver;
	
	public SelectTab(WebDriver driver) {
		this.driver = driver;
	}
	
	By VehicleTabLocator = By.linkText("Vehicle");
	By AlertTabLocator = By.linkText("Alert");
	By HOSTabLocator = By.linkText("HOS");
	By MapTabLocator = By.linkText("Map");
	By ReportTabLocator = By.linkText("Report");
	By DeviceSettingTabLocator = By.linkText("Device Setting");
	By AccountSettingTabLocator = By.linkText("Account Setting");
	By InventoryTabLocator = By.linkText("Inventory");
	By GeoFencingTabLocator = By.linkText("Geo Fencing");
	By eMaintenanceTabLocator = By.linkText("e-Maintenance");
	By LogoutLocator = By.linkText("Logout");

	public SelectTab gotoVehicle(){
		driver.findElement(VehicleTabLocator).click();
		return this; 
	}
	public SelectTab gotoAlert(){
		driver.findElement(AlertTabLocator).click();
		return this; 
	}
	public SelectTab gotoHOS(){
		driver.findElement(HOSTabLocator).click();
		return this; 
	}
	public SelectTab gotoMap(){
		driver.findElement(MapTabLocator).click();
		return this; 
	}
	public SelectTab gotoReport(){
		driver.findElement(ReportTabLocator).click();
		return this; 
	}
	public SelectTab gotoDeviceSetting(){
		driver.findElement(DeviceSettingTabLocator).click();
		return this; 
	}
	public SelectTab gotoAccountSetting(){
		 driver.findElement(AccountSettingTabLocator).click();
		 return this; 
	}
	public SelectTab gotoInventory(){
		driver.findElement(InventoryTabLocator).click();
		return this; 
	}
	public SelectTab gotoGeoFencing(){
		driver.findElement(GeoFencingTabLocator).click();
		return this; 
	} 
	public SelectTab gotoeMaintenance(){
		driver.findElement(eMaintenanceTabLocator).click();
		return this; 
	}
	public LoginPage Logout(){
		driver.findElement(LogoutLocator).click();
		return new LoginPage(driver); 
	}
	

}
