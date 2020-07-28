package fleetup.selenium.newUI;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.JavascriptExecutor;

import fleetup.selenium.webautotest.Time;

public class DateRange {

	WebDriver driver;

	public DateRange(WebDriver driver) {
		this.driver = driver;
	}

	By date_selection = By.xpath("//input[@value='2016-12-18 - 2017-01-16']");
	By startDate = By.id("startDate");
	By endDate = By.id("endDate");

	public boolean defaultDateCheck(String expectedDateRange) throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(driver, 100);
		wait.until(ExpectedConditions.jsReturnsValue("return document.readyState==\"complete\";"));
		Thread.sleep(3000); // wait 3 seconds so it can get the real value instead of the value on website

		String date_range = driver.findElement(date_selection).getAttribute("value");
		String start_date = date_range.substring(0, 10);
		String end_date = date_range.substring(13, 23);
		Time time = new Time();
		Date date = new Date();
		String current_date = time.MMDDYYYYFormat(date);
		current_date = current_date.substring(0, 2) + "-" + current_date.substring(3, 5) + "-"
				+ current_date.substring(6, 10);
		if (expectedDateRange.equalsIgnoreCase("current")) {
			if ((current_date.equals(start_date)) && (current_date.equals(end_date))) {
				return true;
			} else {
				return false;
			}
		} else if (expectedDateRange.equalsIgnoreCase("previous")) {
			String previousWeek_date = time.MMDDYYYYFormat(getPreviousWeekDate());
			previousWeek_date = previousWeek_date.substring(0, 2) + "-" + previousWeek_date.substring(3, 5) + "-"
					+ previousWeek_date.substring(6, 10);
			if ((previousWeek_date.equals(start_date)) && (current_date.equals(end_date))) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	private Date getPreviousWeekDate() {
		return new Date(System.currentTimeMillis() - 7 * 24 * 60 * 60 * 1000);
	}
}
