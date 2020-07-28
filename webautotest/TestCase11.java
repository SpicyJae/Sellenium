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
import org.openqa.selenium.support.ui.WebDriverWait;
/*
 * Check the admin & driver num
 */
public class TestCase11 extends JunitMain {
	
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
			By Driver_Total_Num = By.id("user_menu_01");
			By DriverListLocator = By.xpath("//*[@id='driverTbody']/tr");
			By Admin_Total_Num = By.id("user_menu_02");
			By Admin_User_List = By.xpath("//*[@class='user']//*[@class='table_list']//*[@class='st-body-table']/tbody/tr");
			By AccountTable = By.xpath("//*[@id='user_menu_01_cont']//*[@id='driverTbody']");
			/*
			 * Go to Account Setting
			 */
			selectPage.gotoAccountSetting();
			wait.until(ExpectedConditions.presenceOfElementLocated(AccountTable));
			/*
			 * Driver num
			 */
			String driverTotalNum = getNumFromText(driver.findElement(Driver_Total_Num).getText());
			/*
			 * Comparing the driver num and row #
			 */
			List<WebElement> vehicleList = driver.findElements(DriverListLocator);
			collector.checkThat("Driver number", vehicleList.size(),is(Integer.parseInt(driverTotalNum)));
			/*
			 * Admin num
			 */
			driver.findElement(Admin_Total_Num).click();
			String adminTotalNum = getNumFromText(driver.findElement(Admin_Total_Num).getText());
			/*
			 * Comparing the admin num and row #
			 */
			List<WebElement> adminUserList = driver.findElements(Admin_User_List);
			collector.checkThat("Admin number", adminUserList.size(),is(Integer.parseInt(adminTotalNum)));
			selectPage.Logout();
		}
		catch(Exception e){
			fail("Catch exception : " + e.getMessage());
		}
	}
	public String getNumFromText(String input){
		input = input.substring(input.indexOf("(")+1);
		input = input.substring(0, input.indexOf(")"));
		return input;
	}
}
