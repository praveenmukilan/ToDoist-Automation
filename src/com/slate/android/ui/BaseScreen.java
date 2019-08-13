package com.slate.android.ui;

import java.time.Duration;
import java.util.ArrayList;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.touch.offset.PointOption;

/**
 * BaseScreen pageobject from which other pageobjects inherit from. 
 * This class holds the functionalities that are common to all the pageobjects.
 * @author praveenms
 **/
public class BaseScreen {
	private AndroidDriver<AndroidElement> driver;
	
	public BaseScreen(AndroidDriver<AndroidElement> drvr) {
		this.driver = drvr;
		PageFactory.initElements(new AppiumFieldDecorator(this.driver), this);
	}
	
	public AndroidElement getElementFromList(ArrayList<AndroidElement> list, String elementName) {
		return list.stream().filter(x -> x.getText().equalsIgnoreCase(elementName)).findFirst().get();
	}
	
	
	@SuppressWarnings("rawtypes")
	public void swipeUp() {		
		Dimension size=driver.manage().window().getSize();
		int width=(int)(size.width/2);
		int startY=(int)(size.getHeight() * 0.70);
		int endY=(int)(size.getHeight() * 0.20);
		TouchAction action = new TouchAction(driver);
		action.press(PointOption.point(width, startY))
			.waitAction().moveTo(PointOption.point(width, endY)).release().perform();
		
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void waitForElement(AndroidElement e) {
		Wait wait = new FluentWait(driver)
							.withTimeout(Duration.ofSeconds(20))
							.pollingEvery(Duration.ofSeconds(1))
							.ignoring(NoSuchElementException.class)
					        .ignoring(TimeoutException.class);
		wait.until(ExpectedConditions.visibilityOf(e));
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void waitForElement(String text) {
		Wait wait = new FluentWait(driver)
							.withTimeout(Duration.ofSeconds(20))
							.pollingEvery(Duration.ofSeconds(1))
							.ignoring(NoSuchElementException.class)
					        .ignoring(TimeoutException.class);
		wait.until(ExpectedConditions.visibilityOf(driver.findElementByXPath("//android.widget.TextView[@text=\""+text+"\"]")));
	}
	
	public boolean isElementPresent(AndroidElement e) {
		try {
			return e.isDisplayed();
		} catch(Exception d) {
			return false;
		}
	}
	
}
