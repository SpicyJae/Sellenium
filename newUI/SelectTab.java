package fleetup.selenium.newUI;

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

	By VehicleTabLocator = By.id("menu_1");
	By AlertTabLocator = By.id("menu_2");
	By MapTabLocator = By.id("menu_4");
	By ReportTabLocator = By.id("menu_8");
	By ComplianceTabLocator = By.id("menu_512");
	By GeoFencingTabLocator = By.id("menu_128");
	By eMaintenanceTabLocator = By.id("menu_256");
	By LogoutLocator = By.id("Logout");// need to fix

	public SelectTab gotoVehicle() {
		driver.findElement(VehicleTabLocator).click();
		return this;
	}

	public SelectTab gotoAlert() {
		driver.findElement(AlertTabLocator).click();
		return this;
	}

	public SelectTab gotoMap() {
		driver.findElement(MapTabLocator).click();
		return this;
	}

	public SelectTab gotoReport() {
		driver.findElement(ReportTabLocator).click();
		return this;
	}

	public SelectTab gotoGeofence() {
		driver.findElement(GeoFencingTabLocator).click();
		return this;
	}

	public SelectTab gotoeMaintenance() {
		driver.findElement(eMaintenanceTabLocator).click();
		return this;
	}

	public LoginPage Logout() {
		driver.findElement(LogoutLocator).click();
		return new LoginPage(driver);
	}

}
