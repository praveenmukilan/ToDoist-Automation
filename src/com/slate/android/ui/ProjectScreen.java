package com.slate.android.ui;

import java.util.ArrayList;

import org.openqa.selenium.By;
import org.openqa.selenium.support.PageFactory;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class ProjectScreen extends BaseScreen {

	public AndroidDriver<AndroidElement> driver;

	@AndroidFindBy(id = "com.todoist:id/toolbar")
	public AndroidElement viewGroup;

	@AndroidFindBy(id = "com.todoist:id/fab")
	private AndroidElement plusBtn;

	@AndroidFindBy(id = "android:id/message")
	private AndroidElement taskTitleMessage;

	@AndroidFindBy(id = "android:id/button1")
	private AndroidElement createTaskBtn;

	@AndroidFindBy(id = "com.todoist:id/action_mode_close_button")
	private AndroidElement backBtn;

	@AndroidFindBy(id = "com.todoist:id/text")
	private ArrayList<AndroidElement> tasksList;

	@AndroidFindBy(id = "com.todoist:id/menu_item_complete")
	private AndroidElement completeTaskBtn;

	@AndroidFindBy(id = "com.todoist:id/snackbar_text")
	private AndroidElement completeMsg;


	public ProjectScreen(AndroidDriver<AndroidElement> drvr) {
		super(drvr);
		this.driver = drvr;
		PageFactory.initElements(new AppiumFieldDecorator(this.driver), this);
	}

	public String getProjectTitle() {
		return viewGroup.findElement(By.xpath("//android.widget.TextView")).getText();
	}

	private AndroidElement getProjectElement() {
		return (AndroidElement) viewGroup.findElement(By.xpath("//android.widget.TextView"));
	}

	public boolean isProjectScreen(String prjtName) {
		return getProjectElement().getText().contentEquals(prjtName);
	}

	public void createTask(String taskName) {
		plusBtn.click();
		taskTitleMessage.sendKeys(taskName);
		createTaskBtn.click();
		backBtn.click();
	}

	private AndroidElement getTask(String taskName) {
		return getElementFromList(tasksList, taskName);
	}

	public boolean isTaskDisplayed(String taskName) {
		return getTask(taskName).getText().equals(taskName);

	}

	public void selectTask(String taskName) {
		getTask(taskName).click();
	}

	public void completeTask(String taskName) {
		selectTask(taskName);
		completeTaskBtn.click();
	}

	public boolean isTaskCompleted() {
		return completeMsg.getText().equals("Completed.");
	}

}
