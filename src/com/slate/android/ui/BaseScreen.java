package com.slate.android.ui;

import java.time.Duration;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

import com.slate.android.AppiumDriver;

import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.functions.ExpectedCondition;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.touch.offset.PointOption;

public class BaseScreen {
	private AndroidDriver<AndroidElement> driver;
	
	public BaseScreen(AndroidDriver<AndroidElement> drvr) {
		this.driver = drvr;
		PageFactory.initElements(new AppiumFieldDecorator(this.driver), this);
	}
	
	public AndroidElement getElementFromList(ArrayList<AndroidElement> list, String elementName) {
		return list.stream().filter(x -> x.getText().equalsIgnoreCase(elementName)).findFirst().get();
	}
	
	public void swipeUp() {
		
		Dimension size=driver.manage().window().getSize();
		int width=(int)(size.width/2);
		int startY=(int)(size.getHeight() * 0.70);
		int endY=(int)(size.getHeight() * 0.20);
		int duration=2000;
		TouchAction action = new TouchAction(driver);
		action.press(PointOption.point(width, startY))
			.waitAction().moveTo(PointOption.point(width, endY)).release().perform();
		
	}
	
	public void waitForElement(AndroidElement e) {
		Wait wait = new FluentWait(driver)
							.withTimeout(Duration.ofSeconds(20))
							.pollingEvery(Duration.ofSeconds(1))
							.ignoring(NoSuchElementException.class)
					        .ignoring(TimeoutException.class);
		wait.until(ExpectedConditions.visibilityOf(e));
	}
	
	public void waitForElement(String text) {
		Wait wait = new FluentWait(driver)
							.withTimeout(Duration.ofSeconds(20))
							.pollingEvery(Duration.ofSeconds(1))
							.ignoring(NoSuchElementException.class)
					        .ignoring(TimeoutException.class);
		wait.until(ExpectedConditions.visibilityOf(driver.findElementByXPath("//android.widget.TextView[@text=\""+text+"\"]")));
	}
	
}
