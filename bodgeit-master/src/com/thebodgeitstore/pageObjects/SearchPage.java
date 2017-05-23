package com.thebodgeitstore.pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class SearchPage {
	public static WebElement txt_Query(WebDriver driver) {
		return driver.findElement(By.name("q"));
	}
	public static WebElement btn_Submit(WebDriver driver) {
		return driver.findElement(By.name("query"));
	}
}
