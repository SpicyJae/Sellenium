package fleetup.selenium.webautotest;

import static org.junit.Assert.fail;

import java.util.ArrayList;

import org.junit.Test;
import org.junit.runners.Parameterized.Parameters;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/*
 * This check the login process with account from db 
 * 
 * Testcase for valid account for each type ( Admin , Observer , Driver ) 
 */
public class TestCase01 extends JunitMain{
	
	private final int ROLE_TYPE_ADMIN = 2;
	private final int ROLE_TYPE_OBSERVER = 3;
	private final int ROLE_TYPE_DRIVER = 5;
	
	@Test
	public void run(){
		try{
			DBCon db = new DBCon(); 
			db.ConnectDB();
			/*
			 * Login with admin account
			 */
			for ( int i = 0 ; i < 4 ; i++ ) {
				LoginPage loginPage = PageFactory.initElements(driver, LoginPage.class);
				SelectTab selectPage = loginPage.loginAs(db.GetAvailAccount(ROLE_TYPE_ADMIN), "asdf");
				WebDriverWait wait = new WebDriverWait(driver, 100); 
				wait.until(ExpectedConditions.jsReturnsValue("return document.readyState==\"complete\";"));
				selectPage.Logout();
			}
			/*
			 * Login with observer account
			 */
			for ( int i = 0 ; i < 4 ; i++ ) {
				LoginPage loginPage = PageFactory.initElements(driver, LoginPage.class);
				SelectTab selectPage = loginPage.loginAs(db.GetAvailAccount(ROLE_TYPE_OBSERVER), "asdf");
				WebDriverWait wait = new WebDriverWait(driver, 100); 
				wait.until(ExpectedConditions.jsReturnsValue("return document.readyState==\"complete\";"));
				selectPage.Logout();
			}
			/*
			 * Login with observer account
			 */
			for ( int i = 0 ; i < 4 ; i++ ) {
				LoginPage loginPage = PageFactory.initElements(driver, LoginPage.class);
				SelectTab selectPage = loginPage.loginAs(db.GetAvailAccount(ROLE_TYPE_DRIVER), "asdf");
				WebDriverWait wait = new WebDriverWait(driver, 100); 
				wait.until(ExpectedConditions.jsReturnsValue("return document.readyState==\"complete\";"));
				selectPage.Logout();
			}
		}
		catch(Exception e){
			fail("Catch exception : " + e.getMessage());
		}
	}
}
