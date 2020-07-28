package fleetup.selenium.webautotest;


import java.util.Random;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

/*
 * This check the login process with unavailable account from db 
 * 
 * Testcase for invalid account
 */

public class TestCase02 extends JunitMain{
	
	@Rule
    public ErrorCollector collector = new ErrorCollector();
	
	@Test
	public void run(){
		try{
			invalid_login_check();
		}
		catch(Exception e){
			fail("Catch exception : " + e.getMessage());
		}
		
	}
	
	private void invalid_login_check() {
		Random rn = new Random();
		for ( int i = 0 ; i < 4 ; i++ ) {
			LoginPage loginPage = PageFactory.initElements(driver, LoginPage.class);
			loginPage.loginAsWrongUser(RandomStringUtils.randomAlphanumeric(rn.nextInt(10) + 1),RandomStringUtils.randomAlphanumeric(rn.nextInt(10) + 1));
			collector.checkThat(loginPage.checkLoginMessage(),is("Sorry,Permission Denied!"));
			driver.navigate().refresh();
		}
	}
	
}

