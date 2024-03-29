package com.slate.android.ui;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.support.PageFactory;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

/**
 * LoginScreen pageobject holds the login functionalities and locators in the
 * Welcome, Login screens
 * 
 * @author praveenms
 *
 */
public class LoginScreen extends BaseScreen {

	private AndroidDriver<AndroidElement> driver;

	@AndroidFindBy(id = "com.todoist:id/btn_welcome_continue_with_email")
	private AndroidElement welcomeContinueEmail;

	@AndroidFindBy(id = "com.todoist:id/email_exists_input")
	private AndroidElement emailExistsInput;

	@AndroidFindBy(id = "com.todoist:id/btn_continue_with_email")
	private AndroidElement continueWithEmailBtn;

	@AndroidFindBy(id = "com.todoist:id/log_in_email")
	private AndroidElement userEmail;

	@AndroidFindBy(id = "com.todoist:id/log_in_password")
	private AndroidElement pwdElement;

	@AndroidFindBy(id = "com.todoist:id/btn_log_in")
	private AndroidElement loginBtn;

	@AndroidFindBy(id = "com.todoist:id/alertTitle")
	private AndroidElement alertTitleNewApp;

	@AndroidFindBy(id = "android:id/button2")
	private AndroidElement remindMeLaterBtn;

	@AndroidFindBy(id = "android:id/button1")
	private AndroidElement updateNowBtn;

	public LoginScreen(AndroidDriver<AndroidElement> drvr) {
		super(drvr);
		this.driver = drvr;
		PageFactory.initElements(new AppiumFieldDecorator(this.driver), this);
	}

	public void login(String email, String password) {
		try {
			welcomeContinueEmail.click();
			emailExistsInput.sendKeys(email);
			continueWithEmailBtn.click();
			pwdElement.sendKeys(password);
			loginBtn.click();
			handleNewVersionRequired();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void handleNewVersionRequired() {
		try {
			if (alertTitleNewApp.isDisplayed()) {
				remindMeLaterBtn.click();
			}
		} catch (NoSuchElementException e) {
			System.out.println("\t> Alert is not present!");
		}
	}

}
