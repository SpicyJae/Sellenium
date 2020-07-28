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
 * Create New Observer User > Login check > Delete 
 */
public class TestCase09 extends JunitMain {
	
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
			By User_Id_New = By.id("userIdNew");
			By Password_New = By.id("passwordNew");
			By Password_Con_New = By.id("passwordConNew");
			By User_Name_New = By.id("userNameNew");
			By Phone = By.id("phone");
			By Email = By.id("email");
			By Update_Button = By.id("newUserUpdateBtn");
			By Check_PopUp = By.xpath("//*[@class='messager-button']//*[@class='l-btn']");
			By UserTable = By.xpath("//*[@id='user_menu_01_cont']//*[@id='driverTbody']");
			By AdminSubTab =  By.id("user_menu_02");
			By Admin_User_List = By.xpath("//*[@class='user']//*[@class='table_list']//*[@class='st-body-table']/tbody/tr");
			By Add_User_Button = By.xpath("//*[@class='btn_add']/a");
			By TDLocator = By.tagName("td");
			By DeleteDriverButton = By.className("link_ban");
			By PopUpOKButton = By.xpath("//*[@class='messager-button']//span[text()='Ok']");
			By AccountTable = By.xpath("//*[@id='user_menu_01_cont']//*[@id='driverTbody']");
			By ObserverRole = By.id("osRoleType");
			String observer = "Observer";
			/*
			 * User Info for Driver
			 */
			String obserUserId="obser";
			String obserUserPw="asdf";
			String obserUserName="obser2";
			String obserUserPhone="669-271-5630";
			String obserUserMail="gchoi@fleetup.com";
			/*
			 * Go to Account Setting
			 */
			selectPage.gotoAccountSetting();
			wait.until(ExpectedConditions.presenceOfElementLocated(AccountTable));
			WebElement roleTypeWeb = driver.findElement(ObserverRole);
			/*
			 * Click User Add Button
			 */
			driver.findElement(Add_User_Button).click();
			/*
			 * Choose the role type = Observer
			 */
			Select roleTypeSel= new Select(roleTypeWeb);
			roleTypeSel.selectByVisibleText(observer);
			/*
			 * Fill out the form
			 */
			driver.findElement(User_Id_New).sendKeys(obserUserId);
			driver.findElement(Password_New).sendKeys(obserUserPw);
			driver.findElement(Password_Con_New).sendKeys(obserUserPw);
			driver.findElement(User_Name_New).sendKeys(obserUserName);
			driver.findElement(Phone).sendKeys(obserUserPhone);
			driver.findElement(Email).sendKeys(obserUserMail);
			/*
			 * Update
			 */
			driver.findElement(Update_Button).click();
			driver.findElement(Check_PopUp).click();
			selectPage.Logout();
			/*
			 * Login with new observer account
			 */
			loginPage.loginAs(obserUserId,obserUserPw);
			wait.until(ExpectedConditions.jsReturnsValue("return document.readyState==\"complete\";"));
			selectPage.Logout();
			/*
			 * Delete observer user account
			 */
			loginPage.loginAs(acct2_id,acct2_pw);
			wait.until(ExpectedConditions.jsReturnsValue("return document.readyState==\"complete\";"));
			selectPage.gotoAccountSetting();
			wait.until(ExpectedConditions.presenceOfElementLocated(UserTable));
			driver.findElement(AdminSubTab).click();
			/*
			 * Search the Observer and delete
			 */
			List<WebElement> Admin_user_list = driver.findElements(Admin_User_List);
			for(WebElement admin_user : Admin_user_list){
				List<WebElement> admin_user_element_list = admin_user.findElements(TDLocator);
				if((admin_user_element_list.get(USERID_INDEX).getText().equals(obserUserId))&&(admin_user_element_list.get(USERNAME_INDEX).getText().equals(obserUserName))){
					admin_user.findElement(DeleteDriverButton).click();
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
