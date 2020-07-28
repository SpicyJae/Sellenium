package fleetup.selenium.webautotest;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

public class DateRange {
	
	private final WebDriver driver;
	private final HashMap<Integer,String> month_map = new HashMap<Integer,String>();
	
    public DateRange(WebDriver driver) {
        this.driver = driver;
        /*
         * Change from num to Character for pick webelement
         */
        month_map.put(0, "Jan");
        month_map.put(1, "Feb");
        month_map.put(2, "Mar");
        month_map.put(3, "Apr");
        month_map.put(4, "May");
        month_map.put(5, "Jun");
        month_map.put(6, "Jul");
        month_map.put(7, "Aug");
        month_map.put(8, "Sep");
        month_map.put(9, "Oct");
        month_map.put(10, "Nov");
        month_map.put(11, "Dec");
    }
    
    By StartDateLocator = By.id("startDate");
 	By EndDateLocator = By.id("endDate");
    By MonthSelectorLocator = By.className("ui-datepicker-month");
    By YearSelectorLocator = By.className("ui-datepicker-year");
    By Search_Button = By.className("btn_search"); 
    /*
     * Default date check 
     * should be current date. 
     */
 	public boolean DefaultDateCheck() {
 		boolean correct = false;
 		String start_date = driver.findElement(StartDateLocator).getAttribute("value");
		String end_date = driver.findElement(EndDateLocator).getAttribute("value");
		Time time = new Time();
		Date date = new Date();
		String current_date = time.MMDDYYYYFormat(date);
		if ( (current_date.equals(start_date)) && (current_date.equals(end_date)) ){
			correct = true;
		}
		else{
			correct = false;
		}
        return correct;
 	}
 	
 	
 	/*
 	 * Change the start date
 	 */
 	public DateRange ChangeStartDateRange ( int Year , int month , int day ) {
 		driver.findElement(StartDateLocator).click();
 		/*
 		 * Year
 		 */
 	    WebElement year_select = driver.findElement(YearSelectorLocator);
 	    Select Select_year_select = new Select(year_select);
 	    Select_year_select.selectByValue(Integer.toString(Year));
 	    /*
 	     * Month
 	     */
	    WebElement month_select = driver.findElement(MonthSelectorLocator);
	    Select Select_month_select= new Select(month_select);
	    Select_month_select.selectByVisibleText(month_map.get(month));
	    /*
	     * Day
	     */
	    By DatePickerLocator = By.xpath("//*[@class='ui-datepicker-calendar']//a[text()='"+day+"']");
	    WebElement date_pick = driver.findElement(DatePickerLocator);
	    Actions actions = new Actions(driver);
	    actions.moveToElement(date_pick).click().perform();
        return this;
    }
 	
 	/*
 	 * Change the end date
 	 */
 	public DateRange ChangeEndDateRange ( int Year , int month , int day ) {
 		driver.findElement(EndDateLocator).click();
 		/*
 		 * Year
 		 */
 	    WebElement year_select = driver.findElement(YearSelectorLocator);
	    Select Select_year_select= new Select(year_select);
	    Select_year_select.selectByValue(Integer.toString(Year));
	    /*
	     * Month
	     */
	    WebElement month_select = driver.findElement(MonthSelectorLocator);
	    Select Select_month_select= new Select(month_select);
	    Select_month_select.selectByVisibleText(month_map.get(month));
	    /*
	     * Day    
	     */
	    By DatePickerLocator = By.xpath("//*[@class='ui-datepicker-calendar']//a[text()='"+day+"']");
	    WebElement date_pick = driver.findElement(DatePickerLocator);
	    Actions actions = new Actions(driver);
	    actions.moveToElement(date_pick).click().perform();
        return this;
 	}
 	
 	/*
 	 * Press search button
 	 */
 	public DateRange search() {
 		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
 		driver.findElement(Search_Button).click();
 		return this;
 	}
 	
}
