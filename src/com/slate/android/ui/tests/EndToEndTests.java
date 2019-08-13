package com.slate.android.ui.tests;

import com.slate.android.AppiumDriver;
import com.slate.android.ui.HomeScreen;
import com.slate.android.ui.LoginScreen;
import com.slate.android.ui.ProjectScreen;
import com.slate.api.ApiService;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import org.apache.commons.lang3.RandomStringUtils;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;

public class EndToEndTests {

	public static String URL = "https://todoist.com/api/v7/sync";
	public static String token = "c7179ae59e4f823220c6980c8a0deeccdcc6761d";
	public static String prjtName = "SlateStudio Project - ";
	AndroidDriver<AndroidElement> driver;
	ApiService api;
	LoginScreen loginScr;
	HomeScreen homeScr;
	ProjectScreen prjScr;

	@BeforeSuite
	public void setupSuite() {
		System.out.println("Before Suite");
		api = new ApiService(URL, getToken());
		AppiumDriver.setUp(getDeviceName(), getAppiumServerHost(), getAppiumServerPort());
		this.driver = AppiumDriver.getDriver();
		prjtName += getRandomString();
		loginScr = new LoginScreen(driver);
		homeScr = new HomeScreen(driver);
		prjScr = new ProjectScreen(driver);

	}

	@BeforeMethod
	public void setup() {
		System.out.println("Before method");
		driver.launchApp();
		login();
	}

	@AfterMethod
	public void tearDown() {
		System.out.println("After method\n");
	}

	@Test
	public void createProject() {
		try {
			System.out.println("Create Project - Test");
			api.createProject(prjtName);
			homeScr.openProject(prjtName);
			Assert.assertTrue(prjScr.getProjectTitle().equals(prjtName));
			System.out.println("Test: Create Project - pass");
		} catch (Exception e) {
			System.out.println("Test: Create Project - fail");
			System.out.println(e.getMessage());

		}
	}

	@Test
	public void createTask() {

		try {
			System.out.println("Create Task - Test");
			String name = "SlateStudio Task - " + getRandomString();
			System.out.println(name);
			homeScr.openProject(prjtName);
			prjScr.createTask(name);
			waitForSecs(3);
			String taskId = api.getTaskId(name);
			Assert.assertNotNull(taskId);
			System.out.println("Test: Create Task via mobile phone - pass");
		} catch (Exception e) {
			System.out.println("Test: Create Task via mobile phone - fail");
			System.out.println(e.getMessage());
		}

	}

	@Test
	public void reopenTask() {
		System.out.println("Reopen Task - Test");
		String name = "SlateStudio Task - " + getRandomString();
		try {
			homeScr.openProject(prjtName);
			prjScr.createTask(name);
			Assert.assertTrue(prjScr.isTaskDisplayed(name));
			waitForSecs(3);
			String taskId = api.getTaskId(name);
			Assert.assertNotNull(taskId);
			prjScr.completeTask(name);
			waitForSecs(10);
			String[] taskIds = new String[] { taskId };
			api.uncompleteTasks(taskIds);
			waitForSecs(10);
			prjScr.waitForElement(name);
			Assert.assertTrue(prjScr.isTaskDisplayed(name));
			System.out.println("Test: Reopen Task - pass");
		} catch (Exception e) {
			System.out.println("Test: Reopen Task - fail");
			System.out.println(e.getMessage());
		}

	}

	public void waitForSecs(long secs) {
		try {
			Thread.sleep(secs * 1000);
		} catch (Exception e) {
			System.out.println("Fail to wait!");
			e.printStackTrace();
		}
	}

	@AfterSuite
	public void tearDownSuite() {
		this.driver.quit();
	}

	private void login() {
		String[] credentails = getCredentials();
		loginScr.login(credentails[0], credentails[1]);
		Assert.assertTrue(prjScr.isLoggedIn());
	}

	private String getDeviceName() {
		return System.getProperty("emulator", "emulator-5554");

	}

	private String getAppiumServerPort() {
		return System.getProperty("server.port", "4723");
	}

	private String getAppiumServerHost() {
		System.out.println(System.getProperty("server.ip"));
		return System.getProperty("server.ip", "127.0.0.1");
	}
	
	private String getRandomString() {
		return RandomStringUtils.randomAlphabetic(5);
	}

	private String getToken() {
		return System.getProperty("apiToken", "c7179ae59e4f823220c6980c8a0deeccdcc6761d");
	}

	private String[] getCredentials() {
		String userEmail = System.getProperty("email");
		String userPassword = System.getProperty("pwd");
		if (userEmail == null || userPassword == null) {
			return new String[] { "praveenmukilan@gmail.com", "Letmein01" };
		} else
			return new String[] { userEmail, userPassword };
	}

//	public static void main(String args[]) {
//		EndToEndTests test = new EndToEndTests();
//		test.setupSuite();
//		test.setup();
//		test.createProject();
//		test.tearDown();
//		test.setup();
//		test.createTask();
//		test.tearDown();
//		test.setup();
//		test.reopenTask();
//		test.tearDown();
//	}

}
