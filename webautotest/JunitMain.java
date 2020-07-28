package fleetup.selenium.webautotest;

import static org.junit.Assert.*;


import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.internal.ProfilesIni;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import junit.framework.TestSuite;

public class JunitMain{
	
	static WebDriver driver;
	static String acct1_id; 
	static String acct1_pw;
	/*
	 * For TestCase09
	 */
	static String acct2_id; 
	static String acct2_pw;
	String productionServer;
	String hotfixServer;
	String trunkServer;
	String stageServer;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		
	}
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		
	}

	@Before
	public void setUp() throws Exception {
		
		productionServer = "http://fleetup.us";
		hotfixServer = "http://hotfix.fleetuptrace.com";
		trunkServer = "http://trunk.fleetuptrace.com";
		stageServer = "http://staging.fleetuptrace.com";
		
		acct1_id = "fleetupdemo";
		acct1_pw = "fleetupdemous";
		
		driver = new HtmlUnitDriver();
	    System.setProperty("webdriver.chrome.driver", "./Test/Selenium_Driver/chromedriver_win32/chromedriver.exe");
	    DesiredCapabilities capabilities = DesiredCapabilities.chrome();
	    capabilities.setCapability("marionette", true);
	    ChromeOptions options = new ChromeOptions();
	    options.addArguments("--start-maximized");//changes the chrome option to maximized window -Andy
	    driver = new ChromeDriver(options); //starts chrome with the option -Andy
		driver.get(productionServer);
	}//*[@id="userId"]

	@After
	public void tearDown() throws Exception {
		driver.quit();
	}

}
