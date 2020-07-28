package fleetup.selenium.newUI;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.regex.Pattern;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.SimpleTimeZone;
/*
 * This is using for login page 
 * Ex) Filling out the id & password field
 * 
 */
public class LoginPage {

	private final WebDriver driver;
	private final WebDriverWait wait;

    //By usernameLocator = By.id("userId");
    By passwordLocator = By.id("password");
    By accountLocator = By.xpath("//input[@id='1']");
    By newUsernameLocator = By.name("userId");
    By newUser_nameLocator = By.className("form-control");
    /*
     *  previous button before new ui
     */
    //By loginButtonLocator = By.className("login_submit_btn");
    By loginButtonLocator = By.xpath("//input[@value='Log in']");
    By checkLoginMsgLocator = By.id("checkLoginMsg");
    By newLoginButtonLocator = By.xpath("//input[@value='New Experience (Beta)']");
    
    public LoginPage(WebDriver driver) {
    	this.driver = driver;
    	wait = new WebDriverWait(driver, 100);
    }
    
    public LoginPage typeUsername(String username) {
    	driver.findElement(newUsernameLocator).sendKeys(username);
    	return this;    
    	//changed from usernameLocator
    }
    
    public LoginPage typePassword(String password) {
    	driver.findElement(passwordLocator).sendKeys(password);
    	return this;    
    }

    /**
     * Written by Andy to test the UI4.0
     * @return
     */
    public SelectTab submitNewLogin() {
    	driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    	driver.findElement(newLoginButtonLocator).click();
    	return new SelectTab(driver); 
    }
    
    /**
     * Written by Andy to test the UI4.0
     * @return
     */
    public SelectTab newLoginAs(String username, String password) {
    	checkloadloginPage();
    	typeUsername(username);
    	typePassword(password);
    	return submitNewLogin();
    }
    
    public String checkLoginMessage() {
    	return driver.findElement(checkLoginMsgLocator).getText();
    }
    
    public void checkloadloginPage(){
    	wait.until(ExpectedConditions.presenceOfElementLocated(loginButtonLocator));
    }
}