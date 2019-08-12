package com.slate.android.ui.tests;

import org.apache.commons.lang3.RandomStringUtils;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import com.slate.android.AppiumDriver;
import com.slate.android.ui.HomeScreen;
import com.slate.android.ui.LoginScreen;
import com.slate.android.ui.ProjectScreen;
import com.slate.api.ApiService;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;

public class EndToEndTests {

	public static String URL = "https://todoist.com/api/v7/sync";
	public static String token = "c7179ae59e4f823220c6980c8a0deeccdcc6761d";
	public static String prjtName = "SlateStudio Project - ";
	ApiService api;
	AndroidDriver<AndroidElement> driver;

	LoginScreen loginScr;
	HomeScreen homeScr;
	ProjectScreen prjScr;

	@BeforeSuite
	public void setupSuite() {
		System.out.println("Before Suite");
		api = new ApiService(URL, token);
		AppiumDriver.setUp();
		this.driver = AppiumDriver.getDriver();
		prjtName += getRandomString();
		loginScr = new LoginScreen(driver);
		homeScr = new HomeScreen(driver);
		prjScr = new ProjectScreen(driver);
		
	}

	private String getRandomString() {
		return RandomStringUtils.randomAlphabetic(5);
	}
	
//	@BeforeMethod
	public void setup() {
		System.out.println("Before method");
		loginScr.login("praveenmukilan@gmail.com", "Letmein01");
	}
	
	@AfterMethod
	public void tearDown() {
		System.out.println("After method");
		homeScr.logout();
	}

	@Test
	public void createProject() {
		try {
			System.out.println("Create Project - Test");
			api.createProject(prjtName);
			login();
			homeScr.openProject(prjtName);
			System.out.println(prjScr.getProjectTitle().equals(prjtName));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void createTask() {
		String name = "SlateStudio Task - " + getRandomString();
		login();
		homeScr.openProject(prjtName);
		prjScr.createTask(name);

	}
	
	@Test
	public void reopenTask() {
		String name = "SlateStudio Task - " + getRandomString();
		login();
		homeScr.openProject(prjtName);
		prjScr.createTask(name);
		prjScr.completeTask(name);
	}

	@AfterSuite
	public void tearDownSuite() {
		this.driver.quit();
	}
	
	private void login() {
		loginScr.login("praveenmukilan@gmail.com", "Letmein01");
	}

//	public static void main(String args[]) {
//		EndToEndTests test = new EndToEndTests();
//		test.setupSuite();
//		test.createProject();
//		test.createTask();
//		test.reopenTask();
//	}

}
