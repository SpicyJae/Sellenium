package fleetup.selenium.newUI;

import static org.junit.Assert.*;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ReportTabTest extends DriverSetUp {
	
	By dropDownMenu = By.id("reportEXButton");
	By exportButton = By.id("exportBtn");
	
	@Test
	public void test() throws InterruptedException {
		SelectTab tabChange = new SelectTab(driver);
		tabChange = tabChange.gotoReport();
		DateRange dateRange = new DateRange(driver);
		if (dateRange.defaultDateCheck("previous")) {
			System.out.println("not yet implemented");
			reportExport();
		}

	}
	
	public void reportExport() throws InterruptedException {
		driver.findElement(exportButton).click();
		driver.findElement(dropDownMenu).click();
		Thread.sleep(3000);
	}

}
