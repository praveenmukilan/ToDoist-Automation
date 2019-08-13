package com.slate.android;

import java.io.File;
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

/**
 * This class handles the AppiumDriver initialization, setup of Appium related
 * tasks. Capability to launch Appium Server & Emulators programmatically can be
 * added here.
 * 
 * @author praveenms
 *
 */
public class AppiumDriver {
	public static AndroidDriver<AndroidElement> driver;
	static DefaultExecuteResultHandler resultHandler = new DefaultExecuteResultHandler();
	static DefaultExecutor executor = new DefaultExecutor();

	/**
	 * This method sets the AndroidDriver for the tests with the given capabilities
	 * 
	 * @param deviceName
	 * @param appiumServer
	 * @param appiumPort
	 * @param app
	 */
	public static void setUp(String deviceName, String appiumServer, String appiumPort, File app) {
		try {
			DesiredCapabilities capabilities = new DesiredCapabilities();
			capabilities.setCapability("platformName", "Android");
			capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, "UiAutomator2");
			capabilities.setCapability("appPackage", "com.todoist");
			capabilities.setCapability("app", app);
			capabilities.setCapability("deviceName", deviceName);
			capabilities.setCapability("autoGrantPermissions", true);
			capabilities.setCapability("appWaitActivity", "com.todoist.activity.WelcomeActivity");
			driver = new AndroidDriver<AndroidElement>(new URL("http://" + appiumServer + ":" + appiumPort + "/wd/hub"),
					capabilities);
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		} catch (Exception e) {
			System.out.println("Please check whether the Appium Server is started!");
			e.printStackTrace();
		}
	}

	public static AndroidDriver<AndroidElement> getDriver() {
		return driver;
	}

	public static void tearDown() throws Exception {
		driver.quit();
	}

	public static void launchEmulator(String emulatorName) throws ExecuteException, IOException {
		CommandLine launchEmul = new CommandLine("/Users/praveenms/Library/Android/sdk/emulator/emulator");
		launchEmul.addArgument(emulatorName);
		executor.setExitValue(1);
		executor.execute(launchEmul, resultHandler);
	}

}
