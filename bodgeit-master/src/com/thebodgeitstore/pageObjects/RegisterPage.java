package com.thebodgeitstore.pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class RegisterPage {
	private static WebElement element = null;
	
	public static WebElement lnk_Username(WebDriver driver) {
		element = driver.findElement(By.name("username"));
		return element;
	}
	
	public static WebElement lnk_Password1(WebDriver driver) {
		element = driver.findElement(By.name("password1"));
		return element;
	}
	
	public static WebElement lnk_Password2(WebDriver driver) {
		element = driver.findElement(By.name("password2"));
		return element;
	}
	
	public static WebElement lnk_Submit(WebDriver driver) {
		element = driver.findElement(By.id("submit"));
		return element;
	}
}
