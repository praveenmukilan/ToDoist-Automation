package com.slate.android.ui;

import java.util.ArrayList;

import org.openqa.selenium.support.PageFactory;

import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class HomeScreen extends BaseScreen {

	private AndroidDriver<AndroidElement> driver;

	@AndroidFindBy(accessibility = "Change the current view")
	public AndroidElement hamburgerMenu;

	@AndroidFindBy(id = "com.todoist:id/fab")
	private AndroidElement plusBtn;

	@AndroidFindBy(xpath = "//android.widget.TextView[@text='Projects']")
	private AndroidElement projectsMenuOption;

	@AndroidFindBy(id = "com.todoist:id/name")
	private ArrayList<AndroidElement> menuList;

	@AndroidFindBy(xpath = "//android.widget.TextView[@text='Add project']")
	public AndroidElement addProject;
	
	
	@AndroidFindBy(xpath = "//android.widget.TextView[@text='Settings']")
	public AndroidElement settingsOption;
	
	@AndroidFindBy(xpath = "//android.widget.TextView[@text='Log out']")
	public AndroidElement logoutOption;

	@AndroidFindBy(id = "android:id/button1")
	public AndroidElement logoutYesBtn;
	
	@AndroidFindBy(xpath = "//android.widget.TextView[@text='Support']")
	public AndroidElement supportOption;

	public HomeScreen(AndroidDriver<AndroidElement> drvr) {
		super(drvr);
		this.driver = drvr;
		PageFactory.initElements(new AppiumFieldDecorator(this.driver), this);
	}

	public void selectProject(String name) {
		getProjectElement(name).click();
	}
	
	public void openProject(String name) {
		hamburgerMenu.click();
		clickProjectsOption();
		selectProject(name);
	}
	

	public AndroidElement getProjectElement(String name) {
		return getElementFromList(menuList, name);
	}

	public void clickProjectsOption() {
		projectsMenuOption.click();
		if (!addProject.isDisplayed()) {
			projectsMenuOption.click();
		}
	}

	public boolean isProjectPresent(String projectName) {
		hamburgerMenu.click();
		if (getProjectElement(projectName) != null)
			return true;
		else
			return false;
	}
	
	public void logout() {
		hamburgerMenu.click();
		settingsOption.click();
		waitForElement(settingsOption);
		swipeUp();
		logoutOption.click();
		logoutYesBtn.click();
	}
}
