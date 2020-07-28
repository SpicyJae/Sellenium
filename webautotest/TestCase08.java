package fleetup.selenium.webautotest;

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
 * Create New Driver User > Login check > Delete 
 */
public class TestCase08 extends JunitMain {
	
	private static final int USERID_INDEX = 0;
	private static final int USERNAME_INDEX = 1;
	
	@Rule
    public ErrorCollector collector = new ErrorCollector();
	
	@Test
	public void run(){
		try{
			
			LoginPage loginPage = PageFactory.initElements(driver, LoginPage.class);
			SelectTab selectPage = loginPage.loginAs(acct1_id,acct1_pw);
			WebDriverWait wait = new WebDriverWait(driver, 100); 
			wait.until(ExpectedConditions.jsReturnsValue("return document.readyState==\"complete\";"));
			
			selectPage.gotoAccountSetting();
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id='user_menu_01_cont']//*[@id='driverTbody']")));
			/*
			 * Declare
			 */
			By Add_User_Button = By.xpath("//*[@class='btn_add']/a");
			By User_Id_New = By.id("userIdNew");
			By Password_New = By.id("passwordNew");
			By Password_Con_New = By.id("passwordConNew");
			By Driver_Lic_New = By.id("driverLicNew");
			By Select_Vehicle = By.xpath("//*[@id='osVehicle']//*[@class='combo-arrow']");
			By Select_All_Vehicle = By.xpath("//*[@class='tree-node tree-root-one tree-node-last']//*[@class='tree-checkbox tree-checkbox0']");
			By User_Name_New = By.id("userNameNew");
			By Phone = By.id("phone");
			By Email = By.id("email");
			By Update_Button = By.id("newUserUpdateBtn");
			By Check_PopUp = By.xpath("//*[@class='messager-button']//*[@class='l-btn']");
			By DriverListLocator = By.xpath("//*[@id='driverTbody']/tr");
			By TDLocator = By.tagName("td");
			By DeleteDriverButton = By.className("link_ban");
			By PopUpOKButton = By.xpath("//*[@class='messager-button']//span[text()='Ok']");
			By AccountTable = By.xpath("//*[@id='user_menu_01_cont']//*[@id='driverTbody']");
			WebElement Role_Type_Web = driver.findElement(By.id("osRoleType"));
			/*
			 * User Info for Driver
			 */
			String driverUserId="seleniumtest";
			String driverUserPw="asdf";
			String driverUserLicense="213LGE423s";
			String driverUserName="gahyunseltest";
			String driverUserPhone="669-271-5630";
			String driverUserMail="gchoi@fleetup.com";
			/*
			 * Click Add user button
			 */
			driver.findElement(Add_User_Button).click();
			/*
			 * Click the role type ( Dropdown ) - Driver
			 */
			Select roleTypeSel= new Select(Role_Type_Web);
			roleTypeSel.selectByVisibleText("Driver");
			/*
			 * Fill out the form 
			 */
			driver.findElement(User_Id_New).sendKeys(driverUserId);
			driver.findElement(Password_New).sendKeys(driverUserPw);
			driver.findElement(Password_Con_New).sendKeys(driverUserPw);
			driver.findElement(Driver_Lic_New).sendKeys(driverUserLicense);
			driver.findElement(Select_Vehicle).click();
			driver.findElement(Select_All_Vehicle).click();
			driver.findElement(User_Name_New).sendKeys(driverUserName);
			driver.findElement(Phone).sendKeys(driverUserPhone);
			driver.findElement(Email).sendKeys(driverUserMail);
			/*
			 * Update 
			 */
			driver.findElement(Update_Button).click();
			driver.findElement(Check_PopUp).click();
			selectPage.Logout();
			/*
			 * Test Login with new Driver
			 */
			loginPage.loginAs(driverUserId,driverUserPw);
			selectPage.Logout();
			/*
			 * Delete That Driver
			 */
			loginPage.loginAs(acct1_id,acct1_pw);
			selectPage.gotoAccountSetting();
			wait.until(ExpectedConditions.presenceOfElementLocated(AccountTable));
			List<WebElement> driverLists = driver.findElements(DriverListLocator);
			for(WebElement driverList : driverLists){
				List<WebElement> driverElementList = driverList.findElements(TDLocator);
				if((driverElementList.get(USERID_INDEX).getText().equals(driverUserName))&&(driverElementList.get(USERNAME_INDEX).getText().equals(driverUserId))){
					driverList.findElement(DeleteDriverButton).click();
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
