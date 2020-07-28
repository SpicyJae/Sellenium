package fleetup.selenium.newUI;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class AlertTabTest extends DriverSetUp {
	By alertTableLocator = By.id("alertIndexTableDrivingInfo");
	By alertDetailTableLocator = By.id("alertDetailTable");
	By TDLocator = By.tagName("td");

	public static final int speedingAlert = 2;
	public static final int rpmAlert = 3;
	public static final int idlingAlert = 4;
	public static final int allAlerts = 5;

	@Rule
	public ErrorCollector collector = new ErrorCollector();

	@Test
	public void test() throws InterruptedException {
		SelectTab tabChange = new SelectTab(driver);
		tabChange = tabChange.gotoAlert();
		DateRange dateRange = new DateRange(driver);
		if (dateRange.defaultDateCheck("current")) {
			vehicleAlertTest();
		}
	}

	public void vehicleAlertTest() {
		List<WebElement> alertListTable = driver.findElements(alertTableLocator); // List of the complete Alert Table

		for (WebElement alert : alertListTable) {
			List<WebElement> alertList = alert.findElements(TDLocator);// List of each vehicle
			System.out.println(alertList.get(1).getText());
			System.out.println(alertList.get(allAlerts).getText());

			if (!alertList.get(allAlerts).getText().equals("0")) {
				alertList.get(allAlerts).click();// is not clicking Need to fix
				System.out.println(alertList.get(allAlerts).getText());
				List<WebElement> alertDetailTable = driver.findElements(alertDetailTableLocator);
				System.out.println(alertDetailTable.size());
				collector.checkThat("Number of alerts does not match the number of rows in alert table",
						Integer.parseInt(alertListTable.get(allAlerts).getText()), is(alertDetailTable.size()));
			}
		}
	}
}
