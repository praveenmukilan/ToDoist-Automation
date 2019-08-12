package com.slate.android.ui;

import java.util.ArrayList;

import org.openqa.selenium.support.PageFactory;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class BaseScreen {
	private AndroidDriver<AndroidElement> driver;
	
	public BaseScreen(AndroidDriver<AndroidElement> drvr) {
		this.driver = drvr;
		PageFactory.initElements(new AppiumFieldDecorator(this.driver), this);
	}
	
	public AndroidElement getElementFromList(ArrayList<AndroidElement> list, String elementName) {
		return list.stream().filter(x -> x.getText().equalsIgnoreCase(elementName)).findFirst().get();
	}
	
}
