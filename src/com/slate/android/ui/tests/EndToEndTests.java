package com.slate.android.ui.tests;

import org.apache.commons.lang3.RandomStringUtils;
import org.testng.annotations.AfterSuite;
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
	public static String prjtName = "SlateStudio Test - ";
	ApiService api;
	AndroidDriver<AndroidElement> driver;

	@BeforeSuite
	public void setup() {
		api = new ApiService(URL, token);
		AppiumDriver.setUp();
		this.driver = AppiumDriver.getDriver();
		prjtName += getRandomString();
	}
	
	private String getRandomString() {
		return RandomStringUtils.randomAlphabetic(5);
	}
	
	@Test
	public void testCreateProject() {
		try {
			System.out.println("Create Project - Test");
			api.createProject(prjtName);
			LoginScreen loginScreen = new LoginScreen(driver);
			loginScreen.login("praveenmukilan@gmail.com", "Letmein01");
			HomeScreen homeScreen = new HomeScreen(driver);
			homeScreen.hamburgerMenu.click();
			homeScreen.clickProjectsOption();
			homeScreen.selectProject(prjtName);
			ProjectScreen prjScreen = new ProjectScreen(driver);
			System.out.println(prjScreen.getProjectTitle().equals(prjtName));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	@AfterSuite
	public void tearDown() {
		this.driver.quit();
	}
	
	public static void main(String args[]) {
		EndToEndTests test = new EndToEndTests();
		test.setup();
		test.testCreateProject();
	}

}
