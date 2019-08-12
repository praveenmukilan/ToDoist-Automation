package com.slate.android;

import java.io.IOException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecuteResultHandler;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteException;
import org.openqa.selenium.remote.DesiredCapabilities;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.remote.MobileCapabilityType;

import com.slate.android.ui.HomeScreen;
import com.slate.android.ui.LoginScreen;
import com.slate.android.ui.ProjectScreen;

public class AppiumDriver {
	public static AndroidDriver<AndroidElement> driver;
	static DefaultExecuteResultHandler resultHandler = new DefaultExecuteResultHandler();
	static DefaultExecutor executor = new DefaultExecutor();

	public static void setUp() {
		try {
			DesiredCapabilities capabilities = new DesiredCapabilities();
			capabilities.setCapability("platformName", "Android");
			capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, "UiAutomator2");
			capabilities.setCapability("appPackage", "com.todoist");
			capabilities.setCapability("app", "/Users/praveenms/Downloads/Todoist_v12.8_apkpure.com.apk");
			capabilities.setCapability("deviceName", "emulator-5554");
			capabilities.setCapability("autoGrantPermissions", true);
			capabilities.setCapability("appWaitActivity", "com.todoist.activity.WelcomeActivity");
			driver = new AndroidDriver<AndroidElement>(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
			driver.manage().timeouts().implicitlyWait(80, TimeUnit.SECONDS);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static AndroidDriver<AndroidElement> getDriver() {
		return driver;
	}

	public static void main(String args[]) {

		try {
			launchEmulator(" @Pixel_3_API_28");
			Thread.sleep(20000);
			setUp();
			test01();
			tearDown();
		} catch (Exception e) {
			System.out.println(e);
		}

	}

	public static void tearDown() throws Exception {
		driver.quit();
	}

	public static void test01() {
		try {
			System.out.println("Running test - test01");
			String prjtName = "mobile prjt";
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

	public static void launchEmulator(String emulatorName) throws ExecuteException, IOException {
		CommandLine launchEmul = new CommandLine("/Users/praveenms/Library/Android/sdk/emulator/emulator");
		launchEmul.addArgument(emulatorName);
		executor.setExitValue(1);
		executor.execute(launchEmul, resultHandler);
	}

}
