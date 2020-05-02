package com.bbb.hybrid.testcases;

import java.io.IOException;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.bbb.hybrid.BaseClass;
import com.bbb.hybrid.util.Extentmanager;
import com.relevantcodes.extentreports.LogStatus;

public class CreateAccountTestcase extends BaseClass {

	String TestCase="CreateAccountTestcase";

	@Test

	public void CreateAccountTC( ) throws IOException, Exception{

		report=Extentmanager.getInstance();
		test=report.startTest(TestCase);
		test.log(LogStatus.INFO, "Started execution"+TestCase);
		//Selenium flow
		loadconfig();

		launchbrowser("browser");
		navigate("appUrl");
		testinfo("Automation practice site launched");		


		click("SignIn_Xpath");
		settext("CreateAccountEmail_Xpath","pa621131@gmail.com");
		click("CreateAccountButton_Xpath");

		//testpass("Account created successfully");
		TakeScreenshot("Home page displayed");
		report.endTest(test);



	}

	@AfterMethod
	public void teardown(){

		driver.quit();
		report.flush();
	}




}


