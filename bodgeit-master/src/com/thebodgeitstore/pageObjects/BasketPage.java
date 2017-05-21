package com.thebodgeitstore.pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class BasketPage {
	
	private static WebElement element = null;
	
	public static WebElement link_basic(WebDriver driver){
		 
        element = driver.findElement(By.linkText("Basic Widget"));

        return element;

     }
	
	public static WebElement button_plus(WebDriver driver){
		 
        element = driver.findElement(By.xpath("/html/body/center/table/tbody/tr[3]/td/table/tbody/tr/td[2]/form/center/table/tbody/tr[2]/td[4]/a[2]/img"));

        return element;

     }
	public static WebElement button_minus(WebDriver driver){
		 
        element = driver.findElement(By.xpath("/html/body/center/table/tbody/tr[3]/td/table/tbody/tr/td[2]/form/center/table/tbody/tr[2]/td[4]/a[1]/img"));

        return element;

     }
	public static WebElement button_submit(WebDriver driver){
		 
        element = driver.findElement(By.id("submit"));

        return element;

     }
	
	
	

}
