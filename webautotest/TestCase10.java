package fleetup.selenium.webautotest;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

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
 * Create New Supervisor User > Login check > Delete 
 */
public class TestCase10 extends JunitMain {
	
	private static final int USERID_INDEX = 0;
	private static final int USERNAME_INDEX = 1;
	
	@Rule
    public ErrorCollector collector = new ErrorCollector();
	
	@Test
	public void run(){
		try{
			
			LoginPage loginPage = PageFactory.initElements(driver, LoginPage.class);
			SelectTab selectPage = loginPage.loginAs(acct2_id,acct2_pw);
			WebDriverWait wait = new WebDriverWait(driver, 100); 
			wait.until(ExpectedConditions.jsReturnsValue("return document.readyState==\"complete\";"));
			/*
			 * Declare
			 */
			By Add_User_Button = By.xpath("//*[@class='btn_add']/a");
			By User_Id_New = By.id("userIdNew");
			By Password_New = By.id("passwordNew");
			By Password_Con_New = By.id("passwordConNew");
			By User_Name_New = By.id("userNameNew");
			By Phone = By.id("phone");
			By Email = By.id("email");
			By Update_Button = By.id("newUserUpdateBtn");
			By Check_PopUp = By.xpath("//*[@class='messager-button']//*[@class='l-btn']");
			By Menu_List = By.xpath("//*[@id='meno']/li");
			By AdminSubTab = By.id("user_menu_02");
			By TDLocator = By.tagName("td");
			By DeleteDriverButton = By.className("link_ban");
			By PopUpOKButton = By.xpath("//*[@class='messager-button']//span[text()='Ok']");
			By Admin_User_List = By.xpath("//*[@class='user']//*[@class='table_list']//*[@class='st-body-table']/tbody/tr");
			By AccountTable = By.xpath("//*[@id='user_menu_01_cont']//*[@id='driverTbody']");
			/*
			 * User Info for Driver
			 */
			String superUserId="super";
			String superUserPw="asdf";
			String superUserName="super2";
			String superUserPhone="669-271-5630";
			String superUserMail="gchoi@fleetup.com";
			String supervisor = "Supervisor";
			/*
			 * Go to Account Setting
			 */
			selectPage.gotoAccountSetting();
			wait.until(ExpectedConditions.presenceOfElementLocated(AccountTable));
			WebElement Role_Type_Web = driver.findElement(By.id("osRoleType"));
			/*
			 * Click user Add Button & Select role type = supervisor
			 */
			driver.findElement(Add_User_Button).click();
			Select roleTypeSel= new Select(Role_Type_Web);
			/*
			 * Fill out the form
			 */
			roleTypeSel.selectByVisibleText(supervisor);
			driver.findElement(User_Id_New).sendKeys(superUserId);
			driver.findElement(Password_New).sendKeys(superUserPw);
			driver.findElement(Password_Con_New).sendKeys(superUserPw);
			driver.findElement(User_Name_New).sendKeys(superUserName);
			driver.findElement(Phone).sendKeys(superUserPhone);
			driver.findElement(Email).sendKeys(superUserMail);
			driver.findElement(Update_Button).click();
			driver.findElement(Check_PopUp).click();
			/*
			 * Supervisor's menu list should be same with Admin account menu list
			 */
			List<WebElement> menuList = driver.findElements(Menu_List);
			selectPage.Logout();
			/*
			 * Login with new Supervisor account
			 */
			loginPage.loginAs(superUserId,superUserPw);
			wait.until(ExpectedConditions.jsReturnsValue("return document.readyState==\"complete\";"));
			List<WebElement> menuList2 = driver.findElements(Menu_List);
			for( int i=0 ; i < menuList2.size()-1 ; i++ ){
				collector.checkThat("menu tab is different", menuList2.get(i),is(menuList.get(i)));
			}
			selectPage.Logout();
			/*
			 * Delete Supervisor user
			 */
			loginPage.loginAs(acct2_id,acct2_pw);
			wait.until(ExpectedConditions.jsReturnsValue("return document.readyState==\"complete\";"));
			selectPage.gotoAccountSetting();
			wait.until(ExpectedConditions.presenceOfElementLocated(AccountTable));
			driver.findElement(AdminSubTab).click();
			/*
			 * Search the Supervisor and delete
			 */
			List<WebElement> adminUserList = driver.findElements(Admin_User_List);
			for(WebElement adminUser : adminUserList){
				List<WebElement> adminUserElementList = adminUser.findElements(TDLocator);
				if((adminUserElementList.get(USERID_INDEX).getText().equals(superUserId))&&(adminUserElementList.get(USERNAME_INDEX).getText().equals(superUserName))){
					adminUser.findElement(DeleteDriverButton).click();
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
