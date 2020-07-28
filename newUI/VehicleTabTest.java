package fleetup.selenium.newUI;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class VehicleTabTest extends DriverSetUp {

	By vehicleNum = By.id("vehicle_menu_01");
	By assetNum = By.id("vehicle_menu_02");
	By vehicleListLocator = By.xpath("//*[@id='fuelIndexTbody']/tr");
	By assetListLocator = By.xpath("//*[@id='fuelAssetMonitorTable']//*[@id='fuelIndexTbody']/tr");

	@Rule
	public ErrorCollector collector = new ErrorCollector();

	@Test
	public void test() throws Exception {
		SelectTab tabChange = new SelectTab(driver);
		tabChange = tabChange.gotoVehicle();
		DateRange dateRange = new DateRange(driver);
		if (dateRange.defaultDateCheck("current")) {
			checkVehicleNumber();
			checkAssetNumber();
		}
	}

	public void checkVehicleNumber() {
		String vehicleNumber = driver.findElement(vehicleNum).getText();
		vehicleNumber = vehicleNumber.substring(8, vehicleNumber.length());
		List<WebElement> vehicleList = driver.findElements(vehicleListLocator);
		collector.checkThat("Vehicle number does not match", Integer.parseInt(vehicleNumber), is(vehicleList.size()));
	}

	public void checkAssetNumber() throws InterruptedException {
		driver.findElement(assetNum).click();
		Thread.sleep(3000);
		String assetNumber = driver.findElement(assetNum).getText();
		assetNumber = assetNumber.substring(6, assetNumber.length());
		List<WebElement> assetList = driver.findElements(assetListLocator);
		collector.checkThat("Asset number does not match", Integer.parseInt(assetNumber), is(assetList.size()));
	}

}
