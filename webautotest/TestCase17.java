package fleetup.selenium.webautotest;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.fail;

import java.text.SimpleDateFormat;
import java.util.Date;
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
 * Online vehicle Time check : Current time - Last Update time < 1 minutes
 */
public class TestCase17 extends JunitMain {
	
	@Rule
    public ErrorCollector collector = new ErrorCollector();
	
	@Test
	public void run(){
		try{
			LoginPage loginPage = PageFactory.initElements(driver, LoginPage.class);
			SelectTab selectPage = loginPage.loginAs(acct1_id,acct1_pw);
			WebDriverWait wait = new WebDriverWait(driver, 100); 
			wait.until(ExpectedConditions.jsReturnsValue("return document.readyState==\"complete\";"));
			/*
			 * Go to Map Tab
			 */
			selectPage.gotoMap();
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id='tt_ul']")));
			/*
			 * Declare
			 */
			By Car_List_Locator = By.xpath("//ul[@id='tt_ul']/li/ul/li");
			By LastUpdate_Time = By.xpath("//*[@id='tbl_vehicle']/tbody/tr[3]/td[2]");
			By MapUploadCheck = By.id("tbl_vehicle");
			List<WebElement> carNumList = driver.findElements(Car_List_Locator);
			Time tm = new Time();
			/*
			 * num is indicator for vehicle which is default or group icon.
			 */
			int num = 0;
			for(WebElement carNumListEl : carNumList){
				String carGroup = carNumListEl.findElement(By.xpath("//ul[@id='tt_ul']/li/ul/li["+(num+1)+"]/div/span[3]")).getAttribute("class");
				/*
				 * NOT INSIDE -> If the icon is group icon & green, 
				 */
				if(carGroup.equals("tree-icon tree-folder icon-onLineHouse")){
					carNumListEl.findElement(By.xpath("//ul[@id='tt_ul']/li/ul/li["+(num+1)+"]/div/span[2]")).click();
					List<WebElement> insideCarNumList = driver.findElements(By.xpath("//ul[@id='tt_ul']/li/ul/li["+(num+1)+"]/ul/li"));
					/*
					 * num_2 is indicator for vehicle which is inside the group 
					 */
					int num2 = 0;
					for(WebElement insideCarNumListEl : insideCarNumList){
						String insideCarStatus = insideCarNumListEl.findElement(By.xpath("//ul[@id='tt_ul']/li/ul/li["+(num+1)+"]/ul/li["+(num2+1)+"]/div/span[4]")).getAttribute("class");
						/*
						 * Choose only online vehicle - vehicle
						 */
						if(insideCarStatus.equals("tree-icon tree-file icon-onLineCar")){
							/*
							 * Click the checkbox
							 */
							insideCarNumListEl.findElement(By.xpath("//ul[@id='tt_ul']/li/ul/li["+(num+1)+"]/ul/li["+(num2+1)+"]/div/span[5]")).click();
							wait.until(ExpectedConditions.presenceOfElementLocated(MapUploadCheck));
							/*
							 *  
							 */
							String clickCarTime = driver.findElement(LastUpdate_Time).getText();
							long time = System.currentTimeMillis(); 
							java.util.Date currentTime = tm.yyyyMMddHHmmssFormat(new Date(time));
							java.util.Date clickCarTransToDate = tm.yyyyMMddHHmmssFormat(clickCarTime);
							long timeInterval = currentTime.getTime()-clickCarTransToDate.getTime();
							collector.checkThat("Update time for online car is over 1 minute", timeInterval > 60000 ,is(false));
							insideCarNumListEl.findElement(By.xpath("//ul[@id='tt_ul']/li/ul/li["+(num+1)+"]/ul/li["+(num2+1)+"]/div/span[5]")).click();
						}
						/*
						 * Choose only online vehicle - asset
						 */
						else if(carGroup.equals("tree-icon tree-file icon-asset-car")){
							/*
							 * Click the check box for vehicle
							 */
							insideCarNumListEl.findElement(By.xpath("//ul[@id='tt_ul']/li/ul/li["+(num+1)+"]/ul/li["+(num2+1)+"]/div/span[5]")).click();
							wait.until(ExpectedConditions.presenceOfElementLocated(MapUploadCheck));
							String clickCarTime = driver.findElement(LastUpdate_Time).getText();
							long time = System.currentTimeMillis(); 
							java.util.Date currentTime = tm.yyyyMMddHHmmssFormat(new Date(time));
							java.util.Date clickCarTransToDate = tm.yyyyMMddHHmmssFormat(clickCarTime);
							long timeInterval = currentTime.getTime() - clickCarTransToDate.getTime();
							collector.checkThat("Update time for online car is over 1 minute", timeInterval > 60000 ,is(false));
							/*
							 * deselect the checkbox
							 */
							insideCarNumListEl.findElement(By.xpath("//ul[@id='tt_ul']/li/ul/li["+(num+1)+"]/ul/li["+(num2+1)+"]/div/span[5]")).click();
						}
						num2++;
					}
				}
				/*
				 * NOT INSIDE CAR - DEFAULT GROUP - VEHILCE
				 */
				else if(carGroup.equals("tree-icon tree-file icon-onLineCar")){
					/*
					 * Select the checkbox
					 */
					carNumListEl.findElement(By.xpath("//ul[@id='tt_ul']/li/ul/li["+(num+1)+"]/div/span[4]")).click();
					wait.until(ExpectedConditions.presenceOfElementLocated(MapUploadCheck));
					String clickCarTime = driver.findElement(LastUpdate_Time).getText();
					long time = System.currentTimeMillis(); 
					java.util.Date currentTime = tm.yyyyMMddHHmmssFormat(new Date(time));
					java.util.Date clickCarTransToDate = tm.yyyyMMddHHmmssFormat(clickCarTime);
					long timeInterval = currentTime.getTime() - clickCarTransToDate.getTime();
					collector.checkThat("Update time for online car is over 1 minute", timeInterval > 60000 ,is(false));
					/*
					 * deselect the checkbox
					 */
					carNumListEl.findElement(By.xpath("//ul[@id='tt_ul']/li/ul/li["+(num+1)+"]/div/span[4]")).click();
				
				}
				/*
				 * If the asset car is online
				 */
				else if(carGroup.equals("tree-icon tree-file icon-asset-car")){
					carNumListEl.findElement(By.xpath("//ul[@id='tt_ul']/li/ul/li["+(num+1)+"]/div/span[4]")).click();
					wait.until(ExpectedConditions.presenceOfElementLocated(MapUploadCheck));
					String clickCarTime = driver.findElement(LastUpdate_Time).getText();
					long time = System.currentTimeMillis(); 
					java.util.Date currentTime = tm.yyyyMMddHHmmssFormat(Long.toString(time));
					java.util.Date clickCarTransToDate = tm.yyyyMMddHHmmssFormat(clickCarTime);
					long timeInterval = currentTime.getTime() - clickCarTransToDate.getTime();
					collector.checkThat("Update time for online car is over 1 minute", timeInterval > 60000 ,is(false));
					/*
					 * deselect the checkbox
					 */
					carNumListEl.findElement(By.xpath("//ul[@id='tt_ul']/li/ul/li["+(num+1)+"]/div/span[4]")).click();
				
				}
				num++;
			}
			selectPage.Logout();
		}
		catch(Exception e){
			fail("Catch exception : " + e.getMessage());
		}
	}
}
