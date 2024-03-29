package com.slate.android.ui;

import java.util.ArrayList;
import org.openqa.selenium.support.PageFactory;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

/***
 * HomeScreen holds the locators and functionalities on the HomeScreen of the
 * app.
 * 
 * @author praveenms
 *
 */
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
	private AndroidElement addProject;

	@AndroidFindBy(xpath = "//android.widget.TextView[@text='Settings']")
	private AndroidElement settingsOption;

	@AndroidFindBy(xpath = "//android.widget.TextView[@text='Log out']")
	private AndroidElement logoutOption;

	@AndroidFindBy(id = "android:id/button1")
	private AndroidElement logoutYesBtn;

	@AndroidFindBy(xpath = "//android.widget.TextView[@text='Support']")
	private AndroidElement supportOption;

	public HomeScreen(AndroidDriver<AndroidElement> drvr) {
		super(drvr);
		this.driver = drvr;
		PageFactory.initElements(new AppiumFieldDecorator(this.driver), this);
	}

	protected void selectProject(String name) {
		getProjectElement(name).click();
	}

	public void openProject(String name) {
		System.out.println("\t> opening project: " + name);
		hamburgerMenu.click();
		clickProjectsOption();
		waitForElement(name);
		selectProject(name);
	}

	protected AndroidElement getProjectElement(String name) {
		return getElementFromList(menuList, name);
	}

	protected void clickProjectsOption() {
		projectsMenuOption.click();
		if (isElementPresent(addProject))
			return;
		else
			projectsMenuOption.click();
	}

	protected boolean isProjectPresent(String projectName) {
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
