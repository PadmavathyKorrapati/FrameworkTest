

package com.bbb.hybrid;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;


public class BaseClass {

	public FileInputStream fis=null;
	public Properties prop=null;
	public WebDriver driver=null;
	public ExtentTest test=null;
	public ExtentReports report=null;



	//load config properties

	public void loadconfig() throws IOException {
		if(prop==null){


			fis= new FileInputStream("config.properties");
			prop= new Properties();
			prop.load(fis);
		}
	}

	public void launchbrowser(String browserType ){

		if(prop.getProperty(browserType).equals("Chrome")){

			System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"\\resources\\chromedriver.exe");
			driver= new ChromeDriver();


		}
		else if(prop.getProperty(browserType).equals("ff")) {

			System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir")+"\\resources\\geckodriver.exe" );

			driver=new FirefoxDriver();

		}




		else if(prop.getProperty(browserType).equals("IE")) {
			System.setProperty("webdriver.ie.driver", 
					System.getProperty("user.dir")+"\\resources\\IEDriverServer.exe");
			driver=new InternetExplorerDriver();
		}

		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}	
	public void navigate(String appurl){

		driver.navigate().to(prop.getProperty(appurl));
	}


	public WebElement getElement(String locatorkey) {

		if(locatorkey.endsWith("_Xpath")) {

			return driver.findElement(By.xpath(prop.getProperty("locatorkey")));
		}

		else if (locatorkey.endsWith("_id")) {

			return driver.findElement(By.xpath(prop.getProperty("locatorkey")));
		}

		else if(locatorkey.endsWith("name")) {

			return driver.findElement(By.xpath(prop.getProperty("locatorkey")));
		}

		else {

			System.out.println("locator not correct");
			testfail(locatorkey+"invalid locator provided!!");				
			return null;
		}

	}
	
	public void click(String locatorKey){
		WebElement elm=getElement(locatorKey);
		elm.click();
	}

	
	
	public boolean isElementDisplayed(String locatorKey){	
		try{
			boolean flag;
			if(locatorKey.endsWith("xpath")){
				driver.findElement(By.xpath(prop.getProperty(locatorKey))).isDisplayed();
				flag=true;
			}else if(locatorKey.endsWith("id")){
				driver.findElement(By.id(prop.getProperty(locatorKey))).isDisplayed();
				flag=true;
			}else if(locatorKey.endsWith("name")){
				driver.findElement(By.name(prop.getProperty(locatorKey))).isDisplayed();
				flag=true;
			}else{
				flag=false;
			}
			return flag;
		}catch(Exception e){
			return false;
		}
	}


	public boolean iselementdisplayed(String locatorkey){

		try{

			getElement(locatorkey);
			return true;
		}catch(Exception e){
			return false;
		}		

	}

	public void settext(String locatorkey,String Inputtext){

		WebElement elm=getElement(locatorkey);
		elm.sendKeys(Inputtext);
	}
	public void clcik(String locatorkey) {
		WebElement elm=getElement(locatorkey);
		elm.click();


	}

	public String gettitle(){
		return driver.getTitle();

	}

	public void JS_click(String locatorKey){
		WebElement elm=getElement(locatorKey);
		JavascriptExecutor JS=(JavascriptExecutor)driver;
		JS.executeScript("arguments[0].click();", elm);
	}

	public boolean verifytext(String actvalue,String expectedvalue){
		if(actvalue.equals(expectedvalue)){
			return true ;

		}

		else{
			return false;
		}

	}

	public void switchwindow(int index){

		driver.switchTo().frame(index);



	}



	//Reporting methods

	public void testpass(String message){

		test.log(LogStatus.PASS, message);
	}


	public void testfail(String message){

		test.log(LogStatus.PASS, message);
	}

	public void testinfo(String message) {

		test.log(LogStatus.INFO,message);
	}


	//Capturescreenshot
	public void TakeScreenshot(String msg) {

		try {



			TakesScreenshot screenshot=(TakesScreenshot)driver;

			File src=screenshot.getScreenshotAs(OutputType.FILE);

			String screenshotpath=System.getProperty("user.dir")+"\\Screenshot\\Screenshot_"+".png";

			FileUtils.copyFile(src, new File(screenshotpath));
			test.log(LogStatus.INFO,test.addScreenCapture(screenshotpath));
		}

		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}


}














